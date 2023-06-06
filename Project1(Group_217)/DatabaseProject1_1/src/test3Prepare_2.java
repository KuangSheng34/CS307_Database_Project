import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class test3Prepare_2 {
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
            stmt = con.prepareStatement("INSERT INTO public.post_author_relation (post_id, author_name, relation) " +
                    "VALUES (?,?,?)" + " ON CONFLICT (post_id, author_name, relation) DO NOTHING;");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        for (Post p : posts) {
            if (con != null) {
                try {
                    stmt.setInt(1, p.getPostID());
                    stmt.setString(2, p.getAuthor());
                    stmt.setString(3, "P");
                    stmt.executeUpdate();
                    cnt++;
                    if (cnt % 1000 == 0) {
                        System.out.println("insert " + 1000 + " data successfully!");
                    }

                    for (String s: p.getAuthorWhoFavoritedThePost()) {
                        stmt.setInt(1, p.getPostID());
                        stmt.setString(2, s);
                        stmt.setString(3, "F");
                        stmt.execute();
                        cnt++;
                        if (cnt % 1000 == 0) {
                            System.out.println("insert " + 1000 + " data successfully!");
                        }
                    }

                    for (String s: p.getAuthorWhoSharedThePost()) {
                        stmt.setInt(1, p.getPostID());
                        stmt.setString(2, s);
                        stmt.setString(3, "S");
                        stmt.execute();
                        cnt++;
                        if (cnt % 1000 == 0) {
                            System.out.println("insert " + 1000 + " data successfully!");
                        }
                    }

                    for (String s: p.getAuthorWhoLikedThePost()) {
                        stmt.setInt(1, p.getPostID());
                        stmt.setString(2, s);
                        stmt.setString(3, "L");
                        stmt.execute();
                        cnt++;
                        if (cnt % 1000 == 0) {
                            System.out.println("insert " + 1000 + " data successfully!");
                        }
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }

        closeDB();
        long end = System.currentTimeMillis();
        System.out.println(cnt + " records successfully loaded");
        System.out.println((end - start) * 1.0 / 1000.0 + "s");
        System.out.println("Loading speed : " + (cnt * 1000L) * 1.0 / (end - start) + " records/s");
    }
}

