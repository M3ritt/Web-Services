package com.mypkg;

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
	
	public String get_id(){
		return this.id;
	}
	
	public void add_history(Sandwich s){
		this.history.add(s);
	}
	
	
	public ArrayList<Sandwich> get_history(){
		return this.history;
	}
	
	
	public void see_history(){
		for(Sandwich s : this.history)
			System.out.println(s);
	}
	
	public void see_favorites(){
		for(Sandwich s : this.favorites)
			System.out.println(s);
	}
	
	public void add_favorite(Sandwich s){
		this.favorites.add(s);
	}
	
	public void remove_favorite(Sandwich s){
		this.favorites.remove(s);
	}
	
	
	public ArrayList<Sandwich> get_favorites(){
		return this.favorites;
	}
	
	public Sandwich generate_sandwich(String username){
		Random number = new Random();
		String[] bread_choices = {"9-Grain Wheat", "Multi-grain Flatbread" , "Italian ", "Italian Herbs & Cheese", "Flatbread"}; 
		int bread_pos = number.nextInt(bread_choices.length);
		
		String[] meat_choices = {"Turkey breast", "Ham" , "Chicken breast", "Roast Beef", "Tuna", "Turkey salami", "beefsteak", "bacon", "meatballs", "Genoa Salami", "Turkey bologna", "Shaved Steak"}; 
		int meat_pos = number.nextInt(bread_choices.length);
		
		String[] cheese_choices = {"American", "Monterey Cheddar"}; 
		int cheese_pos = number.nextInt(cheese_choices.length);
		
		String[] sauce_choices = {"Chipotle Southwest", "Light Mayonnaise" , "Regular Mayonnaise" , "Ranch", "Oil", "Subway vinaigrette"}; 
		int sauce_pos = number.nextInt(sauce_choices.length);
		
		String[] vegetable_choices = {"Cucumbers", "Green Peppers", "Lettuce", "Red Onions", "Spinach", "Tomatoes"}; 
		int veggie_pos = number.nextInt(vegetable_choices.length);
		//ArrayList<String> Vegetables = multiple_choice_topping_generator(vegetable_choices);
		
		String[] extra_choices = {"Pepperoni", "Bacon"}; 
		int extra_pos = number.nextInt(extra_choices.length);
		//ArrayList<String> Extras = multiple_choice_topping_generator(extra_choices);
		
		Sandwich s = new Sandwich(bread_choices[bread_pos], cheese_choices[cheese_pos], meat_choices[meat_pos], sauce_choices[sauce_pos], vegetable_choices[veggie_pos], extra_choices[extra_pos], username);
		add_history(s);
		return s;
	}
	
	
	public ArrayList<String> multiple_choice_topping_generator(String[] choices){
		ArrayList<String> toppings = new ArrayList<String>();
		Random number = new Random();
		int num_of_choices = number.nextInt(choices.length);
		for(int i = 0; i < num_of_choices; i++){
			int num = number.nextInt(choices.length);
			if(!toppings.contains(choices[num]))
				toppings.add(choices[num]);
			else i--;
		}
		return toppings;
	}

	
	@Override 
	public String toString(){
		return "[Account]: " + this.id + " has " + this.history.size() + " sandwich(es) in their history and " + this.favorites.size() + " favorite sandwich(es)"; 
	}
}