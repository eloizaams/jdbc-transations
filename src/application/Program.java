package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = DB.getConnection();
			
			conn.setAutoCommit(false); //As operações ficam pendentes de uma confirmação explícita do programador
			
			st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			//int x = 1;
			//if (x < 2) {
			//	throw new SQLException("Fake error");
			//}
			
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			conn.commit(); //confirma que a operação terminou
			
			System.out.println("rows1 "+ rows1);
			System.out.println("rows2 "+ rows2);
			
					
		} 
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Transation rolled back! Caused by: " + e.getMessage());
			}
			catch (SQLException e1) {
				throw new DbException("Error tryin to rollback! Caused by: "+ e.getMessage());
			}
			
		}
		finally {
			DB.closeStatment(st);
			DB.closeConnection();
		}
	}

}
