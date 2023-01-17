import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String DEFAULT_FILE_PATH = "C:\\Work\\TestTasks\\TestTask\\src\\main\\";
    public static UserController userController = new UserController();
    public static void main(String... args) throws IOException {
//        UserController userController = new UserController();
//        userController.collectUserData();

//        DataSorter dataSorter = new DataSorter(userController.getSortMode());
//
//        DataHandler dataHandler = new DataHandler(userController.getFileNames(), dataSorter, userController.getReplyName());
//        dataHandler.processFiles(userController.getDataType());

        DataSorter dataSorter = new DataSorter(true);

        List<String> list = new ArrayList<>();
        list.add("test1.txt");
        list.add("test2.txt");

//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Work\\TestTasks\\TestTask\\src\\main\\file.txt"));
//        bufferedWriter.write("dsfsc");
//        bufferedWriter.close();

        if(userController.getDataType()) {
            IntegerDataHandler dataHandler = new IntegerDataHandler(list, dataSorter, "file.txt");
            dataHandler.process();
        }
    }
}