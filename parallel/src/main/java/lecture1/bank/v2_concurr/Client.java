package lecture1.bank.v2_concurr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URISyntaxException;

import lecture1.bank.v1_seq.Account;
import lecture1.bank.v1_seq.Bank;

public class Client extends Thread {

    static final int numClients = 2;
    
    private int id;
    private Bank bank;
    private BufferedReader in;
    private PrintStream out;
    
    public Client(int id, Bank bank, BufferedReader in, PrintStream out) {
        this.id = id;
        this.bank = bank;
        this.in = in;
        this.out = out;
    }
 
    @Override
    public void run() {
        
        // thread's body here... just the same as single client's
//        while (true) { // running one transaction for testing
            
            try {
                
                // automated clients reads acc id
                String accountId = in.readLine();
                Account account = bank.getAccount(accountId);
                
                if (account == null) {
                    throw new Exception(tab(id) + "[" + id + "] Account not found!");
                } else {
                    out.println(tab(id) + "[" + id + "] Account Id: " + accountId);
                    out.println(tab(id) + "[" + id + "] Balance: " + account.getBalance());
                }
                
                // automated clients reads
                int value = Integer.parseInt(in.readLine());
                out.println(tab(id) + "[" + id + "] Enter amount: " + value);
                
                // check for negative balance
                if (account.getBalance() + value >= 0) {
                    
                    // Thread.sleeps for negative balance simulation!
                    Thread.sleep(100); // 0,1 sec
                    account.post(value);
                    Thread.sleep(100); // 0,1 sec
                    out.println(tab(id) + "[" + id + "] Balance: " + account.getBalance());
                    
                } else {
                    throw new Exception("Not enough money!");
                }
                
            } catch (Exception e) {
                out.println(tab(id) + "[" + id + "] Error! " + e.getMessage());
            }
//        }
    }
    
    private String tab(int id) {
        if ( (id & 1) == 0 ) { 
            //even...
            return "    ";
        }
        //odd... 
        return "";
    }

    public static void main(String[] args) throws IOException, URISyntaxException {

        Bank bank = new Bank();
        bank.addAccount(new Account("Acc001", 100));
        
        Client[] clients = new Client[numClients];
        
        for (int i = 0; i < numClients; i++) {
            // opening file from 'resourses'
            File inFile = new File (Client.class.getClass().getResource( "/bank/input" + (i+1) ).toURI());
            
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
            clients[i] = new Client(i+1, bank, in, System.out);
            clients[i].start();
        }
        
    }

}
