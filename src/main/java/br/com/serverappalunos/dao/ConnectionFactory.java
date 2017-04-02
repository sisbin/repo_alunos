/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.serverappalunos.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Paulo
 */
public class ConnectionFactory {
	
	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:postgresql://ec2-23-21-204-166.compute-1.amazonaws.com:5432/d3cnghovklm6rv?user=gepvbwzyiesuuc&password=7dd20ad60c474337c5bcc8e33b1024af7b9781a68d16be49aea0a17b6871e950");
			return con;
		} catch (Exception ex) {
			System.out.println("Database.getConnection() Error -->"+ ex.getMessage());
			return null;
		}
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}
	
}
    
