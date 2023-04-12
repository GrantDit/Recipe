package pro.sky.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String recipeName;
    private int time;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> instructions;
}
