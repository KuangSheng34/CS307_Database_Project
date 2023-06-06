package View;

import Component.ViewButtonEditor;
import Component.ViewButtonRenderer;
import DataImport.DataImport;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewReplies extends JFrame {
    private int WIDTH;
    private int HEIGHT;
    private Object[][] data;
    private List<List<Object>> list;
    private int post_id;
    private int reply_id;


    public ViewReplies(int post_id) {
        this.post_id = post_id;
        initialize1();
    }

    public ViewReplies(int reply_id, int floor) {
        this.reply_id = reply_id;
        initialize2();
    }

    private void initialize1() {
        setTitle("View Reply");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        loadData1();
        loadTable();

        setLayout(null);
        setVisible(true);

    }

    private void initialize2() {
        setTitle("View Reply");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        loadData2();
        loadTable();

        setLayout(null);
        setVisible(true);
    }

    private void loadData1() {
        String sql = "select reply_id, reply_content from replies where floor = 1 and post_id = " + post_id + ";";
        list = new DataImport().QuerySentence(sql);
        int row = list.size();
        data = new Object[row][3];
        for (int i = 0; i < row; i++) {
            data[i][0] = list.get(i).get(0);
            data[i][1] = list.get(i).get(1);
            data[i][2] = "View reply" + data[i][0];
        }
    }

    private void loadData2() {
        String nestedsql = "(select reply_id from upper_replies where upper_reply_id = " + reply_id + ") table1";
        String sql = "select replies.reply_id, replies.reply_content from " + nestedsql + " join replies on table1.reply_id = replies.reply_id;";
        list = new DataImport().QuerySentence(sql);
        int row = list.size();
        data = new Object[row][3];
        for (int i = 0; i < row; i++) {
            data[i][0] = list.get(i).get(0);
            data[i][1] = list.get(i).get(1);
            data[i][2] = "View reply" + data[i][0];
        }
    }

    private void loadTable() {
        String[] columnNames = {"Reply ID", "Content", "View"};
        //ViewPostTable viewPostTable = new ViewPostTable(this.list, columnNames);
        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(2).setCellRenderer(new ViewButtonRenderer());
        JTextField jTextField = new JTextField();
        table.getColumnModel().getColumn(2).setCellEditor(new ViewButtonEditor(jTextField));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 5, 800, 700);
        add(scrollPane);
    }


}
