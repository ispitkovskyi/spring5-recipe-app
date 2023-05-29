package guru.springframework.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by igors on 9/18/22
 */
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    /**
     * "mappedBy" defines relationship:    FROM Recipe TO Ingredient
     * mappedBy = "recipe" means that there will be "recipe" property (variable) inside Ingredient class
     * all the ingredients coming back will be stored inside the Set
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    /**
     * JPA will create this as a BLOB (Bytes LOB) field inside a database
     */
    @Lob
    private Byte[] image;

    /**
     * EnumType.ORDINAL - the default behavior (based on ORDER of elements in the enum),
     * but it means that position of each enumeration value persists in database.
     * So, IF you want to insert another value into the Enum's list of values somewhere in the middle of initial list, like:
     * EASY, MODERATE, HARD --->>> EASY, MODERATE, KIND_OF_HARD, HARD
     * THEN your data will be messed up, because new KIND_OF_HARD value appears at the position #3, where HARD value used to be
     * That will make your data messed up
     * That's why we use here EnumType.STRING type
     */
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    /**
     * Cascade makes the Recipe and OWNER of Notes.
     * Also if we delete Recipe, it automatically will delete related Notes
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category", //table will be created with name "RECIPE_CATEGORY" joining below columns
        joinColumns = @JoinColumn(name = "recipe_id"), //From this side of the relationship we'll have column RECIPE_ID
            inverseJoinColumns = @JoinColumn(name = "category_id")) //on the other side (on Categories id) we'll have CATEGORY_ID
    //NOTE, you can see names of id-columns ("recipe_id", "category_id" using H2 db web console: http://localhost:8080/h2-console
    //RECIPE_CATEGORY table will be created in the DB
    private Set<Category> categories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
        return this;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
