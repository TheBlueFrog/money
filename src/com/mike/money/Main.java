package com.mike.money;

/**
 * Created by mike on 12/19/2015.
 */
public class Main {
    static public Simulation simulation;

    static public void main(String[] args) {

        /*
            flags etc.

            -noexpenses removes expenses for all years, debugging

            -startYear yyyy     set start year, default 2015
            -endYear yyyy       set end year, default 2047
            -roi fff            set annual rate of return on investments, default is 1%, 0.01

            -investment ddd     initial value of investments, default is reality
            -MikeTraditionalIRA initial value of Mike's traditional IRAs, default is reality
            -MikeRothIRA        initial value of Mike's Roth IRAs, default is reality
            -NogaTraditionalIRA initial value of Noga's traditional IRAs, default is reality
            -NogaRothIRA        initial value of Noga's Roth IRAs, default is reality
        */

        simulation = new Simulation(args);
        simulation.run ();
    }
}
