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
					"jdbc:postgresql://ec2-176-34-186-178.eu-west-1.compute.amazonaws.com:5432/dvmd3ng1ph7ic",
                                "mvlztzhdumrrcu", "103fb9f7b6905a7bbb7e5a4ffdf4b6fdeca2bfc7827c44b3b5547df3f0136aa7");
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
    
