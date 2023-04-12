package pro.sky.recipe.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface RecipeFilesService {
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    boolean cleanDataFile();

    boolean uploadDataFile(MultipartFile file);
    Path createTempFile(String suffix);
}