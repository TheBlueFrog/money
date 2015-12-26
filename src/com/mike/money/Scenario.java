package com.mike.money;

import java.util.*;

/**
 * Created by mike on 12/20/2015.
 */
public class Scenario {

    static public enum People { Mike, Noga, Joint };

    public double mInterestIncomeRate = 0.010;

    private List<Account> mAccounts = new ArrayList<Account>();

    private Expenses mExpenses;

    // setup default data for run

    // these are in the order they should be drained to cover shortfalls or
    // excesses deposited

    private void init (String[] args) {
        if (Simulation.doTest) {
            mAccounts.add(new Account("Bank",       Account.AccountType.General, People.Joint, 1000.0));
            mAccounts.add(new Account("College",    Account.AccountType.General, People.Joint, 2000.0));
            mAccounts.add(new Account("Trading",    Account.AccountType.Trading, People.Joint, 100.0));
            mAccounts.add(new Account("Inh IRA 1",  Account.AccountType.InheritedTraditionalIRA, People.Mike, 2000.0));
            mAccounts.add(new Account("Inh IRA 2",  Account.AccountType.InheritedTraditionalIRA, People.Mike, 2000.0));
            mAccounts.add(new Account("Trad IRA1",  Account.AccountType.TraditionalIRA, People.Mike, 2000.0));
            mAccounts.add(new Account("Trad IRA2",  Account.AccountType.TraditionalIRA, People.Mike, 2000.0));
            mAccounts.add(new Account("Roth IRA1",  Account.AccountType.RothIRA, People.Mike, 2000.0));
            mAccounts.add(new Account("Roth IRA2",  Account.AccountType.RothIRA, People.Mike, 2000.0));

            mInterestIncomeRate = 0.01;
        }
        else {
            // real scenario
            mAccounts.add(new Account("Wells Fargo",        Account.AccountType.General,        People.Joint, 40000.0));
            mAccounts.add(new Account("College",            Account.AccountType.Trading,        People.Joint, 150000.0));
            mAccounts.add(new Account("eBay stock eTrade",  Account.AccountType.Trading,        People.Joint, 48000.0));
            mAccounts.add(new Account("Trading Ameritrade", Account.AccountType.Trading,        People.Joint, 49000.0));
            mAccounts.add(new Account("Intel stock ?",      Account.AccountType.Trading,        People.Joint, 0.0));

            mAccounts.add(new Account("Schwab",             Account.AccountType.TraditionalIRA, People.Mike, 72000.0));
            mAccounts.add(new Account("IRA M Ameritrade",   Account.AccountType.TraditionalIRA, People.Mike, 6500.0));
            mAccounts.add(new Account("Roth M Ameritrade",   Account.AccountType.RothIRA,        People.Mike, 16000.0));
            mAccounts.add(new Account("Securion",           Account.AccountType.InheritedTraditionalIRA, People.Mike, 137000.0));
            mAccounts.add(new Account("TIAA",               Account.AccountType.InheritedTraditionalIRA, People.Mike, 68000.0));
            mAccounts.add(new Account("IRA N Ameritrade",   Account.AccountType.TraditionalIRA, People.Noga, 6400.0));
            mAccounts.add(new Account("IRA N",              Account.AccountType.TraditionalIRA, People.Noga, 16000.0));
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

    public Account getGeneralAccount() throws Exception {
        return findAccount (Simulation.doTest ? "Bank" : "Wells Fargo");
    }

    private Account findAccount(String s) throws Exception {
        for (Account a : mAccounts)
            if (s.equals(a.getName()))
                return a;

        throw new Exception("Unknown account " + s);
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
        for (Account a : mAccounts) {
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
            for (Account a : mAccounts)
                if ( ! (a.getID() == general.getID())) {           // skip Wells
                    if (a.getType().equals(t)) {
                        double d = Math.min(amount, a.getBalance());
                        if (d > 0.0) {
                            a.withdraw(d);
//                            print(String.format("Liquidate %10.0f from %s", d, s));
                            withdrawn += d;
                            if (withdrawn >= amount)
                                return withdrawn;
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
        for (Account a : mAccounts) {
            amount += a.getBalance();
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

        for (Account a : mAccounts)
            a.depositMRD(general, age);
    }

    private List<Account> orderAccounts () {
        // sort into groups by type, then alphabetical
        Map<Account.AccountType, List<Account>> ac = new LinkedHashMap<Account.AccountType, List<Account>>();
        ac.put(Account.AccountType.General, new ArrayList<Account>());
        ac.put(Account.AccountType.Trading, new ArrayList<Account>());
        ac.put(Account.AccountType.InheritedTraditionalIRA, new ArrayList<Account>());
        ac.put(Account.AccountType.TraditionalIRA, new ArrayList<Account>());
        ac.put(Account.AccountType.RothIRA, new ArrayList<Account>());

        for (Account.AccountType type : Account.AccountType.values())
            for(Account a : mAccounts) {
                if (a.getType() == type)
                    ac.get(type).add(a);
            }

        List<Account> r = new ArrayList<Account>();
        for (Map.Entry<Account.AccountType, List<Account>> s : ac.entrySet()) {
            for (Account a : s.getValue())
                r.add(a);
        }

        return r;
    }
    public String showAccountNames() {
        StringBuilder sb = new StringBuilder();
        for (Account a : orderAccounts()) {
            String s = a.getName();
            sb.append(String.format("%9s", s.length() > 9 ? s.substring(0, 7) : s));
        }
        return sb.toString();
    }
    public String showAccountBalances() {
        StringBuilder sb = new StringBuilder();
        for (Account a : orderAccounts()) {
            sb.append(String.format("%9.0f", a.getBalance()));
        }
        return sb.toString();
    }

    public double testDrawDown(Account general, Account.AccountType t) throws Exception {
        int yearsToGo = Simulation.mEndYear - Simulation.mCurrentYear;
        double x = 0.0;
        for (Account a : mAccounts)
            if (a.getType().equals(t)) {
                if ( ! (general.getID() == a.getID())) {
                    double d = a.getBalance() / yearsToGo;
                    a.withdraw(d);
                    general.deposit(d);
                    x += d;
                }
            }

        return x;
    }

}
