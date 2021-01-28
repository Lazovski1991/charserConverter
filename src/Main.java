import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) throws IOException {

        File fileIn = new File("/home/alexander/Рабочий стол/EE/аннотации.txt");
        String charsetIn = "Windows-1251";
        String charsetOut = "UTF-8";
        File folderOut = new File("/home/alexander/Рабочий стол/EE/1");



        if (fileIn.isDirectory()) {
            convertFolder(fileIn, folderOut, charsetIn, charsetOut);
        } else {
            convertFiles(fileIn, folderOut, charsetIn, charsetOut);
        }

    }

    private static void convertFiles(File fileIn, File folderOut, String charsetIn, String charsetOut) throws IOException {
        String slash = File.separator;
        FileReader reader = new FileReader(fileIn, Charset.forName(charsetIn));
        File fileOut = new File(folderOut.getAbsolutePath() + slash + fileIn.getName());
        FileWriter writer = new FileWriter(fileOut, Charset.forName(charsetOut));
        String line = null;
        try (BufferedReader bufferedReader = new BufferedReader(reader);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line + "\n");
            }
        }
    }

    private static void convertFolder(File folderIn, File folderOut, String charsetIn, String charsetOut) throws IOException {
        List<File> filesInFolder = Files.walk(Paths.get(folderIn.getPath()))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        for (File file : filesInFolder) {
            convertFiles(file, folderOut, charsetIn, charsetOut);
        }
    }
}