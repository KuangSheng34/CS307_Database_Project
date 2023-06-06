package View;

import javax.swing.*;
import java.awt.*;

public class personalinterface extends JFrame {

    private int WIDTH;

    private int HEIGHT;
    public personalinterface(){
        initialize();
    }
    private void initialize(){
        setTitle("personal interface");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);

        addBackButton();
        addfollowedauthor();
        addlikedpost();
        addfavoritepost();
        addnewpostButton();
        addsharedpost();
        addposted();
        addreplied();
        addBlacklistButton();

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
    private void addnewpostButton(){
        JButton button = new JButton("A new post");
        button.addActionListener((e) -> {
                    new IssuePost();
                }
        );
        button.setLocation(WIDTH *6/ 32, HEIGHT*4 / 20);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }

    private void addBlacklistButton(){
        JButton button = new JButton("Blacklist");
        button.addActionListener((e) -> {
            dispose();
                    new Blacklist();
                }
        );
        button.setLocation(WIDTH *15/ 32, HEIGHT*4 / 20);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }

    private void addfollowedauthor(){
        JButton button = new JButton("Followed");
        button.addActionListener((e) -> {
                    dispose();
                new ViewFollowed();
                }
        );
        button.setLocation(WIDTH *5/ 32, HEIGHT*8 / 20);
        button.setSize(150, 45);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }
    private void addlikedpost(){
        JButton button = new JButton("Liked");
        button.addActionListener((e) -> {
                    dispose();
                new ViewLiked();
                }
        );
        button.setLocation(WIDTH *12/ 32, HEIGHT*8 / 20);
        button.setSize(150, 45);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }
    private void addfavoritepost(){
        JButton button = new JButton("Favorite");
        button.addActionListener((e) -> {
                    dispose();
                new ViewFavorite();
                }
        );
        button.setLocation(WIDTH *20/ 32, HEIGHT *8/ 20);
        button.setSize(150, 45);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }
    private void addsharedpost(){
        JButton button = new JButton("Shared");
        button.addActionListener((e) -> {
                    dispose();
                new ViewShared();
                }
        );
        button.setLocation(WIDTH *5/ 32, HEIGHT*12 / 20);
        button.setSize(150, 45);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }
    private void addposted(){
        JButton button = new JButton("Posted");
        button.addActionListener((e) -> {
                    dispose();
                    new ViewPosted();
                }
        );
        button.setLocation(WIDTH *12/ 32, HEIGHT*12 / 20);
        button.setSize(150, 45);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }
    private void addreplied(){
        JButton button = new JButton("Replied");
        button.addActionListener((e) -> {
                    dispose();
                    new ViewReplied();
                }
        );
        button.setLocation(WIDTH *20/ 32, HEIGHT *12 / 20);
        button.setSize(150, 45);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }

}
