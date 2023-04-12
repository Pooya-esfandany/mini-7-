import java.util.ArrayList;
import java.util.Iterator;

enum AIDSRange {
    CBC(20, 180),
    BLOOD_PRESSURE(4, 150),
    BMP(35, 340);

    private enum HeartDisease {
        HEALTHY,
        CORONARY,
        STROKE,
        PERIPHERAL_ARTERIAL,
        AORTIC;
    }

    private int range_one;
    private int range_two;

    AIDSRange(int range_one, int range_two) {
        this.range_one = range_one;
        this.range_two = range_two;
    }

    public int getRange_one() {
        return range_one;
    }

    public int getRange_two() {
        return range_two;
    }
}

enum BloodTypeRange {
    CBC(20, 160),
    BLOOD_PRESSURE(4, 100),
    BMP(35, 210);

    private int range_one;
    private int range_two;

    BloodTypeRange(int range_one, int range_two) {
        this.range_one = range_one;
        this.range_two = range_two;
    }

    public int getRange_one() {
        return range_one;
    }

    public int getRange_two() {
        return range_two;
    }

}

public class Main {
    public static void main(String[] args) {


        Test[] x = new Test[7];
        x[0] = new HIVTest();
        x[0].runOperation();
        x[1] = new Anemia();
        x[1].runOperation();
        x[2] = new Theroyid();
        x[2].runOperation();
        x[3] = new BloodType();
        x[3].runOperation();


        String[] illness = new String[0];
        Person p = new Person(10, illness);
        Labratory.getLabratory("hello");

        p.testing(x[0]);
        p.testing(x[1]);
        p.testing(x[2]);
        p.testing(x[3]);

        Labratory.getLabratory().addTest(x[0]);
        Labratory.getLabratory().addTest(x[1]);
        Labratory.getLabratory().addTest(x[2]);
        Labratory.getLabratory().addTest(x[3]);

   /* for(int i=0;i<p.tests.size();i++) {


        System.out.println(p.tests.get(i).getCBC());
        System.out.println(p.tests.get(i).getBloodPressure());
        System.out.println(p.tests.get(i).getBMP());
    }*/
        //for(Statistics statistics:Labratory.getLabratory())
        //System.out.println(statistics.ID);
        //p.requesting(x[0], p);
        showList();
    }

    public static void showList() {
        ArrayList<ArrayList<Statistics>> list = new ArrayList<>();
        for (int k = 0; k < Labratory.getLabratory().statistics.size(); k++) {
            Labratory.getLabratory().statistics.get(k).added = false;
        }
        for (int k = 0; k < Labratory.getLabratory().statistics.size(); k++) {
            ArrayList<Statistics> addStatistic = new ArrayList<>();
            if (Labratory.getLabratory().statistics.get(k).added == false) {
                Labratory.getLabratory().statistics.get(k).added=true;
                addStatistic.add(Labratory.getLabratory().statistics.get(k));
                for (int i = k+1; i < Labratory.getLabratory().statistics.size(); i++) {
                    if (Labratory.getLabratory().statistics.get(i).added = false) {
                        if (Labratory.getLabratory().statistics.get(k).compareTo(Labratory.getLabratory().statistics.get(i)) == 0) {
                            addStatistic.add(Labratory.getLabratory().statistics.get(i));
                            Labratory.getLabratory().statistics.get(i).added = true;
                        }
                    }

                }   list.add(addStatistic);
                }



        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                System.out.printf(list.get(i).get(j).ID);
            }
            System.out.println("");
        }
    }

}
class Person {
    Person(int age,String[] illnesses)
    {
        this.personID =IDCalculator();
        this.age=age;
        this.illnesses=illnesses;
    }

    static long totalID = 1;
    String personID;

    public String[] illnesses;
    public double age;

    ArrayList<Test> tests = new ArrayList<>();
    void testing(Test test)
    {
        if(test instanceof BloodType)
        {
            tests.add(test);
        }
        else
        {
            if(test instanceof HIVTest)
                tests.add(((HIVTest) test).makingPrivate());
            if(test instanceof Theroyid)
                tests.add(((Theroyid) test).makingPrivate());
            if(test instanceof Anemia)
                tests.add(((Anemia) test).makingPrivate());
        }
    }
    String IDCalculator()
    {
        StringBuilder ID=new StringBuilder();
        ID.append(totalID);
        totalID++;
        return ID.toString();
    }
    void requesting(Test test,Person person)
    {
        Test privateTest=null;
        if(test instanceof HIVTest)
            privateTest=(((HIVTest) test).makingPrivate());
        if(test instanceof Theroyid)
            privateTest=(((Theroyid) test).makingPrivate());
        if(test instanceof Anemia)
            privateTest=(((Anemia) test).makingPrivate());

        Requests newRequest=new Requests(person,test);
        Labratory.getLabratory().addRequest(newRequest);
    }

}
class Labratory implements Iterable<Statistics> {
    private static Labratory labratory;
     final String name;
    ArrayList<Test> Tests = new ArrayList<>();
    ArrayList<Person> testedPersons = new ArrayList<>();
    ArrayList<Requests> totalRequests = new ArrayList<>();
    ArrayList<Statistics> statistics = new ArrayList<>();
    public static Labratory getLabratory(String name)
    {
        if(labratory==null)
        {
            labratory=new Labratory(name);
        }
        return labratory;
    }
    private Labratory(String name)
    {

        this.name=name;
    }

    public static Labratory getLabratory() {

        return labratory;
    }

    public void addTest(Test test) {
        Labratory.getLabratory().Tests.add(test);
        Statistics newStatic = new Statistics(test);
        statistics.add(newStatic);
    }

    public void addRequest(Requests requests) {

        this.totalRequests.add(requests);
    }

    public void acceptRequest(int index) {
        int value = -1;
        Requests request = totalRequests.get(index);
        totalRequests.remove(index);
        for (Person x : testedPersons) {
            if (x.personID.equals(request.ID)) ;
            value = testedPersons.indexOf(x);
            break;
        }
        testedPersons.get(value).tests.add(request.test);
       this.totalRequests.remove(index);
    }

    public void denyRequest(int index) {
        totalRequests.remove(index);
    }


    int index = 0;
    @Override
    public Iterator<Statistics> iterator() {

        return new Iterator<Statistics>() {
            @Override
            public boolean hasNext() {
                if (Labratory.getLabratory().statistics.size() > index)
                    return true;
                return false;
            }

            @Override
            public Statistics next() {
                Statistics statistics1=Labratory.getLabratory().statistics.get(index);
                index++;
                return statistics1;
            }
        };
    }
    public ArrayList<ArrayList<Statistics>> showStatistics() {


        ArrayList<ArrayList<Statistics>> list = new ArrayList<>();
        for (Statistics statistic : Labratory.getLabratory()) {


            if (statistic.added == false) {
                ArrayList<Statistics> addStatistic = new ArrayList<>();
                addStatistic.add(statistic);
                for (int i = 0; i < Labratory.getLabratory().statistics.size(); i++) {
                    if (statistic.compareTo(Labratory.getLabratory().statistics.get(i)) == 0) {
                        addStatistic.add(Labratory.getLabratory().statistics.get(i));
                        Labratory.getLabratory().statistics.get(i).added=true;
                    }
                    list.add(addStatistic);
                }

            }

        }

        return list;
    }
}
class Statistics implements Comparable<Statistics> {
    boolean added;
    String ID;
    int CBC;
    int BMP;
    int bloodPressure;

    Statistics(Test test) {
        this.BMP = test.getBMP();
        this.CBC = test.getCBC();
        this.bloodPressure = test.getBloodPressure();
        this.ID=test.testID;
        this.added = false;
    }

    @Override
    public int compareTo(Statistics o) {
        if (BMP != o.BMP) {
            if (BMP > this.BMP) return 1;
            else return -1;
        } else if (CBC != o.CBC) {
            if (CBC > o.CBC) return 1;
            if (CBC < o.CBC) return -1;
        }
        if (bloodPressure == bloodPressure) return 0;
        else {
            if (bloodPressure > o.bloodPressure) return 1;
            else return -1;
        }
    }
}
class Requests {

    String ID;
    Test test;
    Test privateTest;

    public Requests(Person person, Test test) {
        this.ID =person.personID;
        this.test = test;
        if(test instanceof HIVTest)
            this.privateTest=(((HIVTest) test).makingPrivate());
        if(test instanceof Theroyid)
            this.privateTest=(((Theroyid) test).makingPrivate());
        if(test instanceof Anemia)
            this.privateTest=(((Anemia) test).makingPrivate());
    }


}

abstract class Test {
    static int IDCalculator = 1;
    Test()
    {
        this.testID =IDcal();
    }
    String testID;
    Person sickPerson;
    ArrayList<String> illnesses;
    private int CBC;
    private int BMP;
    private int bloodPressure;

    public void Add(int cbc, int bmp, int bloodPressure) {
        this.CBC = cbc;
        this.BMP = bmp;
        this.bloodPressure = bloodPressure;
        this.testID = IDcal();

    }

    String IDcal() {
        StringBuilder newID = new StringBuilder();
        newID.append(IDCalculator);
        IDCalculator++;
        return newID.toString();
    }

    public int getBloodPressure() {

        return bloodPressure;
    }

    public int getCBC() {


        return CBC;
    }

    public int getBMP() {

        return BMP;
    }

    static int getRandomNumber20Percent(int min, int max) {
        if ((double) ((Math.random() * (5))) != 4) {
            return (int) ((Math.random() * (max - min)) + min);
        } else {
            return ((int) ((Math.random() * (min))));
        }


    }

    static int getRandomNumber(int min, int max) {

        int x = (int) ((Math.random() * (max - min)) + min);
        return x;
    }

    public abstract boolean runOperation();

    public abstract Object sendResult();

}

interface IChecking {
    boolean checking(int cbc, int bmp, int bloodPressure);

    boolean wrongAnswer();
}

interface IPrivate {
    Test makingPrivate();

    Requests gettingTheRequests();


}

class BloodType extends Test implements IChecking {
    public boolean accepted;

    @Override
    public boolean runOperation() {
        int cbc;
        int bmp;
        int bloodPressure;
        while (1 == 1) {
            cbc = getRandomNumber20Percent(20, 180);
            bmp = getRandomNumber20Percent(35, 210);
            bloodPressure = getRandomNumber20Percent(4, 100);
            if (checking(cbc, bmp, bloodPressure) == true) break;
            else {
                if (wrongAnswer() == true) {
                    this.accepted = false;
                    return false;
                }
            }

        }
        Add(cbc, bmp, bloodPressure);
        this.accepted = true;
        return true;


    }

    @Override
    public Object sendResult() {
        //clone
        Test newClass = new BloodType();
        newClass.Add(super.getCBC(), super.getBMP(), getBloodPressure());
        newClass.illnesses = super.illnesses;
        newClass.testID = this.testID;
        ((BloodType) newClass).accepted = this.accepted;
        return newClass;
    }

    @Override
    public boolean checking(int cbc, int bmp, int bloodPressure) {
        boolean t1 = (cbc >= BloodTypeRange.CBC.getRange_one() && cbc <= BloodTypeRange.CBC.getRange_two());
        boolean t2 = (bmp >= BloodTypeRange.BMP.getRange_one() && bmp <= BloodTypeRange.BMP.getRange_two());
        boolean t3 = (bloodPressure >= BloodTypeRange.BLOOD_PRESSURE.getRange_one() && bloodPressure <= BloodTypeRange.BLOOD_PRESSURE.getRange_two());
        return (t1 && t2 && t3);
    }

    private int index = 0;

    @Override
    public boolean wrongAnswer() {
        if (this.index == 2) {
            this.index = 0;
            return true;
        } else this.index++;
        return false;

    }
}

class HIVTest extends Test implements IChecking, IPrivate {
    boolean acceptedByLab;
    String hartIllness;


    @Override
    public boolean runOperation() {
        int cbc;
        int bmp;
        int bloodPresure;
        while (1 == 1) {
            cbc = getRandomNumber20Percent(20, 180);
            bmp = getRandomNumber20Percent(35, 340);
            bloodPresure = getRandomNumber20Percent(4, 150);
            if (checking(cbc, bmp, bloodPresure) == true) break;
            else {
                if (wrongAnswer() == true) {
                    return false;
                }
            }
        }
        String[] hartIllness = {"HEALTHY", "CORONARY", "STROKE", "PERIPHERALARTERIAL", "AORTIC", "NOT", "ERROR", "FINDFAILD"};
        super.Add(cbc, bmp, bloodPresure);
        int illnessIndex = getRandomNumber(0, 7);
        this.hartIllness = hartIllness[illnessIndex];
        return true;


    }
    @Override
    public Object sendResult() {
        //clone
        Test newClass = new HIVTest();
        newClass.Add(super.getCBC(), super.getBMP(), getBloodPressure());
        newClass.illnesses = super.illnesses;
        newClass.testID = this.testID;
        ((HIVTest) newClass).hartIllness = this.hartIllness;
        ((HIVTest) newClass).acceptedByLab = this.acceptedByLab;
        return newClass;
    }
    private int index = 0;
    @Override
    public boolean checking(int cbc, int bmp, int bloodPressure) {

        boolean t1 = (cbc >= AIDSRange.CBC.getRange_one() && cbc <= AIDSRange.CBC.getRange_two());
        boolean t2 = (bmp >= AIDSRange.BMP.getRange_one() && bmp <= AIDSRange.BMP.getRange_two());
        boolean t3 = (bloodPressure >= AIDSRange.BLOOD_PRESSURE.getRange_one() && bloodPressure <= AIDSRange.BLOOD_PRESSURE.getRange_two());
        return (t1 && t2 && t3);
    }
    @Override
    public boolean wrongAnswer() {

        if (this.index == 3) {
            this.index = 0;
            return true;
        } else this.index++;
        return false;

    }
    @Override
    public Test makingPrivate() {
        Test newTest = new HIVTest();
        int bmp = super.getBMP();

        newTest.Add(bmp, -1, -1);
        newTest.testID = super.testID;
        newTest.illnesses = super.illnesses;
        ((HIVTest) newTest).hartIllness = this.hartIllness;
        return newTest;
    }
    @Override
    public Requests gettingTheRequests() {
        for (int i = 0; i < Labratory.getLabratory().totalRequests.size(); i++) {
            Labratory.getLabratory().totalRequests.get(i).ID.equals(this.testID);
            return null;
        }

        if ((super.illnesses.size() == 0) || (super.sickPerson.age < 18)) {
            Requests requests = new Requests(super.sickPerson, (Test) (sendResult()));
            Labratory.getLabratory().addRequest(requests);
            return requests;
        }

        return null;
    }
}
class Theroyid extends Test implements IPrivate {
    int heartPressure;

    @Override
    public Test makingPrivate() {
        Test newTest = new Theroyid();
        int bmp = super.getBMP();
        int cbc = this.getCBC();
        newTest.Add(bmp, cbc, -1);
        newTest.testID = super.testID;
        newTest.illnesses = super.illnesses;
        return newTest;
    }

    @Override
    public boolean runOperation() {
        int cbc = 10;
        int randomNumber = getRandomNumber(3, 7);
        for (int i = 1; i <= randomNumber; i++) {
            cbc = cbc * i;
        }
        int bmp = cbc - randomNumber;
        int bloodPressure = cbc / bmp;
        this.heartPressure = randomNumber;
        super.Add(cbc, bmp, bloodPressure);
        return true;
    }

    public static int getRandomNumber(int min, int max) {

        int x = (int) ((Math.random() * (max - min)) + min);
        return x;
    }

    @Override
    public Object sendResult() {
        //clone
        Test newClass = new Theroyid();
        newClass.Add(super.getCBC(), super.getBMP(), getBloodPressure());
        newClass.illnesses = super.illnesses;
        newClass.testID = this.testID;
        ((Theroyid) newClass).heartPressure = this.heartPressure;
        return newClass;
    }

    public Requests gettingTheRequests() {
        for (int i = 0; i < Labratory.getLabratory().totalRequests.size(); i++) {
            Labratory.getLabratory().totalRequests.get(i).ID.equals(super.testID);
            return null;
        }

        if ((super.illnesses.size() == 0)) {
            Requests requests = new Requests(super.sickPerson, (Test) (sendResult()));

            Labratory.getLabratory().addRequest(requests);
            return requests;
        }

        return null;
    }
}
class Anemia extends Test implements IPrivate {
    @Override
    public boolean runOperation() {
        int randomNumber = getRandomNumber(0, 50);
        int cbc = Math.abs(randomNumber);
        int bmp = cbc / 2;
        super.Add(cbc, bmp, 2);
        return true;

    }

    @Override
    public Object sendResult() {
        //clone
        Test newClass = new Anemia();
        newClass.Add(super.getCBC(), super.getBMP(), getBloodPressure());
        newClass.illnesses = super.illnesses;
        newClass.testID = this.testID;
        return newClass;
    }

    @Override
    public Test makingPrivate() {
        Test newTest = new Anemia();
        int bmp = super.getBMP();
        newTest.Add(-1, bmp, -1);
        newTest.testID = super.testID;
        newTest.illnesses = super.illnesses;
        return newTest;
    }

    public Requests gettingTheRequests() {
        for (int i = 0; i < Labratory.getLabratory().totalRequests.size(); i++) {
            Labratory.getLabratory().totalRequests.get(i).ID.equals(super.testID);
            return null;
        }

        if ((super.sickPerson.age < 18)) {
            Requests requests = new Requests(super.sickPerson, (Test) (sendResult()));
            Labratory.getLabratory().addRequest(requests);
            return requests;
        }

        return null;
    }
}
