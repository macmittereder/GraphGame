package svc.com.graphgame.Maps;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;

import svc.com.graphgame.GameFiles.ConnectNodes;
import svc.com.graphgame.GameFiles.GameRules;
import svc.com.graphgame.GameFiles.PebbleNode;
import svc.com.graphgame.R;



/*
* Created 3/31/2016 by Ryan Taylor
* This is the map list class
*/

public class SecondMap extends Activity {

    /*
    * TODO: Fix Node Dimensions
    */

    PebbleNode node1, node2, node3, node4, node5, node6, node7;
    int width, height;
    RelativeLayout relativeLayout;
    ConnectNodes cntNode12, cntNode16, cntNode23, cntNode24, cntNode25,
            cntNode35, cntNode46, cntNode47, cntNode57, cntNode67;
    GameRules gameRules;
    boolean movingPebbles = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //Removes window title bar
        setContentView(R.layout.game_map);// Set layout to the second map layout
        gameRules = new GameRules(this, 2); //Initialize game rules

        //Initialize layout
        relativeLayout = (RelativeLayout) findViewById(R.id.mapGameView);

        //Retrieving dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        //These need adjusted from first map
        //Creating pebble nodes with (Context(Just put 'this'), TopX, TopY, BottomX, BottomY, number of pebbles to start with, boolean if this is the goal node
        //Refer to the PebbleNode class
        node1 = new PebbleNode(this, 175, 180, 375, 380, 6, false);
        node2 = new PebbleNode(this, 550, 180, 750, 380, 7, false);
        node3 = new PebbleNode(this, 850, 600, 1050, 800, 8, false);
        node4 = new PebbleNode(this, 400, 600, 600, 800, 0, false);
        node5 = new PebbleNode(this, 625, 1000, 825, 1200, 0, false);
        node6 = new PebbleNode(this, 50, 600, 250, 800 , 0, false);
        node7 = new PebbleNode(this, 250, 1000, 450, 1200, 0, true); //this is the goal node


        //Creating lines to connect the nodes with (Context(Just put 'this'), first node, second node
        //Refer to the ConnectNodes class
        cntNode12 = new ConnectNodes(this, node1, node2);
        cntNode16 = new ConnectNodes(this, node1, node6);
        cntNode23 = new ConnectNodes(this, node2, node3);
        cntNode24 = new ConnectNodes(this, node2, node4);
        cntNode25 = new ConnectNodes(this, node2, node5);
        cntNode35 = new ConnectNodes(this, node3, node5);
        cntNode46 = new ConnectNodes(this, node4, node6);
        cntNode47 = new ConnectNodes(this, node4, node7);
        cntNode57 = new ConnectNodes(this, node5, node7);
        cntNode67 = new ConnectNodes(this, node6, node7);


        //Add connections to game rules
        gameRules.addConnectedNodes(cntNode12);
        gameRules.addConnectedNodes(cntNode16);
        gameRules.addConnectedNodes(cntNode23);
        gameRules.addConnectedNodes(cntNode24);
        gameRules.addConnectedNodes(cntNode25);
        gameRules.addConnectedNodes(cntNode35);
        gameRules.addConnectedNodes(cntNode46);
        gameRules.addConnectedNodes(cntNode47);
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
        for (ConnectNodes cntNode : gameRules.getListOfConnectedNodes()) {
            cntNode.setSelected(false);
            cntNode.postInvalidate();
        }
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
                        cntNode16.setSelected(true);
                        cntNode12.postInvalidate(); //Invalidate to redraw as new color
                        cntNode16.postInvalidate();
                    }
                    if (node2.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node2);
                        cntNode12.setSelected(true);
                        cntNode23.setSelected(true);
                        cntNode24.setSelected(true);//Sets selected boolean to true to change colors
                        cntNode25.setSelected(true);
                        cntNode12.postInvalidate(); //Invalidate to redraw as new color
                        cntNode23.postInvalidate();
                        cntNode24.postInvalidate();
                        cntNode25.postInvalidate();
                    }
                    if (node3.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node3);
                        cntNode35.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode23.setSelected(true);
                        cntNode35.postInvalidate(); //Invalidate to redraw as new color
                        cntNode23.postInvalidate();
                    }
                    if (node4.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node4);
                        cntNode24.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode46.setSelected(true);
                        cntNode47.setSelected(true);
                        cntNode24.postInvalidate(); //Invalidate to redraw as new color
                        cntNode46.postInvalidate();
                        cntNode47.postInvalidate();
                    }
                    if (node5.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node5);
                        cntNode25.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode35.setSelected(true);
                        cntNode57.setSelected(true);
                        cntNode25.postInvalidate(); //Invalidate to redraw as new color
                        cntNode35.postInvalidate();
                        cntNode57.postInvalidate();
                    }
                    if (node6.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node6);
                        cntNode16.setSelected(true); //Sets selected boolean to true to change colors
                        cntNode46.setSelected(true);
                        cntNode67.setSelected(true);
                        cntNode16.postInvalidate(); //Invalidate to redraw as new color
                        cntNode46.postInvalidate();
                        cntNode67.postInvalidate();
                    }
                    if (node7.contains(touchX, touchY)) {
                        resetLines(); //Resets lines back to black
                        gameRules.setLastNode(node7);
                    }
                }
                break;
            //Action_Move happens when your finger moves on the screen while pressed down
            case MotionEvent.ACTION_MOVE:
                //When swiping across the screen this checks if there was a last node selected
                if(gameRules.getLastNode() != null)
                    movingPebbles = true;
                else
                    movingPebbles = false;
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
                            !(gameRules.checkIllegalDefenderMove(gameRules.getLastNode(), node7))) {
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
                else
                    movingPebbles = true;
                break;
        }
        return super.onTouchEvent(event); //Don't touch this
    }
}
