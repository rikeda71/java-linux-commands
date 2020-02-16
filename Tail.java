import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tail {

    private static String fname = "";
    private static int n = 10;
    private static boolean nextNum = false;

    private static void showUsageAndExit() {
        System.out.println("usage: java Tail [-n lines] [file ...]");
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            showUsageAndExit();
        }
        for (String arg : args) {
            if (nextNum) {
                if (Expand.isNumber(arg)) {
                    n = Integer.parseInt(arg);
                } else {
                    System.out.println("`lines` must be number in `-n lines`");
                    showUsageAndExit();
                }
                nextNum = false;
            } else if (arg.equals("-n")) {
                nextNum = true;
            } else {
                fname = arg;
            }
        }
        Path fp = Paths.get(fname);
        if (!Files.exists(fp)) {
            System.out.println(fname + " doesn't exist");
            System.exit(2);
        }
        try {
            var texts = Files.readAllLines(fp);
            int firstIdx = texts.size() < n ? texts.size() : n;
            firstIdx = texts.size() - firstIdx;
            for (int i = firstIdx; i < texts.size(); i++) {
                System.out.println(texts.get(i));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}