Book Search Service
===================================
> 책 검색 서비스


### Prerequisites   
- JDK 1.8   
- Spring Boot 2.x   
    - Spring Security   
    - JPA   
    - Lombok   
    - Junit 5   
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
   create table USER_KEYWORD
   (
       ID                 BIGINT             not null  primary key,
       KEYWORD            VARCHAR(200)       not null,
       REGISTRATION_DATE  TIMESTAMP(26, 6)   not null,
       USER_ID            BIGINT             not null,
       constraint FK7VF8J5X5GRT4N6LXW8BLIGM4C foreign key (USER_ID) references USER
   );
   ~~~


3. 인기 검색어 테이블   
   ~~~sql
   create table KEYWORD
   (
       ID                 BIGINT             not null  primary key,
       COUNT              BIGINT             not null,
       KEYWORD            VARCHAR(200)       not null,
       REGISTRATION_DATE  TIMESTAMP(26, 6)   not null
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
- Compile and package
   ~~~bash
   mvn compile
   mvn package
   ~~~
- Starting
   ~~~bash
   java -jar target/book-0.0.1-SNAPSHOT.jar book
   ~~~