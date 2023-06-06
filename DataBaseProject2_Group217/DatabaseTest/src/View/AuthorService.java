package View;

import DataImport.*;

import javax.swing.*;
import java.awt.*;

public class AuthorService extends JFrame {
    private int WIDTH;

    private int HEIGHT;

    public AuthorService() {
        initialize();
    }

    private void initialize() {
        setTitle("Author Service");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setVisible(true);


        addDestroyAuthorButton();

        addLogOutButton();
        addBackButton();
    }





    private void addDestroyAuthorButton() {
        JButton button = new JButton("Destroy Author");
        button.addActionListener((e) -> {
                    destroyAuthor();
                }
        );
        button.setLocation(WIDTH * 5 / 16, HEIGHT / 2);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLogOutButton() {
        JButton button = new JButton("Log out");
        button.addActionListener((e) -> {
                    logOut();
                }
        );
        button.setLocation(WIDTH * 5 / 16, HEIGHT * 2 / 3);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addBackButton() {
        JButton button = new JButton("<- Back");
        button.addActionListener((e) -> {
                    dispose();
                    new Menu();
                }
        );
        button.setLocation(WIDTH / 32, HEIGHT / 20);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }


    private void destroyAuthor() {
        if (Menu.user == null) {
            JOptionPane.showMessageDialog(null, "You haven't logged in!");
        } else {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure to destroy the account?", "Caution!",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String name = Menu.user;
                String sql = "Delete from authors where author_name = '" + name + "';";
                new DataImport2().notQuerySentence(sql);
                Menu.user = null;
            } else {
                System.out.println("Cancelled");
            }
        }
    }



    private void logOut() {
        Menu.user = null;
        JOptionPane.showMessageDialog(null, "You have logged out!");
        System.exit(0);
    }
}
