package com.mike.money;

import com.intellij.openapi.vcs.history.VcsRevisionNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 12/21/2015.
 */
public class Expenses {

    static private double TuitionPerYear = (4200.0 * 3);                    // OSU 1st term 2016 $4150
    static private double BasicBurnRate = 5500.0 * 12;                      // 7/1/2015 - 12/30/2015 total expenses $33k
                                                                            // exclusive of Tal tuition and $1k/mo support


    static private Map<Integer, Double> expensesForYear = new HashMap<Integer, Double>();
    static
    {
        double extra = (1000 * 12);                                                         // extra help for a kid, living exp

        int y = 2015;
        expensesForYear.put(y++, BasicBurnRate + TuitionPerYear + extra);                   // kids home plus Tal in school sophmore
        expensesForYear.put(y++, BasicBurnRate + TuitionPerYear + extra);                   // kids plus Tal in school junior
        expensesForYear.put(y++, BasicBurnRate + TuitionPerYear + extra);                   // kids plus Tal in school senior
        expensesForYear.put(y++, BasicBurnRate + (TuitionPerYear * 2) + (extra * 0.5) + (extra * 2)); // no kids guys in school freshmen + Tal               help
        expensesForYear.put(y++, BasicBurnRate + (TuitionPerYear * 2) + (extra * 0.5) + (extra * 2));       // no kids guys in school soph

        // 2020
        expensesForYear.put(y++, BasicBurnRate + (TuitionPerYear * 2) + (extra * 0.5) + (extra * 2));      // no kids guys in school junior
        expensesForYear.put(y++, BasicBurnRate + (TuitionPerYear * 2) + (extra * 0.5) + (extra * 2));       // no kids guys in school senior
        expensesForYear.put(y++, BasicBurnRate + (extra * 2));                              // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + (extra * 2));                              // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + extra);                                    // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + extra);                                    // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + extra);                                    // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + (extra * 0.5));                            // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + (extra * 0.5));                            // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate + (extra * 0.5));                            // no kids bit of help

        // 2030
        expensesForYear.put(y++, BasicBurnRate + (extra * 0.5));                            // no kids bit of help
        expensesForYear.put(y++, BasicBurnRate);                                            // just us
        expensesForYear.put(y++, BasicBurnRate);
        expensesForYear.put(y++, BasicBurnRate);
        expensesForYear.put(y++, BasicBurnRate);
        expensesForYear.put(y++, BasicBurnRate);
        expensesForYear.put(y++, BasicBurnRate);
        expensesForYear.put(y++, BasicBurnRate);
        expensesForYear.put(y++, BasicBurnRate);            // 2039 Mike is 90
        expensesForYear.put(y++, BasicBurnRate);
        // 2040
        expensesForYear.put(y++, BasicBurnRate * 0.7);
        expensesForYear.put(y++, BasicBurnRate * 0.7);
        expensesForYear.put(y++, BasicBurnRate * 0.7);
        expensesForYear.put(y++, BasicBurnRate * 0.7);
        expensesForYear.put(y++, BasicBurnRate * 0.7);
        expensesForYear.put(y++, BasicBurnRate * 0.7);
        expensesForYear.put(y++, BasicBurnRate * 0.7);
        expensesForYear.put(y++, BasicBurnRate * 0.7);      // 2047 Noga is 90
    };

    public Expenses(String[] args) {
//        } else {
//            for (int i = 0; i < args.length; ++i) {
//                String s = args[i];
//                if (s.equals("-noExpenses")) {
//                    expensesForYear.clear();
//                }
//                if (s.equals("-Expenses")) {
//                    expensesForYear.clear();
//                    mConstantExpenses = Double.parseDouble(args[++i]);
//                }
//            }
//        }
    }

    double getExpenses(Scenario scenario, int year) throws Exception {

        double d = 0.0;
        if (expensesForYear.containsKey(year))
            d = expensesForYear.get(year);
        else
            d = expensesForYear.get(2047);

        d += scenario.getHealthInsurancePremiums (year);

        return d;
    }

}
