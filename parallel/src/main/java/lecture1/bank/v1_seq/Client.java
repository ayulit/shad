package lecture1.bank.v1_seq;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Client {

    private Bank bank;
    private BufferedReader in;
    private PrintStream out;

    public Client(Bank bank, BufferedReader in, PrintStream out) {
        this.bank = bank;
        this.in = in;
        this.out = out;
    }    

    public void run() {
        
        while (true) {
            
            try {
                
                out.print("Enter account Id: ");
                // user enters acc id
                String accountId = in.readLine();
                Account account = bank.getAccount(accountId);
                
                if (account == null) {
                    throw new Exception("Account not found!");
                } else {
                    out.println("Balance: " + account.getBalance());
                }
                
                out.print("Enter money amount: ");
                // user enters
                int value = Integer.parseInt(in.readLine());
                
                // check for negative balance
                if (account.getBalance() + value >= 0) {

                    account.post(value);
                    out.println("Balance: " + account.getBalance());
                    
                } else {
                    throw new Exception("Not enough money!");
                }
                
            } catch (Exception e) {
                out.println("Error! " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        Bank bank = new Bank();
        bank.addAccount(new Account("acc007", 100));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        Client  client = new Client(bank, in, System.out);
        client.run();
    }

}
