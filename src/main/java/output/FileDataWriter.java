package output;

import java.io.*;

public class FileDataWriter {

    private final File file = new File("savedData.txt");

    public void writeDataToFile(String data){
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(Writer writer = new BufferedWriter(new FileWriter(file, true))){
            writer.append(data);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
