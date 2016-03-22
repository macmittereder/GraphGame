package svc.com.graphgame.GameFiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import svc.com.graphgame.R;

/*
* Created 3/15/2016 by Mac Mittereder
* This is the first map class
*/

public class PebbleNode extends View {

    /*
    * TODO: Replace rectangle with image
    * TODO: Center text better
    * TODO: Create better description of class
    */

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
        /*  Experimenting with using images instead of rectangle and changing the color without adding a different picture
        *
        *   ColorFilter filter = new LightingColorFilter(Color.BLUE, 1); //Creates color filter and sets the filter color to blue
        *   paint.setColorFilter(filter); //Set color filter in paint
        *   Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.square); //Use bitmap factory to select image from your resources,
        *                                                                                    put images in drawable and find them by R.drawable.(image name minus extention)
        *   canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, (int) width(),
        *       (int) height(), false), left, top, paint); //Resize bitmap using .createScaledBitmap, draw using topLeftX, topLeftY, need to apply a paint, this is where a filter can be set
        *   paint = new Paint();
        */
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

    //Setter method
    public void setPebbles(int numPebbles){
        this.numPebbles = numPebbles;
    }

    //Getter method
    public int getPebbles(){
        return numPebbles;
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
    //and defender's moves once we have those implemented. <- Implemented via GameRules
    public void movePebbles(PebbleNode destination) {
        if (this.getPebbles() >= 2) {
            this.setPebbles(this.getPebbles() - 2);
            destination.setPebbles(destination.getPebbles() + 1);
            this.postInvalidate(); // Update the game display once the move occurs
            destination.postInvalidate();
        }
    }
}
