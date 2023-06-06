package View;

import Component.ViewButtonEditor;
import Component.ViewButtonRenderer;
import DataImport.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class viewauthorinterface extends JFrame {
    private int WIDTH;
    private int HEIGHT;
    private Object[][] data;
    private List<List<Object>> list;

    private String author;
    private int id;
    private int reply_id;


    public viewauthorinterface(String author) {
        this.author = author;

        initialize1();
    }



    private void initialize1() {
        setTitle(author+"'s personal interface");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        addauthorlaber();
        addtimelaber();
         loadData1();
        loadTable();

        setLayout(null);
        setVisible(true);

    }

    private void addauthorlaber(){
        JLabel author=new JLabel();
        author.setText(this.author+"'s personal interface");
        author.setLocation(WIDTH  / 7, HEIGHT / 13);
        author.setSize(800, 60);
        author.setFont(new Font("Rockwell", Font.BOLD, 40));
        add(author);
    }

    private void addtimelaber(){
        JLabel time =new JLabel();
       String sql = "select author_registration_time from authors where Author_name='"+author+"';";
       String times = new DataImport().QuerySentence(sql).get(0).get(0).toString();
        time.setText("registration time: "+times);
        time.setLocation(WIDTH  / 3, HEIGHT *2/ 13);
        time.setSize(400, 60);
        time.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(time);
    }


   private void loadData1() {
        String sql = "select posts.post_id,title from posts join post_author_relation on posts.post_id=post_author_relation.post_id where author_name='"+author+"' and relation='P';";
        list = new DataImport().QuerySentence(sql);
        int row = list.size();
        data = new Object[row][3];
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
        scrollPane.setBounds(100,HEIGHT *3/ 13, 800, 500);
        getContentPane().add(scrollPane);


    }


}
