package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        int totalPlayerWins = intent.getIntExtra("totalPlayerWins", 0);
        int totalComputerWins = intent.getIntExtra("totalComputerWins", 0);

        TextView winsTextView = findViewById(R.id.wins);
        winsTextView.setText("Total Player Wins: " + totalPlayerWins + "\n Total Computer Wins: " + totalComputerWins +
                "\n\n" + determineWinner(totalPlayerWins, totalComputerWins));

        Button playAgainButton = findViewById(R.id.homeBtn);
        playAgainButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }
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