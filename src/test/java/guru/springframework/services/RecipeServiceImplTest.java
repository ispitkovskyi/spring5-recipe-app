package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    //Will inject instance of recipeRepository, when MockitoAnnotations.initMocks(this) is called
    @Mock
    RecipeRepository recipeRepository;


    @Before
    public void setUp() throws Exception {
        //Triggers injection of @Mock annotated instances
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipeByIdTest(){
        Long ID = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(ID);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe recipeReturned = recipeService.findById(ID);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet<>();
        recipesData.add(recipe);

        /** When recipeRepository.findAll() is called (it happens inside recipeService.getRecipes() method call)
         * Then return the recipesData hashset
         */
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1, recipes.size());
        /**Use Mockito to verify that recipeRepository instance will be used only 1 time
        * (when recipeService.getRecipes() is called above)

         IMPORTANT!!!:  the "findAll()" here is actually the NAME OF THE METHOD called inside the recipeRepository instance
            so, FORE EVERY OTHER INSTANCE TYPE THERE WILL BE NAME OF METHOD FROM THE CLASS IF THIS INSTANCE BEING VERIFIED
         */
        verify(recipeRepository, times(1)).findAll();
    }
}