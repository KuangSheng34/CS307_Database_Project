package Component;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ViewButtonRenderer extends JButton implements TableCellRenderer {

    public ViewButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}
