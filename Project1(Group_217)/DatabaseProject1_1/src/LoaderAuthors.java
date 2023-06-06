import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class LoaderAuthors {

    private static Connection con = null;
    private static PreparedStatement stmt = null;

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
                System.out.println("Successfully connected to the database "
                        + prop.getProperty("database") + " as " + prop.getProperty("user"));
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

    private static Properties loadDBUser() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("resources/dbUser.properties")));
            return properties;
        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }


    private static void loaderAuthors() {
        try {
            stmt = con.prepareStatement("INSERT INTO public.Authors (Author_name, Author_ID, Author_Registration_Time, Author_Phone) " +
                    "VALUES (?,?,?,?)" + " ON CONFLICT (Author_name) DO NOTHING;");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Properties prop = loadDBUser();

        int cnt = 0;

        long start = System.currentTimeMillis();
        openDB(prop);

        loaderAuthors();
        try {
            List<String> authors = Files.readAllLines(Path.of("resources//authors.txt"));
            for (String s : authors) {
                String[] strings = s.split(";");
                stmt.setString(1, strings[0]);
                stmt.setString(2, strings[1]);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(strings[2]);
                stmt.setTimestamp(3, new Timestamp(date.getTime()));
                stmt.setString(4, strings[3]);
                stmt.executeUpdate();
                cnt++;
                if (cnt % 1000 == 0) {
                    System.out.println("insert " + 1000 + " data successfully!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            con.commit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        closeDB();
        long end = System.currentTimeMillis();
        System.out.println(cnt + " records successfully loaded");
        System.out.println("Loading speed : " + (cnt * 1000L) / (end - start) + " records/s");
    }
}

