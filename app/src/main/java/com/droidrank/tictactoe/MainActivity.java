package com.droidrank.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    // 0 = yellow, 1 = red
    int activePlayer = 0;
    String winner="";


    //game active or not
    //to end the game once a player wins
    boolean gameIsActive = true;

    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //all possible winning chances
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    Button block1, block2, block3, block4, block5, block6, block7, block8, block9, restart;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        block1 = (Button) findViewById(R.id.bt_block1);
//        block2 = (Button) findViewById(R.id.bt_block2);
//        block3 = (Button) findViewById(R.id.bt_block3);
//        block4 = (Button) findViewById(R.id.bt_block4);
//        block5 = (Button) findViewById(R.id.bt_block5);
//        block6 = (Button) findViewById(R.id.bt_block6);
//        block7 = (Button) findViewById(R.id.bt_block7);
//        block8 = (Button) findViewById(R.id.bt_block8);
//        block9 = (Button) findViewById(R.id.bt_block9);
        result = (TextView) findViewById(R.id.tv_show_result);
        restart = (Button) findViewById(R.id.bt_restart_game);


        /**
         * Restarts the game
         */
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(restart.getText().toString().equalsIgnoreCase(getResources().getString(R.string.restart_button_text_in_middle_of_game))){

                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setCancelable(false);
                        dialog.setTitle(getResources().getString(R.string.app_name));
                        dialog.setMessage(getResources().getString(R.string.restart_message));
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Action for "Ok".
                                restartGame();
                            }
                        })
                                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Action for "Cancel".
                                    }
                                });

                        final AlertDialog alert = dialog.create();
                        alert.show();
                }
                else if (restart.getText().toString().equalsIgnoreCase(getResources().getString(R.string.restart_button_text_initially))){
                        restartGame();
                    }

            }
        });

    }

    public void dropIn(View view) {

        //if(winner.equalsIgnoreCase(""))
            restart.setText(getResources().getString(R.string.restart_button_text_in_middle_of_game));

        Button counter = (Button) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        Log.i("Tapped button", String.valueOf(tappedCounter));

        if (gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            if (activePlayer == 0) {
                counter.setText(getResources().getString(R.string.player_1_move));
                activePlayer = 1;
            } else {
                counter.setText(getResources().getString(R.string.player_2_move));
                activePlayer = 0;
            }

            //to check if the player won
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {

                    //player won

                    gameIsActive = false;

                    winner = getResources().getString(R.string.player_2_wins);

                    if (gameState[winningPosition[0]] == 0) {
                        winner = getResources().getString(R.string.player_1_wins);
                    }

                    result.setText(winner);
                    restart.setText(getResources().getString(R.string.restart_button_text_initially));
                    break;


                } else {
                    boolean gameIsOver = true;
                    //if the game is draw
                    for (int counterState : gameState) {
                        if (counterState == 2) {
                            gameIsOver = false;
                        }
                    }

                    if (gameIsOver) {

                        result.setText("It's a draw");
                        restart.setText(getResources().getString(R.string.restart_button_text_initially));
                    }

                }
            }

        }

    }

    public void restartGame(){
        finish();
        startActivity(getIntent());
    }
}
