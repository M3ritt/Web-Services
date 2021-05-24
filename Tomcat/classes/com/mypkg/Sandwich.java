package com.mypkg;

import java.util.ArrayList;

public class Sandwich{
	private String Bread, Cheese, Sauce, Meat, Vegetables, Extras;
	//private ArrayList<String> Vegetables;
	//private ArrayList<String> Extras;
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
	
	//public ArrayList<String> get_Vegetables(){
	public String get_Veggie(){
		return this.Vegetables;
	}
	
	//public ArrayList<String> get_Extras(){
	public String get_Extra(){
		return this.Extras;
	}

	
	@Override 
	public String toString(){
		String sandwich = this.Bread + " , " + this.Sauce + " , " + this.Cheese + " , " + this.Vegetables + " , " + this.Extras;
		//for(String s : this.Vegetables)
		//	sandwich += s + " , ";
		//for(String s : this.Extras)
		//	sandwich += s + " , ";
		System.out.println("[Sandwich]: \n [Bread]: " + this.Bread +"\n [Meat]: " + this.Meat +"\n [Cheese]: " + 
			this.Cheese + "\n [Sauce]: " + this.Sauce + "\n [Veggie]: " + this.Vegetables + "\n [Extra]: " + this.Extras);

		/*
		System.out.print(" [Vegetable(s)]: ");
		int pos = 1;
		for(String s : this.Vegetables){
			if(pos != this.Vegetables.size())
				System.out.print(s + " , ");
			else System.out.println(s);
			pos++;
		}
		
		System.out.print(" [Extra(s)]: ");
		pos = 1;
		for(String s : this.Extras){
			System.out.println(s);
			pos++;
		}
		*/
		
		return "";
	}
}


