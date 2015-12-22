package com.mike.money;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 12/21/2015.
 */
public class Expenses {

    static private double TuitionPerYear = 15000.0;
    static private double BasicBurnRate = 6000.0 * 12;

    static private Map<Integer, Double> expensesForYear = new HashMap<Integer, Double>();
    static
    {
        int y = 2015;
        expensesForYear.put(y++, BasicBurnRate + TuitionPerYear);     // kids plus Tal in school sophmore
        expensesForYear.put(y++, BasicBurnRate + TuitionPerYear);     // kids plus Tal in school junior
        expensesForYear.put(y++, BasicBurnRate + TuitionPerYear);     // kids plus Tal in school senior
        expensesForYear.put(y++, (8000 * 12) + (TuitionPerYear * 2));     // no kids guys in school freshmen + Tal help
        expensesForYear.put(y++, (8000 * 12) + (TuitionPerYear * 2));     // no kids guys in school soph
        // 2020
        expensesForYear.put(y++, (8000 * 12) + (TuitionPerYear * 2));     // no kids guys in school junior
        expensesForYear.put(y++, (8000 * 12) + (TuitionPerYear * 2));     // no kids guys in school senior
        expensesForYear.put(y++, BasicBurnRate + 6000.0);     // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + 6000.0);     // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + 6000.0);     // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + 6000.0);     // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + 6000.0);     // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + 3000.0);     // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + 3000.0);     // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + 3000.0);     // no kids bit of help
        // 2030
        expensesForYear.put(y++, BasicBurnRate + 3000.0);     // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate);     // just us
        expensesForYear.put(y++, BasicBurnRate);
        expensesForYear.put(y++, BasicBurnRate);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        // 2040
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);     // just noga
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(y++, BasicBurnRate * 0.5);
        expensesForYear.put(2047, BasicBurnRate * 0.5);
    };

    public Expenses(String[] args) {
        for (String s : args) {
            if (s.equals("-noexpenses")){
                expensesForYear.clear();
            }
        }
    }

    double getExpenses(int year) {
        if (expensesForYear.containsKey(year))
            return expensesForYear.get(year);
        else
            return 0.0;
    }

}
