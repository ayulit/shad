package lecture1.bank.v1_seq;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String,Account> accounts;

    // constructor
    public Bank() {
        accounts = new HashMap<String,Account>();
    }

    // add new account
    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }
    
    // get acc by id
    public Account getAccount(String id) {
        return accounts.get(id);
    }
    
    // money transferring
    public void transfer(Account from, Account to, int value) throws Exception {
        if (value <= 0) {
            throw new Exception("Amount must be positive!");
        }
        
        /* DEADLOCK is possible in this configuration! */
        
        // lock 'from'-object
        synchronized (from) {
            
            if (from.getBalance() < value) {
                throw new Exception("Not enough money!");
            } else {
                // withdraw 'value' from..
                from.post(-value); // post is still synchronized
            }
            
            // lock 'to'-object
            synchronized (to) {
                // ..and deposit 'value' to
                to.post(value);   // post is still synchronized              
            }
        
        } // synchronized (from) until this, because of demands of total money amount invariance in the bank!
    }
}
