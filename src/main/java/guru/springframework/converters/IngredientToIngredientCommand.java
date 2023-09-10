package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by igors on 8/27/23
 */
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter){
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient == null) {
            return null;
        }

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setUom(uomConverter.convert(ingredient.getUom()));

        return ingredientCommand;
    }
}
