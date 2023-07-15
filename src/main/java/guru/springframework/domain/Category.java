package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by igors on 9/19/22
 */
@Data
@EqualsAndHashCode(exclude = {"recipes"})   //to avoid java.lang.StackOverflowError: null
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories") //"categories" - is name of variable in Recipe class, where mapping to Category is defined
    private Set<Recipe> recipes;

}
