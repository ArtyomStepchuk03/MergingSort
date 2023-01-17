import java.util.*;
import java.util.List;

public class UserController {
    private final Scanner scanner = new Scanner(System.in);

    private boolean sortMode;     // True = -a, False = -d
    private boolean dataType;     // True = -i, False = -s
    private String replyName;     // The name of final file
    private List<String> fileNames;     // Names of initial files

    public void collectUserData() {
        sortMode = requestSortMode();
        dataType = requestDataType();
        replyName = requestReplyName();
        fileNames = requestFiles();
    }

    private boolean requestSortMode() {
        return scannerQuery("Выберите режим сортировки: -a или -d, где -a - по возрастанию, -d - по убыванию, или введите команду @skip, чтобы применить тип сортировки по умолчанию:",
                "-a", "-d");
    }

    private boolean requestDataType() {
        return scannerQuery("Выберите тип данных: -s или -i, где -s - строки, -i - числа: ",
                "-i", "-s");
    }

    private String requestReplyName() {
        System.out.println("Введите название отсортированного файла с разрешением: ");
        return scanner.next();
    }

    private List<String> requestFiles() {
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
        System.out.println("Ошибка сортировки! Файл предварительно отсортирован неверно! Введите \"y\", чтобы продолжить сортировку (файл будет отсортирован с ошибкой) или \"@finish\", чтобы завершить работу: ");
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

    public String getReplyName() {
        return replyName;
    }

    public List<String> getFileNames() {
        return fileNames;
    }
}
