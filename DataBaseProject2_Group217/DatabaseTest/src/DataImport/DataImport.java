package DataImport;

import View.Menu;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataImport {
    private static Connection con = null;
    private static PreparedStatement stmt = null;

    public DataImport() {

    }


    private static void openDB(Properties prop) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
        try {
            con = DriverManager.getConnection(url, prop);
            if (con != null) {
                //System.out.println("Successfully connected to the database "
                        //+ prop.getProperty("database") + " as " + prop.getProperty("user"));
                con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception ignored) {
            }
        }
    }

    public static Properties loadDBUser() {
        Properties properties = new Properties();
        try {
            if (Menu.visit) {
                properties.load(new InputStreamReader(new FileInputStream("resources/dbUser2.properties")));
            } else {
                properties.load(new InputStreamReader(new FileInputStream("resources/dbUser.properties")));
            }
            return properties;
        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }

    public void notQuerySentence(String sql) {
        Properties prop = loadDBUser();
        openDB(prop);
        try {
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            closeDB();
            System.exit(1);
        }
        try {
            con.commit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        closeDB();
        System.out.println("Successfully " + sql);
    }

    public List<List<Object>> QuerySentence(String sql) {
        List<List<Object>> list = new ArrayList<>();

        Properties prop = loadDBUser();
        openDB(prop);
        try {
            stmt = con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY  );
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            rs.last();
            int row = rs.getRow();
            int column = metaData.getColumnCount();
            rs.beforeFirst();
            //System.out.println(row);
            //System.out.println(column);
            for (int i = 0; i < row; i++) {
                list.add(new ArrayList<Object>());
            }
            int i = 0;
            while (rs.next()) {
                for (int j = 1; j <= column; j++) {
                    list.get(i).add(rs.getObject(j));
                }
                i++;
            }
            //System.out.println(list.get(0).get(0));
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            closeDB();
            System.exit(1);
        }
        try {
            con.commit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        closeDB();
        System.out.println("Successfully " + sql);
        return list;
    }

    public byte[] imageQuerySentence(String sql) {
        Properties prop = loadDBUser();
        byte[] image = null;
        openDB(prop);
        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                image = rs.getBytes("image");
            }
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            closeDB();
            System.exit(1);
        }
        try {
            con.commit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        closeDB();
        System.out.println("Successfully " + sql);
        return image;
    }
}
