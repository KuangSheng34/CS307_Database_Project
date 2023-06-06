package View;

import Component.ViewButtonEditor;
import Component.ViewButtonRenderer;
import DataImport.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewShared extends JFrame {
    private int WIDTH;

    private int HEIGHT;
    private List<List<Object>> list;

    private Object[][] data;

    public ViewShared(){
        initial();
    }
    private void initial(){
        setTitle("View Shared");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addBackButton();
        loadData();
        loadTable();

        setLayout(null);
        setVisible(true);
    }

    private void loadData() {
        String sql = "select posts.post_id, title from posts join post_author_relation on posts.post_id = post_author_relation.post_id where author_name='"+Menu.user+"' and relation='S';";

        this.list = new DataImport().QuerySentence(sql);
        int row = list.size();
        this.data = new Object[row][3];
        for (int i = 0; i < row; i++) {
            data[i][0] = list.get(i).get(0);
            data[i][1] = list.get(i).get(1);
            data[i][2] = "View Post" + list.get(i).get(0);
        }
    }

    private void loadTable() {
        String[] columnNames = {"Post ID", "Title", "View"};
        //ViewPostTable viewPostTable = new ViewPostTable(this.list, columnNames);
        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(2).setCellRenderer(new ViewButtonRenderer());
        JTextField jTextField = new JTextField();
        table.getColumnModel().getColumn(2).setCellEditor(new ViewButtonEditor(jTextField));
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
                    new personalinterface();
                }
        );
        button.setLocation(0, HEIGHT / 20);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }
}
