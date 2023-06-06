import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class test2Connect_2 {
    private static Connection con = null;
    private static Statement stmt = null;

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
//            if (con != null) {
//                System.out.println("Successfully connected to the database "
//                        + prop.getProperty("database") + " as " + prop.getProperty("user"));
//            }
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

        // Empty target table
        openDB(prop);
        closeDB();

        int cnt = 0;

        long start = System.currentTimeMillis();
        openDB(prop);
        for (Post p: posts) {
            if (true) {
                String sql = String.format("INSERT INTO public.post_author_relation (post_id, author_name, relation) " +
                                "VALUES (%d,'%s', '%s')" + " ON CONFLICT (post_id, author_name, relation) DO NOTHING;",
                        p.getPostID(), p.getAuthor(), "P");
                try {
                    if (con != null) {
                        stmt = con.createStatement();
                        stmt.execute(sql);
                        cnt++;
                        if (cnt % 1000 == 0) {
                            System.out.println("insert " + 1000 + " data successfully!");
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            for (String s: p.getAuthorWhoFavoritedThePost()) {
                String sql = String.format("INSERT INTO public.post_author_relation (post_id, author_name, relation) " +
                                "VALUES (%d,'%s', '%s')" + " ON CONFLICT (post_id, author_name, relation) DO NOTHING;",
                        p.getPostID(), s, "F");
                try {
                    if (con != null) {
                        stmt = con.createStatement();
                        stmt.execute(sql);
                        cnt++;
                        if (cnt % 1000 == 0) {
                            System.out.println("insert " + 1000 + " data successfully!");
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            for (String s: p.getAuthorWhoSharedThePost()) {
                String sql = String.format("INSERT INTO public.post_author_relation (post_id, author_name, relation) " +
                                "VALUES (%d,'%s', '%s')" + " ON CONFLICT (post_id, author_name, relation) DO NOTHING;",
                        p.getPostID(), s, "S");
                try {
                    if (con != null) {
                        stmt = con.createStatement();
                        stmt.execute(sql);
                        cnt++;
                        if (cnt % 1000 == 0) {
                            System.out.println("insert " + 1000 + " data successfully!");
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            for (String s: p.getAuthorWhoLikedThePost()) {
                String sql = String.format("INSERT INTO public.post_author_relation (post_id, author_name, relation) " +
                                "VALUES (%d,'%s', '%s')" + " ON CONFLICT (post_id, author_name, relation) DO NOTHING;",
                        p.getPostID(), s, "L");
                try {
                    if (con != null) {
                        stmt = con.createStatement();
                        stmt.execute(sql);
                        cnt++;
                        if (cnt % 1000 == 0) {
                            System.out.println("insert " + 1000 + " data successfully!");
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
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


