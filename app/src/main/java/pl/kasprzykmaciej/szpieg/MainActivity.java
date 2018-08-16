package pl.kasprzykmaciej.szpieg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mPlayButton;
    Button mSettingsButton;
    Button mHelpButton;
    Button mPlacesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        initViewByIDs();

        setButtonsOnClickListeners();


    }

    private void setButtonsOnClickListeners() {


        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , SettingsActivity.class);
                startActivity(intent);
            }
        });
        mPlacesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , PlacesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewByIDs() {
        mPlayButton = findViewById(R.id.play_intent_button);
        mSettingsButton = findViewById(R.id.settings_intent_button);
        mHelpButton = findViewById(R.id.help_intent_button);
        mPlacesButton = findViewById(R.id.places_intent_button);
    }
}
