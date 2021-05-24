package com.example.mypkg;

import java.sql.*;
import java.util.ArrayList;

//ADD METHOD TO DELETE ACCOUNTS

public class Accounts {
    private ArrayList<Account> accounts = new ArrayList<>();


    public Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mywebapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "myuser", "Jam3ritt");
        } catch (SQLException ex){
            return null;
        }
    }

    public Statement connectDB(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            return stmt;
        } catch(Exception ex) {
            return null;
        }
    }


    // This is not currently checking if already in database --> Need to change
    public String add_account(String username, String password){
        String json = "";
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);
            try{
                String sqlStr = "insert into account values ('" + username +  "', '" + password + "')";
                stmt.executeUpdate(sqlStr);
                json += "\n Success : Account created with username: " + username;
                this.accounts.add(new Account(username, password));
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } catch(Exception ex) {
            json += " Error : Invalid username and/or password.\n";
            ex.printStackTrace();
        }
        return json;
    }

    public ArrayList<Account> get_accounts(){
       return this.accounts;
    }

    public StringBuilder seeAccounts(){
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            String sqlStr = "select * from account";
            ResultSet rset = stmt.executeQuery(sqlStr);
            json.append(" \"Accounts: \n\"");
            int count = 0;
            while(rset.next()) {
                json.append("\n \"username\": ").append(rset.getString("username")).append(",\n");
                count++;
            }
            if(count == 0)
                json.append("No Accounts exists");
        } catch(Exception ex) {
            json.append("\n Error: Could not connect to database. }\n}");
        }
        return json;
    }


    // Returning weird output if more than one with same username.
    public StringBuilder findAccount(String username){
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            String sqlStr = "select * from account where username = '" + username + "'";
            ResultSet rset = stmt.executeQuery(sqlStr);
            json.append(" \"Account\": ");
            int count = 0;
            while(rset.next()) {
                json.append("{\n 	\"username\": ").append(rset.getString("username")).append(",\n");
                json.append(" 	\"password (Should be a secret though)\": ").append(rset.getString("password")).append(",\n");
                count++;
            }
            if(count == 0) {
                json.append("No Account exists");
            } else {

                sqlStr = "select history.bread, history.meat, history.cheese, history.sauce, history.veggies, history.extras from account, history where account.username = '" + username + "' and history.username = '" + username + "'";

                rset = stmt.executeQuery(sqlStr);
                count = 0;
                while(rset.next()) {
                    if(!rset.getString("bread").equals(""))
                        count ++;
                }
                json.append(" 	\"History Sandwich list size: \": ").append(count).append(",\n");

                sqlStr = "select favorites.bread, favorites.meat, favorites.cheese, favorites.sauce, favorites.veggies, favorites.extras from account, favorites where account.username = '" + username + "' and favorites.username = '" + username + "'";
                rset = stmt.executeQuery(sqlStr);
                count = 0;
                while(rset.next()) {
                    if(!rset.getString("bread").equals(""))
                        count ++;
                }
                json.append(" 	\"Favorite Sandwich list size: \": ").append(count).append(",\n");
            }
        } catch(Exception ex) {
            json.append("\n Error: Could not connect to database. }\n}");
        }
        return json;
    }

    @Override
    public String toString(){
        return "[Account size]: " + this.accounts.size();
    }

}
