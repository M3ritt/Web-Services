package com.example.mypkg;

public class Sandwich{
    private String Bread, Cheese, Sauce, Meat, Vegetables, Extras;
    private String username;

    public Sandwich(String Bread, String Cheese, String Meat, String Sauce, String Vegetables, String Extras, String username){
        this.Bread = Bread;
        this.Cheese = Cheese;
        this.Sauce = Sauce;
        this.Meat = Meat;
        this.Vegetables = Vegetables;
        this.Extras = Extras;
        this.username = username;
    }

    public String get_Username(){
        return this.username;
    }

    public String get_Bread(){
        return this.Bread;
    }

    public String get_Sauce(){
        return this.Sauce;
    }

    public String get_Cheese(){
        return this.Cheese;
    }

    public String get_Meat(){
        return this.Meat;
    }

    public String get_Veggie(){
        return this.Vegetables;
    }

    public String get_Extra(){
        return this.Extras;
    }

    @Override
    public String toString(){
        String sandwich = this.Bread + " , " + this.Sauce + " , " + this.Cheese + " , " + this.Vegetables + " , " + this.Extras;
        System.out.println("[Sandwich]: \n [Bread]: " + this.Bread +"\n [Meat]: " + this.Meat +"\n [Cheese]: " +
                this.Cheese + "\n [Sauce]: " + this.Sauce + "\n [Veggie]: " + this.Vegetables + "\n [Extra]: " + this.Extras);
        return "";
    }
}
