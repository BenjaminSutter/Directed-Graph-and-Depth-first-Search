/*
 * File: DirectedGraph.java
 * Author: Ben Sutter
 * Date: October 8th, 2020
 * Purpose: Creates the vertex class and holds various methods to create a DirectedGraph.
 * Holds the depthFirst method which uses other methods within the class and from Hierarchy.java and ParenthesizedList.java
 */

package project4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DirectedGraph<V> {

    //Holds the value of what will start the depthFirstSearch
    private V firstVertex = null;
    //Stores all vertices
    Map<String, V> vertexMap = new HashMap();
    //Adjacent list
    Map<V, ArrayList<V>> adjacentList = new HashMap();

    //ArrayLists to keep track of visited vertecies
    ArrayList<V> visitedVertices = new ArrayList();
    ArrayList<V> foundVertices = new ArrayList();

    //Initializes the Hierarchy and Parenthesized representations for use in depthFirst
    Hierarchy hierarchyList = new Hierarchy();
    ParenthesizedList paraList = new ParenthesizedList();

    //Gives way to acces private variable
    public V getFirstVertex() {
        return firstVertex;
    }

    //Gives way to change private variable
    public V setFirstVertex(V vert) {
        firstVertex = vert;
        return firstVertex;
    }

    //Creates the Vertex class to define a vertex
    public class Vertex {

        private String label;

        public Vertex(String label) {
            this.label = label;
        }

        public String toString() {
            return this.label;
        }
    }//End vertex

    public V getVertex(String vert) {
        //If u is not already in vertexMap, add it to it (also ensures no null return)
        if (!vertexMap.containsKey(vert)) {
            vertexMap.put(vert, (V) new Vertex(vert));
        }
        return vertexMap.get(vert);//
    }

    public void addEdge(String vert1, String vert2) {

        //Checks if first vertex already has other values tied to it
        ArrayList<V> list = adjacentList.get(getVertex(vert1));

        //If no values are mapped then initialize list
        if (list == null) {
            list = new ArrayList();
        }
        //Add vertex2 to list
        list.add(getVertex(vert2));
        //Copy changes to adjacentList
        adjacentList.put(getVertex(vert1), list);

    }//End addEdge

    public void depthFirst(V vertex) {

        //If vertex is already found, do cycleDetected actions and then return
        if (foundVertices.contains(vertex)) {
            paraList.cycleDetected();
            hierarchyList.cycleDetected();
            return;
        }

        //Next step is to perform processVertex (addvertex) action
        paraList.processVertex((Vertex) vertex);
        hierarchyList.processVertex((Vertex) vertex);

        foundVertices.add(vertex);//Mark the vertex as found
        visitedVertices.add(vertex);//Mark vertex as visited to determine if all classes were reached

        //Then perform descend action
        paraList.descend((Vertex) vertex);
        hierarchyList.descend((Vertex) vertex);

        //DepthFirst search for all adjacent vertices
        ArrayList<V> list = adjacentList.get(vertex);
        if (list != null) {
            for (V v : list) {
                depthFirst(v);
            }
        }

        //Preform ascend actions
        paraList.ascend((Vertex) vertex);
        hierarchyList.ascend((Vertex) vertex);

        //Remove the vertex from foundVertices to mark as finished
        foundVertices.remove(vertex);

    }//End depthFirst

    boolean allReachable = true;//If any classes are uncreachable, the count goes up
    String unreachableClasses = "";//Stores any unreachable classes

    //Prints the toString of Hierarchy and Parenthesized and displays all unreached classes
    public void showGraphInfo() {
        //Runs through the list of all vertices to see if they have been visited or not
        for (Map.Entry<V, ArrayList<V>> entry : adjacentList.entrySet()) {
            //If the vertex has not been visited, concat it to the unreachableClasses string
            if (!visitedVertices.contains(entry.getKey())) {
                unreachableClasses += "\n" + entry.getKey() + " is unreachable.";
                allReachable = false;
            }
        }
        //Combines the toString methods from Hierarchy and Parenthesized
        System.out.println("Hierarchy: " + hierarchyList.toString() + "\n"
                + "\nParenthesized: " + paraList.toString());
        if (allReachable == true)//If the count has not gone up, then all classes are reachable.
        {
            System.out.println("\nAll classes are reachable");
        } else {
            System.out.println(unreachableClasses);
        }
    }//End showGraphInfo

}//End DirectedGraph.java

/*Vague references: https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
https://www.geeksforgeeks.org/graph-and-its-representations/
https://www.baeldung.com/java-graphs
These references helped me understand more about directed graphs, the final product looks very different
from the references, but I figured I'd list them anyways 

https://stackoverflow.com/questions/64310564/how-come-when-i-try-to-compile-vertex-java-i-get-this-error-file-not-found-ve
By going through this code, I found a few ways my code could be optimized so some aspects of the code were influenced by it.
 */
