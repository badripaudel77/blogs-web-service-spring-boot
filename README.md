# amigos-spring-blogs
Blog application using Spring Boot &amp; Java 
---------------------------------------------
# Dependencies :
- Spring web
- Spring boot dev tools
- Data JPA
- Hibernate validator
- PostgreSQL Driver
- Spring Security & More...

# Architecture followed
- MVC

# Project Structure
- apis -> contains all the rest endpoints [equivalent to resources in grails]
- services -> contains business logic that are not really connected to the view
- models -> contains all the domain class [data class to map to database]
- repositories -> contains all the spring data repositories for data access
- configuration -> contains all relevant configuration files for the app.
- utils -> contains all the utilities classes & methods for additional logic
- dtos -> data transfer objects [ to transfer data / map entity to reponse , just like in grails]
- exceptions -> contains all the classes for handling exception for the application
- ....

# Database
- users [will contain all users, individual users will have many blogs]
- blogs [contains all the blog posts, blog will contain the user_id]
- comments [one blog has many commnents, comment has blog_id & user_id, one user can make multiple comments?]
- roles  [role defined for each user, like ADMIN_ROLE, NORMAL_USER, and more if required.]
- categories [list of categories, one post can have categories...]
- ...

