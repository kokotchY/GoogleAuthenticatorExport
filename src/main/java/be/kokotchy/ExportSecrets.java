package be.kokotchy;

import java.sql.*;

/**
 * Export the url to the barcode of every secrets in the database
 */
public class ExportSecrets {
    public static void main(String[] args) {
        Connection c = null;
        String url = "https://www.google.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://totp/";
        String databaseName = args[0];
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+databaseName, "", "");
            c.setAutoCommit(false);

            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM accounts");
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String secret = rs.getString(3);
                System.out.println(url+email+"%3Fsecret%3D"+secret);
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
