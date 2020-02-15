import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class Ls {

    private static ArrayList<String> path = new ArrayList<>();
    private static boolean isAll = false;
    private static boolean isVertical = false;
    private static boolean isReverse = false;

    public static void main(String[] args) {

        for (String arg : args) {
            if (arg.contains("-")) {
                flagParsing(arg);
            } else {
                path.add(arg);
            }
        }

        if (args.length < 1 || path.size() == 0) {
            showFiles("./");
            return;
        }

        String refPath;
        for (int i = 0; i < path.size(); i++) {
            refPath = path.get(i);
            System.out.println(refPath);
            showFiles(refPath);
        }

    }

    private static void showFiles(String path) {
        File dir = new File(path);
        var files = dir.listFiles();
        ArrayList<String> showStrs = new ArrayList<>();
        String showStr;
        for (File file : files) {
            showStr = file.getName();
            if (showStr.charAt(0) == '.' && !isAll)
                continue;
            showStrs.add(showStr);
        }
        Collections.sort(showStrs);
        if (isReverse) {
            Collections.reverse(showStrs);
        }
        if (isVertical) {
            for (String str : showStrs) {
                System.out.println(str);
            }
        } else {
            ListIterator<String> li = showStrs.listIterator();
            while (li.hasNext()) {
                System.out.print(li.next());
                if (li.nextIndex() % 5 == 0) {
                    System.out.println("");
                } else if (li.hasNext()) {
                    System.out.print("\t");
                }
            }
        }
    }

    private static void flagParsing(String flagStr) {
        if (flagStr.equals("-a") || flagStr.equals("--all")) {
            isAll = true;
        } else if (flagStr.equals("-1") || flagStr.equals("--format=single-column")) {
            isVertical = true;
        } else if (flagStr.equals("-r") || flagStr.equals("--reverse")) {
            isReverse = true;
        }
    }
}