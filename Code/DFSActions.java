/*
 * File: DFSActions.java
 * Author: Ben Sutter
 * Date: October 8th, 2020
 * Purpose: Generic interface with four method signatures for implementation in Hierarchy and parenthesizedList
 */

package project4;

public interface DFSActions<V> {
    
    public void cycleDetected();

    public void processVertex(V vertex);

    public void descend(V vertex);

    public void ascend(V vertex);

}
