package clientesnmp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Driver;

public class Mysql {
	 
	  private Connection con = null;
	  private String hostName = null;
	  private String userName = null;
	  private String password = null;
	  private String url = null;
	  private String jdbcDriver = null;
	  private String dataBaseName = null;
	  private String dataBasePrefix = null;
	  private String dabaBasePort = null;
	  
	  public String MysqlConnection(String SNMPsql) {
		 
		  /*
		  Os dados setados abaixo servem para uma conexão em MySQL.
		  Altere de acordo com seu BD.
		  */
		  hostName = "localhost";
		  userName = "root";
		  password = "Sanmerson@1";    
		  jdbcDriver = "java.sql.Driver";
		  dataBaseName = "smads";
		  dataBasePrefix = "jdbc:mysql://";
		  dabaBasePort = "3306";
		 
		  url = dataBasePrefix + hostName + ":"+dabaBasePort+"/" + dataBaseName;
		  	
		    /*
		    Exemplo de um URL completo para MySQL:    
		    a concatenação acima deve ficar algo como:
		    jdbc:'mysql:/localhost:3306/meu_bd'
		    */
		  getConnection();
		  Inserir(SNMPsql);
		  closeConnection();
		 return "sucess";
		}
	  
	  public Connection getConnection() { // Abre a conexão com o servidor
		  try {
		    if (con == null) {
		      Class.forName(jdbcDriver);
		      con = DriverManager.getConnection(url, userName, password);
		    } else if (con.isClosed()) {
		      con = null;
		      return getConnection();
		    }
		  } catch (ClassNotFoundException e) {
		 
		    e.printStackTrace();
		  } catch (SQLException e) {
		 
		    e.printStackTrace();
		  }
		  return con;
		}
	  
	     public void Inserir(String SNMP_sql){
	    	 	//Comando de inserção de dados no BD
	            String sql = "insert into datacenters(ID_DataCenter, ID_Usuario, Nome_DataCenter, Temperatura_Ambiente, Umidade_Ambiente, Fumaca, DT_Verificacao, HR_Verificacao)"+"values ("+SNMP_sql+")";    

	            try {    
	                PreparedStatement stmt = con.prepareStatement(sql);    
	                stmt.execute(); //executa comando   
	                stmt.close();    

	            } catch (SQLException u) {    
	                throw new RuntimeException(u);    
	            }    
	        
	     }
	  
	  
	  
	  public void closeConnection() { // Encerra a conexão com o banco de dados
		  if (con != null) {
		    try {
		      con.close();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }
	  }
}
