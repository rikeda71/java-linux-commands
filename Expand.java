import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

class Expand {

    private static int parseTabNum = -1;
    private static boolean nextNumFlag = false;
    private static String regex = "^[0-9]+$";
    private static final Pattern p = Pattern.compile(regex);
    private static String fname;

    private static boolean isNumber(String str) {
        return p.matcher(str).find();
    }

    private static void showExampleAndExit() {
        System.out.println("ex.) java Expand -t number filePath");
        System.exit(1);
    }

    private static void showExpandOutput(String fname) {
        Path fp = Paths.get(fname);
        if (!Files.exists(fp)) {
            System.out.println(fp + " doesn't exist");
            System.exit(2);
        }
        try {
            List<String> textList = Files.readAllLines(fp);
            String space = "";
            for (int i = 0; i < parseTabNum; i++) {
                space += " ";
            }
            for (String text : textList) {
                System.out.println(text.replaceAll("\t", space));
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("you must set arguments");
            showExampleAndExit();
        }
        for (String arg : args) {
            if (nextNumFlag) {
                if (isNumber(arg)) {
                    parseTabNum = Integer.parseInt(arg);
                    nextNumFlag = false;
                } else {
                    System.out.println("argumentation error");
                    showExampleAndExit();
                }
            } else if ("-t".equals(arg)) {
                System.out.println(" a  ");
                nextNumFlag = true;
            } else {
                fname = arg;
            }
        }
        showExpandOutput(fname);
    }
}