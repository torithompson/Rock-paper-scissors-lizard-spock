package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class represents the LizardSpock game activity.
 * It extends AppCompatActivity to provide compatibility features and basic app infrastructure.
 * It handles user interaction, updates the UI, and manages the game logic.
 */
public class LizardSpockGame extends AppCompatActivity {
    private int playerWins = 0;
    private int computerWins = 0;
    private int roundsPlayed = 0;
    private ArrayList<Score> topScores = new ArrayList<>();
    private String playerName;
    private TextView message;
    private TextView rounds;

    /**
     * This method is called when the activity is starting.
     * It is where initialization happens.
     * @param savedInstanceState If the activity is being re-initialized
     *      after previously being shut down then this Bundle contains the data it most
     *      recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lizard_spock_game);

        // Get the player's name from the intent
        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");

        // Set up the game buttons
        final ImageView rock = findViewById(R.id.rock);
        final ImageView paper = findViewById(R.id.paper);
        final ImageView scissors = findViewById(R.id.scissors);
        final ImageView lizard = findViewById(R.id.lizard);
        final ImageView spock = findViewById(R.id.spock);

        // Set up the click listeners for the game buttons
        rock.setOnClickListener(v -> playGame("rock"));
        paper.setOnClickListener(v -> playGame("paper"));
        scissors.setOnClickListener(v -> playGame("scissors"));
        lizard.setOnClickListener(v -> playGame("lizard"));
        spock.setOnClickListener(v -> playGame("spock"));

        // Load the top scores
        topScores = loadTopScores(this);

        // Get the views for displaying game messages and rounds
        message = findViewById(R.id.messageTxt);
        rounds = findViewById(R.id.rounds);
    }

    /**
     * This method loads the top scores from SharedPreferences.
     * @param context The application context
     * @return An ArrayList of the top scores
     */
    public static ArrayList<Score> loadTopScores(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("scores", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String scoresJson = sharedPreferences.getString("topScores", null);
        ArrayList<Score> topScores = new ArrayList<>();
        if (scoresJson != null) {
            try {
                Type type = new TypeToken<ArrayList<Score>>() {}.getType();
                topScores = gson.fromJson(scoresJson, type);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        topScores.sort(Collections.reverseOrder());
        return topScores;
    }

    /**
     * This method updates the top scores list and saves it to SharedPreferences.
     * @param newScore The new score to add to the top scores list
     */
    private void updateTopScores(Score newScore) {
        SharedPreferences sharedPreferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
        int idCounter = sharedPreferences.getInt("idCounter", 0);
        newScore.setId(idCounter);
        topScores.add(newScore);
        Collections.sort(topScores);
        if (topScores.size() > 5) {
            topScores.remove(topScores.size() - 1);
        }
        saveTopScores(this, topScores);
    }

    /**
     * This method saves the top scores to SharedPreferences.
     * @param context The application context
     * @param topScores The top scores to save
     */
    public static void saveTopScores(Context context, ArrayList<Score> topScores) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String scoresJson = gson.toJson(topScores);
        editor.putString("topScores", scoresJson);
        int idCounter = sharedPreferences.getInt("idCounter", 0);
        editor.putInt("idCounter", idCounter + 1);
        editor.apply();
    }

    /**
     * This method plays a round of the game with the user's choice.
     * @param userChoice The user's choice ("rock", "paper", "scissors", "lizard", or "spock")
     */
    private void playGame(String userChoice) {
        startCountdown();

        String[] choices = {"rock", "paper", "scissors", "lizard", "spock"};
        Random random = new Random();
        int computerIndex = random.nextInt(5);
        String computerChoice = choices[computerIndex];

        updateComputerImage(computerChoice);

        if (userChoice.equals(computerChoice)) {
            displayMessage("It's a tie!");
        } else if (
                (userChoice.equals("rock") && computerChoice.equals("scissors")) ||
                        (userChoice.equals("paper") && computerChoice.equals("rock")) ||
                        (userChoice.equals("scissors") && computerChoice.equals("paper")) ||
                        (userChoice.equals("rock") && computerChoice.equals("lizard")) ||
                        (userChoice.equals("lizard") && computerChoice.equals("spock")) ||
                        (userChoice.equals("spock") && computerChoice.equals("scissors")) ||
                        (userChoice.equals("scissors") && computerChoice.equals("lizard")) ||
                        (userChoice.equals("lizard") && computerChoice.equals("paper")) ||
                        (userChoice.equals("paper") && computerChoice.equals("spock")) ||
                        (userChoice.equals("spock") && computerChoice.equals("rock"))
        ) {
            playerWins++;
            displayMessage("You win!");
        } else {
            computerWins++;
            displayMessage("Computer wins!");
        }

        if (!userChoice.equals(computerChoice)) {
            roundsPlayed++;
            rounds.setText(MessageFormat.format("Round {0} of 10", roundsPlayed));
        }


        if (roundsPlayed == 10) {
            updateTopScores(new Score(playerName, playerWins));
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("playerName", playerName);
            intent.putExtra("totalPlayerWins", playerWins);
            intent.putExtra("totalComputerWins", computerWins);
            intent.putExtra("source", "LizardSpockGame");
            startActivity(intent);

            finish();

        }
    }

    /**
     * This method updates the computer's image based on its choice.
     * @param computerChoice The computer's choice ("rock", "paper", "scissors", "lizard", or "spock")
     */
    private void updateComputerImage(String computerChoice) {
        ImageView computerImage = findViewById(R.id.compChoiceImg);
        switch (computerChoice) {
            case "rock":
                computerImage.setImageResource(R.drawable.rock);
                break;
            case "paper":
                computerImage.setImageResource(R.drawable.paper);
                break;
            case "scissors":
                computerImage.setImageResource(R.drawable.scissors);
                break;
            case "lizard":
                computerImage.setImageResource(R.drawable.lizard);
                break;
            case "spock":
                computerImage.setImageResource(R.drawable.spock);
                break;
        }
    }

    /**
     * This method starts a countdown timer.
     */
    private void startCountdown() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    /**
     * This method displays a message to the user.
     * @param msg The message to display
     */
    private void displayMessage(String msg) {
        message.setText(msg);
    }
}