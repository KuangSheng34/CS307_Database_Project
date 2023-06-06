package View;

import Component.ViewButtonEditor;
import Component.ViewButtonRenderer;
import DataImport.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class topsearch extends JFrame {
    private int WIDTH;

    private int HEIGHT;
    private List<List<Object>> list;

    private Object[][] data;

    public topsearch() {
        initialize();
    }

    private void initialize() {
        setTitle("Top Search");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addBackButton();
        addNewPostButton();
        loadData();
        loadTable();

        setLayout(null);
        setVisible(true);

    }

    private void loadData() {
        String sql = "select posts.post_id,title, cnt from posts join (select post_id,count(*) cnt from post_author_relation  where relation!='P' group by post_id )x on x.post_id=posts.post_id order by cnt desc,post_id  limit 10;";
        this.list = new DataImport().QuerySentence(sql);
        int row = list.size();
        this.data = new Object[row][4];
        for (int i = 0; i < row; i++) {
            data[i][0] = list.get(i).get(0);
            data[i][1] = list.get(i).get(1);
            data[i][2] = list.get(i).get(2);
            data[i][3] = "View Post" + list.get(i).get(0);
        }
    }

    private void loadTable() {
        String[] columnNames = {"Post ID", "Title",  "Hot Value", "View"};
        //ViewPostTable viewPostTable = new ViewPostTable(this.list, columnNames);
        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(3).setCellRenderer(new ViewButtonRenderer());
        JTextField jTextField = new JTextField();
        table.getColumnModel().getColumn(3).setCellEditor(new ViewButtonEditor(jTextField));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 5, 800, 700);
        getContentPane().add(scrollPane);
//        System.out.println(table.getColumnCount());
//        System.out.println(table.getRowCount());


    }

    private void addBackButton() {
        JButton button = new JButton("<- Back");
        button.addActionListener((e) -> {
                    dispose();
                    new PostService();
                }
        );
        button.setLocation(0, HEIGHT / 20);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }

    private void addNewPostButton() {
        JButton button = new JButton("A new post");
        button.addActionListener((e) -> {
                    new PostService();
                }
        );
        button.setLocation(0, HEIGHT / 8);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);

    }

}
