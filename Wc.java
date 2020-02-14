import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Wc {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("you must set an argument");
            return;
        }
        String fname = args[0];
        Path fp = Paths.get(fname);
        if (!Files.exists(fp)) {
            System.out.println(fname + " doesn't exist");
            return;
        }
        try {
            var textList = Files.readAllLines(fp);
            System.out.println(textList.size());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}