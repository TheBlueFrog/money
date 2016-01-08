package com.mike.money;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by mike on 12/20/2015.
 */
public class Scenario {

    private Map<Integer, List<Pair<Account, Double>>> specials = new HashMap<Integer, List<Pair<Account, Double>>>();

    private List<Person> mPeople = new ArrayList<Person>();

    public double mInvestmentIncomeRate = 0.010;

    private Expenses mExpenses;

    // setup default data for run
    // the ordering is the order displayed

    private void init (String[] args) throws Exception {

        List<Account> mikesAccounts = new ArrayList<Account>();
        List<Account> nogasAccounts = new ArrayList<Account>();

        mikesAccounts.add(new GeneralAccount(this, "Wells Fargo",        40000.0));       // as of 12/31/2015
        mikesAccounts.add(new TradingAccount(this, "College Wells",      (48027.0 * 3))); // "
        mikesAccounts.add(new TradingAccount(this, "Trading Ameritrade", 52043.0));       // "

        mikesAccounts.add(new TradingAccount(this, "eBay stock eTrade",  43443.0));        // "
        mikesAccounts.add(new TraditionalIRA(this, "eBay 401 Schwab",    72693.0));        // "  roi -2.9%

        // this literal is used somewhere
        nogasAccounts.add(new TradingAccount(this, "Intel stock ? ",     2000.0));         // <<<<<<<<<<<<<<<<<<<<< guess
        nogasAccounts.add(new TraditionalIRA(this, "Intel 401 Fidelity", 3661.0));         // "

        mikesAccounts.add(new InheritedTraditionalIRA(this, "Securion",  134654.0));       // " roi 3%
        mikesAccounts.add(new InheritedTraditionalIRA(this, "TIAA",      65560.0));        // " roi 1.5%

        mikesAccounts.add(new TraditionalIRA(this, "IRA M Ameritrade",   6248.0));         // "
        nogasAccounts.add(new TraditionalIRA(this, "IRA N Ameritrade",   2375.0));         // "
        nogasAccounts.add(new TraditionalIRA(this, "IRA N UBS",          8031.0));         // "
        mikesAccounts.add(new RothIRA(this,        "Roth M Ameritrade",  15842.0));        // "

        Person mike = new Person("Mike", 1948, mikesAccounts);
        Person noga = new Person("Noga", 1957, nogasAccounts);

        mPeople.add(mike);
        mPeople.add(noga);

        initSpecialIncome(args);
    }

    private void initSpecialIncome(String[] args) throws Exception {
        for(int i = 0; i < args.length; ) {
            String s = args[i++];
            if (s.equals("-specialInc")) {
                // -specialInc year account amount ...

                int year = Integer.parseInt(args[i++]);

                Account a = findAccount(args[i++], true);

                List<Pair<Account, Double>> x = new ArrayList<Pair<Account, Double>>();
                x.add(new Pair<Account, Double>(a, Double.parseDouble(args[i++])));

                specials.put(year, x);
                return;
            }
        }
    }

    public Scenario(String[] args) throws Exception {

        init (args);

        mExpenses = new Expenses(args);

        SSA.init(this, args);

        // do tweaks
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.equals("-roi"))
                mInvestmentIncomeRate = Double.parseDouble(args[++i]);

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
        for(Person p : mPeople) {
            d += p.getTaxableIncome ();
        }
        return d;
    }

    public void addToIRA(double v, String owner) throws Exception {
        for(Person p : mPeople) {
            if (p.getName().equals(owner))
                p.addToIRA (v);
        }
    }

    public void addToStock(double v, String owner) throws Exception {
        for(Person p : mPeople)
            if (p.getName().equals(owner))
                p.addToStock (v);
    }

    public void depositAfterTaxSpecials(int year) throws Exception {
        if (specials.containsKey(year))
            for (Pair<Account, Double> s : specials.get(year)) {
                Account a = s.getKey();
                double d = s.getValue();
                a.deposit(d);
            }
    }

    /**
     *
     * @param simulation
     * @param general
     * @return work income
     */
    double depositWorkIncome(Simulation simulation, Account general) throws Exception {
        double income = 0.0;

        for(Person p : mPeople)
            income += p.depositeWorkIncome (simulation, general);
        return income;
    }

    public Account getGeneralAccount() throws Exception {
        return findAccount ("Wells Fargo");
    }
    public Account getTradingAccount() throws Exception {
        return findAccount ("Trading Ameritrade");
    }

    /**
     * @param s
     * @return matching account
     * @throws Exception
     */
    private Account findAccount(String s) throws Exception {
        return findAccount(s, false);
    }

    /**
     * @param s
     * @param loose
     * @return like above but looser matching
     * @throws Exception
     */
    private Account findAccount(String s, boolean loose) throws Exception {
        for(Person p : mPeople)
            if (p.hasAccount(s, loose))
                return p.getMatchingAccount(s, loose);

        throw new Exception("Unknown account " + s);
    }

    public void transfer (Account src, Account dst, double amount) throws Exception {
        src.withdraw(amount);
        dst.deposit(amount);
    }

    public void updateAccounts() throws Exception {
        for(Person p : mPeople)
            p.updateAccounts ();
    }

    public double liquidate(Account general, double amount) throws Exception {

        double d = 0.0;

        if (mPeople.size() > 1) {
            Person mike = findPerson("Mike");
            d += liquidate(general, amount - d, "Mike");
        }

        if (d < amount)
            d += liquidate(general, amount - d, "Noga");

        if (d < amount) {
            Main.print(String.format("Gone broke!, short %9.0f", amount));
            Simulation.mStop = true;
        }

        return d;
    }

    private double liquidate (Account account, double amount, String who) throws Exception {
//        List<Account.AccountType> ordering = new ArrayList<Account.AccountType>();
//        ordering.add(Account.AccountType.General);
//        ordering.add(Account.AccountType.Trading);
//        ordering.add(Account.AccountType.InheritedTraditionalIRA);
//        ordering.add(Account.AccountType.RothIRA);
//        ordering.add(Account.AccountType.TraditionalIRA);

        double d = 0.0;

        for (Person p : mPeople)
            if (p.getName().equals(who)){
                d = p.liquidate (account, amount);
                return d;
            }

        throw new Exception("No such person " + who);
    }



    public double getAssets() {
        double d = 0.0;
        for (Person p : mPeople)
            d += p.getAssets();
        return d;
    }

    public void print() {
//        Main.simulation.print(String.format("Initial investment $%.0f", getAssets()));
        Main.print(String.format("Annual investment income rate %.4f", mInvestmentIncomeRate));
//        Main.simulation.print(String.format("Start-end years: %d-%d", Simulation.mStartYear, Simulation.mEndYear));
    }
    public void print(String s) {
        Main.print(s);
    }

    public double getExpenses(int year) throws Exception {
        return mExpenses.getExpenses(this, year);
    }

    public int getAge(int year, String who) throws Exception {
        for (Person p : mPeople)
            if (p.getName().equals(who))
                return p.getAge(year);
        throw new Exception("No such person " + who);
    }

    public double depositMRD(Simulation simulation, Account general, int year) throws Exception {
        double d = 0.0;
        for (Person p : mPeople)
            d += p.depositMRD(general, year);

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
        for (Person p : mPeople)
            p.showAccountNames(sb);

        return sb.toString();
    }
    public String showAccountBalances() {
        StringBuilder sb = new StringBuilder();
        for (Person p : mPeople)
            p.showAccountBalances(sb);
        return sb.toString();
    }

    public void endOfYear() throws Exception {
        for (Person p : mPeople)
            p.endOfYear ();

        if (mPeople.size() > 1) {
            Person mike = findPerson("Mike");
            Person noga = findPerson("Noga");
            if (Simulation.mCurrentYear == mike.getDeathYear()) {

                // take all the accounts this person owns and move them
                // to the other person and then remove myself
                // preserve ordering for display
                noga.addAccounts(mike.takeAccounts());

                mPeople.remove(mike);
            }
        }
    }

    private Person findPerson(String who) throws Exception {
        for (Person p : mPeople)
            if (p.getName().equals(who))
                return p;

        throw new Exception("No such person " + who);
    }


    public double depositSSAIncome(Simulation simulation, Account general) throws Exception {
        double d = 0.0;
        for (Person p : mPeople)
            d += p.depositSSAIncome(simulation, general);
        return d;
    }

    public void setRetireAge(String who, int age) {
        for (Person p : mPeople)
            if (p.getName().equals(who))
                p.setRetireAge (age);
    }

    public void setDeathYear(String who, int year) {
        for (Person p : mPeople)
            if (p.getName().equals(who))
                p.setDeathYear (year);
    }

    public double getHealthInsurancePremiums(int year) {
        double d = 0.0;
        // look to see if anyone is working, if so skip this
        for (Person p : mPeople)
            if (p.isWorking (year))
                return d;

        // nobody working add is something
        for (Person p : mPeople)
            d += p.getHealthInsurancePremiums (year);
        return d;
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
