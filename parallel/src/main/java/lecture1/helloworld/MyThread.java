package lecture1.helloworld;

public class MyThread extends Thread {

    @Override
    public void run() {
        // thread's body here...
        System.out.println("Hello World from MyThread");
    } 
    
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
        
        System.out.println("Main thread finished.");
    }

}
