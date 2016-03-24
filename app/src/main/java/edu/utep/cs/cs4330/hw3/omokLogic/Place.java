package edu.utep.cs.cs4330.hw3.omokLogic;

/**
 * Created by Chelsey on 2/26/2016.
 *
 * Represents a place in a game board. Each place contains
 * an x and y coordinate point. The place also holds a
 * player piece.
 */
public class Place {
    /**X-Coordinate(column), Y-Coordinate(row)*/
    private final int x, y;

    /**Player piece placed in this place*/
    private Player playerPiece;

    /**The game board the place belongs to*/
    private Board omokBoard;

    /**Constructor class takes in the coordinates
     * and the board the place belongs to*/
    public Place(int x, int y, Board omokBoard){
        this.x         = x;
        this.y         = y;
        this.omokBoard = omokBoard;
    }

    /**Return the X-Coordinate of the place*/
    public int getX(){
        return x;
    }

    /**Return the Y-Coordinate of the place*/
    public int getY(){
        return y;
    }

    /**Returns if the place has been occupied
     * by a player*/
    public boolean isOccupied(){
        return playerPiece != null;
    }

    /**Returns if the place is empty*/
    public boolean isEmpty(){
        return playerPiece == null;
    }

    /**Place a player piece in the place*/
    public void placePiece(Player playerPiece){
        this.playerPiece = playerPiece;
    }

    /**Returns the player Piece who occupies the place*/
    public Player player(){
        return playerPiece;
    }

    /**Returns the player number who occupies the place*/
    public int playerNum(){
        return playerPiece.getPlayerNum();
    }

    /**Resets the the place and clears any player piece
     * that may have been here.*/
    public void reset(){
        if(playerPiece != null){
            playerPiece = null;
        }
    }
}
