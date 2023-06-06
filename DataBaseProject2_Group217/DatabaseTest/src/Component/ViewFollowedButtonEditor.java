package Component;

import DataImport.*;
import View.Menu;

import javax.swing.*;
import java.awt.*;

public class ViewFollowedButtonEditor extends DefaultCellEditor {
    private String label;
    private JButton button;

    private String followed;

    private String follower;

    public ViewFollowedButtonEditor(JTextField textField) {
        super(textField);

        button = new JButton();
        button.setOpaque(true);
        button.addActionListener((e) -> {
            //fireEditingStopped();
            if (button.getText().startsWith("follow")) {
                followAction();
            } else {
                unFollowAction();
            }

        });
    }

    private void unFollowAction() {
        this.follower = Menu.user;
        this.followed = button.getText().substring(9);
        String sql = "Delete from followers where followers = '" + follower + "' and followed = '" + followed + "';";

        new DataImport().notQuerySentence(sql);
        button.setText("follow " + followed);
    }

    private void followAction() {
        this.follower = Menu.user;
        this.followed = button.getText().substring(7);
        String sql = "Insert into followers(followers, followed) values('" + follower + "', '" + followed + "');";
        new DataImport().notQuerySentence(sql);
        button.setText("unfollow " + followed);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null) {
            button.setText("");
        } else {
            button.setText(value.toString());
        }
        return button;
    }

    public Object getCellEditorValue() {
        return button.getText();
    }
}
