package View;

import DataImport.DataImport;
import DataImport.DataImport2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class IssuePost extends JFrame {
    private int WIDTH;
    private int HEIGHT;
    private JTextArea titleArea = new JTextArea();
    private JTextArea contentArea = new JTextArea();
    private JButton imageButton = new JButton();
    private JLabel imageLabel = new JLabel();
    private File imageFile = null;
    private JTextArea category1Area = new JTextArea();
    private JTextArea category2Area = new JTextArea();
    private JTextArea category3Area = new JTextArea();
    private JTextArea category4Area = new JTextArea();
    private JTextArea postingCityArea = new JTextArea();
    private JTextArea postingCountryArea = new JTextArea();
    private JCheckBox anonymityCheckBox = new JCheckBox();

    public IssuePost() {
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

        addTitleLabel();
        addTitleArea();
        addImageButton();
        addImageLabel();
        addContentLabel();
        addContentArea();
        addCategory1Label();
        addCategory1Area();
        addCategory2Label();
        addCategory2Area();
        addCategory3Label();
        addCategory3Area();
        addCategory4Label();
        addCategory4Area();
        addPostingCityLabel();
        addPostingCityArea();
        addPostingCountryLabel();
        addPostingCountryArea();
        addAnonymityCheckBox();
        addSubmitButton();


        setLayout(null);
        setVisible(true);
    }

    private void addTitleLabel() {
        JLabel titleLabel = new JLabel("Your post title: ");
        titleLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        titleLabel.setBounds(100, 5, 390, 45);
        add(titleLabel);
    }

    private void addTitleArea() {

        titleArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        titleArea.setEditable(true);
        titleArea.setLineWrap(true);
        titleArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(titleArea);
        jScrollPane.setBounds(100,60,390, 100);
        add(jScrollPane);
    }

    private void addContentLabel() {
        JLabel contentLabel = new JLabel("Your post content: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        contentLabel.setBounds(510, 5, 390, 45);
        add(contentLabel);
    }

    private void addContentArea() {

        contentArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentArea.setEditable(true);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(contentArea);
        jScrollPane.setBounds(510,60,390, 360);
        add(jScrollPane);
    }

    private void addImageButton() {
        imageButton.setText("Add image");
        imageButton.setFont(new Font("Rockwell", Font.BOLD, 18));
        imageButton.setBounds(100, 170, 150,35);
        imageButton.addActionListener((e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                imageFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage image = ImageIO.read(imageFile);
                    Image scaledImage = image.getScaledInstance(210, 210, Image.SCALE_SMOOTH);
                    BufferedImage resizedImage = new BufferedImage(210, 210, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = resizedImage.createGraphics();
                    g2d.drawImage(scaledImage, 0, 0, null);
                    g2d.dispose();
                    ImageIcon icon = new ImageIcon(resizedImage);
                    imageLabel.setIcon(icon);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "无法加载图片", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(imageButton);
    }

    private void addImageLabel() {
        imageLabel.setBounds(100, 215, 210, 210);
        add(imageLabel);
    }

    private void addCategory1Label() {
        JLabel contentLabel = new JLabel("Category1: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(100, 435, 100, 45);
        add(contentLabel);
    }

    private void addCategory1Area() {
        category1Area.setFont(new Font("Rockwell", Font.BOLD, 18));
        category1Area.setEditable(true);
        category1Area.setLineWrap(true);
        category1Area.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(category1Area);
        jScrollPane.setBounds(210, 435, 280, 45);
        add(jScrollPane);
    }

    private void addCategory2Label() {
        JLabel contentLabel = new JLabel("Category2: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(510, 435, 100, 45);
        add(contentLabel);
    }

    private void addCategory2Area() {
        category2Area.setFont(new Font("Rockwell", Font.BOLD, 18));
        category2Area.setEditable(true);
        category2Area.setLineWrap(true);
        category2Area.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(category2Area);
        jScrollPane.setBounds(620, 435, 280, 45);
        add(jScrollPane);
    }
    private void addCategory3Label() {
        JLabel contentLabel = new JLabel("Category3: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(100, 490, 100, 45);
        add(contentLabel);
    }

    private void addCategory3Area() {
        category3Area.setFont(new Font("Rockwell", Font.BOLD, 18));
        category3Area.setEditable(true);
        category3Area.setLineWrap(true);
        category3Area.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(category3Area);
        jScrollPane.setBounds(210, 490, 280, 45);
        add(jScrollPane);
    }

    private void addCategory4Label() {
        JLabel contentLabel = new JLabel("Category4: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(510, 490, 100, 45);
        add(contentLabel);
    }

    private void addCategory4Area() {
        category4Area.setFont(new Font("Rockwell", Font.BOLD, 18));
        category4Area.setEditable(true);
        category4Area.setLineWrap(true);
        category4Area.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(category4Area);
        jScrollPane.setBounds(620, 490, 280, 45);
        add(jScrollPane);
    }

    private void addPostingCityLabel() {
        JLabel contentLabel = new JLabel("City: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(100, 545, 100, 45);
        add(contentLabel);
    }

    private void addPostingCityArea() {
        postingCityArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        postingCityArea.setEditable(true);
        postingCityArea.setLineWrap(true);
        postingCityArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(postingCityArea);
        jScrollPane.setBounds(210, 545, 280, 45);
        add(jScrollPane);
    }

    private void addPostingCountryLabel() {
        JLabel contentLabel = new JLabel("Country: ");
        contentLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        contentLabel.setBounds(510, 545, 100, 45);
        add(contentLabel);
    }

    private void addPostingCountryArea() {
        postingCountryArea.setFont(new Font("Rockwell", Font.BOLD, 18));
        postingCountryArea.setEditable(true);
        postingCountryArea.setLineWrap(true);
        postingCountryArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(postingCountryArea);
        jScrollPane.setBounds(620, 545, 280, 45);
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
            int result = JOptionPane.showConfirmDialog(null, "Do you confirm to submit the post?", "Confirm Message",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String title = titleArea.getText();
                String content = contentArea.getText();
                String category1 = category1Area.getText();
                String category2 = category2Area.getText();
                String category3 = category3Area.getText();
                String category4 = category4Area.getText();
                String city = postingCityArea.getText();
                String country = postingCountryArea.getText();
                String anonymity;
                if (anonymityCheckBox.isSelected()) {
                    anonymity = "Y";
                } else {
                    anonymity = "N";
                }
                if (!title.equals("") && !content.equals("") && !category1.equals("") && !city.equals("") && !country.equals("")) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = sdf.format(timestamp);
                    String sql = "insert into posts(title, content, posting_time, anonymity) values('" + title + "', '" + content + "', '" +
                            time + "', '" + anonymity + "');";
                    new DataImport().notQuerySentence(sql);
                    sql = "select post_id from posts where title = '" + title + "' and content = '" + content + "' and posting_time = '"
                            + time + "';";
                    int post_id = Integer.parseInt(new DataImport().QuerySentence(sql).get(0).get(0).toString());
                    if (imageFile != null) {
                        String path = imageFile.getAbsolutePath();
                        try {
                            byte[] imageBytes = convertImageToByteArray(path);
                            sql = "update posts set image = E'\\\\x" + byteArrayToHex(imageBytes) + "' where post_id = " + post_id + ";";
                            new DataImport2().notQuerySentence(sql);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    sql = "insert into post_author_relation(post_id, author_name, relation) values ('" + post_id + "', '" + Menu.user
                            + "', 'P');";
                    new DataImport2().notQuerySentence(sql);
                    sql = "insert into categories(post_id, category) values ('" + post_id + "', '" + category1 + "');";
                    new DataImport2().notQuerySentence(sql);
                    if (!category2.equals("")) {
                        sql = "insert into categories(post_id, category) values ('" + post_id + "', '" + category2 + "');";
                        new DataImport2().notQuerySentence(sql);
                    }
                    if (!category3.equals("")) {
                        sql = "insert into categories(post_id, category) values ('" + post_id + "', '" + category3 + "');";
                        new DataImport2().notQuerySentence(sql);
                    }
                    if (!category4.equals("")) {
                        sql = "insert into categories(post_id, category) values ('" + post_id + "', '" + category4 + "');";
                        new DataImport2().notQuerySentence(sql);
                    }
                    sql = "insert into areas(post_id, posting_city, posting_country) values ('" + post_id + "', '" + city
                            + "', '" + country + "');";
                    new DataImport2().notQuerySentence(sql);
                    JOptionPane.showMessageDialog(null, "You post post" + post_id + " successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Some post information is empty!");
                }
            } else {
                System.out.println("Cancelled");
            }
        });

        add(submitButton);
    }

    private byte[] convertImageToByteArray(String imagePath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        return baos.toByteArray();
    }

    private static String byteArrayToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }
}
