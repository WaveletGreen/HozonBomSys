package sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class SQLHelper {
    Connection con = null;
    PreparedStatement pre = null;
    ResultSet result = null;
    private static Properties properties;
    private final static String filePath = "resource.properties";

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public SQLHelper() throws IOException {
        defaultLoader();
    }

    private boolean defaultLoader() throws IOException {
        properties = new Properties();
        InputStream in = null;
        try {
            in = this.getClass().getResourceAsStream(filePath);
            properties.load(in);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return false;
    }

    public void executeQuery(String sql, String[] values, String[] keywords) {
        try {
            String url = properties.getProperty("DB_ADDRESS");
            String user = properties.getProperty("DB_USERNAME");
            String password = properties.getProperty("DB_PASSWORD");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("prepare connected");
            // String sql = ;
            pre = con.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                pre.setString(i + 1, String.valueOf(values[i]));
            }
            System.out.println(pre.toString());
            result = pre.executeQuery();
            while (result.next()) {
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < keywords.length; i++) {
                    builder.append(keywords[i] + ":" + result.getString(keywords[i]) + "\t");
                }
                System.out.println(builder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                System.out.println("connection closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void executeInsert(String sql, String[] values) {
        try {
            System.out.println("prepare sql");
            String url = properties.getProperty("DB_ADDRESS");
            String user = properties.getProperty("DB_USERNAME");
            String password = properties.getProperty("DB_PASSWORD");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("prepare connection");
            pre = con.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                pre.setString(i + 1, String.valueOf(values[i]));
            }
            System.out.println(pre.toString());
            result = pre.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                System.out.println("connection closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param sql
     * @param values
     */
    public void revise(String sql, String[] values) {
        try {
            System.out.println("prepare sql");
            String url = properties.getProperty("DB_URL");
            String user = properties.getProperty("DB_USERNAME");
            String password = properties.getProperty("DB_PASSWORD");
            con = DriverManager.getConnection(url, user, password);
            pre = con.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                pre.setString(i + 1, String.valueOf(values[i]));
            }
            result = pre.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                System.out.println("connection closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//	public static void main(String[] args) throws IOException {
//		@SuppressWarnings("unused")
//		String sql = "select * from HZ_QUERY_SUPPLIERS";
//		String sql2 = "insert into HZ_QUERY_SUPPLIERS t (suppliers_id,t.suppliers_name) values(?,?)";
//		new SQLHelper().executeInsert(sql2, new String[] { "123", "��������Դ����" });
//
//	}
}
