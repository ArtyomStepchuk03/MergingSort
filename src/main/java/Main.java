import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
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

        StringDataHandler dataHandler = new StringDataHandler(list, dataSorter, "file.txt");
        dataHandler.process();
    }
}