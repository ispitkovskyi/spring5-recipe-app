package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by igors on 8/27/23
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    final NotesToNotesCommand notesConverter;
    final CategoryToCategoryCommand categoryConverter;
    final IngredientToIngredientCommand ingredientConverter;

    public RecipeToRecipeCommand(NotesToNotesCommand notesConverter, CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source==null) {
            return null;
        }

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setNotes(notesConverter.convert(source.getNotes()));

        if(source.getCategories()!=null && source.getCategories().size()>0){
            source.getCategories().stream()
                    .forEach(categoryCommand -> recipeCommand.getCategories().add(categoryConverter.convert(categoryCommand)));
        }

        if(source.getIngredients()!=null && source.getIngredients().size()>0){
            source.getIngredients().stream()
                    .forEach(ingredientCommand -> recipeCommand.getIngredients().add(ingredientConverter.convert(ingredientCommand)));
        }

        return recipeCommand;
    }
}
