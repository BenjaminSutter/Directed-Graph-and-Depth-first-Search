/*
 * File: ParenthesizedList.java
 * Author: Ben Sutter
 * Date: October 8th, 2020
 * Purpose: Perform actions to create a parenthesized representation of the incoming graph
 */

package project4;

import java.util.LinkedList;
import java.util.Queue;
import project4.DirectedGraph.Vertex;

class ParenthesizedList implements DFSActions<Vertex> {

    //Holds all incoming values and manipulates them based on certain criteria
    Queue<String> res = new LinkedList<>();

    @Override//Add * to represent circular dependency
    public void cycleDetected() {
        res.add("*");
    }

    @Override//Retrie vevertex
    public void processVertex(Vertex vertex) {
        res.add(vertex.toString());
    }

    @Override//Add ( to signify descend action
    public void descend(Vertex vertex) {
        res.add("(");
    }

    @Override//Add ) to signify ascend action
    public void ascend(Vertex vertex) {
        res.add(")");
    }

    @Override
    public String toString() {

        String graph = "\n( ";

        while (res.size() > 0) {
            String c = res.peek();//Sets value to the head of the queue
            res.remove();//Removes the head (c) from the queue

            if (c == "(") {
                if (res.peek() == ")") {
                    res.remove();
                    continue;
                } else if (res.peek() == "*") {
                    graph += res.peek() + " ";
                    res.remove();
                    res.remove();
                    continue;
                }

            }
            graph += c + " ";
        }
        graph += ")";
        return graph; //After all checks have been performed, return final product

    }/*Code heavily inspired by https://stackoverflow.com/questions/64310564/how-come-when-i-try-to-compile-vertex-java-i-get-this-error-file-not-found-ve 
    (I only did some small edits)*/

}//End ParenthesizedList.java
