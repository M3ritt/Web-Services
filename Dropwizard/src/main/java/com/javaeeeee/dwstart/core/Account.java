package com.javaeeeee.dwstart.core;

import java.util.ArrayList;
import java.util.Random;

public class Account {
    private String id, password;

    private ArrayList<Sandwich> history;
    private ArrayList<Sandwich> favorites;

    /** This is here for viewing user data without having to create a dummy Account. */
    public Account(){}

    public Account(String id, String password){
        this.id = id;
        this.password = password;
        this.history = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public String getId(){
        return this.id;
    }

    public String getPassword(){
        return this.password;
    }

    public void addHistory(Sandwich s){
        this.history.add(s);
    }

    public ArrayList<Sandwich> getHistory(){
        return this.history;
    }

    public void addFavorite(Sandwich s){
        this.favorites.add(s);
    }

    public ArrayList<Sandwich> getFavorites(){
        return this.favorites;
    }

    public void removeFavorite(Sandwich s) { this.favorites.remove(s);}

    public Sandwich generateSandwich(String username){
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
        addHistory(s);
        return s;
    }

    @Override
    public String toString(){
        return "[Username] : " + this.id + " [Password] : " + this.password;
    }
}
