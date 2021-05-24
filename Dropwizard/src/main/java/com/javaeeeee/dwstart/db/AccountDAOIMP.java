package com.javaeeeee.dwstart.db;

import com.javaeeeee.dwstart.core.Account;
import com.javaeeeee.dwstart.core.Accounts;
import com.javaeeeee.dwstart.core.Sandwich;

import java.sql.*;
import java.util.Random;

public class AccountDAOIMP implements AccountDAO{


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
            return conn.createStatement();
        } catch(Exception ex) {
            return null;
        }
    }

    @Override
    public String seeAccounts() {
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            String sqlStr = "select * from account";
            ResultSet rset = stmt.executeQuery(sqlStr);
            json.append(" { status : 200 \n \"Accounts: \n\"");
            int count = 0;
            while(rset.next()) {
                json.append("\n \"username\": ").append(rset.getString("username")).append(",\n");
                count++;
            }
            json.append("}");
            if(count == 0)
                return "{ status : 200 \n No Account exists \n}";
        } catch(Exception ex) {
            return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
        return json.toString();
    }

    @Override
    public String getAccount(String username) {
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            String sqlStr = "select * from account where username = '" + username + "'";
            ResultSet rset = stmt.executeQuery(sqlStr);
            json.append("{ status : 200 \n \"Account\": ");
            int count = 0;
            while(rset.next()) {
                json.append("{\n 	\"username\": ").append(rset.getString("username")).append(",\n");
                json.append(" 	\"password (Should be a secret though)\": ").append(rset.getString("password")).append(",\n");
                count++;
            }
            if(count == 0) {
                return "{ status : 200 \n No Account exists \n}";
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
                json.append(" 	\"Favorite Sandwich list size: \": ").append(count).append("\n  }");
            }
        } catch(Exception ex) {
            return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
        return json.toString();
    }

    @Override
    public String getFavorites(String username) {
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            if(stmt == null)
                return "{ status : 200 \n No Account exists \n}";

            String sqlStr = "select favorites.bread, favorites.meat, favorites.cheese, favorites.sauce, favorites.veggies, favorites.extras, favorites.id from favorites where favorites.username = '" + username + "'";
            ResultSet rset = stmt.executeQuery(sqlStr);

            int count = 0;
            json.append("{ status : 200 \n \"Favorites\" :");
            json.append("\n \"Username\": ").append(username).append("\n");
            while(rset.next()) {
                json.append("\n	\"Sandwich\": {" + "\n");
                json.append(" 		\"id\": ").append(rset.getString("id")).append(",\n");
                json.append(" 		\"Bread\": ").append(rset.getString("bread")).append(",\n");
                json.append(" 		\"Meat\": ").append(rset.getString("meat")).append(",\n");
                json.append(" 		\"Cheese\": ").append(rset.getString("cheese")).append(",\n");
                json.append(" 		\"Sauce\": ").append(rset.getString("sauce")).append(",\n");
                json.append(" 		\"Veggie\": ").append(rset.getString("veggies")).append(",\n");
                json.append(" 		\"Extras\": ").append(rset.getString("extras")).append(",\n");
                json.append("	}, \n");
                count++;
            }
            if(count == 0) {
                json.append(" No sandwiches found \n }");
            }
            json.append("}");
        } catch(Exception ex) {
            return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
        return json.toString();
    }

    @Override
    public String getHistory(String username) {
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            if(stmt == null)
                return "{ status : 200 \n No Account exists \n}";

            String sqlStr = "select history.bread, history.meat, history.cheese, history.sauce, history.veggies, history.extras, history.id from history where history.username = '" + username + "'";
            ResultSet rset = stmt.executeQuery(sqlStr);
            int count = 0;
            json.append("{ status : 200 \"History\" :");
            json.append("\n \"Username\": ").append(username).append("\n");
            while(rset.next()) {
                json.append("\n	\"Sandwich\": {" + "\n");
                json.append(" 		\"id\": ").append(rset.getString("id")).append(",\n");
                json.append(" 		\"Bread\": ").append(rset.getString("bread")).append(",\n");
                json.append(" 		\"Meat\": ").append(rset.getString("meat")).append(",\n");
                json.append(" 		\"Cheese\": ").append(rset.getString("cheese")).append(",\n");
                json.append(" 		\"Sauce\": ").append(rset.getString("sauce")).append(",\n");
                json.append(" 		\"Veggie\": ").append(rset.getString("veggies")).append(",\n");
                json.append(" 		\"Extras\": ").append(rset.getString("extras")).append(",\n");
                json.append("	}, \n");
                count++;
            }
            if(count == 0) {
                json.append(" No sandwiches found .\n}");
            }
            json.append("}");
        } catch(Exception ex) {
            return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
        return json.toString();
    }

    @Override
    public String addFavorite(String username, String id) {
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            if(stmt == null)
                return "{ status : 200 \n No Account or id exists \n}";

            try{
                String sqlStr = "select * from account where account.username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
                String loggedIn = "";
                while(rset.next()) {
                    loggedIn = rset.getNString("login");
                }

                if(loggedIn.equals("0"))
                    return "{ Status : 403 \n User is not logged in. Why try to use someone else's account? \n}";
                sqlStr = "select * from history where history.username = '" + username + "' and history.id = '" + id + "'";
                rset = stmt.executeQuery(sqlStr);
                // Checking if the username and id exists in the database
                int count = 0;
                String Bread = "", Meat = "", Cheese = "", Sauce = "", Veggie = "", Extras = "";
                while(rset.next()) {
                    Bread = rset.getString("Bread");
                    Meat = rset.getString("Meat");
                    Cheese = rset.getString("Cheese");
                    Sauce = rset.getString("Sauce");
                    Veggie = rset.getString("Veggies");
                    Extras = rset.getString("Extras");
                    count++;
                }
                if(count == 0) {
                    return "{ status : 200 \n No Account or id exists \n}";
                } else {
                    try {
                        sqlStr = "insert into favorites values ('" + Bread + "', '" + Meat + "', '" + Cheese + "', '" +
                                Sauce + "', '" + Veggie + "', '" + Extras + "', '" + username + "', '" + id + "')";
                        stmt.executeUpdate(sqlStr);
                        json.append("{ status : 200 \n Success : Sandwich : ").append(id).append(" was added to ").append(username).append("'s Favorite list. \n}");
                    } catch(Exception ex){
                        return "{ status : 200 \n  Sandwich : " + id + " is already a favorite.\n}";
                    }
                }
            } catch(Exception ex) {
                return "{ status : 200 \n No Account exists \n}";
            }
        } catch(Exception ex) {
            return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
        return json.toString();
    }

    @Override
    public String removeFavorite(String username, String id) {
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            if(stmt == null)
                return "{ status : 200 \n No Account or id exists \n}";

            try{
                String sqlStr = "select * from account where account.username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
                String loggedIn = "";
                while(rset.next()) {
                    loggedIn = rset.getNString("login");
                }

                if(loggedIn.equals("0"))
                    return "{ Status : 403 \n User is not logged in. Why try to use someone else's account? \n}";

                sqlStr = "select * from favorites where favorites.username = '" + username + "' and favorites.id = '" + "id'";
                rset = stmt.executeQuery(sqlStr);
                // Checking if the username and id exists in the database
                int count = 0;
                while(rset.next()) {
                    count++;
                }
                sqlStr = "delete from favorites where favorites.username = '" + username + "' and favorites.id = '" + id + "'";
                stmt.executeUpdate(sqlStr);
                json.append("{ status : 200 \n Success : Sandwich : ").append(id).append(" was removed from ").append(username).append("'s Favorite list. \n}");
            } catch(Exception ex) {
                return "{ status : 200 \n No Account or id exists \n}";
            }
        } catch(Exception ex) {
         return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
        return json.toString();
    }

    @Override
    public String createAccount(String username, String password) {
        if(seeAccounts().contains(username))
            return "{ status : 200 \n There is already an account with the entered username.\n} ";
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);
            try{
                String sqlStr = "insert into account values ('" + username +  "', '" + password + "', '0')";
                stmt.executeUpdate(sqlStr);
                return "{ status : 200 \n Success : Account created with username: " + username + "\n}";
            } catch(Exception ex) {
                return "{ status : 200 \n Account already  exists \n}";
            }
        } catch(Exception ex) {
            return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
    }

    @Override
    public String deleteAccount(String username, String password) {
        if(!seeAccounts().contains(username))
            return "{status : 200 \n There is no account with that username. }\n";
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);
            try{
                String sqlStr = "select * from account where account.username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
                String loggedIn = "";
                while(rset.next()) {
                    loggedIn = rset.getNString("login");
                }

                if(loggedIn.equals("0"))
                    return "{ Status : 401 \n User is not logged in. Why try to use someone else's account? \n}";

                sqlStr = "delete from account where account.username = '" + username + "' and password = '" + password + "'";
                stmt.executeUpdate(sqlStr);
                return "{ status : 200 \n Success : Account deleted with username: " + username + "\n}";
            } catch(Exception ex) {
                return "{ status : 200 \n Invalid username or password \n}";
            }
        } catch(Exception ex) {
            return "{ status : 200 \n Invalid username or password \n}";
        }
    }

    @Override
    public String login(String username, String password) {
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            try{
                String sqlStr = "select * from account where account.username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
                String dbPassword = "", loggedIn = "";
                while(rset.next()) {
                    dbPassword = rset.getNString("password");
                    loggedIn = rset.getNString("login");
                }
                // If a User is logged in than loggedIn = 1
                if(!password.equalsIgnoreCase(dbPassword) || loggedIn.equals("1"))
                    return " { status : 401 \n  Login cannot be completed. Username or password may be incorrect. \n}";
                else {
                    sqlStr = "update account set login = 1 where username = '" + username + "'";
                    stmt.executeUpdate(sqlStr);
                    return " { status : 200 \n " + username + " has a successful login. \n}";
                }
            } catch(Exception ex){
                return " { status : 401 \n  Login cannot be completed. Username or password may be incorrect. \n}";
            }
        } catch(Exception ex){
            return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
    }

    @Override
    public String logout(String username, String password) {
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            try{
                String sqlStr = "select * from account where account.username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
                String dbPassword = "", loggedIn = "";
                while(rset.next()) {
                    dbPassword = rset.getNString("password");
                    loggedIn = rset.getNString("login");
                }

                // If a User is logged in than loggedIn = 1
                if(!password.equalsIgnoreCase(dbPassword) || loggedIn.equals("0"))
                    return " { status : 401 \n  Login cannot be completed. Username or password may be incorrect. \n}";
                else{
                    sqlStr = "update account set login = 0 where username = '" + username + "'";
                    stmt.executeUpdate(sqlStr);
                    return " { status : 200 \n " + username + " has a successful logout. \n}";
                }
            } catch(Exception ex){
                return " { status : 401 \n  Login cannot be completed. Username or password may be incorrect. \n}";
            }
        } catch(Exception ex){
            return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
    }

    @Override
    public String generateSandwich(String username) {
        String temp = getAccount(username);
        if(!temp.contains(username))
            return "{ status : 200 \n No Account exists \n}";

        Account tempU = new Account("temp", "temp");
        Sandwich s = tempU.generateSandwich(username);

        String json = "{ status : 200 \n {";
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            try{
                String sqlStr = "select * from account where account.username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
                String loggedIn = "";
                while(rset.next()) {
                    loggedIn = rset.getNString("login");
                }

                if(loggedIn.equals("0"))
                    return "{ Status : 401 \n User is not logged in. Why try to use someone else's account? \n}";

                // Getting next id to add
                Random rand = new Random();
                int id = rand.nextInt(1000);

                sqlStr = "select account.username from account where account.username = '" + username + "'";
                rset = stmt.executeQuery(sqlStr);
                int count = 0;
                while(rset.next()) {
                    count++;
                }
                if (count != 0){
                    json += "\n \"Username\": " + username + "\n";
                    json += "   \"Sandwich\": {" + "\n";
                    json += " \"Bread\": " + s.get_Bread() + ",\n";
                    json += " \"Meat\": " + s.get_Meat() + ",\n";
                    json += " \"Cheese\": " + s.get_Cheese() + ",\n";
                    json += " \"Sauce\": " + s.get_Sauce() + ",\n";
                    json += " \"Veggie\": " + s.get_Veggie() + ",\n";
                    json += " \"Extra\": " + s.get_Extra() + ",\n";
                    json += "},\n";

                    sqlStr = "insert into history values ('" + s.get_Bread() + "','" + s.get_Meat() + "','" +
                            s.get_Cheese() + "','" + s.get_Sauce() + "','" + s.get_Veggie() + "','" + s.get_Extra()
                            + "', '" + username + "','" + id + "')";
                    stmt.executeUpdate(sqlStr);
                }
            } catch(Exception ex) {
//                return ex.toString();
                return "{ status : 200 \n No Account exists \n}";
            }
        } catch(Exception ex) {
//            return ex.toString();
            return "{ status : 503 \n Error: Could not connect to database. }\n}";
        }
        json += "}";
        return json;
    }
}
