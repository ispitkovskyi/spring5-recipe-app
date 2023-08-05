package guru.springframework.controllers;

import guru.springframework.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        String viewName = indexController.getIndexPage(model);
        assertEquals("index", viewName);

        /** MOCKITO VERIFICATIONS
         * Assert, that "getRecipes()" method from "recipeService" instance was called only 1 time
         */
        verify(recipeService, times(1)).getRecipes();

        /** Note, when we verify usage of a method WITH ARGUMENTS we need to speicfy argument matchers (eq, anySet, etc.)
         * 1st argument contains exact value passed to the "addAttribute" method, so the "eq" ArgumentMatchers is used
         * 2nd argument - any Set. The "anySet" is a ArgumentMatchers
         */
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}