import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IntegerDataHandler {
    private final String resultName;
    private final List<String> incomingFiles;

    private BufferedWriter resultWriter;
    private boolean flag = false; // Is true when all files are looked
    private List<Integer> currentData = new ArrayList<>();
    private int filesQuantity;

    private Integer lastItem;

    public IntegerDataHandler(List<String> incomingFiles, String resultName) {
        this.resultName = resultName;
        this.incomingFiles = incomingFiles;
    }

    public void process() throws IOException {
        resultWriter = new BufferedWriter(new FileWriter(Main.DEFAULT_FILE_PATH + resultName));

        sortAndSave(findFiles(incomingFiles));

        resultWriter.close();
    }

    public List<Integer> collect(List<BufferedReader> readers, List<Integer> data) throws IOException {
        int nullCount = 0;
        for(BufferedReader reader : readers) {
            try {
                String line = reader.readLine();
                if(line == null) nullCount++;
                else if(!line.contains(" ")) {
                    try{
                        data.add(Integer.parseInt(line));
                    } catch (NumberFormatException e) {
                        System.out.println("Не все строки состоят из чисел! Такие строки будут удалены!");
                    }
                }
            } catch (OutOfMemoryError error) {
                System.out.println("Слишком большой объём строки! Файл не может быть обработан! Строка будет удалена.");
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

    public List<Integer> sortAndSave(List<BufferedReader> readers) throws IOException {
        List<Integer> data = new ArrayList<>();
        collect(readers, data);

        if(flag) {
            write(currentData);
            return null;
        }

        save(Main.dataSorter.sortInteger(data));

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
                return findFiles(Main.userController.getFileNames());
            }
        }
        return bufferedReaders;
    }

    private void save(List<Integer> data) throws IOException {
        if(currentData.isEmpty()) {
            currentData.addAll(data);
            lastItem = currentData.get(data.size()-1);
        }
        else {
            if(lastItem.compareTo(data.get(0)) > 0 == Main.dataSorter.sortMode()) {
                currentData.addAll(data);
                currentData = Main.dataSorter.sortInteger(currentData);
                lastItem = currentData.get(currentData.size()-1);
            } else {
                currentData.addAll(data);
                write(currentData);
                currentData.clear();
            }
        }
    }

    private void write(List<Integer> data) throws IOException {
        for(Integer integer : data) {
            resultWriter.write(integer);
            resultWriter.newLine();
        }
    }
}