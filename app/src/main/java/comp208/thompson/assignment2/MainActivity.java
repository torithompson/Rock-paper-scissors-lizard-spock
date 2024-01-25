package comp208.thompson.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView lizardSpockGame = findViewById(R.id.lizardSpockGame);
        final ImageView classicGame = findViewById(R.id.classicGame);

        lizardSpockGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LizardSpock.class);
                Log.i("---MainActivity","Lizard Spock activity started");
                startActivity(intent);
            }
        });

        classicGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClassicGame.class);
                startActivity(intent);
            }
        });
    }
}
