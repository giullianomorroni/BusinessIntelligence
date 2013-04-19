package br.com.flagme.bi.fabrica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FabricaConexao {

	private static String url = "jdbc:mysql://localhost:3306/";
	private static String dbName = "BUSINESS_INTELLIGENCE";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "bi";
	private static String password = "bi_bi_35_bi_bi!";
	private static Connection conexao = null;

	public static void main(String[] args) {
		conectar();
	}

	public static Connection conectar() {
		try {
			if (conexao == null)
				criarConexao();
			if (conexao.isClosed())
				criarConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conexao;
	}

	public static DataSource jbossDataSource() {
		DataSource ds = null;
		String jndi = "java:jboss/datasources/MySqlDS";
		try {
			Context contexto = new InitialContext();
			ds = (DataSource) contexto.lookup(jndi);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ds;
	}

	private static void criarConexao() {
		try {
			Class.forName(driver).newInstance();
			conexao = DriverManager.getConnection(url + dbName, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fechar(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
