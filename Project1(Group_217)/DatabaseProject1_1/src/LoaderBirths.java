import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class LoaderBirths {

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

    private static List<Post> loadJSONFile() {
        try {
            String jsonStrings = Files.readString(Path.of("resources//posts.json"));
            return JSON.parseArray(jsonStrings, Post.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static List<Replies> loadJSONFile2() {
        try {
            String jsonStrings = Files.readString(Path.of("resources//replies.json"));
            return JSON.parseArray(jsonStrings, Replies.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        Properties prop = loadDBUser();
        List<Post> posts = loadJSONFile();
        List<Replies> replies = loadJSONFile2();

        int cnt = 0;

        long start = System.currentTimeMillis();
        openDB(prop);

        try {
            stmt = con.prepareStatement("INSERT INTO public.births (author_name, birth_year, birth_month, birth_day) " +
                    "VALUES (?,?,?,?)" + " ON CONFLICT (author_name) DO NOTHING;");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        try {
            List<String> authors = Files.readAllLines(Path.of("resources//authors.txt"));
            for (String s : authors) {
                String[] strings = s.split(";");
                stmt.setString(1, strings[0]);
                stmt.setInt(2, Integer.parseInt(strings[1].substring(6, 10)));
                stmt.setInt(3, Integer.parseInt(strings[1].substring(10, 12)));
                stmt.setInt(4, Integer.parseInt(strings[1].substring(12, 14)));
                stmt.executeUpdate();
                cnt++;
                if (cnt % 1000 == 0) {
                    System.out.println("insert " + 1000 + " data successfully!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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

