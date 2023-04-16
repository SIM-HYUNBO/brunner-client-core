package brunner.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BrunnerTableHeaderMouseListener extends MouseAdapter {

    private JBrunnerTable table;

    public BrunnerTableHeaderMouseListener(JBrunnerTable table) {
        this.table = table;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            table.triggerPopup(e);
    }
}
