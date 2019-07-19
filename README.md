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

2. 사용자 검색어 테이블   

3. 인기 검색어 테이블   


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