package com.neha.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean gameIsActive = true;

    // 0=yellow 1=red

    int activePlayer = 0;

    // 2 means unplayed

    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).setDuration(300);

            for(int[] winningPosition: winningPositions) {

                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[0]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {

                    System.out.println("GameState");

                    gameIsActive = false;
                    //someone has won
                    String winner = "Red";

                    if(gameState[winningPosition[0]] == 0) {
                        winner = "Yellow";
                    }

                    TextView winnerMessage =(TextView) findViewById(R.id.winnerMessageTextView);
                    winnerMessage.setText(winner+" has won!");

                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    playAgainButton.setEnabled(true);

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLinearLayout);
                    //layout.setVisibility(View.VISIBLE);
                    layout.setAlpha(1f);

                }else {

                    boolean isGameOver = true;

                    for(int counterState: gameState) {
                        if(counterState == 2) isGameOver =false;
                    }

                    if(isGameOver) {

                        TextView winnerMessage =(TextView) findViewById(R.id.winnerMessageTextView);
                        winnerMessage.setText(" It's a draw ");

                        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                        playAgainButton.setEnabled(true);

                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLinearLayout);
                        layout.setAlpha(1f);
                    }
                }
            }
        }
    }

    public void playAgainButton(View view) {

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLinearLayout);
        layout.setAlpha(0f);

        activePlayer = 0;

        gameIsActive = true;

        for(int i = 0; i <gameState.length; i++) {
            gameState[i]=2;
        }

        TableLayout tableLayout = (TableLayout) findViewById(R.id.boardTableLayout);
        for(int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++) {
                ((ImageView) row.getChildAt(j)).setImageResource(0);
            }
        }

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        playAgainButton.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
