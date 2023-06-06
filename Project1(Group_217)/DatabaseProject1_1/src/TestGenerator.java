import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestGenerator {
    public static void main(String[] args) {
        File file = new File("resources//test.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 1000000; i++) {
                String s = i + "";
                bw.write(s);
                bw.write(";");
                bw.write("Shenzhen");
                bw.write(";");
                bw.write("China");
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
