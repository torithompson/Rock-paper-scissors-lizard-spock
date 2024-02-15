package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * MainActivity is the main activity of the application.
 * It allows the user to enter their name and choose between two games: LizardSpock and ClassicGame.
 */
public class MainActivity extends AppCompatActivity {
    // The player's name
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get views
        final ImageView lizardSpockGame = findViewById(R.id.lizardSpockGame);
        final ImageView classicGame = findViewById(R.id.classicGame);

        // Show the name input dialog
        showNameInputDialog();

        // Set up click listener for the LizardSpock game
        lizardSpockGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the LizardSpock activity
                Intent intent = new Intent(MainActivity.this, LizardSpock.class);
                intent.putExtra("playerName", playerName);
                startActivity(intent);
            }
        });

        // Set up click listener for the ClassicGame
        classicGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ClassicGame activity
                Intent intent = new Intent(MainActivity.this, ClassicGame.class);
                intent.putExtra("playerName", playerName);
                startActivity(intent);
            }
        });
    }

    /**
     * Shows a dialog that prompts the user to enter their name.
     * If the user doesn't enter a name, a default name "Player123" is used.
     */
    private void showNameInputDialog() {
        final EditText input = new EditText(this);
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Enter Your Name")
                .setMessage("Please enter your name:")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = input.getText().toString();
                        if (inputName.isEmpty()) {
                            playerName = "Player123";
                        } else {
                            playerName = inputName;
                        }
                    }
                })
                .setCancelable(false)
                .show();
    }
}