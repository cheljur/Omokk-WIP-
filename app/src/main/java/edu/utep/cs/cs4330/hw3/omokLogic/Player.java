package edu.utep.cs.cs4330.hw3.omokLogic;

/**
 *A player that participates in the game. The
 * player only knows if it is their turn,
 * if they are a winner, and what their player
 * number.
 *
 * Created by Chelsey on 2/26/2016.
 */
public class Player {
    /**Holds the players turn, if they are a winner*/
    private boolean turn, winner;

    /**Are they player 1,2...etc.*/
    private int playerNum;

    private String playerName;

    /**Initialize  the player with their given number*/
    public Player(int playerNum, String playerName){
        this.playerNum = playerNum;
        this.playerName = playerName;
        setTurn(false);
        setWinner(false);
    }

    /**Return the player number*/
    public int getPlayerNum(){
        return playerNum;
    }

    /**Return the player name*/
    public String getPlayerName(){ return playerName; }

    /**Set if it is the player's turn*/
    public void setTurn(boolean turn){
        this.turn = turn;
    }

    /**Set if the player is the winner*/
    public void setWinner(boolean winner){
        this.winner = winner;
    }

    /**Return if it is the player's turn*/
    public boolean isTurn(){
        return turn;
    }

    /**Return if the player is the winner*/
    public boolean isWinner(){
        return winner;
    }

    /**Reset the player to its initial state*/
    public void reset(){
        setTurn(false);
        setWinner(false);
    }
}