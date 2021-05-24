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

@WebServlet("/RemoveFavorite")
public class RemoveFavorite extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doDelete(request, response);
	}

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String json = "{\n \"Response\": " + response.getStatus() + " \n";
		
		String temp = request.getRequestURI();
		String lastbit = temp.substring(temp.lastIndexOf('/') + 1);
		int count = 0;
		int index = lastbit.indexOf("_");
		String bread, meat, cheese, sauce, veggie, extra, username;
		
		bread = helper(lastbit, count);
		lastbit = remove_old_data(lastbit);
		count++;
		
		meat = helper(lastbit, count);
		lastbit = remove_old_data(lastbit);
		count++;
		
		cheese = helper(lastbit, count);
		lastbit = remove_old_data(lastbit);
		count++;
		
		sauce = helper(lastbit, count);
		lastbit = remove_old_data(lastbit);
		count++;
		
		veggie = helper(lastbit, count);
		lastbit = remove_old_data(lastbit);
		count++;
		
		extra = helper(lastbit, count);
		lastbit = remove_old_data(lastbit);
		count++;
		
		username = helper(lastbit, count);
		//json += "username: " + username + " lastbit: " + lastbit + "\n";
		if(username.equalsIgnoreCase("empty")){
			username = lastbit;
			extra = "";
			count--;
		}
		
		if(count == 0){
			json += " [Error] Invalid form of contact.\n";
		} else {
			try (
				Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mywebapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
					"myuser", "Jam3ritt");
				Statement stmt = conn.createStatement();
			) {
				try{
					String sqlStr = "select * from account where username = '" + lastbit + "'";
					ResultSet rset = stmt.executeQuery(sqlStr); 
					count = 0;
					while(rset.next()) {
						count++;
					}
					if(count == 0) {
						json += " [Error] No account with that name exists.";
					} else {
						sqlStr = "delete from favorites where favorites.bread = '" + bread + "' and favorites.meat = '" + meat +
							"' and favorites.cheese = '" + cheese + "' and favorites.sauce = '" + sauce + "' and favorites.veggies = '"+ veggie +
							"' and favorites.extras = '"+ extra + "' and favorites.username = '" + username + "'";
						int rsetU = stmt.executeUpdate(sqlStr);
						json += "SQL String: " +sqlStr + "\n";
						json += "[Favorite was removed from " + lastbit + "'s account.] \n";
					}
				} catch(Exception ex) {
					response.getOutputStream().println("<p>Error: " + ex.getMessage() + "<br> Check Tomcat console for details.</p>");
					ex.printStackTrace();
				}
			} catch(Exception ex) {
				json += " [Error]: Invalid username and/or password.\n";
				ex.printStackTrace();
			}
		}
		json += " }\n}";
		response.getOutputStream().println(json);
		response.setContentType("application/json; charset=UTF-8");
	}
	
	
		public String helper(String lastbit, int count){
		int index = lastbit.indexOf("_");
		if (count == 6)
			return lastbit;
		else if(index != -1)
			return lastbit.substring(0, index);
		else 
			return "empty";
	}
	
	
	public String remove_old_data(String lastbit){
		int index = lastbit.indexOf("_");
		if( index != -1)
			return lastbit.substring(index+1, lastbit.length());
		else return "empty";
	}
}