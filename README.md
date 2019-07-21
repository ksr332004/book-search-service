Book Search Service
===================================
> 책 검색 서비스


### Prerequisites   
- JDK 1.8   
- Spring Boot 2.x   
    - Spring Security   
    - JPA   
    - Lombok   
- Vue.js   
- Maven   
- H2   


## Structure
### Table Structure
1. 사용자 테이블   
   ~~~sql
   create table USER
   (
       ID                 BIGINT             not null  primary key,
       EMAIL              VARCHAR(100)       not null  unique,
       NAME               VARCHAR(50)        not null,
       PASSWORD           VARCHAR(300)       not null,
       REGISTRATION_DATE  TIMESTAMP(26, 6)   not null
   );
   ~~~
   
2. 사용자 검색어 테이블   
   ~~~sql
   create table HISTORY
   (
       ID                 BIGINT             not null  primary key,
       KEYWORD            VARCHAR(200)       not null,
       REGISTRATION_DATE  TIMESTAMP(26, 6)   not null,
       USER_ID            BIGINT             not null,
       constraint FKN4GJYU69M6XA5F3BOT571IMBE foreign key (USER_ID) references USER
   );
   ~~~


## Getting Started
### Frontend   
- Project setup
   ~~~bash
   npm install
   ~~~

- Compiles and hot-reloads (dev)
   ~~~bash
   npm run serve
   ~~~
   
- Compiles and minifies (prod)
   ~~~bash
   npm run build
   ~~~

### Backend
- Compile
   ~~~bash
   mvn clean
   mvn compile
   ~~~
- Package
   ~~~bash
   mvn package
   ~~~
- Starting
   ~~~bash
   java -jar target/book-0.0.1-SNAPSHOT.jar book
   ~~~