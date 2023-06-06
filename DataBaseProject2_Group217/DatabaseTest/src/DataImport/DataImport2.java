package DataImport;

import Util.ProxoolUtil;

public class DataImport2 {
    public DataImport2() {}

    public void notQuerySentence(String sql) {
        new Thread(() -> {
            DataManipulation dm = new DatabaseManipulation(ProxoolUtil.getInstance());
            dm.getConnection();
            dm.notQuerySentence(sql);
            dm.closeConnection();
        }).start();
    }
}
