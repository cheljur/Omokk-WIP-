package edu.utep.cs.cs4330.hw3.omokLogic;

import java.util.*;

/**The board consist of size*size places, holds
 * the last place where a player piece was placed, and
 * if there are five player piece in a row.
 * Created by Chelsey on 2/26/2016.
 */
public class Board {
    /**The default size of the board is 10*10*/
    private static final int DEFAULT_SIZE = 10;

    /**The given size of the board*/
    private final int size;

    /**The last place where a player piece was placed*/
    private Place lastPlace;

    /**The places that make up the board*/
    private final List<Place> places;

    /**Holds if there have been at least five player pieces in a row*/
    private boolean fiveInARow;

    private ArrayList<Place> winningPlaces;

    private BoardScanner scanner = new BoardScanner(this);

    /**Construct the board with the default size*/
    public Board(){
        this(DEFAULT_SIZE);
    }

    /**Board is costructed with a given size,
     * Size*Size places are created.*/
    public Board(int size){
        this.size = size;
        places    = new ArrayList<Place>(size*size);

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                places.add(new Place(x,y,this));
            }
        }
    }

    /**Retrieve the place at the give X and Y coordinate*/
    public Place at(int x, int y){
        for(Place p: places){
            if(p.getX() == x && p.getY() == y){
                return p;
            }
        }
        return null;
    }

    /**Return the size of the board*/
    public int getSize(){
        return size;
    }

    /**Return the list of places*/
    public List<Place> getPlaces(){
        return places;
    }

    /**Place a player piece at a given place if the place is
     * empty*/
    public void placePiece(Place place, Player piece){
        if(place.isEmpty()){
            place.placePiece(piece);
            lastPlace = place;
            winningPlaces = checkWinnerPlaces();
        }
    }

    /**Return the set of winning places*/
    public  ArrayList<Place> getWinningPlaces(){
        return winningPlaces;
    }

    /**Checks if every place on the board has been occupied*/
    public boolean isFull(){
        for(Place p: places){
            if(p.isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**Returns if there is a winner on the board*/
    public boolean checkWinner(){
        return fiveInARow;
    }

    /**Checks if there is a winner row of player pieces,
     * returns a list of these winning places*/
    public ArrayList<Place> checkWinnerPlaces(){
        if(lastPlace == null){
            return null;
        }
        /**Get's information from the last place
         * where a player piece was placed. Get the
         * x,y, and player occupying space*/
        int x = lastPlace.getX();
        int y = lastPlace.getY();
        Player player = lastPlace.player();

        ArrayList<Place> winner = horizontal(x,y,player);
        if(winner == null){
            winner = vertical(x,y,player);
        }
        if(winner == null){
            winner = diagonalDown(x, y, player);
        }
        if(winner == null){
            winner = diagonalUp(x,y,player);
        }
        return winner;
    }

    /**Checks if there are at least 5 places that are
     * adjacent. Sets fiveInARow to true if this condition
     * is met.*/
    private boolean fiveInARow(ArrayList<Place> places){
        if(places.size() < 5){
            return false;
        }

        return fiveInARow = true;
    }

    /**Starting where the place piece was placed,
     * check the places left and right of the
     * piece to see if they belong to the same player.
     * If there are 5 adjacent player pieces, return
     * the list of places.*/
    private ArrayList<Place> horizontal(int x, int y, Player player){
        ArrayList<Place> p = scanner.horizontalScan(x, y, player);
        if(fiveInARow(p)){
            return p;
        }
        return null;
    }

    /**Starting where the place piece was placed,
     * check the places above and below of the
     * piece to see if they belong to the same player.
     * If there are 5 adjacent player pieces, return
     * the list of places.*/
    private ArrayList<Place> vertical(int x, int y, Player player){
        ArrayList<Place> p = scanner.verticalScan(x, y, player);

        if(fiveInARow(p)){
            return p;
        }
        return null;
    }

    /**Starting where the place piece was placed,
     * check the places top/left and below/right of the
     * piece to see if they belong to the same player.
     * If there are 5 adjacent player pieces, return
     * the list of places.*/
    private ArrayList<Place> diagonalDown(int x, int y, Player player){
        ArrayList<Place> p = scanner.diagonalDownScan(x, y, player);

        if(fiveInARow(p)){
            return p;
        }
        return null;
    }

    /**Starting where the place piece was placed,
     * check the places left/below and right/up of the
     * piece to see if they belong to the same player.
     * If there are 5 adjacent player pieces, return
     * the list of places.*/
    private ArrayList<Place> diagonalUp(int x, int y, Player player){
        ArrayList<Place> p = scanner.diagonalUpScan(x, y, player);

        if(fiveInARow(p)){
            return p;
        }
        return null;
    }

    /**Return the board scanner*/
    public BoardScanner getScanner(){
        return scanner;
    }

    /**Get the last place a piece was placed*/
    public Place getLastPlace(){
        return lastPlace;
    }

    /**Reset the board to its initial state*/
    public void reset() {
        lastPlace = null;
        winningPlaces = null;
        fiveInARow = false;
        for (Place p : places) {
            p.reset();
        }
    }

}
