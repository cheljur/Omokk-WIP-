package edu.utep.cs.cs4330.hw3.omokLogic;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains the strategies that are used by the computer. The strategies are:
 *  -Random Strategy
 *  -Smart Strategy
 * Created by Chelsey on 3/21/2016.
 */
public class Strategy {
    private String strategy;
    private Board board;
    private BoardScanner scanner;
    private int computerLastX, computerLastY;

    /**Constructor class, takes in strategy name and the board*/
    public Strategy(String strategy, Board board){
        this.strategy = strategy;
        this.board = board;
        scanner = board.getScanner();
    }

    /**When the computerTurn is called, computer will place a piece
     * according to its appropriate strategy*/
    public int[] computerTurn(Player computer){
        if(strategy.equalsIgnoreCase("random")){
            return randomStrategy();
        }
        else if(strategy.equalsIgnoreCase("smart")){
            return smartStrategy(computer);
        }
        return null;
    }

    /**The computer will randomly place a piece on the board.*/
    private int[] randomStrategy(){
        Random rn = new Random();
        int x, y;
        do {
            x = rn.nextInt(10);
            y = rn.nextInt(10);
        } while(board.at(x,y).isOccupied());
        int [] coord = {x,y};
        return coord;
    }

    /**Smart strategy looks at the last piece placed by the player and the computer. It will
     * calculate who has a longer chain of pieces and the computer will either block or play
     * offensively.*/
   private int[] smartStrategy(Player computer){
       Place last = board.getLastPlace();
       String dir;

       /**Look for all chains created by the player connected to the last piece placed*/
       ArrayList<Place> humanHorizontal = scanner.horizontalScan(last.getX(), last.getY(), last.player());
       ArrayList<Place> humanVertical = scanner.verticalScan(last.getX(), last.getY(), last.player());
       ArrayList<Place> humanDiagUp = scanner.diagonalUpScan(last.getX(), last.getY(), last.player());
       ArrayList<Place> humanDiagDown = scanner.diagonalDownScan(last.getX(), last.getY(), last.player());

       /***Look for all chains created by the computer connected to the last piece placed*/
       ArrayList<Place> computerHorizontal = scanner.horizontalScan(computerLastX, computerLastY, computer);
       ArrayList<Place> computerVertical = scanner.verticalScan(computerLastX, computerLastY, computer);
       ArrayList<Place> computerDiagUp = scanner.diagonalUpScan(computerLastX, computerLastY, computer);
       ArrayList<Place> computerDiagDown = scanner.diagonalDownScan(computerLastX, computerLastY, computer);

       /**find the max length chain for both human and computer*/
       ArrayList<Place> humanList = max(humanHorizontal,humanVertical,humanDiagDown,humanDiagUp);
       ArrayList<Place> computerList = max(computerHorizontal,computerVertical,computerDiagDown,computerDiagUp);


       /**If the human chain is longer, the computer will block around the last placed piece*/
       if((humanList.size() > computerList.size()) || (humanList.size() == 1 && computerList.size() == 1)){
           return block(last.getX(), last.getY());
       }
       /**If the computer chain is longer, the computer will play offensively to complete the chain*/
       if(humanList.size() <= computerList.size()){
           dir = maxDirection(computerHorizontal,computerVertical,computerDiagDown,computerDiagUp);
           return offensive(computerList,dir);
       }
       return block(last.getX(), last.getY());
    }

    /**When the computer detects it has a chain longer than the player, based on last piece placed
     * the computer will take the offensive by placing pieces in the appropriate location*/
    private int[] offensive(ArrayList<Place> places, String dir){

        Log.d("SHIT", "dir "+ dir);
        Place beginning = places.get(0); //place at the beginning of the chain
        Place ending = places.get(places.size()-1); //place at the end of the chain
        /**Place piece in the horizontal chain*/
        if(dir.equals("horizontalScan")){
            if(beginning.getX() > 0) {
                computerLastX = beginning.getX() - 1;
                computerLastY = beginning.getY();
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(beginning.getX() < 9){
                computerLastX = beginning.getX() +1;
                computerLastY = beginning.getY();
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(ending.getX() > 0){
                computerLastX = ending.getX() - 1;
                computerLastY = ending.getY();
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(ending.getX() < 9){
                computerLastX = ending.getX() +1;
                computerLastY = ending.getY();
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
        }
        /**Place piece in the vertical chain*/
        else if(dir.equals("verticalScan")){
            if(beginning.getY() > 0) {
                computerLastX = beginning.getX();
                computerLastY = beginning.getY()-1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(beginning.getY() < 9){
                computerLastX = beginning.getX();
                computerLastY = beginning.getY()+1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(ending.getY() > 0){
                computerLastX = ending.getX();
                computerLastY = ending.getY()-1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(ending.getY() < 9){
                computerLastX = ending.getX();
                computerLastY = ending.getY()+1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
        }
        /**Place piece in a diaganol down chain*/
        else if(dir.equals("diagDown")){
            if(beginning.getX() > 0 && beginning.getY() > 0) {
                computerLastX = beginning.getX()-1;
                computerLastY = beginning.getY()-1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(beginning.getX() < 9 && beginning.getY() < 9){
                computerLastX = beginning.getX()+1;
                computerLastY = beginning.getY()+1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(ending.getX() > 0 && ending.getY() > 0){
                computerLastX = ending.getX()-1;
                computerLastY = ending.getY()-1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(ending.getX() < 9 && ending.getY() < 9){
                computerLastX = ending.getX()+1;
                computerLastY = ending.getY()+1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
        }
        //Place piece in a diaganol up chain
        else if(dir.equals("diagUp")){
            if(beginning.getX() < 9 && beginning.getY() > 0) {
                computerLastX = beginning.getX()+1;
                computerLastY = beginning.getY()-1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(beginning.getX() > 0 && beginning.getY() < 9){
                computerLastX = beginning.getX()-1;
                computerLastY = beginning.getY()+1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(ending.getX() < 9 && ending.getY() > 0){
                computerLastX = ending.getX()+1;
                computerLastY = ending.getY()-1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
            if(ending.getX() > 0 && ending.getY() < 9){
                computerLastX = ending.getX()-1;
                computerLastY = ending.getY()+1;
                if(board.at(computerLastX,computerLastY).isEmpty()){
                    return new int[]{computerLastX,computerLastY};
                }
            }
        }
        /**If no piece can be placed on the chain, place piece randomly*/
        int[] coor = randomStrategy();
        computerLastX = coor[0];
        computerLastY = coor[1];
        return coor;
    }

    /**Block randomly around the last piece the player placed on the board*/
    private int[] block(int humanLastX, int humanLastY){
        Random rn = new Random();
        int randomDir = rn.nextInt(4);
        boolean spaceNotFound = true;

        while(spaceNotFound) {
            //check left
            if (humanLastX > 0 && board.at(humanLastX - 1, humanLastY).isEmpty() && randomDir == 0) {
                computerLastX = humanLastX - 1;
                computerLastY = humanLastY;
                spaceNotFound = false;
            }
            //check right
            else if (humanLastX < 9 && board.at(humanLastX + 1, humanLastY).isEmpty() && randomDir == 1) {
                computerLastX = humanLastX + 1;
                computerLastY = humanLastY;
                spaceNotFound = false;
            }

            //up
            else if (humanLastY > 0 && board.at(humanLastX, humanLastY - 1).isEmpty() && randomDir == 2) {
                computerLastX = humanLastX;
                computerLastY = humanLastY - 1;
                spaceNotFound = false;
            }

            //check down
            else if (humanLastY < 9 && board.at(humanLastX, humanLastY + 1).isEmpty() && randomDir == 3) {
                computerLastX = humanLastX;
                computerLastY = humanLastY + 1;
                spaceNotFound = false;
            }
            else{
                randomDir = rn.nextInt(4);
            }
        }
        return new int[]{computerLastX, computerLastY};
    }

    /**Find the longest chain of pieces*/
    private ArrayList<Place> max(ArrayList<Place> horizontal, ArrayList<Place> vertical, ArrayList<Place> diagDown, ArrayList<Place> diagUp){
        int max = horizontal.size();
        ArrayList<Place> maxList = horizontal;

        if(vertical.size() > max){
            max = vertical.size();
            maxList = vertical;
        }
        else if(diagDown.size() > max){
            max = diagDown.size();
            maxList = diagDown;
        }
        else if(diagUp.size() > max){
            maxList = diagUp;
        }

        return maxList;
    }

    /**Find the direction of the longest chain of pieces*/
    private String maxDirection(ArrayList<Place> horizontal, ArrayList<Place> vertical, ArrayList<Place> diagDown, ArrayList<Place> diagUp){
        int max = horizontal.size();
        String maxList = "horizontalScan";

        if(vertical.size() > max){
            max = vertical.size();
            maxList = "verticalScan";
        }
        else if(diagDown.size() > max){
            max = diagDown.size();
            maxList = "diagDown";
        }
        else if(diagUp.size() > max){
            maxList = "diagUp";
        }

        return maxList;
    }
}
