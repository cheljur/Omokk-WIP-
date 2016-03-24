package edu.utep.cs.cs4330.hw3.omok;

/**
 * Created by Chelsey on 3/18/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.hw3.omokLogic.Board;
import edu.utep.cs.cs4330.hw3.omokLogic.Game;
import edu.utep.cs.cs4330.hw3.omokLogic.Place;

/**
 * A special view class to display an omok board. An omok board is
 * displayed as a 2D grid, and black/white stones are displayed as
 * filled circles. When a place (or intersection) of the board is touched,
 * it is notified to all registered
 * @{link edu.utep.cs.cs4330.hw2.BoardView.BoardTouchListener}.
 *
 * @author Yoonsik Cheon\
 * modified by Chelsey Jurado
 */
public class BoardView extends View {

    /** To listen to board touches. */
    public interface BoardTouchListener {

        /** Called when a board place at (x,y) is touched, where
         * x and y are 0-based column and row indices. */
        void onTouch(int x, int y);
    }

    /** Listeners to be notified for board touches. */
    private final List<BoardTouchListener> listeners = new ArrayList<>();

    /** Number of places or intersections in rows and columns,
     * total boardSize x boardSize places.
     * FIX: obtain from the corresponding board.
     */
    private int boardSize = 10;

    /** Board background color. */
    private int boardColor = Color.YELLOW;


    /** Last place touched. Just for testing! */
    private float lastX = -1;
    private float lastY = -1;


    /** Create a new board view to be run in the given context. */
    public BoardView(Context context) {
        this(context, null);
    }

    /** Create a new board view with the given attributes. */
    public BoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /** Create a new board view with the given attributes and style. */
    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** Overridden here to draw a 2-D representation of an omok board. */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw a 2D grid
        final float maxIndex = maxIndex();
        final float lineGap = lineGap();
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(boardColor);
        canvas.drawRect(0, 0, maxIndex, maxIndex, paint);
        paint.setColor(Color.BLACK);
        for (int i = 0; i < numOfLines(); i++) {
            float index = i * lineGap;
            canvas.drawLine(0, index, maxIndex, index, paint); // horizontalScan
            canvas.drawLine(index, 0, index, maxIndex, paint); // verticalScan
        }

        //checkColor of Chip
        checkChipColor();
        paint.setColor(chipColor);

        // for testing, draw a stone at the last touched location
        //Do not continue to draw if the game is over.
        if (lastX >= 0 && lastX <= maxIndex
                && lastY >= 0 && lastY <= maxIndex && !game.isGameOver()) {
            float r = lineGap / 2;
            canvas.drawCircle(lastX, lastY, r, paint);
        }

        /**Draw previous game pieces placed on the board*/
        drawPieces(canvas, lineGap, paint);

        /**Mark the winning row of game pieces*/
        drawWinner(canvas, lineGap, paint);
    }

    /** Return the margin between horizontalScan/verticalScan lines. */
    private float lineGap() {
        return Math.min(getMeasuredWidth(), getMeasuredHeight())
                / (boardSize + 1.0f);
    }

    /** Return the number of horizontalScan/verticalScan lines. */
    private int numOfLines() {
        return boardSize + 2;
    }

    /** Return the maximum x/y screen coordinate. */
    private float maxIndex() {
        return lineGap() * (numOfLines() - 1);
    }

    /** Overridden here to identify a board place corresponding to
     * a screen position touched. If the corresponding place is located,
     * all registered board touch listeners will be notified.
     *
     *
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int xy = locatePlace(event.getX(), event.getY());
        if (xy >= 0) {
            int x = xy / 100;
            int y = xy % 100;
            lastX = screenCoor(x);
            lastY = screenCoor(y);
            invalidate();
            notifyBoardTouch(x, y);
        }
        //toast(event.getX(), event.getY());
        return true;
    }

    /**
     * Given screen coordinates, locate the corresponding place
     * (intersection) in the board and return its coordinates;
     * return -1 if the given coordinates don't correspond to
     * a place in the board. The returned coordinates are encoded
     * as <code>x*100 + y</code>.
     */
    private int locatePlace(float x, float y) {
        int bx = calculateBoardCoordinate(x);
        if (bx < 0) {
            return -1;
        }
        int by = calculateBoardCoordinate(y);
        if (by < 0) {
            return -1;
        }
        return bx * 100 + by;
    }

    /** Return the 0-based board coordinate corresponding to the given
     * screen coordinate, or -1 if it doesn't correspond to
     * any board place. */
    private int calculateBoardCoordinate(float x) {
        //
        // X---X---X-- O: placeable
        // |PS |   |   X: Not placeable
        // X---O---O--
        //
        final float placeSize = lineGap();
        final float radius = placeSize / 2; // of a stone

        // off board?
        if (x < placeSize - radius
                || x > placeSize * boardSize + radius) {
            return -1;
        }

        // --(radius+radius)--(radius+-
        int boardX = (int) ((x - placeSize) / placeSize);
        float remainder = (x - placeSize) - boardX * placeSize;
        if (remainder <= radius) {
            return boardX;
        } else if (remainder >= placeSize - radius) {
            return boardX + 1;
        }
        return -1;
    }

    /** Register the given listener. */
    public void addBoardTouchListener(BoardTouchListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /** Unregister the given listener. */
    public void removeBoardTouchListener(BoardTouchListener listener) {
        listeners.remove(listener);
    }

    /** Notify a place touch to all registered listeners. */
    private void notifyBoardTouch(int x, int y) {
        for (BoardTouchListener listener: listeners) {
            listener.onTouch(x, y);
        }
    }

    private void toast(float x, float y) {
        Toast.makeText(getContext(), "x = " + x + " y = " + y,
                Toast.LENGTH_SHORT).show();
    }

    /**Added methods and variables to the view Class***********************************************/
    /**Initially set the piece color to black*/
    private int chipColor = Color.BLACK;

    /** This is the game attached to view*/
    private Game game;
    /** Board that is being modeled by the view*/
    private Board board;


    /**Notify that the computer has placed a piece so the board can be redrawn.*/
    public boolean onComputerTouch(int x, int y){
        invalidate();
        notifyBoardTouch(x,y);
        return true;
    }

    /**Draw piece placed earlier on the board*/
    private void drawPieces(Canvas canvas, float lineGap, Paint paint){
        for(Place p : board.getPlaces()){
            if(p != null && p.isOccupied()){
                chipColor = chooseColor(p.playerNum());
                paint.setColor(chipColor);
                float x = screenCoor(p.getX());
                float y = screenCoor(p.getY());
                float r = lineGap / 2;
                canvas.drawCircle(x, y, r, paint);
            }
        }
    }

    /**Draw the winning row of pieces given there
     * is a winning row from the board*/
    private void drawWinner(Canvas canvas, float lineGap, Paint paint){
        ArrayList<Place> winnerPlaces = board.getWinningPlaces();
        if(winnerPlaces == null){
            return;
        }
        for(Place p: winnerPlaces){
            paint.setColor(Color.RED);
            float x = screenCoor(p.getX());
            float y = screenCoor(p.getY());
            float r = lineGap / 2;
            canvas.drawCircle(x, y, r, paint);
        }
    }


    /**Attach the current game and board to the view,
     * so the view can pull information from them*/
    public void addGame(Game currentGame){
        this.game = currentGame;
        this.board = currentGame.getOmokBoard();
    }

    /**Check the current chip color based on current player*/
    private void checkChipColor(){
        if(game == null){
            chipColor = Color.BLACK;
        }
        chipColor = chooseColor(game.currentPlayerNum());
    }

    /**Assign the appropriate color based on current player*/
    private int chooseColor(int player){
        if(player == 1){
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    /**Convert X&Y coords e.g(1,2) into screen coords*/
    private float screenCoor(int coor){
        return (coor+1) * lineGap();
    }

    /**Reset the board x & y*/
    public void reset(){
        lastX=-1;
        lastY=-1;
    }
}
