## Spring Boot Recipe Application

[![CircleCI](https://circleci.com/gh/springframeworkguru/spring5-recipe-app.svg?style=svg)](https://circleci.com/gh/springframeworkguru/spring5-recipe-app)

This repository is for an example application built in my Spring Framework 5 - Beginner to Guru

You can learn about my Spring Framework 5 Online course [here.](https://go.springframework.guru/spring-framework-5-online-course)

###What information this project contains
####data.sql file
By default, data.sql scripts get executed before the Hibernate is initialized. When we run the project with this file on the classpath, Spring will pick it up and use it to populate the data.
####Default implementation of CrudRepository<Category, Long>
Ability to manipulate with data in H2 database
##### H2 db credentials:
 -  jdbc:h2:mem:testdbd
 -  [user "sa"]
 -  [empty password]
####Spring Data JPA Query Methods in repository classes
Like findBy... methods in CategoryRepository and UnitOfMeasureRepository classes
####Relationships between entities (one-to-one, one-to-many, many-to-one, many-to-many)
####Unit tests
####Integration tests
UnitOfMeasureRepositoryIT class
####Controllers, which add attributes to the HTML document model
These attributes then accessible to the Thymeleaf in .html templates 
(see example with "recipe" attribute below)
####Thymeleaf templates with different examples of usage
 - Overriding default text values with dynamic properties, like 
    th:text="${recipe.getNotes().getRecipeNotes()}"
 - Iterating through lists of elements, like
     "\<li th:each="ingredient : ${recipe.ingredients}"
                                        th:text="${ingredient.getAmount() +'
                                        ' ' + ingredient.uom.getDescription() +'
                                        ' - ' + ingredient.getDescription()}">1 Teaspoon of Sugar\</li\>"
####Binding data to POJO in Spring   