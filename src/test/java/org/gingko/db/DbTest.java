package org.gingko.db;

import org.junit.Test;

import java.sql.*;

/**
 * @author Kyia
 */
public class DbTest {

	public Connection getConn() {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
			try {
				conn = DriverManager.getConnection(url, "gingko", "gingko");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

	@Test
	public void test() {
		Connection conn = getConn();
		String sql = "select * from gk_match_rule";
		Statement stmt;
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
