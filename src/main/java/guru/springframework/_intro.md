<h1>Program abstractions layering</h1>

<ul>
<li>Controller (IndexController.java)</li> -> Level: connector between UI and logic in Service layer
<li>------ Service (RecipeServiceImpl.java)</li> -> Level: wrapper over methods in repository
<li>------------ Repository (RecipeRepository.java)</li> -> Level: operations with database data
<li>------------------------ Model (Recipe.java)</li> -> Level: database entries level
</ul>