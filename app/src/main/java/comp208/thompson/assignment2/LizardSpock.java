package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class LizardSpock extends AppCompatActivity {
    Button btnReady;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lizardspock);

        btnReady = findViewById(R.id.readyButton);

        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LizardSpock.this, LizardSpockGame.class);
                startActivity(intent);
            }
        });


    }
}