package pro.sky.recipe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipe.model.Recipe;
import pro.sky.recipe.service.RecipeService;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Recipes", description = "CRUD-операций и работы с рецептами")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @PostMapping
    @Operation(
            summary = "Создание нового рецепта",
            description = "Создание нового рецепта с его номером"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "рецепт успешно создан",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe createRecipe = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(createRecipe);
    }
    @GetMapping("/{recipeNumber}")
    @Operation(
            summary = "Найти рецепт по его номеру",
            description = "Поиск по номеру рецепта"
    )
    @Parameters(value = {
            @Parameter(name = "recipeNumber", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> getRecipe(@PathVariable long recipeNumber) {
        Recipe recipe = recipeService.getRecipe(recipeNumber);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
    public ResponseEntity<Map<Long, Recipe>> getAllRecipes() {
        Map<Long, Recipe> recipes = recipeService.getAllRecipes();
        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @PutMapping("/{recipeNumber}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable long recipeNumber, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.editRecipe(recipeNumber, recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe1);
    }
    @PutMapping("/{recipeNumber}")
    @Operation(
            summary = "Обновить рецепт по его номеру",
            description = "Поиск по номеру рецепта для его обновления"
    )
    @Parameters(value = {
            @Parameter(name = "recipeNumber", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно обновлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteRecipeById(@PathVariable long recipeNumber) {
        if (recipeService.deleteRecipeById(recipeNumber)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping
    @Operation(
            summary = "Удалить рецепт по его номеру",
            description = "Удалить все рецепты"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "рецепт успешно удален"
            )
    })
    public ResponseEntity<Void> deleteAllRecipes() {
        recipeService.deleteAllRecipes();
        return ResponseEntity.ok().build();
    }
}