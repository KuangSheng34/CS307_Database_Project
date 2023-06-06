package DataImport;

import Util.DBUtil;

import javax.swing.*;
import java.sql.*;

public class DatabaseManipulation implements DataManipulation {
    private Connection con;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private DBUtil util;

    public DatabaseManipulation(DBUtil util) {
        this.util = util;
    }

//    public List<String> findStationsByLine(int lineId) {
//        List<String> stations = new ArrayList<>();
//        String sql = "select ld.num, s.english_name, s.chinese_name\n" +
//                "from line_detail ld\n" +
//                "         join stations s on ld.station_id = s.station_id\n" +
//                "where ld.line_id = ? " +
//                "order by ld.num;";
//        try {
//            Thread.sleep(2000);
//            preparedStatement = con.prepareStatement(sql);
//            preparedStatement.setInt(1, lineId);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                stations.add(String.format("%d, %s, %s", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
//            }
//        } catch (SQLException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return stations;
//    }

    public void notQuerySentence(String sql) {
        try {
            preparedStatement =con.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(1);
        }
        System.out.println("Successfully " + sql);
    }

//    public List<List<Object>> querySentence(String sql) {
//        List<List<Object>> list = new ArrayList<>();
//        try {
//            preparedStatement = con.prepareStatement(sql,
//                    ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY  );
//            ResultSet rs = preparedStatement.executeQuery();
//            ResultSetMetaData metaData = rs.getMetaData();
//            rs.last();
//            int row = rs.getRow();
//            int column = metaData.getColumnCount();
//            rs.beforeFirst();
//            for (int i = 0; i < row; i++) {
//                list.add(new ArrayList<Object>());
//            }
//            int i = 0;
//            while (rs.next()) {
//                for (int j = 1; j <= column; j++) {
//                    list.get(i).add(rs.getObject(j));
//                }
//                i++;
//            }
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//            JOptionPane.showMessageDialog(null, e.getMessage());
//            System.exit(1);
//        }
//        System.out.println("Successfully " + sql);
//        return list;
//    }
//
//    public byte[] imageQuerySentence(String sql) {
//        byte[] image = null;
//        try {
//            preparedStatement = con.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//            if (rs.next()) {
//                image = rs.getBytes("image");
//            }
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            JOptionPane.showMessageDialog(null, e.getMessage());
//            System.exit(1);
//        }
//        System.out.println("Successfully " + sql);
//        return image;
//    }

    public void getConnection() {
        con = this.util.getConnection();
        System.out.println("------Thread " + Thread.currentThread().getId() + " visiting DB!------");
        System.out.println(this.util.getConnectState());

    }

    public void closeConnection() {
        this.util.closeConnection(con, preparedStatement, resultSet);
        System.out.println("------Thread " + Thread.currentThread().getId() + " close DB!------");
    }


}
