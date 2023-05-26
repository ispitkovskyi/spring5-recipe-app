package guru.springframework.domain;

import javax.persistence.*;

/**
 * Created by igors on 9/18/22
 */
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * No Cascade here means, that if we delete Notes, we do NOT want to automatically will delete related Recipe
     * Recipe owns Notes, not vice versa, so no need in Cascade here
     */
    @OneToOne
    private Recipe recipe;

    /**
     * Allow strings longer than 256 symbols. JPA will store this in a CLOB database field
     * JPA will create this as a CLOB (Character LOB) field inside a database
     */
    @Lob
    private String recipeNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNotes() {
        return recipeNotes;
    }

    public void setRecipeNotes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }
}
