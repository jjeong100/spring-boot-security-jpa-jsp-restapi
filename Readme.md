Example project for Spring Boot with security and JSP
=====================================================

Project with Registration and Login Example with Spring Boot, Spring Security, 
Spring Data, JPA and H2 DB.

Setup
-----
1. Create project by using Spring Boor Initializer.

   a) the following technologies should be selected:

        Web, JPA, Security, H2 db

2. Modify pom.xml by adding dependencies.

    a) add dependencies to support JSP and JSTL
    
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
        </dependency>
