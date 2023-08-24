package guru.springframework.services;

import guru.springframework.domain.Recipe;

import java.util.Set;

/**
 * Created by igors on 9/19/22
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);
}
