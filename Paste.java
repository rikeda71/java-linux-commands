import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Paste {

    private static String delim = "";
    private static boolean columnToRow = false;
    private static boolean nextDelim = false;

    private static void showUsageAndExit() {
        System.out.println("usage: Java Paste [-s] [-d delimiters] file ...");
        System.exit(1);
    }

    private static void showPastedOutput(List<String> files) {

        ArrayList<List<String>> texts = new ArrayList<List<String>>();
        int maxLength = 0;

        for (int i = 0; i < files.size(); i++) {
            String file = files.get(i);
            Path fp = Paths.get((file));
            if (!Files.exists(fp)) {
                System.out.println((String) file + " doesn't exist");
                System.exit(2);
            } else {
                try {
                    var text = Files.readAllLines(fp);
                    maxLength = text.size() > maxLength ? text.size() : maxLength;
                    texts.add(text);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }

        if (columnToRow) {
            for (int i = 0; i < texts.size(); i++) {
                System.out.println(String.join(delim, texts.get(i)));
            }
        } else {
            for (int i = 0; i < maxLength; i++) {
                for (int j = 0; j < texts.size(); j++) {
                    if (i < texts.get(j).size()) {
                        System.out.print(texts.get(j).get(i));
                    }
                    if (j + 1 < texts.size()) {
                        System.out.print(delim);
                    } else {
                        System.out.println("");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            showUsageAndExit();
        }
        ArrayList<String> files = new ArrayList<>();
        for (String arg : args) {
            if (arg.equals("-s")) {
                columnToRow = true;
            } else if (nextDelim) {
                delim = arg;
                nextDelim = false;
            } else if (arg.equals("-d")) {
                nextDelim = true;
            } else {
                files.add(arg);
            }
        }

        showPastedOutput(files);
    }
}
