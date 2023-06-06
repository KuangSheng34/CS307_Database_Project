package Component;

import View.Post;
import View.Reply;

import javax.swing.*;
import java.awt.*;

public class ViewButtonEditor extends DefaultCellEditor {

    private String label;
    private JButton button;

    public ViewButtonEditor(JTextField textField) {
        super(textField);

        button = new JButton();
        button.setOpaque(true);
        button.addActionListener((e) -> {
            //fireEditingStopped();
            if (getCellEditorValue().toString().startsWith("View Post")) {
                int id = Integer.parseInt(getCellEditorValue().toString().substring(9));
                new Post(id);
            } else {
                int id = Integer.parseInt(getCellEditorValue().toString().substring(10));
                new Reply(id);
            }
        });
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
