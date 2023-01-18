import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StringDataHandler {
    private final String resultName;
    private final List<String> incomingFiles;

    private BufferedWriter resultWriter;
    private boolean flag = false; // Is true when all files are looked
    private List<String> currentData = new ArrayList<>();
    private int filesQuantity;

    private String lastItem;

    public StringDataHandler(List<String> incomingFiles, String resultName) {
        this.resultName = resultName;
        this.incomingFiles = incomingFiles;
    }

    public void process() throws IOException {
        compareDataTypes(findFiles(incomingFiles));

        resultWriter = new BufferedWriter(new FileWriter(Main.DEFAULT_FILE_PATH + resultName));

        sortAndSave(findFiles(incomingFiles));

        resultWriter.close();
    }

    public List<String> collect(List<BufferedReader> readers, List<String> data) throws IOException {
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

    public List<String> sortAndSave(List<BufferedReader> readers) throws IOException {
        List<String> data = new ArrayList<>();
        collect(readers, data);

        if(flag) {
            write(currentData);
            return null;
        }

        save(Main.dataSorter.sortString(data));

        return sortAndSave(readers);
    }

    public List<BufferedReader> findFiles(List<String> names) {
        filesQuantity = names.size();

        List<BufferedReader> bufferedReaders = new ArrayList<>();
        for(String name : names) {
            try {
                bufferedReaders.add(new BufferedReader(new FileReader(Main.DEFAULT_FILE_PATH + name)));
            } catch (FileNotFoundException e) {
                System.out.println("Введено неверное название файла! Попробуйте ещё раз: ");
                System.out.println();
                return findFiles(Main.userController.getFileNames());
            }
        }
        return bufferedReaders;
    }

    private void save(List<String> data) throws IOException {
        if(currentData.isEmpty()) {
            currentData.addAll(data);
            lastItem = currentData.get(data.size()-1);
        }
        else {
            if(lastItem.compareTo(data.get(0)) > 0 == Main.dataSorter.sortMode()) {
                if(!Main.userController.invalidSortedFile)
                    Main.userController.invalidSortedFile = Main.userController.completeSorting();
                currentData.addAll(data);
                currentData = Main.dataSorter.sortString(currentData);
                lastItem = currentData.get(currentData.size()-1);
            } else {
                currentData.addAll(data);
                write(currentData);
                currentData.clear();
            }
        }
    }

    private void write(List<String> strings) throws IOException {
        for(String string : strings) {
            resultWriter.write(string);
            resultWriter.newLine();
        }
    }

    private void compareDataTypes(List<BufferedReader> readers) throws IOException {
        boolean isDigit = true;
        for(BufferedReader reader : readers) {
            String line = reader.readLine();
            for (int i = 0; i < line.length(); i++) {
                if(!Character.isDigit(line.charAt(i))) {
                    isDigit = false;
                    break;
                }
            }
            if(isDigit) {
                System.out.println("Выбран неверный тип данных! Попробуйте ещё раз: ");
                if(!Main.userController.requestDataType())
                    break;
            } else {
                isDigit = true;
            }
        }
    }
}
