package interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public interface DataHandler {
    List<BufferedReader> findFiles();
    List<?> collect() throws IOException;
    void process() throws IOException;
    List<?> sortAndSave() throws IOException;
    void save() throws IOException;
}
