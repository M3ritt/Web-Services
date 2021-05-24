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


public class GenerateSandwich extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String temp = request.getRequestURI();
		String lastbit = temp.substring(temp.lastIndexOf('/') + 1);
		String json = "{\n \"Response\": " + response.getStatus() + "\n";
		try (
          Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mywebapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "myuser", "Jam3ritt");
          Statement stmt = conn.createStatement();
		) {
			try{
				String sqlStr = "select account.username from account where account.username = '" + lastbit + "'";
				ResultSet rset = stmt.executeQuery(sqlStr); 
				int count = 0;
				while(rset.next()) {
					count++;
				}
				if(count != 0){
					//Only needed to generate a sandwich
					Account will_remove = new Account("temp", "temp");
					Sandwich s = will_remove.generate_sandwich("Josh_Admin");
					
					json += "\"Sandwich\": {" + "\n";
					json += " \"Bread\": " + s.get_Bread() + ",\n";
					json += " \"Meat\": " + s.get_Meat() + ",\n";
					json += " \"Cheese\": " + s.get_Cheese() + ",\n";
					json += " \"Sauce\": " + s.get_Sauce() + ",\n";
					json += " \"Veggie\": " + s.get_Veggie() + ",\n";
					json += " \"Extra\": " + s.get_Extra() + ",\n";
					json += "],\n";	
				
					sqlStr = "insert into history values ('" + s.get_Bread() + "','" + s.get_Meat() + "','" + s.get_Cheese() + "','" + s.get_Sauce() + "','" + s.get_Veggie() + "','" + s.get_Extra() + "', '" + lastbit + "')";
					int rsetU = stmt.executeUpdate(sqlStr);
				} else {
					json += " [Error] No account with that name exists.";
				}
			} catch(Exception ex) {
				response.getOutputStream().println("<p>Error: " + ex.getMessage() + "<br> Check Tomcat console for details.</p>");
				ex.printStackTrace();
			}
	} catch(Exception ex) {
		json += " [Error]: No account with that name exists. \n}";
		ex.printStackTrace();
    }
	json += " }\n}";
	response.getOutputStream().println(json);
	response.setContentType("application/json; charset=UTF-8");
	}
}