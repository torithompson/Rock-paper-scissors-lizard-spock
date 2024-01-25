package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.Random;

public class ClassicGame extends AppCompatActivity {
    private int playerWins = 0;
    private int computerWins = 0;
    private int roundsPlayed = 0;

    private TextView message;
    private TextView rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classicgame);

        final ImageView rock = findViewById(R.id.classicRock);
        final ImageView paper = findViewById(R.id.classicPaper);
        final ImageView scissors = findViewById(R.id.classicScissors);

        rock.setOnClickListener(v -> playGame("rock"));
        paper.setOnClickListener(v -> playGame("paper"));
        scissors.setOnClickListener(v -> playGame("scissors"));

        message = findViewById(R.id.message);
        rounds = findViewById(R.id.round);
    }

    private void playGame(String userChoice) {
        startCountdown();

        String[] choices = {"rock", "paper", "scissors"};
        Random random = new Random();
        int computerIndex = random.nextInt(3);
        String computerChoice = choices[computerIndex];

        updateComputerImage(computerChoice);

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
    private void startCountdown() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {           }

            @Override
            public void onFinish() {
            }
        }.start();
    }
    private void displayMessage(String msg) {
        message.setText(msg);
    }
}