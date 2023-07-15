package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by igors on 9/18/22
 */
@Data
@EqualsAndHashCode(exclude = {"recipe"}) //to avoid java.lang.StackOverflowError: null
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

}
