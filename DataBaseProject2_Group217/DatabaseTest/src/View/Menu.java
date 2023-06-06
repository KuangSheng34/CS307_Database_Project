package View;

import Util.OrdinaryUtil;
import Util.ProxoolUtil;
import DataImport.*;
import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class Menu extends JFrame {

    public static boolean visit = false;
    public static String user = null;
    private int WIDTH;

    private int HEIGHT;

    public Menu() {
        addLogInQuestion();
        initialize();
    }

    private void addLogInQuestion() {
        int count = 0;
        while (Menu.user == null) {
            if (count >= 4) {
                JOptionPane.showMessageDialog(null, "Your operation is too frequent!");
                System.exit(0);
            }

            int authorExisted = JOptionPane.showConfirmDialog(null, "Do you hava an author account?",
                    null, JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (authorExisted == JOptionPane.OK_OPTION) {
                logIn();
            } else {
                int visitor = JOptionPane.showConfirmDialog(null, "Do you want to visit posts just as a visitor?",
                        null, JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (visitor == JOptionPane.OK_OPTION) {
                    visit = true;
                    OrdinaryUtil.user = "kuang";
                    OrdinaryUtil.pwd = "123456";
                    ProxoolUtil.proxoolName = "src/proxool2.xml";
                    break;
                } else {
                    createAuthor();
                }
            }
            count++;
        }
    }

    private void logIn() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField field = new JTextField(10);

        panel.add(new JLabel("Your Author name:"));
        panel.add(field);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Enter the author information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = field.getText();
            String sql = "select count(*) from authors where author_name = '" + name + "';";
            System.out.println(sql);
            List<List<Object>> list =  new DataImport().QuerySentence(sql);
            if ((Long)list.get(0).get(0) != 0) {
                Menu.user = name;
                JOptionPane.showMessageDialog(null, "You have logged in successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "The author hasn't been created.");
            }
        } else {
            System.out.println("Cancelled");
        }
    }

    private void createAuthor() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField field1 = new JTextField(10);
        JTextField field2 = new JTextField(10);
        JTextField field3 = new JTextField(10);

        panel.add(new JLabel("Your Author name:"));
        panel.add(field1);

        panel.add(new JLabel("Your ID number:"));
        panel.add(field2);

        panel.add(new JLabel("Your phone number:"));
        panel.add(field3);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Enter the author information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String sql;
            String name = field1.getText();
            sql = "select count(*) from authors where author_name = '" + name + "';";
            List<List<Object>> list = new DataImport().QuerySentence(sql);
            if ((long)list.get(0).get(0) == 0) {
                String id = field2.getText();
                String phone = field3.getText();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf.format(timestamp);
                sql = "insert into authors(author_name, author_id, author_registration_time, author_phone) values('"
                        + name + "', '" + id + "', '" + time + "', '" + phone + "');";
                new DataImport2().notQuerySentence(sql);
                Menu.user = name;
                JOptionPane.showMessageDialog(null, "You create the author successfully!");
//                System.out.println("Field 1 value: " + field1.getText());
//                System.out.println("Field 2 value: " + field2.getText());
//                System.out.println("Field 3 value: " + field2.getText());
            } else {
                JOptionPane.showMessageDialog(null, "The author name have been created!");
            }

        } else {
            System.out.println("Cancelled");
        }
    }

    private void initialize() {
        setTitle("Menu");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        setVisible(true);

        addAuthorButton();
        addPostButton();
        addpersonalinterface();
    }

    private void addAuthorButton() {
        JButton button = new JButton("Author Service");
        button.addActionListener((e) -> {
                    loadAuthorService();
                }
        );
        button.setLocation(WIDTH * 5 / 16, HEIGHT / 2);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addpersonalinterface(){
        JButton button = new JButton("Personal Interface");
        button.addActionListener((e) -> {
                    dispose();
                    new personalinterface();
                }
        );
        button.setLocation(WIDTH * 5 / 16, HEIGHT / 4);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addPostButton() {
        JButton button = new JButton("Post Service");
        button.addActionListener((e) -> {
                    loadPostService();
                }
        );
        button.setLocation(WIDTH * 5 / 16, HEIGHT*3 / 8);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void loadAuthorService() {
        dispose();
        new AuthorService();
    }

    private void loadPostService() {
        dispose();
        new PostService();
    }



}

