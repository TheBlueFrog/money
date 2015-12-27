package com.mike.money;

/**
 * Created by mike on 12/20/2015.
 */ /* from https://www.irs.gov/publications/p590b/index.html

Table III
(Uniform Lifetime)
(For Use by:
Unmarried Owners,

Married Owners Whose Spouses Are Not More Than 10 Years Younger, and

Married Owners Whose Spouses Are Not the Sole Beneficiaries of Their IRAs)
*/
class MRDTable {
    static private MRDTable[] MRDAges = {
        new MRDTable(70, 27.4),
        new MRDTable(71, 26.5),
        new MRDTable(72, 25.6),
        new MRDTable(73, 24.7),
        new MRDTable(74, 23.8),
        new MRDTable(75, 22.9),
        new MRDTable(76, 22.0),
        new MRDTable(77, 21.2),
        new MRDTable(78, 20.3),
        new MRDTable(79, 19.5),
        new MRDTable(80, 18.7),
        new MRDTable(81, 17.9),
        new MRDTable(82, 17.1),
        new MRDTable(83, 16.3),
        new MRDTable(84, 15.5),
        new MRDTable(85, 14.8),
        new MRDTable(86, 14.1),
        new MRDTable(87, 13.4),
        new MRDTable(88, 12.7),
        new MRDTable(89, 12.0),
        new MRDTable(90, 11.4),
        new MRDTable(91, 10.8),
        new MRDTable(92, 10.2),
        new MRDTable(93, 9.6),
        new MRDTable(94, 9.1),
        new MRDTable(95, 8.6),
        new MRDTable(96, 8.1),
        new MRDTable(97, 7.6),
        new MRDTable(98, 7.1),
        new MRDTable(99, 6.7),
        new MRDTable(100, 6.3),
        new MRDTable(101, 5.9),
        new MRDTable(102, 5.5),
        new MRDTable(103, 5.2),
        new MRDTable(104, 4.9),
        new MRDTable(105, 4.5),
        new MRDTable(106, 4.2),
        new MRDTable(107, 3.9),
        new MRDTable(108, 3.7),
        new MRDTable(109, 3.4),
        new MRDTable(110, 3.1),
        new MRDTable(111, 2.9),
        new MRDTable(112, 2.6),
        new MRDTable(113, 2.4),
        new MRDTable(114, 2.1),
        new MRDTable(115, 1.9)
    };
    public int age;
    public double lifeExpectancy;

    public MRDTable(int age, double le) {
        this.age = age;
        this.lifeExpectancy = le;
    }

    public static double getIRAMRDLifeExpectancy(int age) throws Exception {

        if (age > 115)
            return MRDAges[MRDAges.length].lifeExpectancy;

        for (MRDTable i : MRDAges)
            if (i.age == age)
                return i.lifeExpectancy;

        throw new Exception("error in getIRAMRDLifeExpectancy");
    }

    static public double getMRD(int age, TraditionalIRA account) throws Exception {
        if (Simulation.doTest) {
            // take a 1/10 mrd, that's not enough to drain it so
            // we test the liquidation code also
            return account.getBalance() / 10.0;
        }
        else {
            double lifeExp = account.getLifeExpectancy(age);
            if (lifeExp > 0)
                return account.getBalance() / lifeExp;

            return 0.0;
        }
    }


}
