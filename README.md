#This project contains code for performing basic CRUD operations on database using JDBC


* Database: HSQL DB  
* CRUD operations using JDBC  

 *Note: Make changes according to your needs in JDBCConstants.java file. Check out the comments and system console after running the program. This project needs to be refactored to use best practices which will be done later. As of now this code should be used only for understanding purposes.*  

#### Note

Table creation SQL on HSQLDB: 
```html
create table PERSON (ID bigint not null, EMAIL varchar(255), FIRST_NAME varchar(255), JOINED_DATE date, LAST_NAME varchar(255), primary key (id))  
```
