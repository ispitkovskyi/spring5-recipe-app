package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by igors on 9/18/22
 */
//Using @Getter/@Setter lombok annotations instead of @Data to avoid
// springboot app Method threw 'java.lang.StackOverflowError' exception. Cannot evaluate toString()
@Getter
@Setter
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
    /**
     * JPA will create this as a BLOB (Bytes LOB) field inside a database - when more than 256 characters are expected
     * to be stored in the field
     */
    @Lob
    private Byte[] image;
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

    /**
     * By default the Springboot creates 2 tables in the DB:  CATEGORY_RECIPES and RECIPE_CATEGORIES, which is NOT what we need
     * To fis this, we need to configure Springboot to create a single table, joining the aforementioned tables by
     * "recipe_id" and "category_id" fields.
     * That will create a single RECIPE_CATEGORY table (as it's name is specified in "name" attribute of @JoinTable annotation -
     * instead of two tables - "recipe_category"). And this table will have 2 columns "RECIPE_ID", "CATEGORY_ID" (as it is
     * specified in "joinColumns" attributes.
     *
     * NOTE, you can see names of id-columns ("recipe_id", "category_id" using H2 db web console: http://localhost:8080/h2-console
     * H2 db credentials:
     *  jdbc:h2:mem:testdbd
     *  [user "sa"]
     *  [empty password]
     */
    @ManyToMany
    @JoinTable(name = "recipe_category", //table will be created with name "RECIPE_CATEGORY" joining below columns
        joinColumns = @JoinColumn(name = "recipe_id"), //From this side of the relationship we'll have column RECIPE_ID
            inverseJoinColumns = @JoinColumn(name = "category_id")) //on the other side (on Categories id) we'll have CATEGORY_ID
    private Set<Category> categories = new HashSet<>();

    /**
     * Adds Ingredient to list of ingredients in Recipe, AND
     * sets id of Recipe into RECIPE_ID column in INGREDIENT table
     * @param ingredient
     * @return
     */
    public Recipe addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
        return this;
    }

    /**
     * BIDIRECTIONAL RELATIONSHIP: sets vice versa, id of Recipe into ID column in NOTES table
     * @param notes
     */
    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }
}
