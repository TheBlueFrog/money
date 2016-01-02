package com.mike.money;

import java.util.*;

/**
 * Created by mike on 12/20/2015.
 */
public class Scenario {


    private Map<Integer, List<Double>> specials = new HashMap<Integer, List<Double>>();

    private Map<Integer, Double> nogaSalary = new HashMap<Integer, Double>();

    public void endOfYear() {
        for (Account a : mAccounts)
            a.endOfYearReset();
    }

    static public enum People { Mike, Noga, Joint };

    public double mInterestIncomeRate = 0.010;

    private List<Account> mAccounts = new ArrayList<Account>();

    private Expenses mExpenses;

    /*
    The three major areas of difference between a 401(k) and a Roth IRA are tax treatment, investment
    options and possible employer contributions.
     */


    // setup default data for run
    // the ordering is the order displayed

    private void init (String[] args) {
        if (Simulation.doTest) {
            mAccounts.add(new GeneralAccount(this, "Bank",        People.Joint, 1000.0));
            mAccounts.add(new TradingAccount(this, "College",     People.Joint, 2000.0));
            mAccounts.add(new TradingAccount(this, "Trading",     People.Joint, 100.0));
            mAccounts.add(new TraditionalIRA(this, "Inh IRA 1",   People.Mike, 2000.0));
            mAccounts.add(new InheritedTraditionalIRA(this, "Inh IRA 2", People.Mike, 2000.0));
            mAccounts.add(new TraditionalIRA(this, "Trad IRA1",   People.Mike, 2000.0));
            mAccounts.add(new TraditionalIRA(this, "Trad IRA2",   People.Mike, 2000.0));
            mAccounts.add(new RothIRA(this, "Roth IRA1",          People.Mike, 2000.0));
            mAccounts.add(new RothIRA(this, "Roth IRA2",          People.Mike, 2000.0));

            mInterestIncomeRate = 0.01;
        }
        else {
            // real scenario
            mAccounts.add(new GeneralAccount(this, "Wells Fargo",        People.Joint, 40000.0));       // as of 12/31/2015
            mAccounts.add(new TradingAccount(this, "College Wells",      People.Joint, (48027.0 * 3))); // "
            mAccounts.add(new TradingAccount(this, "Trading Ameritrade", People.Joint, 52043.0));       // "

            mAccounts.add(new TradingAccount(this, "eBay stock eTrade",  People.Mike, 43443.0));        // "
            mAccounts.add(new TraditionalIRA(this, "eBay 401 Schwab",    People.Mike, 72693.0));        // "  roi -2.9%

            // this literal is used somewhere
            mAccounts.add(new TradingAccount(this, "Intel stock ? ",     People.Noga, 2000.0));         // <<<<<<<<<<<<<<<<<<<<< guess
            mAccounts.add(new TraditionalIRA(this, "Intel 401 Fidelity", People.Noga, 3661.0));         // "

            mAccounts.add(new InheritedTraditionalIRA(this, "Securion",  People.Mike, 134654.0));       // " roi 3%
            mAccounts.add(new InheritedTraditionalIRA(this, "TIAA",      People.Mike, 65560.0));        // " roi 1.5%

            mAccounts.add(new TraditionalIRA(this, "IRA M Ameritrade",   People.Mike, 6248.0));         // "
            mAccounts.add(new TraditionalIRA(this, "IRA N Ameritrade",   People.Noga, 2375.0));         // "
            mAccounts.add(new TraditionalIRA(this, "IRA N UBS",          People.Noga, 8031.0));         // "
            mAccounts.add(new RothIRA(this,        "Roth M Ameritrade",  People.Mike, 15842.0));        // "
        }

        initSpecialsIncome(args);
        initNogaSalary(args);
    }

    private void initSpecialsIncome (String[] args) {
        for(String s : args)
            if (s.equals("-noSpecials")) {
                Main.print("No special after-tax income");
                return;
            }

        List<Double> x = new ArrayList<Double>();
        x.add(300000.0);      // net after downsizing house

        specials.put(2018, x);
    }
    private void initNogaSalary (String[] args) {
        nogaSalary.put(2015, 100000.0);
        nogaSalary.put(2016, 101000.0);
        nogaSalary.put(2017, 102000.0);
        nogaSalary.put(2018, 103000.0);
        nogaSalary.put(2019, 104000.0);
        nogaSalary.put(2020, 105000.0);
        nogaSalary.put(2021, 106000.0);
        nogaSalary.put(2022, 107000.0);
        nogaSalary.put(2023, 108000.0);
        nogaSalary.put(2024, 109000.0);
        nogaSalary.put(2025, 110000.0);
        nogaSalary.put(2026, 111000.0);
        nogaSalary.put(2027, 112000.0);
        nogaSalary.put(2028, 113000.0);
        nogaSalary.put(2029, 114000.0);
    }

    public Scenario(String[] args) throws Exception {

        SSA.init(this, args);

        mExpenses = new Expenses(args);

        init (args);

        // do tweaks
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.equals("-roi"))
                mInterestIncomeRate = Double.parseDouble(args[++i]);

//            if (arg.equals("-noStock")) {
//                mAccounts.get("College").setBalance(0.0);
//                mAccounts.get("Trading Ameritrade").setBalance(0.0);
//                mAccounts.get("eBay stock eTrade").setBalance(0.0);
//                mAccounts.get("Intel stock ?").setBalance(0.0);
//            }
//
        }
    }

    public double getAccountTaxableIncome() {
        double d = 0.0;
        for(Account a : mAccounts) {
            d += a.mYearsTaxableGain;
        }
        return d;
    }

    public void addToIRA(double v, People owner) throws Exception {
        for(Account a : mAccounts) {
            if (a instanceof TraditionalIRA) {
                if (a.mOwner.equals(owner)) {
                    a.deposit(v);
                }
            }
        }
    }

    public void addToStock(double v, People owner) throws Exception {
        for(Account a : mAccounts) {
            if (a.getName().startsWith("Intel stock")) {
                if (a.mOwner.equals(owner)) {
                    a.deposit(v);
                }
            }
        }
    }

    public void depositAfterTaxSpecials(Account general, int year) throws Exception {
        if (specials.containsKey(year))
            for (double d : specials.get(year))
                general.deposit(d);
    }

    /**
     *
     * @param general
     * @param who
     * @param simulation
     * @return work income
     */
    double depositWorkIncome(Account general, People who, Simulation simulation) throws Exception {
        double income = 0.0;

        if (Simulation.doTest) {
            income = 10.0;
        }
        else
        if (who.equals(People.Noga)) {
            if (Simulation.mCurrentYear <= Simulation.mNogaRetireYear) {
//                    Main.print("Noga working");
                income = nogaSalary.get(Simulation.mCurrentYear);

                addToIRA(0.08 * income, who);
                addToStock(0.08 * income, who);
            }
        }

        general.deposit(income);
        return income;
    }

    public Account getGeneralAccount() throws Exception {
        return findAccount (Simulation.doTest ? "Bank" : "Wells Fargo");
    }

    private Account findAccount(String s) throws Exception {
        for (Account a : mAccounts)
            if (s.equals(a.getName()))
                return a;

        throw new Exception("Unknown account " + s);
    }

    public void transfer (Account src, Account dst, double amount) throws Exception {
        src.withdraw(amount);
        dst.deposit(amount);
    }

    public void updateAccounts() throws Exception {
        for (Account a : mAccounts) {
            a.mYearsTaxableGain = 0.0;
            a.yearlyUpdate();
        }
    }

    public double liquidate(Account general, double amount) throws Exception {
        // drain by type, assumes who they belong to doesn't matter
        // @ TODO is this correct?

//        List<Account.AccountType> ordering = new ArrayList<Account.AccountType>();
//        ordering.add(Account.AccountType.General);
//        ordering.add(Account.AccountType.Trading);
//        ordering.add(Account.AccountType.InheritedTraditionalIRA);
//        ordering.add(Account.AccountType.RothIRA);
//        ordering.add(Account.AccountType.TraditionalIRA);

        double withdrawn = 0.0;

//        for (Account.AccountType t : ordering) {
            for (Account a : mAccounts)
                if ( ! (a.getID() == general.getID())) {           // skip Wells
//                    if (a.getType().equals(t)) {
                        double d = Math.min(amount, a.getBalance());
                        if (d > 0.0) {
                            a.withdraw(d);
//                            print(String.format("Liquidate %10.0f from %s", d, s));
                            withdrawn += d;
                            if (withdrawn >= amount)
                                return withdrawn;
                        }
                    }
//                }
//        }

        if (Simulation.doTest && (Simulation.getYear() == 2010)) {
            Main.print(String.format("Test passes, went broke at 10 years, short %9.0f", amount));
        }
        else {
            Main.print(String.format("Gone broke!, short %9.0f", amount));
        }
        Simulation.mStop = true;
        return withdrawn;
    }

    public double getAssets() {
        double amount = 0.0;
        for (Account a : mAccounts) {
            amount += a.getBalance();
        }
        return amount;
    }

    public void print() {
//        Main.simulation.print(String.format("Initial investment $%.0f", getAssets()));
        Main.print(String.format("Annual investment income rate %.4f", mInterestIncomeRate));
//        Main.simulation.print(String.format("Start-end years: %d-%d", Simulation.mStartYear, Simulation.mEndYear));
    }
    public void print(String s) {
        Main.print(s);
    }

    public double getExpenses(int year) throws Exception {
        if (Simulation.doTest) {
            return 0.0;
        }

        return mExpenses.getExpenses(this, year);
    }

    public int getAge(int year, People who) {
        int birthYear = 0;
        if (who.equals(People.Mike))
            birthYear = Simulation.MikeBirthYear;
        if (who.equals(People.Noga))
            birthYear = Simulation.NogaBirthYear;
        return year - birthYear;
    }

    public double depositMRD(Account general, int year, People who) throws Exception {
        int age = getAge(year, who);
        double d = 0.0;

        for (Account a : mAccounts)
            d += a.depositMRD(general, who, age);

        return d;
    }

//    private List<Account> orderAccounts () {
//        // sort into groups by type, then alphabetical
//        Map<Account.AccountType, List<Account>> ac = new LinkedHashMap<Account.AccountType, List<Account>>();
//        ac.put(Account.AccountType.General, new ArrayList<Account>());
//        ac.put(Account.AccountType.Trading, new ArrayList<Account>());
//        ac.put(Account.AccountType.InheritedTraditionalIRA, new ArrayList<Account>());
//        ac.put(Account.AccountType.TraditionalIRA, new ArrayList<Account>());
//        ac.put(Account.AccountType.RothIRA, new ArrayList<Account>());
//
//        for (Account.AccountType type : Account.AccountType.values())
//            for(Account a : mAccounts) {
//                if (a.getType() == type)
//                    ac.get(type).add(a);
//            }
//
//        List<Account> r = new ArrayList<Account>();
//        for (Map.Entry<Account.AccountType, List<Account>> s : ac.entrySet()) {
//            for (Account a : s.getValue())
//                r.add(a);
//        }
//
//        return r;
//    }

    public String showAccountNames() {
        StringBuilder sb = new StringBuilder();
        for (Account a : mAccounts) {
            String s = a.getName();
            sb.append(String.format("%9s", s.length() > 9 ? s.substring(0, 7) : s));
        }
        return sb.toString();
    }
    public String showAccountBalances() {
        StringBuilder sb = new StringBuilder();
        for (Account a : mAccounts) {
            sb.append(String.format("%9.0f", (a.getBalance() / 1000.0)));
        }
        return sb.toString();
    }

//    public double testDrawDown(Account general, Account.AccountType t) throws Exception {
//        int yearsToGo = Simulation.mEndYear - Simulation.mCurrentYear;
//        double x = 0.0;
//        for (Account a : mAccounts)
//            if (a.getType().equals(t)) {
//                if ( ! (general.getID() == a.getID())) {
//                    double d = a.getBalance() / yearsToGo;
//                    a.withdraw(d);
//                    general.deposit(d);
//                    x += d;
//                }
//            }
//
//        return x;
//    }

}
