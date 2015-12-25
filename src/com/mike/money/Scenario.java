package com.mike.money;

import java.util.*;

/**
 * Created by mike on 12/20/2015.
 */
public class Scenario {

    static public enum People { Mike, Noga, Joint };

    public double mInterestIncomeRate = 0.010;

    private Map<String, Account> mAccounts = new HashMap<String, Account>();

    private Expenses mExpenses;

    // setup default data for run

    // these are in the order they should be drained to cover shortfalls or
    // excesses deposited

    private void init (String[] args) {
        if (Simulation.doTest) {
            mAccounts.put("Bank", new Account(Account.AccountType.General, People.Joint, 1000.0));
            mAccounts.put("College", new Account(Account.AccountType.General, People.Joint, 2000.0));
            mAccounts.put("Trading", new Account(Account.AccountType.Trading, People.Joint, 100.0));
            mAccounts.put("Inh IRA 1", new Account(Account.AccountType.InheritedTraditionalIRA, People.Mike, 2000.0));
            mAccounts.put("Inh IRA 2", new Account(Account.AccountType.InheritedTraditionalIRA, People.Mike, 2000.0));
            mAccounts.put("Trad IRA1", new Account(Account.AccountType.TraditionalIRA, People.Mike, 2000.0));
            mAccounts.put("Trad IRA2", new Account(Account.AccountType.TraditionalIRA, People.Mike, 2000.0));
            mAccounts.put("Roth IRA1", new Account(Account.AccountType.RothIRA, People.Mike, 2000.0));
            mAccounts.put("Roth IRA2", new Account(Account.AccountType.RothIRA, People.Mike, 2000.0));

            mInterestIncomeRate = 0.01;
        }
        else {
            // real scenario
            mAccounts.put("Wells Fargo",        new Account(Account.AccountType.General,        People.Joint, 40000.0));
            mAccounts.put("College",            new Account(Account.AccountType.Trading,        People.Joint, 150000.0));
            mAccounts.put("eBay stock eTrade",  new Account(Account.AccountType.Trading,        People.Joint, 48000.0));
            mAccounts.put("Trading Ameritrade", new Account(Account.AccountType.Trading,        People.Joint, 49000.0));
            mAccounts.put("Intel stock ?",      new Account(Account.AccountType.Trading,        People.Joint, 0.0));

            mAccounts.put("Schwab",             new Account(Account.AccountType.TraditionalIRA, People.Mike, 72000.0));
            mAccounts.put("IRA 1 Ameritrade",   new Account(Account.AccountType.TraditionalIRA, People.Mike, 6500.0));
            mAccounts.put("IRA 2 Ameritrade",   new Account(Account.AccountType.RothIRA,        People.Mike, 16000.0));
            mAccounts.put("Securion",           new Account(Account.AccountType.InheritedTraditionalIRA, People.Mike, 137000.0));
            mAccounts.put("TIAA",               new Account(Account.AccountType.InheritedTraditionalIRA, People.Mike, 68000.0));
            mAccounts.put("IRA 3 Ameritrade",   new Account(Account.AccountType.TraditionalIRA, People.Noga, 6400.0));
            mAccounts.put("IRA ?",              new Account(Account.AccountType.TraditionalIRA, People.Noga, 16000.0));
        }
    }

    public Scenario(String[] args) throws Exception {

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
//            if (arg.equals("-noIRA")) {
//                for(String s : mAccounts.keySet()) {
//                    Account a = mAccounts.get(s);
//                    if (   (a.getType() == Account.AccountType.InheritedTraditionalIRA)
//                        || (a.getType() == Account.AccountType.RothIRA)
//                        || (a.getType() == Account.AccountType.TraditionalIRA))
//                        a.setBalance(0.0);
//                }
//            }
//            if (arg.equals("-noInheritedIRA")) {
//                for(String s : mAccounts.keySet()) {
//                    Account a = mAccounts.get(s);
//                    if (   (a.getType() == Account.AccountType.InheritedTraditionalIRA))
//                        a.setBalance(0.0);
//                }
//            }
//
//            if (arg.equals("-startYear"))
//                Simulation.mStartYear = 2015;
//            if (arg.equals("-endYear"))
//                Simulation.mEndYear = 2047;
//            if (arg.equals("-roi"))
//                mInterestIncomeRate = Double.parseDouble(args[++i]);
        }
    }

    public Account getGeneralAccount() {
        return mAccounts.get(Simulation.doTest ? "Bank" : "Wells Fargo");
    }

//    private void set(Account.AccountType type, People owner, double amount) throws Exception {
//        for (String name : mAccounts.keySet()) {
//            Account a = mAccounts.get(name);
//            if (a.matches(type, owner)) {
//                a.setBalance (amount);
//                return;
//            }
//        }
//
//        throw new Exception("Failed to setup account");
//    }

    public void depositInvestmentGain() throws Exception {
        for (String s : mAccounts.keySet()) {
            Account a = mAccounts.get(s);
            switch (a.getType()) {
                case General:
                    break;
                case Trading:
                case InheritedTraditionalIRA:
                case RothIRA:
                case TraditionalIRA:
                    double d = a.getBalance() * mInterestIncomeRate;
                    a.deposit(d);
                    break;

                default:
                    print("Missing case statement " + a.getType().toString());
            }
        }
    }

    public double liquidate(Account general, double amount) throws Exception {
        // drain by type, assumes who they belong to doesn't matter
        // @ TODO is this correct?

        List<Account.AccountType> ordering = new ArrayList<Account.AccountType>();
        ordering.add(Account.AccountType.General);
        ordering.add(Account.AccountType.Trading);
        ordering.add(Account.AccountType.InheritedTraditionalIRA);
        ordering.add(Account.AccountType.RothIRA);
        ordering.add(Account.AccountType.TraditionalIRA);

        double withdrawn = 0.0;

        for (Account.AccountType t : ordering) {
            for (String s : mAccounts.keySet()) {
                Account a = mAccounts.get(s);
                if ( ! (a.getID() == general.getID())) {           // skip Wells
                    if (a.getType().equals(t)) {
                        double d = Math.min(amount, a.getBalance());
                        if (d > 0.0) {
                            a.withdraw(d);
                            print(String.format("Liquidate %10.0f from %s", d, s));
                            withdrawn += d;
                            if (withdrawn >= amount)
                                return withdrawn;
                        }
                    }
                }
            }
        }

        if (Simulation.doTest && (Simulation.getYear() == 2010)) {
            Main.simulation.print(String.format("Test passes, went broke at 10 years, short %9.0f", amount));
        }
        else {
            Main.simulation.print(String.format("Gone broke!, short %9.0f", amount));
        }
        Simulation.mStop = true;
        return withdrawn;
    }

    public double getAssets() {
        double amount = 0.0;
        for (String a : mAccounts.keySet()) {
            amount += mAccounts.get(a).getBalance();
        }
        return amount;
    }

    public void print() {
        Main.simulation.print(String.format("Initial investment $%.0f", getAssets()));
        Main.simulation.print(String.format("Annual investment income rate %.4f", mInterestIncomeRate));
        Main.simulation.print(String.format("Start-end years: %d-%d", Simulation.mStartYear, Simulation.mEndYear));
    }
    public void print(String s) {
        Main.simulation.print(s);
    }

    public double getExpenses(int year) throws Exception {
        if (Simulation.doTest) {
            return 0.0;
        }

        return mExpenses.getExpenses(year);
    }

    public int getAge(int year, People who) {
        int birthYear = 0;
        if (who.equals(People.Mike))
            birthYear = Simulation.MikeBirthYear;
        if (who.equals(People.Noga))
            birthYear = Simulation.NogaBirthYear;
        return year - birthYear;
    }

    public void depositMRD(Account general, int year, People who) throws Exception {
        int age = getAge(year, who);

        for (String g : mAccounts.keySet()) {
            Account a = mAccounts.get(g);
            a.depositMRD(general, age);
        }
    }

    public String showAccountNames() {
        StringBuilder sb = new StringBuilder();
        Set<String> ss = new TreeSet<String>(mAccounts.keySet()); // sort the set by toString
        for (String s : ss) {
            sb.append(String.format("%9s", s.length() > 10 ? s.substring(0, 8) : s));
        }
        return sb.toString();
    }
    public String showAccountBalances() {
        StringBuilder sb = new StringBuilder();
        Set<String> ss = new TreeSet<String>(mAccounts.keySet()); // sort the set by toString
        for (String s : ss) {
            Account a = mAccounts.get(s);
            sb.append(String.format("%9.0f", a.getBalance()));
        }
        return sb.toString();
    }

    public double testDrawDown(Account general, Account.AccountType t) throws Exception {
        int yearsToGo = Simulation.mEndYear - Simulation.mCurrentYear;
        double x = 0.0;
        for (String s : mAccounts.keySet()) {
            Account a = mAccounts.get(s);
            if (a.getType().equals(t)) {
                if ( ! (general.getID() == a.getID())) {
                    double d = a.getBalance() / yearsToGo;
                    a.withdraw(d);
                    general.deposit(d);
                    x += d;
                }
            }
        }

        return x;
    }

}
