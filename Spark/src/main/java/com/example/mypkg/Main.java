package com.example.mypkg;

import static spark.Spark.*;

/**
 * Notes to myself for clarity.
 *
 * MVC component:
 *  - Controller: This class since it is how the user interacts with the application.
 *  - Model: Accounts and Account since those are what update the database and decides what the user views.
 *  - View: This class since it is what returns the JSON which is what the user sees.
 *
 * Need to work on:
 *  - Need to add endpoint for deleting users.
 *  - Need to check if user is already in database when creating new account.
 *  - Need to change sandwiches in an account to having an ID so endpoint isn't insanely long when adding/removing favorite.
 *  - Add route paths so not garbled mess.
 *
 * Current endpoints:
 *  - POST /CreateAccount/*
 *  - GET /Accounts
 *  - GET /Account/*
 *  - POST /Account/GenerateSandwich/*
 *  - GET /Account/ViewHistory/*
 *  - POST /Account/AddFavorite/*
 *  - GET /Account/ViewFavorites/*
 *  - DELETE /Account/RemoveFavorite/*
 */
public class Main {

    public static void main(String[] args) {
        // Correct case for /Account/
        get("/Account/:username", (req, res) -> {
            res.type("application/json");
            String username = req.params(":username");
            StringBuilder json = new StringBuilder("{");
            Accounts accounts = new Accounts();
            json.append(accounts.findAccount(username));
            json.append(" \n}");
            return json;
        });
        // Incorrect cases for /Account/
        get("/Account/", (req, res) -> {
            res.type("application/json");
            return "{ \n Error : No account was listed. Should be of the form: GET /Account/username }";
        });
        redirect.post("/Account/", "/Account/");
        redirect.post("/Account/:username", "/Account/");
        redirect.delete("/Account/", "/Account/");
        redirect.delete("/Account/:username", "/Account/");

        // Correct case for /CreateAccount/
        post("/CreateAccount/*", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            String json = "{";
            if(username.equals("") || password.equals("")) {
                json += " [Error] Invalid username and/or password.\n";
            } else {
                Accounts accounts = new Accounts();
                json += accounts.add_account(username, password);
            }
            json += " }\n";
            res.type("application/json");

            return json;
        });
        // Incorrect cases for /CreateAccount/
        get("/CreateAccount/", (req, res) -> {
            res.type("application/json");
            return "{ \n Error : Username and password were not given. Should be of the form: POST /CreateAccount/?username=username&password=password }";
        });
        // Post is causing 500 error
        redirect.post("/CreateAccount/", "/CreateAccount/");
        redirect.get("/CreateAccount/:username", "/CreateAccount/");
        redirect.delete("/CreateAccount/", "/CreateAccount/");
        redirect.delete("/CreateAccount/:username", "/CreateAccount/");

        // Correct case for /GenerateSandwich/
        post("/Account/GenerateSandwich/:username", (req, res) -> {
            res.type("application/json");
            String username = req.params(":username");

            String json = "{";
            Account will_remove = new Account("temp", "temp");
            json += will_remove.generate_sandwich(username);
            json += " }\n";
            res.type("application/json");

            return json;
        });
        // Incorrect cases for /GenerateSandwich/
        get("/Account/GenerateSandwich/", (req, res) -> {
            res.type("application/json");
            return "{ \n Error : No username was given. Should be of form: POST /Account/GenerateSandwich/username}";
        });
        redirect.post("/Account/GenerateSandwich/", "/Account/GenerateSandwich/");
        redirect.get("/Account/GenerateSandwich/:username", "/Account/GenerateSandwich/");
        redirect.delete("/Account/GenerateSandwich/", "/Account/GenerateSandwich/");
        redirect.delete("/Account/GenerateSandwich/:username", "/Account/GenerateSandwich/");

        // Correct case for /ViewHistory/
        get("/Account/ViewHistory/:username", (req, res) -> {
            res.type("application/json");
            String username = req.params(":username");

            Account temp = new Account("temp", "temp");
            return "{" + temp.see_history(username) + "}";
        });
        // Incorrect cases for /ViewHistory/
        get("/Account/ViewHistory/", (req, res) -> {
            res.type("application/json");
            return "{ \n Error : No account was listed. Should be of the form: GET /Account/ViewHistory/username }";
        });
        redirect.post("/Account/ViewHistory/", "/Account/ViewHistory/");
        redirect.post("/Account/ViewHistory/:username", "/Account/ViewHistory/");
        redirect.delete("/Account/ViewHistory/", "/Account/ViewHistory/");
        redirect.delete("/Account/ViewHistory/:username", "/Account/ViewHistory/");

        //Super long and should just be id of sandwich and check if the id is in the user's history
        //http://localhost:4567/Account/AddFavorite/?username=test&bread=Multi-grain Flatbread&meat=Tuna&cheese=Monterey Cheddar&sauce=LightMayonnaise&veggie=Lettuce&extra=Pepperoni
        // Correct case of /Account/AddFavorite/
        post("/Account/AddFavorite/*", (req, res) -> {
            res.type("application/json");

            String username = req.queryParams("username");
            //Should've set id to each sandwich when added to database but started later than I should have so just continuing implementation from last assignment
            String bread = req.queryParams("bread");
            String meat = req.queryParams("meat");
            String cheese = req.queryParams("cheese");
            String sauce = req.queryParams("sauce");
            String veggie = req.queryParams("veggie");
            String extra = req.queryParams("extra");

            String json = "{";
            Account temp = new Account("temp", "temp");
            json += temp.add_favorite(bread, meat, cheese, sauce, veggie, extra, username);
            json += " }\n";
            return json;
        });
        // Incorrect cases of /Account/AddFavorite/
        get("/Account/AddFavorite/", (req, res) -> {
            res.type("application/json");
            return "{ \n Error : No username was given. Should be of form: POST /Account/AddFavorite/?username=username&bread=bread&meat=meat&cheese=cheese&sauce=sauce&extra=extra}";
        });
        redirect.post("/Account/AddFavorite/", "/Account/AddFavorite/");
        redirect.get("/Account/AddFavorite/:username", "/Account/AddFavorite/");
        redirect.delete("/Account/AddFavorite/", "/Account/AddFavorite/");
        redirect.delete("/Account/AddFavorite/:username", "/Account/AddFavorite/");


        //Super long and should just be id of sandwich and check if the id is in the user's history
        //http://localhost:4567/Account/RemoveFavorite/?username=test&bread=Multi-grain Flatbread&meat=Tuna&cheese=Monterey Cheddar&sauce=LightMayonnaise&veggie=Lettuce&extra=Pepperoni
        post("/Account/RemoveFavorite/*", (req, res) -> {
            res.type("application/json");

            String username = req.queryParams("username");
            //Should've set id to each sandwich when added to database but started later than I should have so just continuing implementation from last assignment
            String bread = req.queryParams("bread");
            String meat = req.queryParams("meat");
            String cheese = req.queryParams("cheese");
            String sauce = req.queryParams("sauce");
            String veggie = req.queryParams("veggie");
            String extra = req.queryParams("extra");

            String json = "{";
            Account temp = new Account("temp", "temp");
            json += temp.remove_favorite(bread, meat, cheese, sauce, veggie, extra, username);
            json += " }\n";
            return json;
        });
        get("/Account/RemoveFavorite/", (req, res) -> {
            res.type("application/json");
            return "{ \n Error : No username was given. Should be of form: POST /Account/RemoveFavorite/?username=username&bread=bread&meat=meat&cheese=cheese&sauce=sauce&extra=extra}";
        });
        redirect.post("/Account/RemoveFavorite/", "/Account/RemoveFavorite/");
        redirect.get("/Account/RemoveFavorite/:username", "/Account/RemoveFavorite/");
        redirect.delete("/Account/RemoveFavorite/", "/Account/RemoveFavorite/");
        redirect.delete("/Account/RemoveFavorite/:username", "/Account/RemoveFavorite/");

        // Correct case for /ViewFavorites/
        get("/Account/ViewFavorites/:username", (req, res) -> {
            res.type("application/json");
            String username = req.params(":username");
            Account temp = new Account("temp", "temp");
            return "{" + temp.see_favorites(username) + "}";
        });
        // Incorrect cases for /ViewFavorites/
        get("/Account/ViewFavorites/", (req, res) -> {
            res.type("application/json");
            return "{ \n Error : No account was listed. Should be of the form: GET /Account/ViewFavorites/username }";
        });
        redirect.post("/Account/ViewFavorites/", "/Account/ViewFavorites//");
        redirect.post("/Account/ViewFavorites/:username", "/Account/ViewFavorites/");
        redirect.delete("/Account/ViewFavorites/", "/Account/ViewFavorites/");
        redirect.delete("/Account/ViewFavorites/:username", "/Account/ViewFavorites/");

        // Correct case for /Accounts
        get("/Accounts", (req, res) -> {
            res.type("application/json");
            Accounts temp = new Accounts();
            return "{" + temp.seeAccounts() + "}";
        });
        // Incorrect cases for /Accounts
        post("/Accounts", (req, res) -> {
            res.type("application/json");
            return "{ \n Error : No accounts are listed. Should be of the form: GET /Accounts }";
        });
        delete("/Accounts", (req, res) -> {
            res.type("application/json");
            return "{ \n Error : No accounts are listed. Should be of the form: GET /Accounts }";
        });
    }
}
