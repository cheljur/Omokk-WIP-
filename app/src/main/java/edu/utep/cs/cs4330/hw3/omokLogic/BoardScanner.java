package edu.utep.cs.cs4330.hw3.omokLogic;

import java.util.ArrayList;

/**
 * The purpose of this class is to scan the board looking
 * for the player pieces in a row. We scan horizontalScan, v
 * verticalScan, diagnol up, and diagnol down from a given point.
 * Created by Chelsey on 3/22/2016.
 */
public class BoardScanner {
    private Board board;

    public BoardScanner(Board board){
        this.board = board;
    }

    /**Scan for the horizontal chain starting at coordinate x,y for player*/
    public ArrayList<Place> horizontalScan(int x, int y, Player player){
        ArrayList<Place> p = new ArrayList<>();
        p.add(board.at(x, y));

        //go left
        int x1 = x-1;
        while(x1 > -1 && board.at(x1, y).player()==player){
            p.add(board.at(x1--,y));
        }

        //go right
        x1 = x+1;
        while(x1 < 10 && board.at(x1, y).player()==player) {
            p.add(board.at(x1++, y));
        }

        return p;
    }

    /**Scan for the vertical chain starting at coordinate x,y for player*/
    public ArrayList<Place> verticalScan(int x, int y, Player player) {
        ArrayList<Place> p = new ArrayList<>();
        p.add(board.at(x, y));

        //go up
        int y1 = y - 1;
        while (y1 > -1 && board.at(x, y1).player() == player) {
            p.add(board.at(x, y1--));
        }

        //go down
        y1 = y + 1;
        while (y1 < 10 && board.at(x, y1).player() == player) {
            p.add(board.at(x, y1++));
        }

        return p;
    }

    /**Scan for the diagonal down chain starting at coordinate x,y for player*/
    public ArrayList<Place> diagonalDownScan(int x, int y, Player player) {
        ArrayList<Place> p = new ArrayList<>();
        p.add(board.at(x, y));

        //go left/up
        int x1 = x - 1;
        int y1 = y - 1;
        while (x1 > -1 && y1 > -1 && board.at(x1, y1).player() == player) {
            p.add(board.at(x1--, y1--));
        }

        //go right/down
        x1 = x + 1;
        y1 = y + 1;
        while (x1 < 10 && y1 < 10 && board.at(x1, y1).player() == player) {
            p.add(board.at(x1++, y1++));
        }
        return p;
    }

    /**Scan for the diagonal up chain starting at coordinate x,y for player*/
    public ArrayList<Place> diagonalUpScan(int x, int y, Player player) {
        ArrayList<Place> p = new ArrayList<>();
        p.add(board.at(x, y));

        //go right/up
        int x1 = x + 1;
        int y1 = y - 1;
        while (x1 < 10 && y1 > -1 && board.at(x1, y1).player() == player) {
            p.add(board.at(x1++, y1--));
        }

        //go left/down
        x1 = x - 1;
        y1 = y + 1;
        while (x1 > -1 && y1 < 10 && board.at(x1, y1).player() == player) {
            p.add(board.at(x1--, y1++));
        }

        return p;
    }
}
