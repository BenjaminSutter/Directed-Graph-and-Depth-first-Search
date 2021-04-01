/*
 * File: Hierarchy.java
 * Author: Ben Sutter
 * Date: October 8th, 2020
 * Purpose: Perform actions to create a hierarchal representation of the incoming graph
 */

package project4;

import java.util.LinkedList;
import java.util.Queue;
import project4.DirectedGraph.Vertex;

class Hierarchy implements DFSActions<Vertex> {

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

    @Override//Add v to signify descend action
    public void descend(Vertex vertex) {
        res.add("v");
    }

    @Override//Add ^ to signify descend action
    public void ascend(Vertex vertex) {
        res.add("^");

    }

    @Override
    public String toString() {

        String graph = "";

        //use to keep track of how far indented each class should be
        int indents = 0;

        while (res.size() != 0) {
            String c = res.peek();//Sets value to the head of the queue
            res.remove();//Removes the head (c) from the queue

            //If ascend or descend character is detected, increase or decrease indents and ignore
            if (c == "v") {
                indents++;
                continue;
            } else if (c == "^") {
                --indents;
                continue;
            }
            //If it isn't * then new line and tab, otherwuse add * to signify dependence
            if (c != "*") {
                graph += "\n";
                for (int i = 0; i < indents; i++) {
                    graph += "    ";
                }
            }
            graph += c + " ";
        }
        return graph;//After all checks have been performed, return final product
    }/*Code heavily inspired by https://stackoverflow.com/questions/64310564/how-come-when-i-try-to-compile-vertex-java-i-get-this-error-file-not-found-ve 
    (I only did some small edits)*/

}//End Hierarchy.java
