package svc.com.graphgame.GameFiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/*
* Created 3/16/2016 by Mac Mittereder
* This is the first map class
*/

public class ConnectNodes extends View{

    //Create global variables for objects on screen and import classes
    PebbleNode node1, node2;
    boolean selected;

    //Default initialization
    public ConnectNodes(Context context) {
        super(context);
    }

    //This is how we pass in the nodes to create the lines
    public ConnectNodes(Context context, PebbleNode node1, PebbleNode node2, Boolean selected){
        super(context);
        this.node1 = node1;
        this.node2 = node2;
        this.selected = selected;
    }

    //This method gets executed after it's called to draw the item to your 'canvas' or layout
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        if (selected)
            paint.setColor(Color.GREEN);
        else
            paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20); //Line thickness
        //Some math to figure out the center of the node
        //.drawLine is (StartX, StartY, EndX, EndY, paint(Color, style, size, etc.))
        canvas.drawLine((node1.width() / 2) + node1.left, (node1.height() / 2) + node1.top,
                (node2.width() / 2) + node2.left, (node2.height() / 2) + node2.top, paint);
    }

    //Setter method
    public void setSelected(boolean selected){
        this.selected = selected;
    }
}
