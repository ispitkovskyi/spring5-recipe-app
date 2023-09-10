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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    final NotesCommandToNotes notesConverter;
    final CategoryCommandToCategory categoryConverter;
    final IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, CategoryCommandToCategory categoryConverter, IngredientCommandToIngredient ingredientConverter) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source==null) {
            return null;
        }

        Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setUrl(source.getUrl());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setDirections(source.getDirections());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if(source.getCategories()!=null && source.getCategories().size()>0){
            source.getCategories().stream()
                    .forEach(categoryCommand -> recipe.getCategories().add(categoryConverter.convert(categoryCommand)));
        }

        if(source.getIngredients()!=null && source.getIngredients().size()>0){
            source.getIngredients().stream()
                    .forEach(ingredientCommand -> recipe.getIngredients().add(ingredientConverter.convert(ingredientCommand)));
        }

        return recipe;
    }
}
