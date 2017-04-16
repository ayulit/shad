package lecture1.bank.v1_seq;

public class Account {

    private String id;
    private int balance;

    // constructor
    public Account(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    // getters
    public String getId() {
        return id;
    }
    synchronized public int getBalance(){
        return balance;
    }

    // deposit and withdraw
    synchronized public void post(int value) {
        balance += value;
    }

}
