import java.util.ArrayList;
enum  AIDSRange{
    CBC(20 , 180),
    BLOOD_PRESSURE(4 , 150),
    BMP(35 , 340);
    private enum HeartDisease{
        HEALTHY,
        CORONARY,
        STROKE,
        PERIPHERAL_ARTERIAL,
        AORTIC ;
    }
    private int range_one ;
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
enum  BloodTypeRange{
    CBC(20 , 160),
    BLOOD_PRESSURE(4 , 100),
    BMP(35 , 210);

    private int range_one ;
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

    }
}
class Person {


    static long totalID=1;
    String ID;

    public String[] illnesses;
    public double age;

    ArrayList<Test> tests=new ArrayList<>();

}
class Labratory
{
    private static Labratory labratory;
    static final String name="Lab";
     ArrayList<Test> doneTests=new ArrayList<>();
     ArrayList<Person> testedPersons=new ArrayList<>();
     ArrayList<Requests> totalRequests=new ArrayList<>();
     public static Labratory getLabratory() {
    return labratory;
}

    public void addTest(Test test)
    {
        this.addTest(test);
    }
    public void addRequest(Requests requests)
    {

        this.totalRequests.add(requests);
    }
    public void acceptRequest(int index)
    {
        int value=-1;
        Requests request=totalRequests.get(index);
        totalRequests.remove(index);
        for(Person x:testedPersons)
        {
            if(x.ID.equals(request.ID));
            value=testedPersons.indexOf(x);
            break;
        }
        testedPersons.get(value).tests.remove(request.privateTest);
        testedPersons.get(value).tests.add(request.test);
        testedPersons.remove(value);


    }
    public void denyRequest(int index)
    {
        totalRequests.remove(index);
    }



}
class Requests
{
    String ID;
    Test test;
    Test privateTest;
    public Requests(Person person,Test test,Test privateTest)
    {
        this.ID=ID;
        this.test=test;
        this.privateTest=privateTest;
    }


}
abstract class Test
{
    static int IDCalculator=0;
    String ID;
    Person sickPerson;
    ArrayList<String> illnesses;
    private int CBC;
    private int BMP;
    private int bloodPressure;

    public void Add(int cbc,int bmp,int bloodPressure)
    {
        this.CBC=cbc;
        this.BMP=bmp;
        this.bloodPressure=bloodPressure;

    }
    public int getBloodPressure()
    {

        return bloodPressure;
    }
    public int getCBC()
    {


        return CBC;
    }
    public int getBMP()
    {

        return BMP;
    }
    static int getRandomNumber20Percent(int min, int max) {
        if((double)((Math.random() * (5)))!=4)
        {
            return (int) ((Math.random() * (max - min)) + min);
        }else{
            return((int) ((Math.random() * (min))));
        }


    }
    static int getRandomNumber(int min, int max) {

        int x=(int)((Math.random() * (max - min)) + min);
        return x;
    }
    public abstract boolean runOperation();
    public abstract Object sendResult();

}

interface IChecking
{
boolean checking(int cbc,int bmp,int bloodPressure);
boolean wrongAnswer();
}
interface IPrivate
{
    Test makingPrivate();
    Requests gettingTheRequests();


}

 class BloodType extends Test implements IChecking
{
    public boolean accepted;
    @Override
    public boolean runOperation()
    {   int cbc;
        int bmp;
        int bloodPressure;
        while(1==1) {
             cbc = getRandomNumber20Percent(20, 180);
             bmp = getRandomNumber20Percent(35, 210);
             bloodPressure = getRandomNumber20Percent(4, 100);
            if (checking(cbc, bmp,bloodPressure) == true) break;
            else {
                if (wrongAnswer() == true) {
                    this.accepted=false;
                    return false;
                }
            }

        }
        Add(cbc,bmp,bloodPressure);
        this.accepted=true;
        return true;



    }

    @Override
    public Object sendResult() {
        //clone
        Test newClass=new BloodType();
        newClass.Add(super.getCBC(),super.getBMP(), getBloodPressure());
        newClass.illnesses=super.illnesses;
        newClass.ID=this.ID;
        ((BloodType)newClass).accepted =this.accepted;
        return newClass;
    }

    @Override
    public boolean checking(int cbc, int bmp, int bloodPressure) {
            boolean t1=(cbc>=BloodTypeRange.CBC.getRange_one() && cbc<=BloodTypeRange.CBC.getRange_two());
            boolean t2=(bmp >=BloodTypeRange.BMP.getRange_one() && bmp<=BloodTypeRange.BMP.getRange_two());
            boolean t3=(bloodPressure>=BloodTypeRange.BLOOD_PRESSURE.getRange_one() && bloodPressure<=BloodTypeRange.BLOOD_PRESSURE.getRange_two());
            return(t1 && t2 &&t3);
        }
        private int index=0;
    @Override
    public boolean wrongAnswer() {
        if(this.index==2)
        {
            this.index=0;
            return true;
        }
        else this.index++;
        return false;

    }
}
class HIVTest extends Test implements IChecking,IPrivate
{
    boolean acceptedByLab;
    String hartIllness;


    @Override
    public boolean runOperation() {
        int cbc;
        int bmp;
        int bloodPresure;
        while(1==1)
    {
       cbc=getRandomNumber20Percent(20,180);
       bmp=getRandomNumber20Percent(35,340);
        bloodPresure=getRandomNumber20Percent(4,150);
        if(checking(cbc,bmp,bloodPresure)==true)break;
        else{
           if(wrongAnswer()==true)
           {
               return false;
           }
        }
    }
        String[] hartIllness={"HEALTHY","CORONARY" ,"STROKE" ,"PERIPHERALARTERIAL","AORTIC","NOT","ERROR","FINDFAILD"};
        super.Add(cbc,bmp,bloodPresure);
        int illnessIndex=getRandomNumber(0,7);
        this.hartIllness=hartIllness[illnessIndex];
        return true;


    }
       @Override
    public Object sendResult() {
        //clone
        Test newClass=new HIVTest();
        newClass.Add(super.getCBC(),super.getBMP(), getBloodPressure());
        newClass.illnesses=super.illnesses;
        newClass.ID=this.ID;
        ((HIVTest)newClass).hartIllness=this.hartIllness;
        ((HIVTest)newClass).acceptedByLab=this.acceptedByLab;
        return newClass;
    }
    private int index=0;

    @Override
    public boolean checking(int cbc, int bmp, int bloodPressure) {
   boolean t1=(cbc>=AIDSRange.CBC.getRange_one() && cbc<=AIDSRange.CBC.getRange_two());
   boolean t2=(bmp >=AIDSRange.BMP.getRange_one() && bmp<=AIDSRange.BMP.getRange_two());
   boolean t3=(bloodPressure>=AIDSRange.BLOOD_PRESSURE.getRange_one() && bloodPressure<=AIDSRange.BLOOD_PRESSURE.getRange_two());
   return(t1 && t2 &&t3);
    }
    @Override
    public boolean wrongAnswer()
    {
        if(this.index==3)
        {
            this.index=0;
            return true;
        }
        else this.index++;
        return false;

    }

    @Override
    public Test makingPrivate() {
        Test newTest=new HIVTest();
        int bmp=super.getBMP();

        newTest.Add(bmp,-1,-1);
        newTest.ID=super.ID;
        newTest.illnesses=super.illnesses;
        ((HIVTest)newTest).hartIllness=this.hartIllness;
        return newTest;
    }

    @Override
    public Requests gettingTheRequests()
    {
        for(int i=0;i<Labratory.getLabratory().totalRequests.size();i++) {
            Labratory.getLabratory().totalRequests.get(i).ID.equals(this.ID);
        return null;
        }

        if((super.illnesses.size()==0) ||(super.sickPerson.age<18))
        {
            Requests requests=new Requests(super.sickPerson,(Test)(sendResult()),makingPrivate());
            Labratory.getLabratory().addRequest(requests);
            return requests;
        }

        return null;
    }



}
class Theroyid extends Test implements IPrivate
{
    int heartPressure;
    @Override
    public Test makingPrivate() {
        Test newTest=new Theroyid();
        int bmp=super.getBMP();
        int cbc=this.getCBC();
        newTest.Add(bmp,cbc,-1);
        newTest.ID=super.ID;
        newTest.illnesses=super.illnesses;
        return newTest;
    }

    @Override
    public boolean runOperation()
    {
        int cbc=10;
        int randomNumber=getRandomNumber(3,7);
        for(int i=1;i<=randomNumber;i++)
        {
            cbc=cbc*i;
        }
        int bmp =cbc-randomNumber;
        int bloodPressure=cbc/ bmp;
        this.heartPressure=randomNumber;
        super.Add(cbc, bmp,bloodPressure);
        return true;
    }
    public static int getRandomNumber(int min, int max)
    {

        int x=(int)((Math.random() * (max - min)) + min);
        return x;
    }
    @Override
    public Object sendResult() {
        //clone
        Test newClass=new Theroyid();
        newClass.Add(super.getCBC(),super.getBMP(), getBloodPressure());
        newClass.illnesses=super.illnesses;
        newClass.ID=this.ID;
        ((Theroyid)newClass).heartPressure=this.heartPressure;
        return newClass;
    }

    public Requests gettingTheRequests()
    {
        for(int i=0;i<Labratory.getLabratory().totalRequests.size();i++) {
            Labratory.getLabratory().totalRequests.get(i).ID.equals(super.ID);
            return null;
        }

        if((super.illnesses.size()==0))
        {
            Requests requests=new Requests(super.sickPerson,(Test)(sendResult()),makingPrivate());

            Labratory.getLabratory().addRequest(requests);
            return requests;
        }

        return null;
    }
}
class Anemia extends Test implements IPrivate
{
    @Override
    public boolean runOperation() {
        int randomNumber=getRandomNumber(0,50);
        int cbc =Math.abs(randomNumber);
        int bmp=cbc/2;
        super.Add(cbc,bmp,2);
        return true;

    }

    @Override
    public Object sendResult() {
        //clone
        Test newClass=new Anemia();
        newClass.Add(super.getCBC(),super.getBMP(), getBloodPressure());
        newClass.illnesses=super.illnesses;
        newClass.ID=this.ID;
        return newClass;
    }

    @Override
    public Test makingPrivate() {
        Test newTest=new Anemia();
        int bmp=super.getBMP();
        newTest.Add(-1,bmp,-1);
        newTest.ID=super.ID;
        newTest.illnesses=super.illnesses;
        return newTest;
    }
    public Requests gettingTheRequests()
    {
        for(int i=0;i<Labratory.getLabratory().totalRequests.size();i++) {
            Labratory.getLabratory().totalRequests.get(i).ID.equals(super.ID);
            return null;
        }

        if((super.sickPerson.age<18))
        {
            Requests requests=new Requests(super.sickPerson,(Test)(sendResult()),makingPrivate());
            Labratory.getLabratory().addRequest(requests);
            return requests;
        }

        return null;
    }
}


