package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by igors on 8/24/23
 */
@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * @param id - the methods the ID as a path variable from the path, provided in @RequestMappint annotation
     *           nave of variable in the @RequestMapping path "{id}" MUST equal to name of the method argument (id)
     *           so, Spring could handle multiple path variables
     * @param model
     * @return
     */
    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        //Sets a model-attribute "recipe" found by Id
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show"; //return relative path to the new show.html template
    }

    /**
     * @GetMapping or @PostMapping (or other types) says, that we only expect this type of HTTP method here
     * @param model
     * @return
     */
    @GetMapping
    @RequestMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform"; //returns "recipeform.html" file from the templates resource directory "recipe"
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeform"; //returns "recipeform.html" file from the templates resource directory "recipe"
    }

    /**
     *
     * @param command - @ModelAttribute will tell Spring to bind the form-post parameters to RecipeCommand object, the
     *                binding will happen automatically by the naming convention of the properties we're adding in the form
     *                for example:
     *                th:field="*{prepTime} form-attribute will be automatically mapped to the RecipeCommand.prepTime property
     * @return
     */
    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        //Use a redirect to a specific URL which points to the saved recipe object by it's ID
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id){
        log.debug("Deleting id: " + id);
        recipeService.deleteRecipeById(Long.valueOf(id));
        return "redirect:/";
    }

}