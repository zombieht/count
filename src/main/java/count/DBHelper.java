package count;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
	private Connection conn = null;

	public DBHelper(String driver, String url, String user, String password) {

		switch (driver) {
		case "mysql":
			driver = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://" + url;
			break;
		case "pgsql":
			driver = "org.postgresql.Driver";
			url = "jdbc:postgresql://" + url;
			break;
		default:
			System.out.println("不支持此数据库");
			break;
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.conn;
	}

	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
}
