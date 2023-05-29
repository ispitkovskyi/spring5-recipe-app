package guru.springframework.domain;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by igors on 9/18/22
 */
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;

    /**
     * Because we dot NOT need to go FROM a UnitOfMeasure to Ingredient - this is a UNIDIRECTIONAL relationship,
     * which means we do NOT set @OneToOne annotation over "recipe" property in the UnitOfMeasure
     *
     * FetchType.EAGER makes Hibernate fetching this OneToOne to get from database every time
     *
     * No CASCADE operations here, because we do NOT want to delete a UnitOfMeasure, when we delete an ingredient
     */
    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure uom;

    /**
     * no cascade here, because we don't want to delete a parent recipe object, when ingredient is deleted
     */
    @ManyToOne
    private Recipe recipe;

    public Ingredient() {

    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }
}
