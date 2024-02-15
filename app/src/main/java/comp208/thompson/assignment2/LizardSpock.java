package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * LizardSpock is an activity that represents the introduction screen of the Lizard-Spock game.
 * It extends AppCompatActivity to provide compatibility features and basic app infrastructure.
 * It handles user interaction and manages navigation to the LizardSpockGame activity.
 */
public class LizardSpock extends AppCompatActivity {
    // Button to start the game
    Button btnReady;

    /**
     * Called when the activity is starting.
     * This is where most initialization happens.
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lizardspock);

        // Get the player's name from the intent
        Intent intent = getIntent();
        String playerName = intent.getStringExtra("playerName");

        // Set up the ready button
        btnReady = findViewById(R.id.readyButton);

        // Set up the click listener for the ready button
        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent for the LizardSpockGame activity
                Intent intent = new Intent(LizardSpock.this, LizardSpockGame.class);

                // Pass the player's name to the LizardSpockGame activity
                intent.putExtra("playerName", playerName);

                // Start the LizardSpockGame activity
                startActivity(intent);
            }
        });
    }
}