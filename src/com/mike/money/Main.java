package com.mike.money;

/**
 * Created by mike on 12/19/2015.
 */
public class Main {
    static public Simulation simulation;

    static public void main(String[] args) {

        /*
            flags etc.

-noSSA -roi 0 -noIncome -Expenses 12000.0 -general 0 -noStock -noIRA

            -noexpenses removes expenses for all years, debugging

            -startYear yyyy     set start year, default 2015
            -endYear yyyy       set end year, default 2047
            -roi fff            set annual rate of return on investments, default is 1%, 0.01

            -general ddd        set general account to ddd

            -noStock            zero all trading/stock accounts
            -noIRA              zero all IRAs including inherited
            -noInheritedIRA     zero inherited IRA account
        */

        try {
            simulation = new Simulation(args);
            simulation.run ();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
