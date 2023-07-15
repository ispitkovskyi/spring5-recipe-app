package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by igors on 9/18/22
 */
@Data
@EqualsAndHashCode(exclude = {"recipe"})  //to avoid java.lang.StackOverflowError: null
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

}
