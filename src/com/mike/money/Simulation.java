package com.mike.money;

/**
 * Created by mike on 12/19/2015.
 */
public class Simulation {
    public static final int MikeBirthYear = 1948;
    static public final int NogaBirthYear = 1957;

    private final Scenario mScenario;
    private boolean mUseSSA = true;
    private boolean mNoIncome = false;

    public Scenario getScenario () {
        return mScenario;
    }

    public Simulation(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.equals("-noSSA"))
                mUseSSA = false;
            if (arg.equals("-noIncome"))
                mNoIncome = true;
        }

        mScenario = new Scenario(args);
    }

    public void run() {
        print();

        print(String.format("%4s %10s %10s %10s %10s %10s %10s %10s %10s",
                "Year", "Inv", "Inv Inc", "SSA Inc", "Mike Inc", "Noga Inc", "Net Inc", "Expenses", "Net"));

        for (int year = mScenario.mStartYear; year < mScenario.mEndYear; ++year) {
            double investmentGain = mScenario.getInvestmentGain();

            double mikeIncome = getWorkIncome(year, "mike") + getIRAIncome(year, "mike");
            double nogaIncome = getWorkIncome (year, "noga") + getIRAIncome(year, "noga");

            double ssaIncome = getSSAIncome(year, "mike") + getSSAIncome(year, "noga");

            double netIncome = getNetIncome (investmentGain, ssaIncome, mikeIncome, nogaIncome);

            double expenses = mScenario.getExpenses(year);

            double gainLoss = netIncome - expenses;

            print(String.format("%4d %10.0f %10.0f %10.0f %10.0f %10.0f %10.0f %10.0f %10.0f",
                    year, mScenario.getInvestments(), investmentGain, ssaIncome, mikeIncome, nogaIncome, netIncome, expenses, gainLoss));

            mScenario.update(gainLoss);
        }
    }

    /**
     * mostly compute something like taxes
     * @TODO other taxes, state,
     * @param investmentGain
     * @param ssaIncome
     * @param income
     * @return
     */
    private double getNetIncome(double investmentGain, double ssaIncome, double mikeIncome, double nogaIncome) {
        /*
        as of 2015, married filing jointly

        tax rate    income
        10%	        $0 - $18,450
        15%	        $18,450 - $74,900
        25%	        $74,900 - $151,200
        28%	        $151,200 - $230,450
        */

        double i = investmentGain + ssaIncome + mikeIncome + nogaIncome;
        double tax = 0;
        if (i < 18450)
            tax = 0.10;
        if (i < 74900)
            tax = 0.15;
        if (i < 151200)
            tax = 0.25;
        else
            tax = 0.28;

        return i - (i * tax);
    }

    /**
     * @param year
     * @param who
     * @return work income
     */
    private double getWorkIncome(int year, String who) {
        double income = 0.0;

        if ( ! mNoIncome) {
            if (who.equals("noga")) {
                if (year <= 2019)
                    income += 110000.00;
            }
        }

        return income;
    }

    /**
     * @param year
     * @param who
     * @return minimum distribution income from IRA
     */
    private double getIRAIncome(int year, String who) {
        double income = 0.0;

        if (who.equals("mike")) {
            income += mScenario.takeMRD(year, who);
        }
        else
        if (who.equals("noga")) {
            income += mScenario.takeMRD(year, who);
        }

        return income;
    }

    private double getSSAIncome(int year, String who) {
        if (mUseSSA)
            return SSA.getIncome(year, who);
        else
            return 0.0;
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void print() {
        mScenario.print();
    }
}

