package lecture1.helloworld;

public class RunnablePerson extends Person implements Runnable {


    public RunnablePerson(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i=0; i<10; i++) {
            System.out.println(getName() + ": You cute!");
        }
    }

    public static void main(String[] args) {
        RunnablePerson p1 = new RunnablePerson("Fry");
        Thread t1 = new Thread(p1);
        t1.start();
        
        RunnablePerson p2 = new RunnablePerson("Luci");
        Thread t2 = new Thread(p2);
        t2.start();

        System.out.println("Main thread finished.");
    }
}
