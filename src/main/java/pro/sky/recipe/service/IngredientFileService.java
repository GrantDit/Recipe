package pro.sky.recipe.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IngredientFileService {
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    boolean cleanDataFile();

    boolean uploadDataFile(MultipartFile file);
}