package com.mypkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.ArrayList;


public class GAccount extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String temp = request.getRequestURI();
		String lastbit = temp.substring(temp.lastIndexOf('/') + 1);
		
		String json = "{\n \"Response\":" + response.getStatus() + "\n";
		try (
			Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mywebapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "myuser", "Jam3ritt");
			Statement stmt = conn.createStatement();
		){
			String sqlStr = "select * from account where username = '" + lastbit + "'";
			ResultSet rset = stmt.executeQuery(sqlStr); 
			json += " \"Account\": {";
			int count = 0;
			while(rset.next()) {
				json += "\n 	\"username\": " + rset.getString("username") + ",\n";
				json += " 	\"password (Should be a secret though)\": " + rset.getString("password") + ",\n";
				count++;
			}
			if(count == 0) {
				json += " [Error] No account with that name exists.";
			} else {
				
				sqlStr = "select history.bread, history.meat, history.cheese, history.sauce, history.veggies, history.extras from account, history where account.username = '" + lastbit + "' and history.username = '" + lastbit + "'";
				
				rset = stmt.executeQuery(sqlStr); 
				count = 0;
				while(rset.next()) {
					if(rset.getString("bread") != "")
						count ++;
				}
				json += " 	\"History Sandwich list size: \": " + count + ",\n";
		  
				sqlStr = "select favorites.bread, favorites.meat, favorites.cheese, favorites.sauce, favorites.veggies, favorites.extras from account, favorites where account.username = '" + lastbit + "' and favorites.username = '" + lastbit + "'";
				rset = stmt.executeQuery(sqlStr); 
				count = 0;
				while(rset.next()) {
					if(rset.getString("bread") != "")
						count ++;
				}
				json += " 	\"Favorite Sandwich list size: \": " + count + ",\n";
			}
		} catch(Exception ex) {
			json += "Error: " + ex +" }\n}";
			ex.printStackTrace();
		}
		json += " }\n}";
		response.getOutputStream().println(json);
		response.setContentType("application/json; charset=UTF-8");
	}
	
	@Override 
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.sendRedirect("GenerateSandwich");
	}
	
	@Override 
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.sendRedirect("RemoveFavorite");
	}
}