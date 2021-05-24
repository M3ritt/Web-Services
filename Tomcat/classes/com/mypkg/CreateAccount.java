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


public class CreateAccount extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String json = "{\n \"Response\": " + response.getStatus() + " \n";
		
		String temp = request.getRequestURI();
		String lastbit = temp.substring(temp.lastIndexOf('/') + 1);
		int index = lastbit.indexOf("_");
		if( index == -1){
			json += " [Error] Invalid username and/or password.\n";
		} else {
			String username = lastbit.substring(0, index);
			String password = lastbit.substring(lastbit.lastIndexOf('_') + 1);
			try (
				Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mywebapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
					"myuser", "Jam3ritt");
				Statement stmt = conn.createStatement();
			) {
				try{
					String sqlStr = "insert into account values ('" + username +  "', '" + password + "')";
					int rsetU = stmt.executeUpdate(sqlStr);
					json += "[Success] Account created.";
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
}