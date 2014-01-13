package be.kokotchy;

import be.kokotchy.util.QRCodeUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

/**
 * Export the url to the barcode of every secrets in the database
 */
public class ExportSecrets {
	public static void main(String[] args) {
		Connection c = null;

		// qrcode-terminal is available here: https://github.com/gtanner/qrcode-terminal.git
		String app = "/usr/bin/node qrcode-terminal/bin/qrcode-terminal.js";
		String databaseName = args[0];
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + databaseName, "", "");
			c.setAutoCommit(false);

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("SELECT * FROM accounts");
			while (rs.next()) {
				int id = rs.getInt(1);
				String email = rs.getString(2);
				String secret = rs.getString(3);

				BufferedReader reader = QRCodeUtil.displayQRCode(app, email, secret);
				reader.close();
				System.out.println(QRCodeUtil.generateUrl(email, secret));
			}
			rs.close();
			st.close();

			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
			try {
				if (c != null && !c.isClosed()) {
					c.rollback();
					c.close();
				}
			} catch (SQLException sql) {

			}
		}
	}
}
