package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.MessageFormat;
import java.util.Random;

/**
 * This class represents the ClassicGame activity.
 * It extends AppCompatActivity to provide compatibility features and basic app infrastructure.
 * It handles user interaction, updates the UI, and manages the game logic.
 */
public class ClassicGame extends AppCompatActivity {
    // Variables to keep track of the player's and computer's wins and the number of rounds played
    private int playerWins = 0;
    private int computerWins = 0;
    private int roundsPlayed = 0;

    // TextViews to display messages and the number of rounds
    private TextView message;
    private TextView rounds;

    // The player's name
    private String playerName;

    // Database instance
    ScoreDB db;

    /**
     * Called when the activity is starting.
     * This is where most initialization happens.
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classicgame);

        // Set up the game buttons and their click listeners
        final ImageView rock = findViewById(R.id.classicRock);
        final ImageView paper = findViewById(R.id.classicPaper);
        final ImageView scissors = findViewById(R.id.classicScissors);

        rock.setOnClickListener(v -> playGame("rock"));
        paper.setOnClickListener(v -> playGame("paper"));
        scissors.setOnClickListener(v -> playGame("scissors"));

        // Initialize the message and rounds TextViews
        message = findViewById(R.id.message);
        rounds = findViewById(R.id.round);

        // Get the database instance
        db = ScoreDB.getInstance(this);

        // Get the player's name from the intent
        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
    }

    /**
     * Plays a round of the game with the user's choice.
     * @param userChoice The user's choice ("rock", "paper", or "scissors")
     */
    private void playGame(String userChoice) {
        // Start the countdown timer
        startCountdown();

        // The computer makes a random choice
        String[] choices = {"rock", "paper", "scissors"};
        Random random = new Random();
        int computerIndex = random.nextInt(3);
        String computerChoice = choices[computerIndex];

        // Update the computer's image based on its choice
        updateComputerImage(computerChoice);

        // Determine the winner and update the score
        if (userChoice.equals(computerChoice)) {
            displayMessage("It's a tie!");
        } else if (
                (userChoice.equals("rock") && computerChoice.equals("scissors")) ||
                        (userChoice.equals("paper") && computerChoice.equals("rock")) ||
                        (userChoice.equals("scissors") && computerChoice.equals("paper"))
        ) {
            playerWins++;
            displayMessage("You win!");
        } else {
            computerWins++;
            displayMessage("Computer wins!");
        }

        // If the user's choice is not the same as the computer's choice, increment the number of rounds played
        if (!userChoice.equals(computerChoice)) {
            roundsPlayed++;
            rounds.setText(MessageFormat.format("Round {0} of 10", roundsPlayed));
        }

        // If 10 rounds have been played, update the top scores and start the ResultsActivity
        if (roundsPlayed == 10) {
            db.scoreDAO().insertOrUpdate(new Score(playerName, playerWins));
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("playerName", playerName);
            intent.putExtra("totalPlayerWins", playerWins);
            intent.putExtra("totalComputerWins", computerWins);
            intent.putExtra("source", "ClassicGame");
            startActivity(intent);

            // Finish the ClassicGame activity
            finish();
        }
    }

    /**
     * Updates the computer's image based on its choice.
     * @param computerChoice The computer's choice ("rock", "paper", or "scissors")
     */
    private void updateComputerImage(String computerChoice) {
        ImageView computerImage = findViewById(R.id.classicCompImg);
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
        }
    }

    /**
     * Starts a countdown timer.
     */
    private void startCountdown() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {           }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    /**
     * Displays a message to the user.
     * @param msg The message to display
     */
    private void displayMessage(String msg) {
        message.setText(msg);
    }
}