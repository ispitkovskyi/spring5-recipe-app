package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    @Mock
    Model model;

    @Mock
    RecipeServiceImpl recipeService;

    IndexController indexController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void getIndexPage() {
        //given
        HashSet<Recipe> recipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipes.add(recipe1);
        recipes.add(recipe2);

        /**
        When method "getRecipes" is called inside Mockito-managed "recipesService" instance,
        return the Set of recipes we created above
         */
        when(recipeService.getRecipes()).thenReturn(recipes);

        /**
         * Create an interceptor for arguments of Set.class, passed to methods inside Mockito-managed instances
         */
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = indexController.getIndexPage(model);

        //then
        assertEquals("index", viewName);

        /** MOCKITO VERIFICATIONS
         * Assert number of times, when "getRecipes()" method in "recipeService" instance was called
         */
        verify(recipeService, times(1)).getRecipes();

        /** When we verify usage of a method WITH ARGUMENTS we need to specify argument matchers (eq, anySet, etc.)
         * 1st argument contains exact value passed to the "addAttribute" method, so the "eq" ArgumentMatchers is used
         * 2nd argument - any Set. The "anySet" is a ArgumentMatchers
         */
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());

        /** When we verify usage of a method WITH ARGUMENTS we also can specify an argument captor (interceptor, which catches argument)
         * Using ArgumentCaptor which intercepts arguments, passed to instances managed by Mockito
         * 1. Specify argument captor at the position of argument you want to capture
         * 2. Using the captor, get the argument value passed to a method in Mockito-managed instance
         */
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}