package edu.utep.cs.cs4330.hw3.omokLogic;

/**
 * Contains the board to be used throughout the
 * game. Also includes the players involved
 * in the game, and if the game is over.
 *
 * Created by Chelsey on 2/26/2016.
 */
public class Game {
    /**Players participating in the game*/
    private Player player1, player2;

    /**Board used throughout the game*/
    private Board omokBoard;

    /**Holds if the game is over*/
    private boolean gameOver;

    /**Holds if the you are playing against the computer*/
    private boolean isComputer;

    private String strategy;

    private Strategy computer;

    /**When the game is initialized the players and
     * the board is set.*/
    public Game(){
        omokBoard = new Board();
    }

    /**Create the the players for the games,
     * assign their player numbers, and set
     * player1 to have the next turn*/
    public void setPlayers(String player1Name,String player2Name){
        player1   = new Player(1, player1Name);
        player2   = new Player(2, player2Name);
        player1.setTurn(true);
    }

    public void setComputer(Boolean isComputer){
        this.isComputer = isComputer;
        strategy = null;
        computer = null;
    }

    public void setComputer(Boolean isComputer, String strategy){
        this.isComputer = isComputer;
        this.strategy = strategy;
        computer = new Strategy(strategy, omokBoard);
    }

    public boolean isComputer(){
        return isComputer;
    }

    /**Retrieves the board being used in the game*/
    public Board getOmokBoard(){
        return omokBoard;
    }

    /**Returns if the game is over*/
    public boolean isGameOver(){
        return gameOver;
    }

    /**Alternate the the player's turn*/
    public void switchTurns(){
        player1.setTurn(!player1.isTurn());
        player2.setTurn(!player2.isTurn());
    }

    /**Retrieve the current player's number*/
    public int currentPlayerNum(){
        if(player1.isTurn()){
            return player1.getPlayerNum();
        }
        else{
            return player2.getPlayerNum();
        }
    }

    public String currentPlayerName(){
        if(player1.isTurn()){
            return player1.getPlayerName();
        }
        else{
            return player2.getPlayerName();
        }
    }

    public int[] computerTurn(){
        int[] coord = null;
        if(isComputer && strategy != null){
            coord = computer.computerTurn(player2);
        }
        return coord;
    }

    /**Return the current player*/
    public Player currentPlayer(){
        if(player1.isTurn()){
            return player1;
        }
        else{
            return player2;
        }
    }

    /**Return the player who's turn was
     * the previous round*/
    public Player previousPlayer(){
        if(player1.isTurn()){
            return player2;
        }
        else{
            return player1;
        }
    }

    /**Check if there is a winner on the board.
     * If the board is full, the game is a tie
     * and both players are marked as winners.
     * If a winning row is found, the previous player
     * is marked as the winner. This is because by the time
     * this method is called, the player's turn has ended and
     * the next player is considered the current player*/
    public boolean checkWinner(){
        if(omokBoard.isFull()){
            player1.setWinner(true);
            player2.setWinner(true);
            gameOver = true;
            return true;
        }
        boolean winner = omokBoard.checkWinner();
        if(winner) {
            previousPlayer().setWinner(true);
            gameOver = true;
            return true;
        }
        return false;
    }

    /**Check which player's are marked as winners,
     * returns a String value of who is the winner.*/
    public String winner(){
        String winner = "";
        if(player1.isWinner()){
            winner = player1.getPlayerName()+" wins!";
        }
        if(player2.isWinner()){
            winner = player2.getPlayerName()+" wins!";
        }
        if(player1.isWinner() && player2.isWinner()){
            winner = "Tied Game!";
        }

        return winner;
    }

    /**Resets the game to its initial state*/
    public void reset(){
        gameOver = false;
        omokBoard.reset();
        player1.reset();
        player2.reset();
        player1.setTurn(true);
    }

}

