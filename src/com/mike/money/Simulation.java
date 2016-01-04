package com.mike.money;

import static com.mike.money.Scenario.*;

/**
 * Created by mike on 12/19/2015.
 */
public class Simulation {

    static public int mStartYear = 2016;
    static public int mEndYear = 2047;
    public static int mCurrentYear;

    // state of simulation
    public static boolean mStop = true;

    public static String[] mSSASummary = null;

    // factor in state income tax
    private boolean mNoStateTax = false;

    // show all accounts
    private boolean mShowAccounts = false;

    private Scenario mScenario;

    public Scenario getScenario () {
        return mScenario;
    }

    public Simulation(String[] args) throws Exception {

        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.equals("-showAccounts"))
                mShowAccounts = true;
            if (arg.equals("-noStateTax"))
                mNoStateTax = true;

//                if (arg.equals("-nogaRetire"))
//                    mNogaRetireYear = Integer.parseInt(args[++i]);
        }

        mScenario = new Scenario(args);
    }

/*         Mike age   Noga age    sell house
  2015        66        57
    16        67        58
    17        68        59
    18        69        60          300k
    19        70        61
    20        71        62
    21        72        63
    22        73        64
    23        74        65
    24        75        66
    25        76        67
    26        77        68
    27        78        69
    28        79        70
 */
    public static int getYear() {
        return mCurrentYear;
    }

    public void run() throws Exception {
        mScenario.print();

//        for (int i = 0; i < mSSASummary.length; ++i)
//            Main.print(mSSASummary[i]);

        String lineSpec = "%4s ";
        for (int i = 0; i < 8; ++i)
            lineSpec += "%8.0f";
        lineSpec += "%s";

        Main.print(String.format(lineSpec.replace("%8.0f", "%8s"),
                "Year", "Exp", "Taxes", "Tot Out", "SSA", "MRD", "Work", "Liquid", "Assets",
                mShowAccounts ? mScenario.showAccountNames() : ""));

        Main.print(String.format(lineSpec,
                mCurrentYear - 1,
                0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0,
                mScenario.getAssets(),
                mShowAccounts ? mScenario.showAccountBalances() : ""));

        mCurrentYear = mStartYear;
        mStop = false;
        while ((mCurrentYear <= mEndYear) && ( !mStop)) {
            Account general = mScenario.getGeneralAccount();

            mScenario.updateAccounts();

            double workIncome = mScenario.depositWorkIncome(this, general);

            double mrd = mScenario.depositMRD(this, general, mCurrentYear);

            double ssa = mScenario.depositSSAIncome(this, general);

//            double fakeExpenses = 0.0;
//            if (doTest) {
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.General);
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.Trading);
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.RothIRA);
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.General);
//            }

            double taxableIncome =   workIncome
                                   + mrd
                                   + mScenario.getAccountTaxableIncome()
                                   + (ssa * 0.85);         // only 85% of SSA is taxable, apparently
            double tax = getTax(taxableIncome);
            double taxPaid = taxableIncome * tax;

            // pay taxes below

            mScenario.depositAfterTaxSpecials (mCurrentYear);

            // pay expenses and taxes

            double expenses = mScenario.getExpenses(mCurrentYear);
            double expensesAndTaxes = expenses + taxPaid;

            double liquidated = 0.0;
            if (general.getBalance() < expensesAndTaxes) {
                liquidated = mScenario.liquidate(general, expensesAndTaxes - general.getBalance());
                general.deposit(liquidated);
            }
            general.withdraw(expensesAndTaxes);

//          "Year", "Exp", "Taxes", "Tot Out", "Work", "SSA", "MRD", "Liquid", "Assets",

            Main.print(String.format(lineSpec,
                    mCurrentYear,
                    expenses, taxPaid, expensesAndTaxes,
                    ssa, mrd, workIncome,
                    liquidated, mScenario.getAssets(),
                    mShowAccounts ? mScenario.showAccountBalances() : ""));

            mCurrentYear++;

            mScenario.endOfYear ();
        }
    }

    private double getTax(double taxableIncome) {
        /*
        as of 2015, married filing jointly

        tax rate    income
        10%	        $0 - $18,450
        15%	        $18,450 - $74,900
        25%	        $74,900 - $151,200
        28%	        $151,200 - $230,450
        */

        double tax = 0;

        if (taxableIncome < 18450)
            tax = 0.10;
        if (taxableIncome < 74900)
            tax = 0.15;
        if (taxableIncome < 151200)
            tax = 0.25;
        else
            tax = 0.28;

        if ( ! mNoStateTax)
            tax += 0.035;        // NM is 4.9, AZ is 4.6 gross
                                        // NM calc says effective rate is 3.5%

        return tax;
    }


}

