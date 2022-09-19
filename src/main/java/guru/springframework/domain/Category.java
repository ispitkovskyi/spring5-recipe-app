package guru.springframework.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by igors on 9/19/22
 */
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories") //"categories" - is name of variable in Recipe class, where mapping to Category is defined
    private Set<Recipe> recipes;

    public String getDescription() {
        return description;
    }

    public void setDescription(String categoryName) {
        this.description = categoryName;
    }
}
