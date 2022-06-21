# amigos-spring-blogs
Blog application using Spring Boot &amp; Java 
---------------------------------------------

# TODO: please add redis with spring redis .... for learning purposes and observe the time efficiency for retrieving data.

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
- comments [one blog has many comments, comment has blog_id & user_id, one user can make multiple comments?]
- roles  [role defined for each user, like ADMIN_ROLE, NORMAL_USER, and more if required.]
- categories [list of categories, one post can have categories...]

# Spring security reference :
- REF 1: https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details.html
- REF 2 : https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html
- REF 3 : [Learn Code With Durgesh] https://www.youtube.com/watch?v=3m_mLFz-uQE&list=PL0zysOflRCen-GihOcm1hZfYAlwr63K_M&index=31
- REF 4: https://www.youtube.com/watch?v=X80nJ5T7YpE [Java Brains]

# Spring boot image [file] uploading & serving  [image] download url
 - REF1 : https://stackoverflow.com/questions/45651119/spring-boot-images-uploading-and-serving
 - REF2 : https://stackoverflow.com/a/52151216/9898251
 - REF : https://www.devglan.com/spring-boot/spring-boot-file-upload-download

# JWT authentication
- REF : https://www.youtube.com/watch?v=X80nJ5T7YpE
- Stateless authentication mechanism.

