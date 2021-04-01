/*
 * File: Project4.java
 * Author: Ben Sutter
 * Date: October 8th, 2020
 * Purpose: Create a DirectedGraph object and fill it with classes from the incoming file
 */

package project4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Project4 {

    public static void main(String[] args) throws FileNotFoundException {

        DirectedGraph graph = new DirectedGraph();//Initializes the graph to be be added to as lines are read in
        //Creates new file chooser that will open to the default directory
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());

        int userApproval = jfc.showOpenDialog(null);//Initializes variable for approval later on

        if (userApproval == JFileChooser.APPROVE_OPTION) {//Changes userApproval to start program if a file is chosen
            File theChosenOne = jfc.getSelectedFile();
            BufferedReader reader = new BufferedReader(new FileReader(theChosenOne));//Creates new reader to read the selected file

            //Tries to open file, if invalid the user will be met with an exception
            try (Scanner dataReader = new Scanner(reader)) {
                // Reads file and adds data to arrays
                while (dataReader.hasNext()) {
                    String incomingClass = dataReader.nextLine();
                    String[] classes = incomingClass.split("\\s+");//Splits the current line into an array of individual classes
                    //If there is not a first vertex to start the Depth First Search it makes sure there will be one
                    if (graph.getFirstVertex() == null) {
                        graph.setFirstVertex(graph.getVertex(classes[0]));
                    }
                    //Since 0 (first class) will always be the main class, add classes(i) as its dependent classes
                    for (int i = 1; i < classes.length; i++) {
                        graph.addEdge(classes[0], classes[i]);
                    }

                }
            } catch (Exception e) {
                System.out.println(e);//Prints the exception message
            }
        }

        graph.depthFirst(graph.getFirstVertex()); //Performs the DFS recursive search starting with the first vertex.
        graph.showGraphInfo();//Displays Hierarchal and Parenthesized representation of the graph, and any unreachable classes

    }
}
