package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * ResultsActivity is an activity that displays the results of the game.
 * It shows the total wins for the player and the computer, and the top scores.
 * It also allows the user to play again or delete all scores.
 */
public class ResultsActivity extends AppCompatActivity {
    // Database instance
    ScoreDB db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Get views
        TextView resultsFrom = findViewById(R.id.resultsFrom);
        Intent intent = getIntent();

        // Get data from intent
        String playerName = intent.getStringExtra("playerName");
        int totalPlayerWins = intent.getIntExtra("totalPlayerWins", 0);
        int totalComputerWins = intent.getIntExtra("totalComputerWins", 0);
        String source = intent.getStringExtra("source");

        // Get top scores from database or SharedPreferences depending on the source name
        ArrayList<Score> topScores;
        if (source != null && source.equals("ClassicGame")) {
            db = ScoreDB.getInstance(this);
            topScores = (ArrayList<Score>) db.scoreDAO().findAllScores();
            resultsFrom.setText(R.string.ResultsDB);
        } else {
            topScores = LizardSpockGame.loadTopScores(this);
            resultsFrom.setText(R.string.ResultsSP);
        }

        // Sort top scores and display them
        topScores.sort((o1, o2) -> o2.getScore() - o1.getScore());
        StringBuilder topScoresText = new StringBuilder();
        for (int i = 0; i < topScores.size(); i++) {
            Score score = topScores.get(i);
            topScoresText.append(i + 1).append(". ").append(score.getPlayerName()).append(": ").append(score.getScore()).append("\n");
        }
        TextView topScoresTextView = findViewById(R.id.topScores);
        topScoresTextView.setText(topScoresText.toString());

        // Display total wins
        TextView winsTextView = findViewById(R.id.wins);
        winsTextView.setText("Total Player Wins: " + totalPlayerWins + "\n Total Computer Wins: " + totalComputerWins +
                "\n\n" + determineWinner(totalPlayerWins, totalComputerWins));

        // Set up delete all button
        Button deleteAll = findViewById(R.id.deleteAll);
        deleteAll.setOnClickListener(v -> {
            if (source != null && source.equals("ClassicGame")) {
                db.scoreDAO().deleteAll();
                topScoresTextView.setText("");
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("scores", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                topScoresTextView.setText("");
            }
        });

        // Set up play again button
        Button playAgainButton = findViewById(R.id.homeBtn);
        playAgainButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }

    /**
     * Determines the winner based on the total wins of the player and the computer.
     * @param playerWins the total wins of the player
     * @param computerWins the total wins of the computer
     * @return a string indicating the winner
     */
    private String determineWinner(int playerWins, int computerWins) {
        if (playerWins > computerWins) {
            return "You're the winner!";
        } else if (computerWins > playerWins) {
            return "Computer won \uD83E\uDD16";
        } else {
            return "It's a tie!";
        }
    }
}