package svc.com.graphgame.GameFiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/*
* Created 3/15/2016 by Mac Mittereder
* This is the first map class
*/

public class PebbleNode extends View {

    //Create global variables for objects on screen and import classes
    Paint paint = new Paint();
    float left, top, right, bottom, sideLength = 500;
    int numPebbles;
    boolean goalNode;

    //Default initialization
    public PebbleNode(Context context) {
        super(context);
    }

    //This is the method to create nodes, this takes (Context(Just leave as is), TopLeftX, TopLeftY, BottomRightX, BottomRightY,
    //number of pebbles the node starts with, boolean to tell which is the goal node
    public PebbleNode(Context context, float left, float top, float right,
            float bottom, int numPebbles, Boolean goalNode){
        super(context);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.numPebbles = numPebbles;
        this.goalNode = goalNode;
    }

    //This method gets executed after it's called to draw the item to your 'canvas' or layout
    @Override
    public void onDraw(Canvas canvas) {
        if (goalNode)
            paint.setColor(Color.BLUE);
        else
            paint.setColor(Color.BLACK);
        //Draw rectangle with TopLeftX, TopLeftY, BottomRightX, BottomRightY, paint is the style (Color, text size, etc.)
        canvas.drawRect(left, top, right, bottom, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(85f);
        //Used string builder to append the number of pebbles and goal text
        StringBuilder sb = new StringBuilder();
        if (goalNode) {
            sb.append("Goal");
            //Drawing the text to the middle of the Rectangle with -70 X adjustment
            canvas.drawText(sb.toString(), (width() / 2) + left - 70, (height() / 2 ) + top, paint);
        } else {
            sb.append(numPebbles);
            //Drawing the text to the middle of the Rectangle (needs slight X & Y adjustment)
            canvas.drawText(sb.toString(), (width() / 2) + left, (height() / 2) + top, paint);
        }
    }

    //Getter method
    public float height(){
        return bottom - top;
    }

    //Getter method
    public float width(){
        return right - left;
    }
    
    //Getter method
    public int getPebbles(){
        return numPebbles;
    }
    
    //Getter method
    public boolean isGoal(){
        return goalNode;
    }

    //Setter method
    public void setNumPebbles(int numPebbles){
        this.numPebbles = numPebbles;
    }

    //Method to check if touch coordinates are inside of Rectangle bounds
    public boolean contains(int touchX, int touchY){
        if (left <= touchX  && touchX <= right)
            if (top <= touchY && touchY <= bottom)
                return true;
        else
                return false;
        return false;
    }
    
    //Basic pebble moving method. Must be updated later to account for the differing rules between the attacker's
    //and defender's moves once we have those implemented.
    public void movePebbles(PebbleNode moveFrom, PebbleNode moveTo)
    {
        if (moveFrom.getPebbles() >= 2)
        {
            moveFrom.setNumPebbles(moveFrom.getPebbles() - 2);
            moveTo.setNumPebbles(moveTo.getPebbles() + 1);
            moveFrom.postInvalidate(); // Update the game display once the move occurs
            moveTo.postInvalidate();
        }
    }

}
