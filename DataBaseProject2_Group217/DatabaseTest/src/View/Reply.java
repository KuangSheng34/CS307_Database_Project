package View;

import DataImport.DataImport;
import DataImport.DataImport2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Reply extends JFrame {
    private int WIDTH;
    private int HEIGHT;
    private int reply_id;
    private int floor;
    private int post_id;
    private String author;
    private JButton authorButton = new JButton();
    private int starCount;
    private String content;
    private String upper_reply = "No upper reply!";
    private boolean star;
    private JButton starButton = new JButton();
    private boolean anonymity;

    public Reply(int reply_id) {
        this.reply_id = reply_id;
        initialize();
    }

    private void initialize() {
        setTitle("Reply" + reply_id);
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loadReplyInformation();

        addReplyIDArea();
        addReplyPostArea();
        addUpperReplyArea();
        addReplyContentArea();
        addAuthorArea();
        addAuthorButton();
        addStarButton();
        addReplyButton();
        addViewRepliesButton();

        setLayout(null);
        setVisible(true);
    }

    private void loadReplyInformation() {
        String sql = "select * from reply_view where reply_id = " + reply_id + ";";
        List<List<Object>> list = new DataImport().QuerySentence(sql);
        floor = Integer.parseInt(list.get(0).get(1).toString());
        post_id = Integer.parseInt(list.get(0).get(2).toString());
        author = list.get(0).get(3).toString();
        starCount = Integer.parseInt(list.get(0).get(4).toString());
        content = list.get(0).get(5).toString();
        if (list.get(0).get(6).toString().equals("Y")) {
            anonymity = true;
        } else {
            anonymity = false;
        }
        if (floor != 1) {
            upper_reply = list.get(0).get(7).toString();
        }
        sql = "select count(*) from reply_author_relation where reply_id = " + reply_id
                + " and author_name = '" + Menu.user + "' and relation = 'S';";
        list = new DataImport().QuerySentence(sql);
        if ((Long)list.get(0).get(0) != 0) {
            star = true;
        } else {
            star = false;
        }
    }

    private void addReplyIDArea() {
        JTextArea idArea = new JTextArea("Reply id: " + reply_id);
        idArea.setFont(new Font("Rockwell", Font.BOLD, 30));
        idArea.setEditable(false);
        //idArea.setLocation(WIDTH / 10, HEIGHT / 10);
        //idArea.setLocation(new Point(WIDTH / 10, HEIGHT / 10));
        JScrollPane jScrollPane = new JScrollPane(idArea);
        jScrollPane.setBounds(100, 5, 375, 45);
        add(jScrollPane);
    }

    private void addReplyPostArea() {
        JTextArea replyPostArea = new JTextArea("Reply Post: " + post_id);
        replyPostArea.setFont(new Font("Rockwell", Font.BOLD, 30));
        replyPostArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(replyPostArea);
        jScrollPane.setBounds(525, 5, 375, 45);
        add(jScrollPane);
    }

    private void addUpperReplyArea() {
        JTextArea upperReplyArea = new JTextArea("UPPER REPLY: \n" + upper_reply);
        upperReplyArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        upperReplyArea.setEditable(false);
        upperReplyArea.setLineWrap(true);
        upperReplyArea.setWrapStyleWord(true);
        //jTextArea.getDocument().addDocumentListener(new WordWrapDocumentListener(jTextArea, 1));
        JScrollPane jScrollPane = new JScrollPane(upperReplyArea);
        jScrollPane.setBounds(100,60,800, 250);
        add(jScrollPane);
    }

    private void addReplyContentArea() {
        JTextArea ReplyContentArea = new JTextArea("REPLY CONTENT: \n" + content);
        ReplyContentArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        ReplyContentArea.setEditable(false);
        ReplyContentArea.setLineWrap(true);
        ReplyContentArea.setWrapStyleWord(true);
        //jTextArea.getDocument().addDocumentListener(new WordWrapDocumentListener(jTextArea, 1));
        JScrollPane jScrollPane = new JScrollPane(ReplyContentArea);
        jScrollPane.setBounds(100,320,800, 250);
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
        jScrollPane.setBounds(100, 580, 300, 30);
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
            authorButton.setBounds(425, 580, 300, 30);
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

    private void addStarButton() {
        if (star) {
            starButton.setText("unstar(" + starCount + ")");
        } else {
            starButton.setText("star(" + starCount + ")");
        }
        starButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        starButton.setBounds(100, 620, 200, 30);
        starButton.addActionListener((e) -> {
            if (star) {
                unstarAction();
            } else {
                starAction();
            }
        });
        add(starButton);
    }

    private void addReplyButton() {
        JButton replyButton = new JButton("reply");
        replyButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        replyButton.setBounds(400, 620, 200, 30);
        replyButton.addActionListener((e) -> {
            new ReplyPost(post_id, reply_id, floor + 1);
        });
        add(replyButton);
    }

    private void addViewRepliesButton() {
        JButton viewRepliesButton = new JButton("view replies");
        viewRepliesButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        viewRepliesButton.setBounds(700, 620, 200, 30);
        viewRepliesButton.addActionListener((e) -> {
            new ViewReplies(reply_id, floor);
        });
        add(viewRepliesButton);
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

    private void starAction() {
        star = true;
        starCount++;
        starButton.setText("unstar(" + starCount + ")");
        String sql = "insert into reply_author_relation(reply_id, author_name, relation) values ('" + reply_id + "', '"
                + Menu.user + "', 'S');";
        new DataImport2().notQuerySentence(sql);
        sql = "update replies set reply_star = " + starCount + " where reply_id = " +reply_id + " ;";
        new DataImport2().notQuerySentence(sql);
    }

    private void unstarAction() {
        star = false;
        starCount--;
        starButton.setText("star(" + starCount + ")");
        String sql = "delete from reply_author_relation where reply_id = " + reply_id + " and author_name = '"
                + Menu.user + "' and relation = 'S';";
        new DataImport2().notQuerySentence(sql);
        sql = "update replies set reply_star = " + starCount + " where reply_id = " +reply_id + " ;";
        new DataImport2().notQuerySentence(sql);
    }
}
