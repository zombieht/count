package count;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Service {
	@SuppressWarnings("resource")
	public List<Model> findAll(String driver, String url, String user, String password, String table) throws Exception {
		DBHelper jdc = new DBHelper(driver, url, user, password);
		Connection conn = jdc.getConnection();
		String sql = null;
		PreparedStatement pstmt = null;
		List<Model> list = new ArrayList<Model>();
		String driverName = conn.getMetaData().getDriverName();

		try {
			if (driverName.equals("PostgreSQL JDBC Driver")) {
				sql = "CREATE OR REPLACE VIEW cpm_view AS SELECT  *  FROM (select A.relname,B.reltuples from pg_stat_user_tables A,pg_class B where A.relname=B.relname order by A.relname) a";
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();

				sql = "SELECT relname,reltuples FROM cpm_view";
				pstmt = conn.prepareStatement(sql);
				if (table != null) {
					sql = "SELECT relname,reltuples FROM cpm_view where relname = ?";
					pstmt = jdc.getConnection().prepareStatement(sql);
					pstmt.setString(1, table);
				}
			} else if (driverName.equals("MySQL-AB JDBC Driver")) {
				String url2 = conn.getMetaData().getURL();
				String database = url2.substring(url2.lastIndexOf("/") + 1);
				sql = "select table_name,table_rows from information_schema.tables where table_schema= ? and table_type='base table'order by table_rows desc;";
				pstmt = jdc.getConnection().prepareStatement(sql);
				pstmt.setString(1, database);
				if (table != null) {
					sql = "SELECT t.TABLE_NAME as NAME ,t.TABLE_ROWS as rows FROM information_schema.TABLES AS t WHERE t.TABLE_SCHEMA = ? and t.TABLE_NAME = ?";
					pstmt = jdc.getConnection().prepareStatement(sql);
					pstmt.setString(1, database);
					pstmt.setString(2, table);
				}
			}

			ResultSet rs = pstmt.executeQuery();
			Integer num1 = 0;
			Integer num2 = 0;
			System.out.print("表名                 ");
			System.out.println("表行数");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "   ");
				System.out.println(rs.getInt(2));
				num1++;
				num2 = num2 + rs.getInt(2);
			}
			System.out.println("合计：" + num1 + "      " + num2);
			rs.close();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (Exception e) {
			}
		}
		return list;
	}
}
