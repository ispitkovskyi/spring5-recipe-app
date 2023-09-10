package guru.springframework.commands;

import guru.springframework.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by igors on 8/27/23
 */
@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    /**
     * IMPORTANT!!!
     * It is critical to keep names of the variables matching to (SAME AS) names of variables in the respective Recipe class
     * Because, when we pass RecipeCommand instance to the thymeleaf template "recipeform.html" ->
     *  -> see "RecipeController.newRecipe(Model model)" method
     * the thymeleaf will try to bind attributes to names of the variables in provided instance
     * th:field="*{notes.recipeNotes}"
     * So, if you have "notesCommand" name instead of "notes", there will be parsing error while opening "/recipe/new" url
     */
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;     //notesCommand; - IT WILL CAUSE THYMELEAF PARSING ERROR
    private Set<CategoryCommand> categories = new HashSet<>();
}
