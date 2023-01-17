import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StringDataHandler {
    private final String resultName;
    private final List<String> incomingFiles;
    private final String DEFAULT_FILE_PATH = "C:\\Work\\TestTasks\\TestTask\\src\\main\\";

    private final DataSorter dataSorter;

    private BufferedWriter resultWriter;
    private boolean flag = false; // Is true when all files are looked
    public static boolean invalidSortedFlag = false;
    private List<String> currentData = new ArrayList<>();
    private int filesQuantity;

    private String lastItem;

    public StringDataHandler(List<String> incomingFiles, DataSorter dataSorter, String resultName) {
        this.resultName = resultName;
        this.incomingFiles = incomingFiles;
        this.dataSorter = dataSorter;
    }

    private List<BufferedReader> findFiles(List<String> names) {
        filesQuantity = names.size();

        List<BufferedReader> bufferedReaders = new ArrayList<>();
        for(String name : names) {
            try {
                bufferedReaders.add(new BufferedReader(new FileReader(DEFAULT_FILE_PATH + name)));
            } catch (FileNotFoundException e) {
                System.out.println("Введено неверное название файла! Попробуйте ещё раз: ");
                System.out.println();
                return findFiles(Main.userController.getFileNames());
            }
        }
        return bufferedReaders;
    }

    public void process() throws IOException {
        resultWriter = new BufferedWriter(new FileWriter(DEFAULT_FILE_PATH + resultName));

        sortAndSave(findFiles(incomingFiles));

        resultWriter.close();
    }
    // TODO Rename methods
    private List<String> sortAndSave(List<BufferedReader> readers) throws IOException {
        List<String> data = new ArrayList<>();
        collect(readers, data);

        if(flag) {
            writeInFile(currentData);
            return null;
        }

        checkDataAndSave(dataSorter.sortString(data));

        return sortAndSave(readers);
    }

    private void checkDataAndSave(List<String> data) throws IOException {
        if(currentData.isEmpty()) {
            currentData.addAll(data);
            lastItem = currentData.get(filesQuantity-1);
        }
        else {
            if(lastItem.compareTo(data.get(0)) > 0 == dataSorter.sortMode()) {
                repeatSorting(data);
                lastItem = data.get(filesQuantity-1);
            } else {
                currentData.addAll(data);
                writeInFile(currentData);
                currentData.clear();
            }
        }
    }

    private void repeatSorting(List<String> data) {
        currentData.addAll(data);
        currentData = dataSorter.sortString(currentData);
    }

    private void writeInFile(List<String> strings) throws IOException {
        for(String string : strings) {
            resultWriter.write(string);
            resultWriter.newLine();
        }
//        currentData.clear();
    }

    private List<String> collect(List<BufferedReader> readers, List<String> data) throws IOException {
        int nullCount = 0;
        for(BufferedReader reader : readers) {
            String line = reader.readLine();
            try {
                if(line == null) nullCount++;
                else if(!line.contains(" ")) data.add(line);
            } catch (OutOfMemoryError error) {
                System.out.println("Слишком большой объём строки. Файл не может быть обработан!");
            }
        }
        int size = data.size();
        if(nullCount == filesQuantity) {
            flag = true;
            return null;
        }
        if(size < filesQuantity && !flag) return collect(readers, data);

        return data;
    }
}
