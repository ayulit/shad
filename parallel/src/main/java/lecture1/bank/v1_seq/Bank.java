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
}
