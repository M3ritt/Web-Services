package com.example.mypkg;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Account{
    private String id, password;
    private ArrayList<Sandwich> history;
    private ArrayList<Sandwich> favorites;

    public Account(String id, String password){
        this.id = id;
        this.password = password;
        this.history = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

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

    public String get_id(){
        return this.id;
    }

    public void add_history(Sandwich s){
        this.history.add(s);
    }


    public ArrayList<Sandwich> get_history(){
        return this.history;
    }

    public StringBuilder see_history(String username){
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

        if(stmt == null)
            return json.append("[Error] No account with that name exists.\n}");
        String sqlStr = "select history.bread, history.meat, history.cheese, history.sauce, history.veggies, history.extras from history where history.username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
            int count = 0;
            json.append(" \"History\" :");
            json.append("\n \"Username\": ").append(username).append("\n");
            while(rset.next()) {
                json.append("\n	\"Sandwich\": {" + "\n");
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
                json.append(" [No sandwiches found].\n");
            }
        } catch(Exception ex) {
            json.append(" Error : Trouble connecting to database. \n}");
        }
        return json;
    }

    public StringBuilder see_favorites(String username){
        StringBuilder json = new StringBuilder();
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            if(stmt == null)
                return json.append("[Error] No account with that name exists.\n}");

            String sqlStr = "select favorites.bread, favorites.meat, favorites.cheese, favorites.sauce, favorites.veggies, favorites.extras from favorites where favorites.username = '" + username + "'";
            ResultSet rset = stmt.executeQuery(sqlStr);

            int count = 0;
            json.append(" \"Favorites\" :");
            json.append("\n \"Username\": ").append(username).append("\n");
            while(rset.next()) {
                json.append("\n	\"Sandwich\": {" + "\n");
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
                json.append(" [No sandwiches found].\n");
            }
        } catch(Exception ex) {
            json.append(" [Error] No account with that name exists.\n}");
            ex.printStackTrace();
        }
        return json;
    }

    public String add_favorite(String bread, String meat, String cheese, String sauce, String veggie, String extra, String username){
        this.favorites.add(new Sandwich(bread, meat, cheese, sauce, veggie, extra, username));
        String json = "";
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mywebapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "myuser", "Jam3ritt");
                Statement stmt = conn.createStatement()
        ) {
            try{
                String sqlStr = "select * from account where username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
                // Checking if the username exists in the database
                int count = 0;
                while(rset.next()) {
                    count++;
                }
                if(count == 0) {
                    json += "\n Error : No account with that name exists. \n";
                } else {
                    sqlStr = "insert into favorites values ('" + bread + "','" + meat + "','" + cheese +
                            "','" + sauce + "','" + veggie + "','" + extra + "', '" + username + "')";
                    stmt.executeUpdate(sqlStr);
                    json += "\n Success : Sandwich was added to " + username + "'s Favorite list. \n";
                }
            } catch(Exception ex) {
                json += " \n Error: No account with that name exists. \n";
                //ex.printStackTrace();
            }
        } catch(Exception ex) {
            json += " \n Error : Could not connect to database.\n";
            //ex.printStackTrace();
        }
        return json;
    }

    public String remove_favorite(String bread, String meat, String cheese, String sauce, String veggie, String extra, String username){
        this.favorites.remove(new Sandwich(bread, meat, cheese, sauce, veggie, extra, username));
        String json = "";
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mywebapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "myuser", "Jam3ritt");
                Statement stmt = conn.createStatement()
        ) {
            try{
                String sqlStr = "select * from account where username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
                // Checking if the username exists in the database
                int count = 0;
                while(rset.next()) {
                    count++;
                }
                if(count == 0) {
                    json += "\n Error : No account with that name exists.";
                } else {
                    sqlStr = "delete from favorites where favorites.bread = '" + bread + "' and favorites.meat = '"
                            + meat + "' and favorites.cheese = '" + cheese + "' and favorites.sauce = '" + sauce +
                            "' and favorites.veggies = '"+ veggie + "' and favorites.extras = '"+ extra +
                            "' and favorites.username = '" + username + "'";
                    stmt.executeUpdate(sqlStr);
                    json += "\n Success : Sandwich was removed from " + username + "'s Favorite list. \n";
                }
            } catch(Exception ex) {
                json += " \n Error: No account with that name exists. \n}";
                ex.printStackTrace();
            }
        } catch(Exception ex) {
            json += " \n Error : Invalid username and/or password.\n";
            ex.printStackTrace();
        }
        return json;
    }


    public ArrayList<Sandwich> get_favorites(){
        return this.favorites;
    }

    public String generate_sandwich(String username){
        Random number = new Random();
        String[] bread_choices = {"9-Grain Wheat", "Multi-grain Flatbread" , "Italian ", "Italian Herbs & Cheese", "Flatbread"};
        String bread = bread_choices[number.nextInt(bread_choices.length)];

        String[] meat_choices = {"Turkey breast", "Ham" , "Chicken breast", "Roast Beef", "Tuna", "Turkey salami", "beefsteak", "bacon", "meatballs", "Genoa Salami", "Turkey bologna", "Shaved Steak"};
        String meat = meat_choices[number.nextInt(bread_choices.length)];

        String[] cheese_choices = {"American", "Monterey Cheddar"};
        String cheese = cheese_choices[number.nextInt(cheese_choices.length)];

        String[] sauce_choices = {"Chipotle Southwest", "Light Mayonnaise" , "Regular Mayonnaise" , "Ranch", "Oil", "Subway vinaigrette"};
        String sauce = sauce_choices[number.nextInt(sauce_choices.length)];

        String[] vegetable_choices = {"Cucumbers", "Green Peppers", "Lettuce", "Red Onions", "Spinach", "Tomatoes"};
        String vegetable = vegetable_choices[number.nextInt(vegetable_choices.length)];

        String[] extra_choices = {"Pepperoni", "Bacon"};
        String extra = extra_choices[number.nextInt(extra_choices.length)];

        Sandwich s = new Sandwich(bread, cheese, meat, sauce, vegetable, extra, username);
        add_history(s);
        String json = "";
        try{
            Connection conn = getConnection();
            Statement stmt = connectDB(conn);

            if(stmt == null)
                return json += "[Error] No account with that name exists.\n}";
            try{
                String sqlStr = "select account.username from account where account.username = '" + username + "'";
                ResultSet rset = stmt.executeQuery(sqlStr);
                int count = 0;
                while(rset.next()) {
                    count++;
                }
                if (count != 0){
                    json += "\n \"Username\": " + username + "\n";
                    json += "   \"Sandwich\": {" + "\n";
                    json += " \"Bread\": " + bread + ",\n";
                    json += " \"Meat\": " + meat + ",\n";
                    json += " \"Cheese\": " + cheese + ",\n";
                    json += " \"Sauce\": " + sauce + ",\n";
                    json += " \"Veggie\": " + vegetable + ",\n";
                    json += " \"Extra\": " + extra + ",\n";
                    json += "},\n";

                    sqlStr = "insert into history values ('" + bread + "','" + meat + "','" + cheese + "','" + sauce + "','" + vegetable + "','" + extra + "', '" + username + "')";
                    stmt.executeUpdate(sqlStr);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } catch(Exception ex) {
            json += "\n Error : No account with that name exists. \n}";
            ex.printStackTrace();
        }
        return json;
    }


    @Override
    public String toString(){
        return "[com.mypkg.Account]: " + this.id + " has " + this.history.size() + " sandwich(es) in their history and " + this.favorites.size() + " favorite sandwich(es)";
    }
}
