package com.mypkg;

import java.util.ArrayList;

public class Accounts{
	private ArrayList<Account> accounts;
	
	public Accounts(ArrayList<Account> accounts){
		this.accounts = accounts;
	}
	

	public void add_account(Account a){
		this.accounts.add(a);
	}
	

	public void get_accounts(){
		for(Account a : this.accounts)
			System.out.println(a);
	}
	
	@Override
	public String toString(){
		return "[Account size]: " + this.accounts.size();
	}
}