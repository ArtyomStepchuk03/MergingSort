import java.util.*;
import java.util.List;

public class UserController {
    private final Scanner scanner = new Scanner(System.in);

    private boolean sortMode;     // True = -a, False = -d
    private boolean dataType;     // True = -i, False = -s
    private String resultName;     // The name of final file
    private List<String> fileNames;     // Names of initiated files
    public boolean invalidSortedFile = false;     // Flag: true, when file was sorted incorrectly
    public boolean invalidDataType = false;

    public void getParams() {
        sortMode = requestSortMode();
        dataType = requestDataType();
        resultName = requestResultName();
        fileNames = requestFiles();
    }

    public boolean requestSortMode() {
        return scannerQuery("Выберите режим сортировки: -a или -d, где -a - по возрастанию, -d - по убыванию, или введите команду @skip, чтобы применить тип сортировки по умолчанию:",
                "-a", "-d");
    }

    public boolean requestDataType() {
        return scannerQuery("Выберите тип данных: -s или -i, где -s - строки, -i - числа: ",
                "-i", "-s");
    }

    public String requestResultName() {
        System.out.println("Введите название отсортированного файла с разрешением: ");
        return scanner.next();
    }

    public List<String> requestFiles() {
        try {
            fileNames.clear();
        } catch (NullPointerException ignored){}
        System.out.println("Введите названия обрабатываемых файлов с разрешением и команду \"@start\", чтобы начать сортировку: ");
        List<String> introducedFiles = new ArrayList<>();
        while(scanner.hasNext()) {
            String line = scanner.next();
            if(line.equals("@start"))
                break;
            else
                introducedFiles.add(line);
        }
        return introducedFiles;
    }

    public boolean completeSorting() {
        System.out.println("Ошибка сортировки! Файл предварительно отсортирован неверно! Введите \"y\", чтобы продолжить сортировку (займёт значительно больше времени) или \"@finish\", чтобы завершить работу: ");
        String line = scanner.next();

        if(line.equals("y"))
            return true;
        if(line.equals("@finish"))
            finish();

        System.out.println("Введён неверный символ!");
        return completeSorting();
    }

    private boolean scannerQuery(String query, String firstCondition, String secondCondition) {
        System.out.println(query);
        String line = scanner.next();

        if(line.equals("@finish")) // Exit check
            finish();

        if(line.equals(firstCondition) || (firstCondition.equals("-a") && line.equals("@skip"))) // First condition check
            return true;

        if(line.equals(secondCondition)) // Second condition check
            return false;

        System.out.println("Введён неверный символ!"); // If no condition satisfied
        return scannerQuery(query, firstCondition, secondCondition);
    }

    private void finish() {
        System.out.println("Завершение работы...");
        scanner.close();
        System.exit(0);
    }

    public boolean getSortMode() {
        return sortMode;
    }

    public boolean getDataType() {
        return dataType;
    }

    public String getResultName() {
        return resultName;
    }

    public List<String> getFileNames() {
        return fileNames;
    }
}
