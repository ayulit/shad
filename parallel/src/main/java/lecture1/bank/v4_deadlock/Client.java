package lecture1.bank.v4_deadlock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URISyntaxException;

import lecture1.bank.v1_seq.Account;
import lecture1.bank.v1_seq.Bank;

/** v4_deadlock  */
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

        // thread's body here... 

        try {

            // automated clients reads acc id
            String accountId = in.readLine();
            Account account = bank.getAccount(accountId);
            if (account == null) {
                throw new Exception(tab(id) + "[" + id + "] Account not found!");
            } else {
                out.println(tab(id) + "[" + id + "] From Account Id: " + accountId);
            }

            String toAccountId = in.readLine();
            Account toAccount = bank.getAccount(toAccountId);
            if (toAccountId == null) {
                throw new Exception(tab(id) + "[" + id + "] Account not found!");
            } else {
                out.println(tab(id) + "[" + id + "] To Account Id: " + toAccountId);
            }

            // automated clients reads money amount
            int value = Integer.parseInt(in.readLine());
            out.println(tab(id) + "[" + id + "] Enter transfer amount: " + value);

            // transfer
            bank.transfer(account, toAccount, value);

            // check balance
            out.println(tab(id) + "[" + id + "] Balance: " + account.getBalance());


        } catch (Exception e) {
            out.println(tab(id) + "[" + id + "] Error! " + e.getMessage());
        }
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
        bank.addAccount(new Account("Acc002", 100));
        
        Client[] clients = new Client[numClients];
        
        // let the clients live!
        for (int i = 0; i < numClients; i++) {
            // opening file from 'resourses'
            File inFile = new File (Client.class.getClass().getResource( "/lecture1/bank/v4_deadlock/input" + (i+1) ).toURI());
            
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
            clients[i] = new Client(i+1, bank, in, System.out);
            clients[i].start();
        }
        
    }

}
