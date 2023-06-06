package View;

import DataImport.DataImport;
import DataImport.DataImport2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReplyPost extends JFrame {
    private int WIDTH;
    private int HEIGHT;
    private int postID;
    private int upper_replyID;
    private int replyFloor;

    private JTextArea replyArea = new JTextArea();

    private JCheckBox anonymityCheckBox = new JCheckBox();

    public ReplyPost(int id) {
        postID = id;
        initialize();
    }

    public ReplyPost(int postID, int upper_replyID, int replyFloor) {
        this.postID = postID;
        this.upper_replyID = upper_replyID;
        this.replyFloor = replyFloor;
        initialize2();
    }

    private void initialize() {
        setTitle("Reply Post " + postID);
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addReplyLabel();
        addReplyArea();
        addSubmitButton();
        addAnonymityCheckBox();

        setLayout(null);
        setVisible(true);
    }

    private void initialize2() {
        setTitle("Reply Post " + postID);
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addReplyLabel();
        addReplyArea();
        addSubmitButton2();
        addAnonymityCheckBox();

        setLayout(null);
        setVisible(true);
    }

    private void addReplyLabel() {
        JLabel replyLabel = new JLabel("Your reply Content: ");
        replyLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        replyLabel.setBounds(100, 5, 800, 45);
        add(replyLabel);
    }

    private void addReplyArea() {

        replyArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        replyArea.setEditable(true);
        replyArea.setLineWrap(true);
        replyArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(replyArea);
        jScrollPane.setBounds(100,60,800, 530);
        add(jScrollPane);
    }

    private void addAnonymityCheckBox() {
        anonymityCheckBox.setFont(new Font("Rockwell", Font.BOLD, 18));
        anonymityCheckBox.setText("Anonymous");
        anonymityCheckBox.setBounds(620, 600, 140, 30);
        anonymityCheckBox.setBackground(Color.WHITE);
        add(anonymityCheckBox);
    }

    private void addSubmitButton() {
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Rockwell", Font.BOLD, 30));
        submitButton.setBounds(400, 640, 200, 50);
        submitButton.addActionListener((e) -> {
            int result = JOptionPane.showConfirmDialog(null, "Do you confirm to submit the reply?", "Confirm Message",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String content = replyArea.getText();
                String anonymity;
                if (anonymityCheckBox.isSelected()) {
                    anonymity = "Y";
                } else {
                    anonymity = "N";
                }
                if (!content.equals("")) {
                    String sql = "insert into replies(floor, post_id, reply_author, reply_star, reply_content, anonymity) values ('1', '" + postID
                            + "', '" + Menu.user + "', '0', '" + content + "', '" + anonymity + "');";
                    new DataImport2().notQuerySentence(sql);
                    JOptionPane.showMessageDialog(null, "Your reply of post " + postID + " successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Your reply content is empty!");
                }
            } else {
                System.out.println("Cancelled");
            }
        });

        add(submitButton);
    }

    private void addSubmitButton2() {
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Rockwell", Font.BOLD, 30));
        submitButton.setBounds(400, 640, 200, 50);
        submitButton.addActionListener((e) -> {
            int result = JOptionPane.showConfirmDialog(null, "Do you confirm to submit the reply?", "Confirm Message",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String content = replyArea.getText();
                String anonymity;
                if (anonymityCheckBox.isSelected()) {
                    anonymity = "Y";
                } else {
                    anonymity = "N";
                }
                if (!content.equals("")) {
                    String sql = "insert into replies(floor, post_id, reply_author, reply_star, reply_content, anonymity) values ('"+ replyFloor + "', '" + postID
                            + "', '" + Menu.user + "', '0', '" + content + "', '" + anonymity + "');";
                    new DataImport().notQuerySentence(sql);
                    sql = "select reply_id from replies where floor = " + replyFloor + " and post_id = " + postID + " and reply_star = 0 and reply_content = '" +
                            content + "' and reply_author = '" + Menu.user + "';";
                    List<List<Object>> list = new DataImport().QuerySentence(sql);
                    int reply_id = Integer.parseInt(list.get(0).get(0).toString());
                    sql = "insert into upper_replies(reply_id, upper_reply_id) values ('" + reply_id + "', '" + upper_replyID + "');";
                    new DataImport2().notQuerySentence(sql);
                    JOptionPane.showMessageDialog(null, "Your reply of reply " + upper_replyID + " successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Your reply content is empty!");
                }
            } else {
                System.out.println("Cancelled");
            }
        });

        add(submitButton);
    }
}
