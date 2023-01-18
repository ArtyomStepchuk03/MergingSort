import java.io.IOException;

public class Main {
    public static String DEFAULT_FILE_PATH = "C:\\Work\\TestTasks\\TestTask\\src\\main\\";
    public static UserController userController = new UserController();
    public static DataSorter dataSorter;
    public static void main(String... args) throws IOException {
        userController.getParams();
        dataSorter = new DataSorter(userController.getSortMode());

        if(userController.getDataType()) {
            IntegerDataHandler dataHandler = new IntegerDataHandler(userController.getFileNames(), userController.getResultName());
            dataHandler.process();
        } else {
            StringDataHandler dataHandler = new StringDataHandler(userController.getFileNames(), userController.getResultName());
            dataHandler.process();
        }

        System.out.println("Сортировка завершена!");
    }
}