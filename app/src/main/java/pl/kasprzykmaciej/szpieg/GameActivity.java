package pl.kasprzykmaciej.szpieg;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import pl.kasprzykmaciej.szpieg.database.Word;
import pl.kasprzykmaciej.szpieg.database.WordViewModel;

public class GameActivity extends AppCompatActivity {


    private TextView mPlaceTV;
    private int numOfPlayers;
    private int currentPlayer;
    private int gameState;
    private WordViewModel mWordViewModel;
    private List<Word> words;
    private int countOfWords;
    private int szpiegIndex;
    private String currentLocation;

    private final int GAME_STATE_NEXT_PLAYER = 0;
    private final int GAME_STATE_PLACE = 1;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        mPlaceTV = findViewById(R.id.display_place_tv);


        final Button button = findViewById(R.id.button_next);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (gameState == GAME_STATE_NEXT_PLAYER) {
                    gameState = GAME_STATE_PLACE;
                } else {
                    currentPlayer++;
                    gameState = GAME_STATE_NEXT_PLAYER;
                }

                if(currentPlayer == numOfPlayers + 1)
                    finish();
                else updateView();
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String numberOfPlayersString = prefs.getString(getString(R.string.pref_number_key), "");
        numOfPlayers = Integer.parseInt(numberOfPlayersString);
        gameState = GAME_STATE_NEXT_PLAYER;

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        words = mWordViewModel.getAllWordsRaw();
        countOfWords = words.size();

        Random generator = new Random();
        szpiegIndex = Math.abs( generator.nextInt())%numOfPlayers + 1;
        currentPlayer = 1;
        currentLocation = words.get(Math.abs(generator.nextInt()) % countOfWords).getWord();

        updateView();

    }

    private void updateView() {
        if (gameState == GAME_STATE_PLACE) {
            if (currentPlayer == szpiegIndex) {
                mPlaceTV.setText(R.string.game_spy_message);
            } else {
                mPlaceTV.setText(currentLocation);
            }
        } else {
            String s = getString(R.string.game_pre_player_number_message) + currentPlayer + ".";
            mPlaceTV.setText(s);
        }
    }
}

