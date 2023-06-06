package DataImport;

public interface DataManipulation {

    public void getConnection();
    public void closeConnection();
    public void notQuerySentence(String sql);
//    public List<List<Object>> querySentence(String sql);
//    public byte[] imageQuerySentence(String sql);

}
