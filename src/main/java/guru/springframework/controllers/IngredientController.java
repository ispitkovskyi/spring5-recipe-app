package guru.springframework.controllers;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by igors on 9/24/23
 */
@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    /** URL associated with View button in the recipe/show.html sends request which comes to this controller's endpoint:
     * <a class="btn btn-default" href="#" th:href="@{'/recipe/' + ${recipe.id} + '/ingredients'}" role="button">View</a>
     */
    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model){
        log.debug("Getting ingredient list for recipe id: " + id);
        //use command-object (RecipeCommand) to avoid lazy load errors in Thymeleaf.
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }

    /**
     * URL associated with View button in the recipe/ingredient/list.html sends request which comes to this controller's endpoint:
     * <a href="#" th:href="@{'/recipe/' + ${recipe.id} + '/ingredient/' + ${ingredient.id} + '/show'}">View</a>
     */
    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model){
        log.debug("Getting ingredient list for recipe id: " + recipeId + " and ingredient id: " + id);
        //use command-object (RecipeCommand) to avoid lazy load errors in Thymeleaf.
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }

}
