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
* Created 4/2/2016 by Adam DeMarchi
* This is the third map class
*/

public class ThirdMap extends Activity {

    /*
    * TODO: Bring lines from selected node to front
    * TODO: (fix) Sometimes on touch_down the node won't be highlighted, probably caused by touch_move
    * TODO: Create ratio to resize nodes better on different screen sizes
    * TODO: Create better description of class
    * TODO: Add visible pebbles in Action_Move to follow your finger to place the pebbles
    */

    //Create global variables and import classes
    PebbleNode node1, node2, node3, node4, node5, node6, node7;
    int width, height;
    RelativeLayout relativeLayout;
    ConnectNodes cntNode12, cntNode14, cntNode23, cntNode24, cntNode25,
            cntNode34, cntNode35, cntNode36, cntNode45, cntNode47, cntNode56, cntNode57, cntNode67;
    GameRules gameRules;
    boolean movingPebbles = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Don't change the following lines
        requestWindowFeature(Window.FEATURE_NO_TITLE); //Removes window title bar
        setContentView(R.layout.game_map); //Set layout to the first map layout
        gameRules = new GameRules(this, 3); //Initialize game rules

        //Initialize layout
        relativeLayout = (RelativeLayout) findViewById(R.id.mapGameView);

        //Retrieving dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        //Creating pebble nodes with (Context(Just put 'this'), TopX, TopY, BottomX, BottomY, number of pebbles to start with, boolean if this is the goal node
        //Refer to the PebbleNode class
        //All measurements here are expressed in terms of width and height to account for varying screen sizes. The screen is divided into 7 imaginary segments
        //with each node taking up 1/7th of the width and height of the screen, with a 1/7th wide gap between each node.
        node1 = new PebbleNode(this, 100, 100, 400, 400, 15, false);
        node2 = new PebbleNode(this, (width / 2) - 150, 100, (width / 2) + 150, 400, 12, false);
        node3 = new PebbleNode(this, width - 400, 100, width - 100, 400, 13, false);
        node4 = new PebbleNode(this, 100, 650, 400, 950, 0, false);
        node5 = new PebbleNode(this, (width / 2) - 150, 650, (width / 2) + 150, 950, 0, false);
        node6 = new PebbleNode(this, width - 400, 650, width - 100, 950, 0, false);
        node7 = new PebbleNode(this, (width / 2) - 150, 1200, (width / 2) + 150, 1500, 0, true);//This is goal node so set to true

        //Creating lines to connect the nodes with (Context(Just put 'this'), first node, second node
        //Refer to the ConnectNodes class
        cntNode12 = new ConnectNodes(this, node1, node2);
        cntNode14 = new ConnectNodes(this, node1, node4);
        cntNode23 = new ConnectNodes(this, node2, node3);
        cntNode24 = new ConnectNodes(this, node2, node4);
        cntNode25 = new ConnectNodes(this, node2, node5);
        cntNode34 = new ConnectNodes(this, node3, node4);
        cntNode35 = new ConnectNodes(this, node3, node5);
        cntNode36 = new ConnectNodes(this, node3, node6);
        cntNode45 = new ConnectNodes(this, node4, node5);
        cntNode47 = new ConnectNodes(this, node4, node7);
        cntNode56 = new ConnectNodes(this, node5, node6);
        cntNode57 = new ConnectNodes(this, node5, node7);
        cntNode67 = new ConnectNodes(this, node6, node7);

        //Add connections to the game rules
        gameRules.addConnectedNodes(cntNode12);
        gameRules.addConnectedNodes(cntNode14);
        gameRules.addConnectedNodes(cntNode23);
        gameRules.addConnectedNodes(cntNode24);
        gameRules.addConnectedNodes(cntNode25);
        gameRules.addConnectedNodes(cntNode34);
        gameRules.addConnectedNodes(cntNode35);
        gameRules.addConnectedNodes(cntNode36);
        gameRules.addConnectedNodes(cntNode45);
        gameRules.addConnectedNodes(cntNode47);
        gameRules.addConnectedNodes(cntNode56);
        gameRules.addConnectedNodes(cntNode57);
        gameRules.addConnectedNodes(cntNode67);

        //Add pebble nodes to game rules
        gameRules.addPebbleNodes(node1);
        gameRules.addPebbleNodes(node2);
        gameRules.addPebbleNodes(node3);
        gameRules.addPebbleNodes(node4);
        gameRules.addPebbleNodes(node5);
        gameRules.addPebbleNodes(node6);
        gameRules.addPebbleNodes(node7);

        //Add connections to view first to show under the nodes
        for (ConnectNodes cntNode : gameRules.getListOfConnectedNodes())
            relativeLayout.addView(cntNode);

        //Add nodes to view
        for (PebbleNode node : gameRules.getListOfPebbleNodes())
            relativeLayout.addView(node);

        //Set goal node in game rules
        gameRules.setGoalNode(node7);
        gameRules.setScreenSize(width, height);

        //Set game rules to front to display win
        relativeLayout.addView(gameRules);
        gameRules.bringToFront();
    }

    //This is a class to reset the lines back to black
    //.postInvalidate() redraws the item after the set selected is set to false
    public void resetLines(){
        cntNode12.setSelected(false);
        cntNode14.setSelected(false);
        cntNode23.setSelected(false);
        cntNode24.setSelected(false);
        cntNode25.setSelected(false);
        cntNode34.setSelected(false);
        cntNode35.setSelected(false);
        cntNode36.setSelected(false);
        cntNode45.setSelected(false);
        cntNode47.setSelected(false);
        cntNode56.setSelected(false);
        cntNode57.setSelected(false);
        cntNode67.setSelected(false);
        cntNode12.postInvalidate();
        cntNode14.postInvalidate();
        cntNode23.postInvalidate();
        cntNode24.postInvalidate();
        cntNode25.postInvalidate();
        cntNode34.postInvalidate();
        cntNode35.postInvalidate();
        cntNode36.postInvalidate();
        cntNode45.postInvalidate();
        cntNode47.postInvalidate();
        cntNode56.postInvalidate();
        cntNode57.postInvalidate();
        cntNode67.postInvalidate();
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
                if (!(movingPebbles)) {
                    if (node1.contains(touchX, touchY)) { //Refer to PebbleNode.contains()
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node1);
                        cntNode12.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode14.setSelected(true);
                        cntNode12.postInvalidate(); //Invalidate to redraw as new color
                        cntNode14.postInvalidate();
                    }
                    if (node2.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node2);
                        cntNode12.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode23.setSelected(true);
                        cntNode24.setSelected(true);
                        cntNode25.setSelected(true);
                        cntNode12.postInvalidate(); //Invalidate to redraw as new color
                        cntNode23.postInvalidate();
                        cntNode24.postInvalidate();
                        cntNode25.postInvalidate();
                    }
                    if (node3.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node3);
                        cntNode23.setSelected(true);
                        cntNode34.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode35.setSelected(true);
                        cntNode36.setSelected(true);
                        cntNode23.postInvalidate();
                        cntNode34.postInvalidate(); //Invalidate to redraw as new color
                        cntNode35.postInvalidate();
                        cntNode36.postInvalidate();
                    }
                    if (node4.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node4);
                        cntNode14.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode24.setSelected(true);
                        cntNode34.setSelected(true);
                        cntNode45.setSelected(true);
                        cntNode47.setSelected(true);
                        cntNode14.postInvalidate(); //Invalidate to redraw as new color
                        cntNode24.postInvalidate();
                        cntNode34.postInvalidate();
                        cntNode45.postInvalidate();
                        cntNode47.postInvalidate();
                    }
                    if (node5.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node5);
                        cntNode25.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode35.setSelected(true);
                        cntNode45.setSelected(true);
                        cntNode56.setSelected(true);
                        cntNode57.setSelected(true);
                        cntNode25.postInvalidate(); //Invalidate to redraw as new color
                        cntNode35.postInvalidate();
                        cntNode45.postInvalidate();
                        cntNode56.postInvalidate();
                        cntNode57.postInvalidate();
                    }
                    if (node6.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node6);
                        cntNode36.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode56.setSelected(true);
                        cntNode67.setSelected(true);
                        cntNode36.postInvalidate(); //Invalidate to redraw as new color
                        cntNode56.postInvalidate();
                        cntNode67.postInvalidate();
                    }
                    if (node7.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(null);
                    }
                }
                break;
            //Action_Move happens when your finger moves on the screen while pressed down
            case MotionEvent.ACTION_MOVE:
                //When swiping across the screen this checks if there was a last node selected
                if(gameRules.getLastNode() != null) {
                    movingPebbles = true;
                } else {
                    movingPebbles = false;
                }
                break;
            //Action_Up happens when your finger leaves the screen
            case MotionEvent.ACTION_UP:
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
                        resetLines();
                        movingPebbles = false;
                    }
                    if (node7.contains(touchX, touchY) &&
                            gameRules.checkPebbleMove(gameRules.getLastNode(), node7) &&
                            !(gameRules.checkIllegalDefenderMove(gameRules.getLastNode(), node7))) { //Refer to PebbleNode.contains() and GameRules.checkPebbleMove()
                        gameRules.getLastNode().movePebbles(node7);
                        gameRules.setLastMoveFrom(gameRules.getLastNode());
                        gameRules.setLastMoveTo(node7);
                        gameRules.setLastNode(null);
                        resetLines();
                        movingPebbles = false;
                    }
                    else {
                        resetLines();
                        movingPebbles = false;
                    }
                }
                else if (gameRules.getLastNode() != null)
                    movingPebbles = true;
                break;
        }
        return super.onTouchEvent(event); //Don't touch this
    }
}
