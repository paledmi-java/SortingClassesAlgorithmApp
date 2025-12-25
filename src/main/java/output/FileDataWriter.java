package output;

import java.io.*;

public class FileDataWriter {

    private final File file = new File("savedData.txt");

    public void writeDataToFile(String data){
        if(file.exists() && file.isDirectory()){
            throw new RuntimeException("По пути сохранения находится директория");
        }

        if (!file.exists()) {
            try {
                if(!file.createNewFile()){
                    throw new RuntimeException("Не удалось создать файл");
                }
            } catch (IOException e) {
                throw new RuntimeException("Ошибка создания файла", e);
            }
        }

        try(Writer writer = new BufferedWriter(new FileWriter(file, true))){
            writer.append(data);
        } catch (IOException e){
            throw new RuntimeException("Ошибка записи в файл", e);
        }
    }
}
