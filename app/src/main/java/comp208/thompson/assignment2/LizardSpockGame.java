package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.Random;

public class LizardSpockGame extends AppCompatActivity {
    private int playerWins = 0;
    private int computerWins = 0;
    private int roundsPlayed = 0;

    private TextView message;
    private TextView rounds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lizard_spock_game);

        final ImageView rock = findViewById(R.id.rock);
        final ImageView paper = findViewById(R.id.paper);
        final ImageView scissors = findViewById(R.id.scissors);
        final ImageView lizard = findViewById(R.id.lizard);
        final ImageView spock = findViewById(R.id.spock);

        rock.setOnClickListener(v -> playGame("rock"));
        paper.setOnClickListener(v -> playGame("paper"));
        scissors.setOnClickListener(v -> playGame("scissors"));
        lizard.setOnClickListener(v -> playGame("lizard"));
        spock.setOnClickListener(v -> playGame("spock"));

        message = findViewById(R.id.messageTxt);
        rounds = findViewById(R.id.rounds);
    }
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
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("totalPlayerWins", playerWins);
            intent.putExtra("totalComputerWins", computerWins);
            startActivity(intent);

            finish();

        }
    }

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
    private void displayMessage(String msg) {
        message.setText(msg);
    }
}