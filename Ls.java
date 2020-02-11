import java.io.File;

class Ls {
    public static void main(String[] args) {
        String fpath = args.length < 1 ? "./" : args[0];
        File dir = new File(fpath);
        var files = dir.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}