package pro.sky.recipe.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import pro.sky.recipe.model.Recipe;
import pro.sky.recipe.service.RecipeFilesService;
import pro.sky.recipe.service.RecipeService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static Map<Long, Recipe> recipes = new HashMap<>();
    private static long recipeNumber = 0;
    private final RecipeFilesService recipeFilesService;

    public RecipeServiceImpl(RecipeFilesService recipeFilesService) {
        this.recipeFilesService = recipeFilesService;
    }

    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipes.putIfAbsent(recipeNumber++, recipe);
        saveToFile();
        return recipe;
    }

    @Override
    public Map<Long, Recipe> getAllRecipes() {
        ObjectUtils.isNotEmpty(recipes);
        return recipes;
    }

    @Override
    public Recipe getRecipe(long recipeNumber) {
        ObjectUtils.isNotEmpty(recipes);
        return recipes.get(recipeNumber);
    }

    @Override
    public Recipe editRecipe(long recipeNumber, Recipe recipe) {
        ObjectUtils.isNotEmpty(recipe);
        if (recipes.containsKey(recipeNumber)) {
            recipes.put(recipeNumber, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipeById(long recipeNumber) {
        ObjectUtils.isNotEmpty(recipes);
        if (recipes.containsKey(recipeNumber)) {
            recipes.remove(recipeNumber);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public void deleteAllRecipes() {
        recipes = new HashMap<>();
        saveToFile();
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            recipeFilesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = recipeFilesService.readFromFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path createTextDataFile() throws IOException {
        Path path = recipeFilesService.createTempFile("recipesDataFile");
        for (Recipe recipe : recipes.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(recipe.getRecipeName()).append("\n \n").append("Cooking time: ").append(String.valueOf(recipe.getInstructions())).append(" minutes.").append("\n");
                writer.append("\n");
                writer.append("Ingredients: \n \n");
                recipe.getIngredients().forEach(ingredient -> {
                    try {
                        writer.append(" - ").append(ingredient.getIngredientName()).append(" - ").append(String.valueOf(ingredient.getTime())).append(" ").append(ingredient.getUnit()).append("\n \n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.append("\n");
                writer.append("Cooking instructions: \n \n");
                recipe.getInstructions().forEach(step -> {
                    try {
                        writer.append(" > ").append(step).append("\n \n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.append("\n \n");
            }
        }
        return path;
    }
}
