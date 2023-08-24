package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by igors on 8/24/23
 */
@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    /**
     * @param id - the methods the ID as a path variable from the path, provided in @RequestMappint annotation
     *           nave of variable in the @RequestMapping path "{id}" MUST equal to name of the method argument (id)
     *           so, Spring could handle multiple path variables
     * @param model
     * @return
     */
    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model){
        //Sets a model-attribute "recipe" found by Id
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        //return relative path to the new show.html template
        return "recipe/show";
    }
}
