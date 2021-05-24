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

public class ViewFavorites extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String temp = request.getRequestURI();
		String lastbit = temp.substring(temp.lastIndexOf('/') + 1);
		
		String json = "{\n \"Response\": " + response.getStatus() + " \n";
		try (
			Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mywebapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "myuser", "Jam3ritt");
			Statement stmt = conn.createStatement();
		) {
			String sqlStr = "select favorites.bread, favorites.meat, favorites.cheese, favorites.sauce, favorites.veggies, favorites.extras from favorites where favorites.username = '" + lastbit + "'";
			ResultSet rset = stmt.executeQuery(sqlStr); 
 
			int count = 0;
			json += " \"Favorites\" :";
			while(rset.next()) {
				json += "\n	\"Sandwich\": {" + "\n";
				json += " 		\"Bread\": " + rset.getString("bread") + ",\n";
				json += " 		\"Meat\": " + rset.getString("meat") + ",\n";
				json += " 		\"Cheese\": " + rset.getString("cheese") + ",\n";
				json += " 		\"Sauce\": " + rset.getString("sauce") + ",\n";
				json += " 		\"Veggie\": " + rset.getString("veggies") + ",\n";
				json += " 		\"Extras\": " + rset.getString("extras") + ",\n";
				json += "	}, \n";
				count++;
			}
			if(count == 0) {
				json += " [No sandwiches found].\n";
			}
		} catch(Exception ex) {
			json += " [Error] No account with that name exists.\n}";
			ex.printStackTrace();
		}
		json += "}";
		response.getOutputStream().println(json);
		response.setContentType("application/json; charset=UTF-8");
	}
}