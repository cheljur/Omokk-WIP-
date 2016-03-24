package edu.utep.cs.cs4330.hw3.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import edu.utep.cs.cs4330.hw3.omok.BoardView;
import edu.utep.cs.cs4330.hw3.omok.R;
import edu.utep.cs.cs4330.hw3.omokLogic.Board;
import edu.utep.cs.cs4330.hw3.omokLogic.Game;


/**
 * Created by Chelsey on 2/25/2016.*/

public class OmokActivity extends AppCompatActivity {
    private Game omok;
    private Board omokBoard;
    private BoardView boardView;
    private TextView gameStatus;

    private SoundPool soundPool;
    private SparseIntArray soundMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omok);

        gameStatus = (TextView) findViewById(R.id.turnStatus);
        configSounds();
        start();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.clone();
    }



    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String opponent = extras.getString("Opponent");
            String player1name = extras.getString("Player1Name");

            if(opponent.equalsIgnoreCase("human")){
                String player2name = extras.getString("Player2Name");
                omok.setPlayers(player1name, player2name);
                omok.setComputer(false);
            }
            else if(opponent.equalsIgnoreCase("computer")){
                String computerStrategy = extras.getString("Strategy");
                omok.setPlayers(player1name, "Computer");
                omok.setComputer(true, computerStrategy);
            }
        }
    }

    /**Create a new game, set initial status, and create te view*/
    private void start(){

        omok = new Game();
        getExtras();
        omokBoard = omok.getOmokBoard();
        setTurnStatus();
        createBoard();
    }

    /**The board view is created. Attach an instance of the game to
     * the view.*/
    private void createBoard(){
        boardView = (BoardView) findViewById(R.id.boardView);
        boardView.addGame(omok);
        boardView.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                playRound(x, y);
            }
        });
    }

    /** Each round, check if there is a winner, if not place a player piece
     * on the board and switch between players. This is called from onTouch.
     * X and Y are graph coordinates, e.g. (0,0),(0,1),...,(9,9)*/
    private void playRound(int x, int y){
        if(omok.checkWinner()){
            setWinnerStatus();
            /**since the players have switch, check if the player is the computer to
              play the winning sound sound*/
            if(omok.winner().equalsIgnoreCase("computer wins!")){
                soundPool.play(3,1,1,1,0,1.0f);//player lost
            }
            else {
                soundPool.play(2, 1, 1, 1, 0, 1.0f);//player win
            }
        }
        if (omokBoard.at(x, y).isEmpty() && !omok.isGameOver()) {
            soundPool.play(1, 1, 1, 1, 0, 1.0f);//place piece on board
            /**Place a piece on board*/
            omokBoard.placePiece(omokBoard.at(x, y), omok.currentPlayer());
            omok.switchTurns();
            setTurnStatus();

            //if computer place piece stratigically
            if(omok.isComputer() && omok.currentPlayerName().equalsIgnoreCase("computer")){
                int[] computerCoord = omok.computerTurn(); //[x,y]
                boardView.onComputerTouch(computerCoord[0], computerCoord[1]);
            }

        }
    }

    /**On click of the new game button, an alert dialog will
     * be generated.*/
    public void resetGame(View view){
        alertDialog("Are you sure you want to start a new game?", "reset");
    }

    /**Called from an alert dialog generated. This will reset the game, view
     * and the status.*/
    private void reset(){
        omok.reset();
        boardView.reset();
        boardView.invalidate();
        setTurnStatus();
    }

    /**Alert dialog template, displays given message,
     * onClick of a positive button, depending on the
     * dialogAction, a method will be called to respond
     * accordingly.*/
    private void alertDialog(String msg, final String dialogAction) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (dialogAction) {
                            case "reset":
                                reset();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .create().show();

    }

    /**Retrieve the winner from the Game class and set the status
     * to display the winner*/
    private void setWinnerStatus(){
        gameStatus.setText(omok.winner());
    }

    /**Retrieve the current player and set the status to
     * display whose turn it is*/
    private void setTurnStatus(){
        String playerName = omok.currentPlayerName();
        gameStatus.setText(playerName+"'s Turn");
    }

    private void configSounds(){
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundMap = new SparseIntArray(2);
        soundMap.put(1, soundPool.load(this, R.raw.place_piece, 1));
        soundMap.put(2, soundPool.load(this, R.raw.you_win, 1));
        soundMap.put(3, soundPool.load(this, R.raw.you_lose, 1));
    }


    /***MENU SHIT**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.settings){
            return true;
        }
        else if(id == R.id.exit){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*************************************************************/
}