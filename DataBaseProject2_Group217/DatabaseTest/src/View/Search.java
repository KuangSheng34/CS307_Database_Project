package View;

import javax.swing.*;
import java.awt.*;

public class Search extends JFrame {
    private int WIDTH;
    private int HEIGHT;
    private JTextArea keyword1Area = new JTextArea();
    private JTextArea authorArea = new JTextArea();


    private JTextArea categeryArea = new JTextArea();
    private JTextArea starttimeArea = new JTextArea();
    private JTextArea endtimeArea = new JTextArea();



    public Search() {
        initialize();
    }

    private void initialize() {
        setTitle("Issue Post ");
        this.WIDTH = 1000;
        this.HEIGHT = 800;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addkeyword1();
        addkeyword1Area();

        keyword2();
        addkeyword2Area();
        addkeyword3Label();
        addkeyword3Area();
        addstarttimeLabel();
        addstarttimeArea();
        addendtimeLabel();
        addendtimeArea();
        addBackButton();
        addSubmitButton();


        setLayout(null);
        setVisible(true);
    }

    private void addkeyword1() {
        JLabel titleLabel = new JLabel("key word : ");
        titleLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        titleLabel.setBounds(100, 5, 390, 45);
        add(titleLabel);
    }

    private void addkeyword1Area() {

        keyword1Area.setFont(new Font("Rockwell", Font.BOLD, 18));
        keyword1Area.setEditable(true);
        keyword1Area.setLineWrap(true);
        keyword1Area.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(keyword1Area);
        jScrollPane.setBounds(100,55,390, 45);
        add(jScrollPane);
    }

    private void keyword2() {
        JLabel contentLabel = new JLabel("author: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(100, 105, 390, 45);
        add(contentLabel);
    }

    private void addkeyword2Area() {

        authorArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        authorArea.setEditable(true);
        authorArea.setLineWrap(true);
        authorArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(authorArea);
        jScrollPane.setBounds(100,155,390, 45);
        add(jScrollPane);
    }


    private void addkeyword3Label() {
        JLabel contentLabel = new JLabel("categery : ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(100, 205, 390, 45);
        add(contentLabel);
    }

    private void addkeyword3Area() {
        categeryArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        categeryArea.setEditable(true);
        categeryArea.setLineWrap(true);
        categeryArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(categeryArea);
        jScrollPane.setBounds(100, 255, 390, 45);
        add(jScrollPane);
    }

    private void addstarttimeLabel() {
        JLabel contentLabel = new JLabel("start time: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(100, 305, 390, 45);
        add(contentLabel);
    }

    private void addstarttimeArea() {
        starttimeArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        starttimeArea.setEditable(true);
        starttimeArea.setLineWrap(true);
        starttimeArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(starttimeArea);
        jScrollPane.setBounds(100, 355, 390, 45);
        add(jScrollPane);
    }
    private void addendtimeLabel() {
        JLabel contentLabel = new JLabel("end time: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(100, 405, 390, 45);
        add(contentLabel);
    }

    private void addendtimeArea() {
        endtimeArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        endtimeArea.setEditable(true);
        endtimeArea.setLineWrap(true);
        endtimeArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(endtimeArea);
        jScrollPane.setBounds(100, 455, 390, 45);
        add(jScrollPane);
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



    private void addSubmitButton() {
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Rockwell", Font.BOLD, 30));
        submitButton.setBounds(400, 640, 200, 50);
        submitButton.addActionListener((e) -> {

                String keyword1 = keyword1Area.getText();
                String author = authorArea.getText();
                String category = categeryArea.getText();
                String starttime = starttimeArea.getText();
                String endtime = endtimeArea.getText();
                String sql;
                if (!keyword1.equals("") && !author.equals("") && !category.equals("") && !starttime.equals("") && !endtime.equals("")){
                    sql="select distinct (posts.post_id),title from posts join categories  on posts.post_id = categories.post_id join post_author_relation on posts.post_id=post_author_relation.post_id where relation='P' and title ilike '%"+keyword1+"%' or content ilike '%"+keyword1+"%' and category ilike '%"+category+"%' and posting_time>=to_timestamp('"+starttime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') and posting_time<to_timestamp('"+endtime+" 00:00:00','yyyy-mo-dd HH24:mi:ss')and author_name ilike '%"+author+"%' order by posts.post_id;";
                    dispose();
                    new viewsearch(sql); }else if (!keyword1.equals("") && !author.equals("") && !category.equals("")){
                    sql="select distinct (posts.post_id),title from posts join categories  on posts.post_id = categories.post_id join post_author_relation on posts.post_id=post_author_relation.post_id where relation='P' and title ilike '%"+keyword1+"%'  or content ilike  '%"+keyword1+"%' and category ilike '%"+category+"%'and author_name ilike '%"+author+"%' order by posts.post_id;";
                    dispose();
                    new viewsearch(sql); }else if (!author.equals("") && !category.equals("") && !starttime.equals("") && !endtime.equals("")){
                    sql="select distinct (posts.post_id),title from posts join categories  on posts.post_id = categories.post_id join post_author_relation on posts.post_id=post_author_relation.post_id where relation='P' and category ilike '%"+category+"%' and posting_time>=to_timestamp('"+starttime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') and posting_time<to_timestamp('"+endtime+" 00:00:00','yyyy-mo-dd HH24:mi:ss')and author_name ilike '%"+author+"%' order by posts.post_id;";
                    dispose();
                    new viewsearch(sql);}else if (!keyword1.equals("") && !category.equals("") && !starttime.equals("") && !endtime.equals("")){
                    sql="select distinct (posts.post_id),title from posts join categories  on posts.post_id = categories.post_id where title ilike '%"+keyword1+"%' or content ilike '%"+keyword1+"%' and category ilike '%"+category+"%' and posting_time>=to_timestamp('"+starttime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') and posting_time<to_timestamp('"+endtime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') order by posts.post_id;";
                    dispose();
                    new viewsearch(sql); }else if (!keyword1.equals("") && !author.equals("")  && !starttime.equals("") && !endtime.equals("")){
                   sql="select distinct (posts.post_id),title from posts join post_author_relation on posts.post_id=post_author_relation.post_id where relation='P' and title ilike '%"+keyword1+"%'  or content ilike  '%"+keyword1+"%' and author_name ilike '%"+author+"%' and posting_time>=to_timestamp('"+starttime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') and posting_time<to_timestamp('"+endtime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') order by posts.post_id;";
                    dispose();
                    new viewsearch(sql); }else if (!category.equals("") && !starttime.equals("") && !endtime.equals("")){
                    sql="select distinct (posts.post_id),title from posts join categories c on posts.post_id = c.post_id where category ilike '%"+category+"%' and posting_time>=to_timestamp('"+starttime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') and posting_time<to_timestamp('"+endtime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') order by posts.post_id;";
                    dispose();
                    new viewsearch(sql); }else if (!keyword1.equals("") && !starttime.equals("") && !endtime.equals("")){
                    sql="select distinct (post_id),title from posts  where  title ilike '%"+keyword1+"%'  or content ilike  '%"+keyword1+"%' and posting_time>=to_timestamp('"+starttime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') and posting_time<to_timestamp('"+endtime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') order by post_id;";
                    dispose();
                    new viewsearch(sql);}else if (!author.equals("")  && !starttime.equals("") && !endtime.equals("")){
                    sql="select distinct (posts.post_id),title from posts join public.post_author_relation par on posts.post_id = par.post_id where relation='P' and author_name ilike '%"+author+"%' and posting_time>=to_timestamp('"+starttime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') and posting_time<to_timestamp('"+endtime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') order by posts.post_id;";
                    dispose();
                    new viewsearch(sql); }else if (!keyword1.equals("") && !author.equals("")  ){
                    sql="select distinct (posts.post_id),title from posts join post_author_relation on posts.post_id=post_author_relation.post_id where relation='P' and title ilike '%"+keyword1+"%'  or content ilike  '%"+keyword1+"%' and author_name ilike '%"+author+"%' order by posts.post_id;";
                    dispose();
                    new viewsearch(sql); }else if ( !author.equals("") && !category.equals("") ){
                    sql="select distinct (posts.post_id),title from posts join categories  on posts.post_id = categories.post_id join post_author_relation on posts.post_id=post_author_relation.post_id where relation='P'  and category ilike '%"+category+"%'and author_name ilike '%"+author+"%' order by posts.post_id;";
                    dispose();
                    new viewsearch(sql); }else if (!keyword1.equals("") &&  !category.equals("") ){
                    sql="select distinct (posts.post_id),title from posts join categories  on posts.post_id = categories.post_id where title ilike '%"+keyword1+"%' or content ilike '%"+keyword1+"%' and category ilike '%"+category+"%' order by posts.post_id;";
                    dispose();
                    new viewsearch(sql); }else if (!starttime.equals("") && !endtime.equals("")){
                    sql="select distinct (post_id),title from posts  where  posting_time>=to_timestamp('"+starttime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') and posting_time<to_timestamp('"+endtime+" 00:00:00','yyyy-mo-dd HH24:mi:ss') order by post_id;";
                    dispose();
                    new viewsearch(sql); }else if (!category.equals("")){
                    sql="select distinct (posts.post_id),title from posts join categories  on posts.post_id = categories.post_id where category ilike '%"+category+"%' order by posts.post_id;";
                    dispose();
                    new viewsearch(sql);}else if (!keyword1.equals("") ){
                    sql="select distinct (post_id),title from posts  where  title ilike '%"+keyword1+"%'  or content ilike  '%"+keyword1+"%' order by post_id;";
                    dispose();
                    new viewsearch(sql);}else if (!author.equals("") ){
                    sql="select distinct (posts.post_id),title from posts join post_author_relation on posts.post_id=post_author_relation.post_id where relation='P' and author_name ilike '%"+author+"%' order by posts.post_id;";
                    dispose();
                    new viewsearch(sql);}
                else {
                    JOptionPane.showMessageDialog(null, "invalid information");
                }


        });

        add(submitButton);
    }


}
