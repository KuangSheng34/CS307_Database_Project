package View;

import javax.swing.*;
import java.awt.*;

public class PostService extends JFrame{
    private int WIDTH;

    private int HEIGHT;

    public PostService() {
        initialize();
    }

    private void initialize() {
        setTitle("Post Service");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);

        addViewPostButton();
        addSearchPostButton();
        addBackButton();
        addTopsearchButton();
    }

    private void addViewPostButton() {
        JButton button = new JButton("View Post");
        button.addActionListener((e) -> {
            dispose();
            new ViewPost();
                }
        );
        button.setLocation(WIDTH * 5 / 16, HEIGHT / 3);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addTopsearchButton(){
        JButton button = new JButton("Top Search");
        button.addActionListener((e) -> {
                    dispose();
                    new topsearch();
                }
        );
        button.setLocation(WIDTH * 5 / 16, HEIGHT*2 / 3);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSearchPostButton() {
        JButton button = new JButton("Search Post");
        button.addActionListener((e) -> {
            new Search();
            dispose();
                }
        );
        button.setLocation(WIDTH * 5 / 16, HEIGHT / 2);
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


}
