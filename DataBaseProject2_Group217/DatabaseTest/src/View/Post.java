package View;

import DataImport.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class Post extends JFrame {
    private int WIDTH;

    private int HEIGHT;

    private int id;

    private String content;
    private String title;

    private String category = "";

    private String author;

    private JButton authorButton = new JButton();

    private String postingTime;
    private boolean anonymity;

    private String postingArea;

    private int likeCount;

    private int favoriteCount;

    private int shareCount;

    private boolean like;

    private boolean favorite;

    private boolean share;

    private byte[] imageData;

    private JButton likeButton = new JButton();

    private JButton favoriteButton = new JButton();

    private JButton shareButton = new JButton();

    private JButton shieldButton =new JButton();


    //private JPanel contentPanel = new JPanel();

    public Post(int id){
        this.id = id;
        initialize();
    }

    private void initialize() {
        setTitle("Post" + id);
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loadPostInformation();

        addIDArea();
        addTitleArea();
        addContentArea();
        addImageArea();
        addCategoryArea();
        addAuthorArea();
        addAuthorButton();
        addPostingTimeArea();
        addPostingAreaArea();
        addLikeButton();
        addFavoriteButton();
        addShareButton();
        addReplyButton();
        addViewReplyButton();
        addshieldbutton();
        addViewauthorinterfaceButton();

        setLayout(null);
        setVisible(true);
    }

    private void loadPostInformation() {
        String sql = "select * from posts where post_id = " + id + " ;";
        List<List<Object>> list = new DataImport().QuerySentence(sql);
        title = list.get(0).get(1).toString();
        content = list.get(0).get(2).toString();
        postingTime = list.get(0).get(3).toString().substring(0, 19);
        if (list.get(0).get(4).toString().equals("Y")) {
            anonymity = true;
        } else {
            anonymity = false;
        }

        sql = "select image from posts where post_id = " + id + " ;";
        imageData = new DataImport().imageQuerySentence(sql);

        sql = "select category from categories where post_id = " + id + ";";
        list = new DataImport().QuerySentence(sql);
        for (int i = 0; i < list.size(); i++) {
            category += list.get(i).get(0) + " ";
        }

        sql = "select author_name from post_author_relation where post_id = " + id + " and relation = 'P';";
        author = new DataImport().QuerySentence(sql).get(0).get(0).toString();

        sql = "select posting_city, posting_country from areas where post_id = " + id + ";";
        list = new DataImport().QuerySentence(sql);
        postingArea = list.get(0).get(0) + ", " + list.get(0).get(1);

        sql = "select count(*) from post_author_relation where post_id = " + id + " and relation = 'L';";
        list = new DataImport().QuerySentence(sql);
        likeCount = Integer.parseInt(list.get(0).get(0).toString());

        sql = "select count(*) from post_author_relation where post_id = " + id + " and relation = 'F';";
        list = new DataImport().QuerySentence(sql);
        favoriteCount = Integer.parseInt(list.get(0).get(0).toString());

        sql = "select count(*) from post_author_relation where post_id = " + id + " and relation = 'S';";
        list = new DataImport().QuerySentence(sql);
        shareCount = Integer.parseInt(list.get(0).get(0).toString());
    }

    private void addIDArea() {
        JTextArea idArea = new JTextArea("Post id: " + id);
        idArea.setFont(new Font("Rockwell", Font.BOLD, 30));
        idArea.setText("Post id: " + this.id);
        idArea.setEditable(false);
        //idArea.setLocation(WIDTH / 10, HEIGHT / 10);
        //idArea.setLocation(new Point(WIDTH / 10, HEIGHT / 10));
        JScrollPane jScrollPane = new JScrollPane(idArea);
        jScrollPane.setBounds(100, 5, 800, 45);
        add(jScrollPane);

    }

    private void addTitleArea() {
        JTextArea titleArea = new JTextArea("Title: " + title);
        titleArea.setFont(new Font("Rockwell", Font.BOLD, 30));
        titleArea.setText("Title: " + title);
        titleArea.setEditable(false);
        //titleArea.setLocation(WIDTH / 2, HEIGHT / 2);
        //titleArea.setLocation(new Point(WIDTH / 2, HEIGHT / 2));
        JScrollPane jScrollPane = new JScrollPane(titleArea);
        jScrollPane.setBounds(100, 60, 800, 45);
        add(jScrollPane);
    }

    private void addContentArea() {
        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        //jTextArea.getDocument().addDocumentListener(new WordWrapDocumentListener(jTextArea, 1));
        JScrollPane jScrollPane = new JScrollPane(contentArea);
        jScrollPane.setBounds(100,115,530, 250);
        add(jScrollPane);
    }

    private void addImageArea() {
        JLabel jLabel = new JLabel();
        jLabel.setBounds(650, 115, 250, 250);
        if (imageData != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(this.imageData);
                BufferedImage image = ImageIO.read(bais);
                Image scaledImage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                BufferedImage resizedImage = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(scaledImage, 0, 0, null);
                g2d.dispose();
                ImageIcon icon = new ImageIcon(resizedImage);
                jLabel.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        add(jLabel);
    }

    private void addCategoryArea() {
        JTextArea categoryArea = new JTextArea("Category: " + category);
        categoryArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        categoryArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(categoryArea);
        jScrollPane.setBounds(100, 375, 800, 30);
        add(jScrollPane);
    }

    private void addAuthorArea() {
        JTextArea authorArea;
        if (!anonymity) {
            authorArea = new JTextArea("Author: " + author);
        } else {
            authorArea = new JTextArea("Author: anonymous!");
        }
        authorArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        authorArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(authorArea);
        jScrollPane.setBounds(100, 415, 300, 30);
        add(jScrollPane);
    }

    private void addAuthorButton() {
        if (!anonymity) {
            String sql = "select count(*) from followers where followers = '" + Menu.user + "' and followed = '" + author + "';";
            List<List<Object>> list = new DataImport().QuerySentence(sql);
            if ((Long)list.get(0).get(0) != 0) {
                authorButton.setText("unfollow " + author);
            } else {
                authorButton.setText("follow " + author);
            }
            authorButton.setFont(new Font("Rockwell", Font.BOLD, 18));
            authorButton.setBounds(425, 415, 300, 30);
            authorButton.addActionListener((e) -> {
                if (authorButton.getText().startsWith("follow")) {
                    followAction();
                } else {
                    unfollowAction();
                }
            });
            add(authorButton);
        }
    }

    private void addshieldbutton(){
        if (!anonymity) {
            shieldButton.setText("shield(" + author + ")");
            shieldButton.setFont(new Font("Rockwell", Font.BOLD, 18));
            shieldButton.setBounds(400, 535, 200, 30);
            shieldButton.addActionListener((e) -> {
                String sql = "insert into shield(shielders, shielded) values('" + Menu.user + "', '" + author + "');";
                new DataImport2().notQuerySentence(sql);
                JOptionPane.showMessageDialog(null, "Your have shield the author! If want to cancer, please go to the personal interface--Blacklist!");
                dispose();
            });
            add(shieldButton);
        }
    }


    private void addPostingTimeArea() {
        JTextArea postingTimeArea = new JTextArea("Posting time: " + postingTime);
        postingTimeArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        postingTimeArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(postingTimeArea);
        jScrollPane.setBounds(100, 455, 310, 30);
        add(jScrollPane);
    }

    private void addPostingAreaArea() {
        JTextArea postingAreaArea = new JTextArea("Posting area: " + postingArea);
        postingAreaArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        postingAreaArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(postingAreaArea);
        jScrollPane.setBounds(450, 455, 450, 30);
        add(jScrollPane);
    }

    private void addLikeButton() {
        String sql = "select count(*) from post_author_relation where post_id = "
                + id + " and author_name = '" + Menu.user + "' and relation = 'L';";
        List<List<Object>> list = new DataImport().QuerySentence(sql);
        if ((Long)list.get(0).get(0) != 0) {
            like = true;
            likeButton.setText("unlike(" + likeCount + ")");
        } else {
            like = false;
            likeButton.setText("like(" + likeCount + ")");
        }
        likeButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        likeButton.setBounds(100, 495, 200, 30);
        likeButton.addActionListener((e) -> {
            if (like) {
                unlikeAction();
            } else {
                likeAction();
            }
        });
        add(likeButton);
    }

    private void addFavoriteButton() {
        String sql = "select count(*) from post_author_relation where post_id = "
                + id + " and author_name = '" + Menu.user + "' and relation = 'F';";
        List<List<Object>> list = new DataImport().QuerySentence(sql);
        if ((Long)list.get(0).get(0) != 0) {
            favorite = true;
            favoriteButton.setText("unfavorite(" + favoriteCount + ")");
        } else {
            favorite = false;
            favoriteButton.setText("favorite(" + favoriteCount + ")");
        }
        favoriteButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        favoriteButton.setBounds(400, 495, 200, 30);
        favoriteButton.addActionListener((e) -> {
            if (favorite) {
                unFavoriteAction();
            } else {
                favoriteAction();
            }
        });
        add(favoriteButton);
    }

    private void addShareButton() {
        String sql = "select count(*) from post_author_relation where post_id = "
                + id + " and author_name = '" + Menu.user + "' and relation = 'S';";
        List<List<Object>> list = new DataImport().QuerySentence(sql);
        if ((Long)list.get(0).get(0) != 0) {
            share = true;
            shareButton.setText("unshare(" + shareCount + ")");
        } else {
            share = false;
            shareButton.setText("share(" + shareCount + ")");
        }
        shareButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        shareButton.setBounds(700, 495, 200, 30);
        shareButton.addActionListener((e) -> {
            if (share) {
                unShareAction();
            } else {
                shareAction();
            }
        });
        add(shareButton);
    }

    private void addReplyButton() {
        JButton replyButton = new JButton("reply");
        replyButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        replyButton.setBounds(100, 535, 200, 30);
        replyButton.addActionListener((e) -> {
            new ReplyPost(id);
        });
        add(replyButton);
    }

    private void addViewReplyButton() {
        JButton viewRepliesButton = new JButton("view replies");
        viewRepliesButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        viewRepliesButton.setBounds(700, 535, 200, 30);
        viewRepliesButton.addActionListener((e) -> {
            new ViewReplies(id);
        });
        add(viewRepliesButton);
    }

    private void addViewauthorinterfaceButton() {
        JButton viewauthorinterfaceButton = new JButton("view author interface");
        viewauthorinterfaceButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        viewauthorinterfaceButton.setBounds(500, 615, 300, 30);
        viewauthorinterfaceButton.addActionListener((e) -> {
            new viewauthorinterface(author);
        });
        add(viewauthorinterfaceButton);
    }

    private void followAction() {
        String sql = "insert into followers(followers, followed) values('" + Menu.user + "', '" + author + "');";
        new DataImport2().notQuerySentence(sql);
        authorButton.setText("unfollow " + author);
    }


    private void unfollowAction() {
        String sql = "delete from followers where followers = '" + Menu.user + "' and followed = '" + author + "';";
        new DataImport2().notQuerySentence(sql);
        authorButton.setText("follow " + author);
    }

    private void likeAction() {
        like = true;
        likeCount++;
        likeButton.setText("unlike(" + likeCount + ")");
        String sql = "call inserts('"+Menu.user+"','L',"+id+");";
        new DataImport2().notQuerySentence(sql);
    }

    private void unlikeAction() {
        like = false;
        likeCount--;
        likeButton.setText("like(" + likeCount + ")");
        String sql = "call deletes('"+Menu.user+"','L',"+id+");";
        new DataImport2().notQuerySentence(sql);
    }

    private void favoriteAction() {
        favorite = true;
        favoriteCount++;
        favoriteButton.setText("unfavorite(" + favoriteCount + ")");
        String sql = "call inserts('"+Menu.user+"','F',"+id+");";
        new DataImport2().notQuerySentence(sql);
    }

    private void unFavoriteAction() {
        favorite = false;
        favoriteCount--;
        favoriteButton.setText("favorite(" + favoriteCount + ")");
        String sql = "call deletes('"+Menu.user+"','F',"+id+");";
        new DataImport2().notQuerySentence(sql);
    }

    private void shareAction() {
        share = true;
        shareCount++;
        shareButton.setText("unshare(" + shareCount + ")");
        String sql = "call inserts('"+Menu.user+"','S',"+id+");";
        new DataImport2().notQuerySentence(sql);
    }

    private void unShareAction() {
        share = false;
        shareCount--;
        shareButton.setText("share(" + shareCount + ")");
        String sql = "call deletes('"+Menu.user+"','S',"+id+");";
        new DataImport2().notQuerySentence(sql);
    }
}
