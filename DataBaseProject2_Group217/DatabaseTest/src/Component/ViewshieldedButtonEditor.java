package Component;

import DataImport.*;
import View.Menu;

import javax.swing.*;
import java.awt.*;

public class ViewshieldedButtonEditor extends DefaultCellEditor {
    private String label;
    private JButton button;

    private String shielded;

    private String shielder;

    public ViewshieldedButtonEditor(JTextField textField) {
        super(textField);

        button = new JButton();
        button.setOpaque(true);
        button.addActionListener((e) -> {
            //fireEditingStopped();
            if (button.getText().startsWith("unshield")) {

                unshieldAction();
            } else {
                shieldAction();
            }

        });
    }

    private void unshieldAction() {
        this.shielder = Menu.user;
        this.shielded = button.getText().substring(9);
        String sql = "delete from shield where shielders= '" + shielder + "' and shielded = '" + shielded + "';";

        new DataImport().notQuerySentence(sql);
        button.setText("shield " + shielded);
    }

    private void shieldAction() {
        this.shielder = Menu.user;
        this.shielded = button.getText().substring(7);
        String sql = "insert into shield(shielders, shielded) values('" + shielder + "', '" + shielded + "');";
        new DataImport().notQuerySentence(sql);
        button.setText("unshield " + shielded);
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
