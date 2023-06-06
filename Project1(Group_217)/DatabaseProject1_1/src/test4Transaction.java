import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class test4Transaction {

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

    public static void setPrepareStatement() {
        try {
            stmt = con.prepareStatement("INSERT INTO public.movies (movieid, title, country, year_released, runtime) " +
                    "VALUES (?,?,?,?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
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
            stmt = con.prepareStatement("INSERT INTO public.areas (post_id, posting_city, posting_country) " +
                    "VALUES (?,?,?)" + " ON CONFLICT (post_id) DO NOTHING;");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        for (Post p : posts) {
            String[] s = p.getPostingCity().split(", ");
            if (s[0].equals("Washington")) {
                s[0] = s[0] + ", " + s[1];
                s[1] = s[2];
            }
            if (con != null) {
                try {
                    stmt.setInt(1, p.getPostID());
                    stmt.setString(2, s[0]);
                    stmt.setString(3, s[1]);
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            cnt++;
            if (cnt % 1000 == 0) {
                System.out.println("insert " + 1000 + " data successfully!");
            }
        }

        try {
            con.commit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        closeDB();
        long end = System.currentTimeMillis();
        System.out.println(cnt + " records successfully loaded");
        System.out.println((end - start) * 1.0 / 1000.0 + "s");
        System.out.println("Loading speed : " + (cnt * 1000L) * 1.0 / (end - start) + " records/s");
    }
}

