package svc.com.graphgame.GameFiles;

import android.content.Context;
import android.graphics.LinearGradient;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import svc.com.graphgame.Maps.FirstMap;

/**
 * Created by Mac Mittereder on 4/11/2016.
 * Do not change, only for testing
 */
public class DefendAI {

    GameRules rules;
    ArrayList<ConnectNodes> cntNodes;
    ArrayList<PebbleNode> nodes, priorityNodes;
    PebbleNode goalNode;

    public DefendAI (GameRules rules) {
        this.rules = rules;
        cntNodes = rules.getListOfConnectedNodes();
        nodes = rules.getListOfPebbleNodes();
        priorityNodes = new ArrayList<>();
        goalNode = rules.goalNode;

        findPriorityNodes();
        Log.d("Found priority nodes: ", priorityNodes.toString());
    }

    public void takeTurn(){
        PebbleNode bestMoveStart = null, bestMoveDestination = null;
        int destinationPebbles = 2000;
        boolean priority = false;
        ArrayList<PebbleNode> possibleMoves = new ArrayList<>();

        Log.d("Turn", "Defender Taking Turn");
        //Check chance
        Log.d("Check chances", "Defender");
        int count = 0;
        for (PebbleNode node : priorityNodes) {
            if (node.getPebbles() >= 2)
                count++;
        }
        if (count > 1 &&
                rules.getTurn() == false) {
            //Accept defeat
            Log.d("Accept defeat", "GG WP");
            for (PebbleNode node : priorityNodes) {
                if (node.getPebbles() >= 2 &&
                        tryMove(node, goalNode))
                        makeMove(node, goalNode);
                    break;
            }
        }

        Log.d("No lose", "Going on");
        if (!rules.checkLose() &&
                goalNode.getPebbles() == 0 &&
                rules.getTurn() == false) {
            //Check priority nodes first
            for (PebbleNode node : priorityNodes) {
                Log.d("Check Priority Node: ", node.toString());
                if (node.getPebbles() >= 2) {
                    Log.d("Defend priority: ", node.toString() + " has " + node.getPebbles() + " pebbles");
                    possibleMoves.clear();
                    possibleMoves = findPossibleMoves(node);
                    Log.d("Moves for " + node.toString(), possibleMoves.toString());

                    for (PebbleNode findBestMove : possibleMoves) {
                        if (destinationPebbles >= findBestMove.getPebbles()  &&
                                !priorityNodes.contains(findBestMove) &&
                                tryMove(node, findBestMove)) {
                            bestMoveDestination = findBestMove;
                            bestMoveStart = node;
                            priority = true;
                        }
                    }
                    if (bestMoveDestination != null)
                        Log.d("Current Best Move: ",  bestMoveStart.toString() + " to " + bestMoveDestination.toString());
                    else
                        Log.d("Priority " + node.toString(), "safe");
                }
            }
            possibleMoves.clear();

            if (bestMoveDestination == null && priority == false) {
                priority = true;

                //Have to make a move so go with least cost
                for (PebbleNode node : nodes) {
                    if (node.getPebbles() >= 2) { //Pebbles can be moved in current node check
                        possibleMoves.clear();
                        possibleMoves = findPossibleMoves(node); //Find possible moves from current node
                        Log.d("Moves for " + node.toString(), possibleMoves.toString());
                        for (PebbleNode possibleMove : possibleMoves) {
                            Log.d("Smart move", "Trying " + node.toString() + " to " + possibleMove.toString());
                            if (priorityNodes.contains(possibleMove) &&
                                    tryMove(node, possibleMove) &&
                                    destinationPebbles > possibleMove.getPebbles() &&
                                    priority == true) {
                                //Moving to priority node with 0 pebbles and destination pebbles must be greater than priority pebbles
                                Log.d("Picked Priority", "worst case, looking for non primary");
                                priority = true; //Boolean for priority since this is the worst case
                                bestMoveDestination = possibleMove;
                                bestMoveStart = node;
                                destinationPebbles = possibleMove.getPebbles();
                            } else if (tryMove(node, possibleMove) &&
                                    !priorityNodes.contains(possibleMove) &&
                                    destinationPebbles > possibleMove.getPebbles()) {
                                Log.d("Picked Other", "Picked a low threat node");
                                priority = false;
                                bestMoveDestination = possibleMove;
                                bestMoveStart = node;
                                destinationPebbles = possibleMove.getPebbles();
                            }
                        } if (bestMoveDestination != null)
                        Log.d("Current best move: ", bestMoveStart.toString() + " to " + bestMoveDestination.toString());
                    }
                }
            }
            possibleMoves.clear();

            if (bestMoveDestination != null) {
                Log.d("Best Move Found: ", bestMoveStart.toString() + " to " + bestMoveDestination.toString());
                makeMove(bestMoveStart, bestMoveDestination);
            } else {
                Log.d("?", "I don't want to move");
            }
        }
    }

    public boolean tryMove(PebbleNode node, PebbleNode bestMove) {
        Log.d("Trying Move", node.toString() + " to " + bestMove.toString());
        if (rules.getTurn())
            return false;
        if (rules.lastNodeMovedTo == node && rules.lastNodeMovedFrom == bestMove) {
            Log.d("Trying Move Failed", "Can't undo attacker move");
            return false;
        }
        if (!rules.checkPebbleMove(node, bestMove)) {
            Log.d("Trying Move failed", "Can't move there");
            return false;
        }
        if (node == goalNode || bestMove == goalNode) {
            Log.d("Trying Move failed", "Not moving pebbles to goal node");
            return false;
        }
        return true;
    }

    public void makeMove(PebbleNode start, PebbleNode destination) {
        rules.setLastMoveFrom(start);
        start.movePebbles(destination);
        rules.setLastNode(null);

        rules.swapTurn();
    }

    public void findPriorityNodes() {
        for (ConnectNodes cntNode : cntNodes) {
            if (cntNode.node1 == goalNode || cntNode.node2 == goalNode) {
                if (cntNode.node1 != goalNode) {
                    priorityNodes.add(cntNode.node1);
                } else {
                    priorityNodes.add(cntNode.node2);
                }
            }
        }
    }

    public ArrayList<PebbleNode> findPossibleMoves(PebbleNode node) {
        ArrayList<PebbleNode> possibleMoves = new ArrayList<>();

        Log.d("PM", node.toString());
        for (ConnectNodes cntNode : cntNodes) {
            Log.d("PM", cntNode.toString());

            if (node == cntNode.node1) {
                Log.d("PM IF", node.toString() + " 1= " + cntNode.node1.toString());

                if (cntNode.node1 != goalNode || cntNode.node2 != goalNode &&
                        !(possibleMoves.contains(cntNode.node1) || possibleMoves.contains(cntNode.node2))) {
                    Log.d("PM", cntNode.toString() + " Approved connection");
                    if (cntNode.node1 != node) {
                        Log.d("PM", "Adding " + cntNode.node1);
                        possibleMoves.add(cntNode.node1);
                    } else if (cntNode.node2 != node){
                        Log.d("PM", "Adding " + cntNode.node2);
                        possibleMoves.add(cntNode.node2);
                    }
                }
            } else if ( node == cntNode.node2) {
                Log.d("PM IF", node.toString() + " 2= " + cntNode.node2.toString());

                if (cntNode.node1 != goalNode || cntNode.node2 != goalNode &&
                        !(possibleMoves.contains(cntNode.node1) || possibleMoves.contains(cntNode.node2))) {
                    Log.d("PM", cntNode.toString() + " Approved connection");
                    if (cntNode.node1 != node) {
                        Log.d("PM", "Adding " + cntNode.node1);
                        possibleMoves.add(cntNode.node1);
                    } else if (cntNode.node2 != node){
                        Log.d("PM", "Adding " + cntNode.node2);
                        possibleMoves.add(cntNode.node2);
                    }
                }
            } else {
                Log.d("PM IF", "Not Equal");
            }
/*
            if ((node == cntNode.node1 || node == cntNode.node2) &&
                    cntNode.node1 != goalNode || cntNode.node2 != goalNode &&
                    !(possibleMoves.contains(cntNode.node1) || possibleMoves.contains(cntNode.node2))) {
                Log.d("PM", cntNode.toString() + " Approved connection");
                if (cntNode.node1 != node) {
                    Log.d("PM", "Adding " + cntNode.node1);
                    possibleMoves.add(cntNode.node1);
                } else if (cntNode.node2 != node){
                    Log.d("PM", "Adding " + cntNode.node2);
                    possibleMoves.add(cntNode.node2);
                }
            } */
        }

        return possibleMoves;
    }
}
