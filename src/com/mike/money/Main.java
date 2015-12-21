package com.mike.money;

/**
 * Created by mike on 12/19/2015.
 */
public class Main {
    static public Simulation simulation;

    static public void main(String[] args) {
        simulation = new Simulation(args);
        simulation.run ();
    }
}
