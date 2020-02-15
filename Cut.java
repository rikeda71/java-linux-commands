import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class Cut {

    private static int mode = 0; // 1: char, 2: field
    private static String delim = "";
    private static boolean nextDelimFlag = false;
    private static boolean showNoIncludeDelimSentence = false;
    private static int startNum = 0;
    private static int endNum = 0;
    private static final String regex = "^[0-9]*-[0-9]*$";
    private static final Pattern p = Pattern.compile(regex);

    private static void showUsageAndExit() {
        System.out.println("usage: java Cut -c list [file ...]");
        System.out.println("       java Cut -f list [-d delim] [file ...]");
        System.exit(1);
    }

    private static void showCuttedOutput(Path fp) {
        try {
            List<String> textList = Files.readAllLines(fp);
            if (mode == 1) { // char unit
                for (String text : textList) {
                    int firstIdx = startNum >= text.length() ? text.length() : startNum;
                    int lastIdx = endNum >= text.length() ? text.length() : endNum;
                    System.out.println(text.substring(firstIdx, lastIdx));
                }
            } else if (mode == 2) { // delim unit
                for (String text : textList) {
                    String[] columns = text.split(delim);
                    if (!showNoIncludeDelimSentence && text.contains(delim)) {
                        continue;
                    }
                    int firstIdx = startNum >= columns.length ? columns.length : startNum;
                    int lastIdx = endNum >= columns.length ? columns.length : endNum;
                    for (int i = firstIdx; i < lastIdx; i++) {
                        System.out.print(columns[i]);
                        if (i + 1 <= lastIdx) {
                            System.out.println("");
                        } else {
                            System.out.print(delim);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        if (args.length < 3 || (args[0].equals("-c") && args[0].equals("-f"))) {
            showUsageAndExit();
        } else if (args[0].equals("-c")) {
            mode = 1;
        } else if (args[0].equals("-f")) {
            mode = 2;
        }

        // processing list
        if (!Expand.isNumber(args[1]) && !p.matcher(args[1]).find()) {
            System.out.println("java Cut: [-cf] list: illegal list value");
            System.exit(2);
        } else if (Expand.isNumber(args[1])) {
            startNum = Integer.parseInt(args[1]) - 1;
            endNum = Integer.parseInt(args[1]);
        } else {
            String[] nums = args[1].split("-");
            if (Expand.isNumber(nums[0])) {
                startNum = Integer.parseInt(nums[0]) - 1;
            }
            if (nums.length > 1 && Expand.isNumber(nums[1])) {
                endNum = Integer.parseInt(nums[1]) - 1;
            }
        }

        String fname = "";
        for (int i = 2; i < args.length; i++) {
            var arg = args[i];
            if (nextDelimFlag) {
                delim = arg;
                nextDelimFlag = false;
            } else if (arg.equals("-d")) {
                nextDelimFlag = true;
            } else if (arg.equals("-s")) {
                showNoIncludeDelimSentence = true;
            } else {
                fname = arg;
            }
        }
        Path fp = Paths.get(fname);
        if (!Files.exists(fp)) {
            System.out.println(fp + " doesn't exist");
            System.exit(3);
        }
        showCuttedOutput(fp);
    }
}