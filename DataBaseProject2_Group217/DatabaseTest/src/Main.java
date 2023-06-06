
import View.Menu;

import javax.swing.*;

public class


Main {

    public static void main(String[] args) {
//        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("GMT+8"));
//        long timeStampMillis = zonedDateTime.toInstant().toEpochMilli();
//        System.out.println("Current timestamp in GMT+8: " + timeStampMillis);
//
//0
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu();
        });
    }
}
