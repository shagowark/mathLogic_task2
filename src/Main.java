
public class Main {

    public static void main(String[] args) throws Exception{

        if (args.length == 0) {
            System.err.println("Укажите input и output файлы");
        } else {
            runInConsole(args);
        }
    }

    private static void runInConsole (String[] args) throws Exception {
        CmdLineArgsParser argsParser = new CmdLineArgsParser(args);

        String inputFilePath = argsParser.getArgumentValue("-i", "--input-file");
        String outputFilePath = argsParser.getArgumentValue("-o", "--output-file");


        Logic.getResult(inputFilePath, outputFilePath);
    }
}
