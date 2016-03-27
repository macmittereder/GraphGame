package svc.com.graphgame.Maps;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import svc.com.graphgame.GameFiles.ConnectNodes;
import svc.com.graphgame.GameFiles.GameRules;
import svc.com.graphgame.GameFiles.PebbleNode;
import svc.com.graphgame.R;

/*
* Created 3/10/2016 by Mac Mittereder
* This is the first map class
*/

public class FirstMap extends Activity {

    /*
    * TODO: Bring lines from selected node to front
    * TODO: (fix) Sometimes on touch_down the node won't be highlighted, probably caused by touch_move
    * TODO: Create ratio to resize nodes better on different screen sizes
    * TODO: Create better description of class
    * TODO: Add visible pebbles in Action_Move to follow your finger to place the pebbles
    */

    //Create global variables and import classes
    PebbleNode node1, node2, node3, node4, node5, node6;
    int width, height;
    RelativeLayout relativeLayout;
    ConnectNodes cntNode12, cntNode14, cntNode16, cntNode25, cntNode34,
        cntNode36, cntNode45, cntNode56;
    GameRules gameRules;
    TextView textView;
    boolean movingPebbles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Don't change the following lines
        requestWindowFeature(Window.FEATURE_NO_TITLE); //Removes window title bar
        setContentView(R.layout.first_map_layout); //Set layout to the first map layout
        gameRules = new GameRules(this); //Initialize game rules

		//Initialize layout
        relativeLayout = (RelativeLayout) findViewById(R.id.firstMapGameView);
        textView = (TextView) findViewById(R.id.touchEvent);

		//Retrieving dimensions of the screen 
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        //Creating pebble nodes with (Context(Just put 'this'), TopX, TopY, BottomX, BottomY, number of pebbles to start with, boolean if this is the goal node
        //Refer to the PebbleNode class
        node1 = new PebbleNode(this, 45, 45, 400, 400, 0, false);
        node2 = new PebbleNode(this, (width - (int) node1.width()) - 45, 45, width - 45, 400, 7, false);
        node3 = new PebbleNode(this, 45, 510, 400, 865, 0, false);
        node4 = new PebbleNode(this, (width - (int) node3.width()) - 45, 510, width - 45, 865, 5, false);
        node5 = new PebbleNode(this, 45, 975, 400, 1330, 0, false);
        node6 = new PebbleNode(this, (width - (int) node5.width()) - 45, 975, width - 45, 1330, 0, true); //This is goal node so set to true

        //Creating lines to connect the nodes with (Context(Just put 'this'), first node, second node
        //Refer to the ConnectNodes class
        cntNode12 = new ConnectNodes(this, node1, node2);
        cntNode14 = new ConnectNodes(this, node1, node4);
        cntNode16 = new ConnectNodes(this, node1, node6);
        cntNode25 = new ConnectNodes(this, node2, node5);
        cntNode34 = new ConnectNodes(this, node3, node4);
        cntNode36 = new ConnectNodes(this, node3, node6);
        cntNode45 = new ConnectNodes(this, node4, node5);
        cntNode56 = new ConnectNodes(this, node5, node6);

        //Add the nodes and lines connecting them to the layout
        relativeLayout.addView(node1);
        relativeLayout.addView(node2);
        relativeLayout.addView(node3);
        relativeLayout.addView(node4);
        relativeLayout.addView(node5);
        relativeLayout.addView(node6);
        relativeLayout.addView(cntNode12);
        relativeLayout.addView(cntNode14);
        relativeLayout.addView(cntNode16);
        relativeLayout.addView(cntNode25);
        relativeLayout.addView(cntNode34);
        relativeLayout.addView(cntNode36);
        relativeLayout.addView(cntNode45);
        relativeLayout.addView(cntNode56);
        relativeLayout.addView(gameRules);

        //Bring nodes to front so the lines are behind them
        node1.bringToFront();
        node2.bringToFront();
        node3.bringToFront();
        node4.bringToFront();
        node5.bringToFront();
        node6.bringToFront();

        //Add connections to the game rules
        gameRules.addConnectedNodes(cntNode12);
        gameRules.addConnectedNodes(cntNode14);
        gameRules.addConnectedNodes(cntNode16);
        gameRules.addConnectedNodes(cntNode25);
        gameRules.addConnectedNodes(cntNode34);
        gameRules.addConnectedNodes(cntNode36);
        gameRules.addConnectedNodes(cntNode45);
        gameRules.addConnectedNodes(cntNode56);

        //Add pebble nodes to game rules
        gameRules.addPebbleNodes(node1);
        gameRules.addPebbleNodes(node2);
        gameRules.addPebbleNodes(node3);
        gameRules.addPebbleNodes(node4);
        gameRules.addPebbleNodes(node5);
        gameRules.addPebbleNodes(node6);

        //Set goal node in game rules
        gameRules.setGoalNode(node6);
        gameRules.setScreenSize(width, height);

        //Set game rules to front to display win
        gameRules.bringToFront();
    }

    //This is a class to reset the lines back to black
    //.postInvalidate() redraws the item after the set selected is set to false
    public void resetLines(){
        cntNode12.setSelected(false);
        cntNode14.setSelected(false);
        cntNode16.setSelected(false);
        cntNode25.setSelected(false);
        cntNode34.setSelected(false);
        cntNode36.setSelected(false);
        cntNode45.setSelected(false);
        cntNode56.setSelected(false);
        cntNode12.postInvalidate();
        cntNode14.postInvalidate();
        cntNode16.postInvalidate();
        cntNode25.postInvalidate();
        cntNode34.postInvalidate();
        cntNode36.postInvalidate();
        cntNode45.postInvalidate();
        cntNode56.postInvalidate();
    }

    //This is a debugging class to show which node was clicked
    public void alert(int num){
        Toast.makeText(this, "Node " + num, Toast.LENGTH_SHORT).show();
    }

    //This is the touch handling class for the nodes
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Retrieving the touch location
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        switch (event.getAction()) {
            //Action_Down happens when your finger presses down on the screen
            case MotionEvent.ACTION_DOWN:
                textView.setText("Touch Down"); //Touch debugging
                    if (node1.contains(touchX, touchY)) { //Refer to PebbleNode.contains()
                        alert(1); //Debugging method
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node1);
                        cntNode12.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode14.setSelected(true);
                        cntNode16.setSelected(true);
                        cntNode12.postInvalidate(); //Invalidate to redraw as new color
                        cntNode14.postInvalidate();
                        cntNode16.postInvalidate();
                    }
                    if (node2.contains(touchX, touchY)) {
                        alert(2); //Debugging method
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node2);
                        cntNode12.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode25.setSelected(true);
                        cntNode12.postInvalidate(); //Invalidate to redraw as new color
                        cntNode25.postInvalidate();
                    }
                    if (node3.contains(touchX, touchY)) {
                        alert(3); //Debugging method
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node3);
                        cntNode34.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode36.setSelected(true);
                        cntNode34.postInvalidate(); //Invalidate to redraw as new color
                        cntNode36.postInvalidate();
                    }
                    if (node4.contains(touchX, touchY)) {
                        alert(4); //Debugging method
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node4);
                        cntNode14.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode34.setSelected(true);
                        cntNode45.setSelected(true);
                        cntNode14.postInvalidate(); //Invalidate to redraw as new color
                        cntNode34.postInvalidate();
                        cntNode45.postInvalidate();
                    }
                    if (node5.contains(touchX, touchY)) {
                        alert(5); //Debugging method
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node5);
                        cntNode25.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode45.setSelected(true);
                        cntNode56.setSelected(true);
                        cntNode25.postInvalidate(); //Invalidate to redraw as new color
                        cntNode45.postInvalidate();
                        cntNode56.postInvalidate();
                    }
                    if (node6.contains(touchX, touchY)) {
                        alert(6); //Debugging method
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node6);
                    }
                break;
            //Action_Move happens when your finger moves on the screen while pressed down
            case MotionEvent.ACTION_MOVE:
                textView.setText("Touch Move"); //Touch debugging
                //When swiping across the screen this checks if there was a last node selected
                if(gameRules.getLastNode() != null) {
                    movingPebbles = true;
                } else {
                    movingPebbles = false;
                }
                break;
            //Action_Up happens when your finger leaves the screen
            case MotionEvent.ACTION_UP:
                textView.setText("Touch Up"); //Touch debugging
                // The following activates when another node was selected before the node that was just touched, signifying a move.
                //Checks if the nodes are connected via connected nodes in game rules
                if(movingPebbles){
                    if (node1.contains(touchX, touchY) &&
                            gameRules.checkPebbleMove(gameRules.getLastNode(), node1) &&
                            !(gameRules.checkIllegalDefenderMove(gameRules.getLastNode(), node1))) { //Refer to PebbleNode.contains() and GameRules.checkPebbleMove()
                        gameRules.getLastNode().movePebbles(node1);
                        gameRules.setLastMoveFrom(gameRules.getLastNode());
                        gameRules.setLastMoveTo(node1);
                        gameRules.setLastNode(null);
                        gameRules.swapTurns();
                        resetLines();
                        movingPebbles = false;
                    }
                    if (node2.contains(touchX, touchY) &&
                            gameRules.checkPebbleMove(gameRules.getLastNode(), node2) &&
                            !(gameRules.checkIllegalDefenderMove(gameRules.getLastNode(), node2))) {
                        gameRules.getLastNode().movePebbles(node2);
                        gameRules.setLastMoveFrom(gameRules.getLastNode());
                        gameRules.setLastMoveTo(node2);
                        gameRules.setLastNode(null);
                        gameRules.swapTurns();
                        resetLines();
                        movingPebbles = false;
                    }
                    if (node3.contains(touchX, touchY) &&
                            gameRules.checkPebbleMove(gameRules.getLastNode(), node3) &&
                            !(gameRules.checkIllegalDefenderMove(gameRules.getLastNode(), node3))) {
                        gameRules.getLastNode().movePebbles(node3);
                        gameRules.setLastMoveFrom(gameRules.getLastNode());
                        gameRules.setLastMoveTo(node3);
                        gameRules.setLastNode(null);
                        gameRules.swapTurns();
                        resetLines();
                        movingPebbles = false;
                    }
                    if (node4.contains(touchX, touchY) &&
                            gameRules.checkPebbleMove(gameRules.getLastNode(), node4) &&
                            !(gameRules.checkIllegalDefenderMove(gameRules.getLastNode(), node4))) {
                        gameRules.getLastNode().movePebbles(node4);
                        gameRules.setLastMoveFrom(gameRules.getLastNode());
                        gameRules.setLastMoveTo(node4);
                        gameRules.setLastNode(null);
                        gameRules.swapTurns();
                        resetLines();
                        movingPebbles = false;
                    }
                    if (node5.contains(touchX, touchY) &&
                            gameRules.checkPebbleMove(gameRules.getLastNode(), node5) &&
                            !(gameRules.checkIllegalDefenderMove(gameRules.getLastNode(), node5))) {
                        gameRules.getLastNode().movePebbles(node5);
                        gameRules.setLastMoveFrom(gameRules.getLastNode());
                        gameRules.setLastMoveTo(node5);
                        gameRules.setLastNode(null);
                        gameRules.swapTurns();
                        resetLines();
                        movingPebbles = false;
                    }
                    if (node6.contains(touchX, touchY) &&
                            gameRules.checkPebbleMove(gameRules.getLastNode(), node6) &&
                            !(gameRules.checkIllegalDefenderMove(gameRules.getLastNode(), node6))) {
                        gameRules.getLastNode().movePebbles(node6);
                        gameRules.setLastMoveFrom(gameRules.getLastNode());
                        gameRules.setLastMoveTo(node6);
                        gameRules.setLastNode(null);
                        gameRules.swapTurns();
                        resetLines();
                        movingPebbles = false;
                    }
                    else {
                        resetLines();
                        movingPebbles = false;
                    }
                }
                break;
        }
        return super.onTouchEvent(event); //Don't touch this
    }
}
