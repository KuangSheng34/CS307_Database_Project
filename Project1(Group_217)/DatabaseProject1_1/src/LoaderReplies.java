import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LoaderReplies {

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
            stmt = con.prepareStatement("INSERT INTO public.replies (reply_id, floor, post_id, reply_author, reply_star, reply_content) " +
                    "VALUES (?,?,?,?,?,?)" + " ON CONFLICT (reply_id) DO NOTHING;");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        List<SingleReply> list = new ArrayList<>();

        Label1:
        for (Replies r: replies) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (r.getPostID() == list.get(i).getPost_id1() && r.getReplyAuthor().equals(list.get(i).getAuthor1())
                        && r.getReplyStars() == list.get(i).getStar1() && r.getReplyContent().equals(list.get(i).getContent1())) {
                    continue Label1;
                }
            }
            list.add(new SingleReply(list.size(), r.getPostID(), 1, r.getReplyAuthor(), r.getReplyContent(), r.getReplyStars()
                    , null));
        }

        Label2:
        for (Replies r: replies) {
            int index = -1;
            for (int i = 1228; i >= 0; i--) {
                if (r.getPostID() == list.get(i).getPost_id1() && r.getReplyAuthor().equals(list.get(i).getAuthor1())
                        && r.getReplyStars() == list.get(i).getStar1() && r.getReplyContent().equals(list.get(i).getContent1())) {
                    index = i;
                    break;
                }
            }
            for (int i = list.size() - 1; i >= 1229; i--) {
                if (r.getPostID() == list.get(i).getPost_id1() && r.getSecondaryReplyAuthor().equals(list.get(i).getAuthor1())
                        && r.getSecondaryReplyStars() == list.get(i).getStar1() && r.getSecondaryReplyContent().equals(list.get(i).getContent1())) {
                    if (index == list.get(i).getUpper_floor1()) {
                        continue Label2;
                    }
                }
            }
            list.add(new SingleReply(list.size(), r.getPostID(), 2, r.getSecondaryReplyAuthor(), r.getSecondaryReplyContent(),
                    r.getSecondaryReplyStars(), index));
        }

        for (SingleReply r: list) {
            if (con != null) {
                try {
                    stmt.setInt(1, r.getReply_id1());
                    stmt.setInt(2, r.getFloor1());
                    stmt.setInt(3, r.getPost_id1());
                    stmt.setString(4, r.getAuthor1());
                    stmt.setInt(5, r.getStar1());
                    stmt.setString(6, r.getContent1());
                    stmt.execute();
                    cnt++;
                    if (cnt % 1000 == 0) {
                        System.out.println("insert " + 1000 + " data successfully!");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
        System.out.println("Loading speed : " + (cnt * 1000L) / (end - start) + " records/s");
    }
}

