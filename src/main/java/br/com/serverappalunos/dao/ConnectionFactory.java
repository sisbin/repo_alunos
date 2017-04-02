/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.serverappalunos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 *
 * @author Paulo
 */
public class ConnectionFactory {
	
	static DataSource datasource;
    final static Logger logger = Logger.getLogger(ConnectionFactory.class);

    /**Retorna uma connection JNDI
     * @throws SQLException */
	public static Connection getConnection() throws SQLException{
		
		String DATASOURCE_CONTEXT = "java:comp/env/jdbc/serverappalunos";
		Connection conn = null;
		try {
			Context initialContext = new InitialContext();
			
			datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
			if (datasource != null) {
				conn = datasource.getConnection();
			} else {
				logger.error("lookup do datasource FALHOW");
			}

		} catch (NamingException ex) {
			logger.error("Nao consigo pegar uma conexao: " + ex);
		}

		return conn;
	}	
	
	/**
	 * Fecha a conexao passada via parametro
	 * @param conn - A Connection a ser fechada.
	 * @throws SQLException 
	 */
	public static void closeConnection(Connection conn){ 		
		try{				
			conn.close();
		}catch (Exception e){			
			logger.error(e.getMessage());
		}		
	}
}
    
