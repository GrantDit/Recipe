package pro.sky.recipe.service;

import pro.sky.recipe.model.Recipe;

import java.util.Map;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);

    Map<Long, Recipe> getAllRecipes();

    Recipe getRecipe(long recipeNumber);

    Recipe editRecipe(long recipeNumber, Recipe recipe);

    boolean deleteRecipeById(long recipeNumber);

    void deleteAllRecipes();
}