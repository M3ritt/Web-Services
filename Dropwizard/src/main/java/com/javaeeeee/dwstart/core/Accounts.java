package com.javaeeeee.dwstart.core;

import java.util.ArrayList;

public class Accounts {
    private ArrayList<Account> accounts = new ArrayList<>();

    /**
     * Gets ArrayList of all the Accounts.
     *
     * @return : ArrayList<Account> of Accounts
     */
    public ArrayList<Account> get_accounts(){
        return this.accounts;
    }


    @Override
    public String toString(){
        return "[Account size]: " + this.accounts.size();
    }
}
