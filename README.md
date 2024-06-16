#  Product Menagement
___
## About project
The Product Management System is an e-commerce application 
designed to facilitate the management and purchase of products. 
The application includes several key entities such as products, 
categories, customers, shipping addresses, users, roles, carts, 
cart items, orders, order items, order addresses, and product images.
___
## UML Diagram
![alt text](C:\Users\Niks\IdeaProjects\productMenagement3\src\main\resources\static\upload\productMenagement.jpg)
___
## Description
### User Roles:

#### Admin: 
Can manage users, including suspending and reactivating them, and has full access to the system.
#### Customer:
Automatically assigned to new users upon registration. Customers can browse products, add or remove products from their cart, and make purchases.
### Product Management:

Customers can view a list of all available products and add them to their cart.
Products are categorized for easier browsing.

---

## Technology stack
#### This project was built using IntelliJ IDEA and uses the following technologies:
- Java 21
- Spring Framework
- Spring Security
- Maven
- PostgreSQL
- Rest Error Handlers
- Postman
- Git
___
## Setup 
___
### Setup for local development
1. Install IntelliJ
2. Clone the repository.
    - `git clone https://github.com/nniks95/productMenagement3.git`
3. Install Smart Tomcat 4.7.2 plugin in IntelliJ
4. Setup Apache Tomcat
    - Set Catalina base as root of the project
    - Set deployment directory to:  *absolutePath*
      - Navigate to `src/main/webapp`, right click and copy absolute path
    - Set context path to `/`
5. Create DB in PgAdmin with name `productMenagement`
6. Run application in IntelliJ
7. Test API using Postman
   - default user credentials
     - username: `nikola.nikolic.it@gmail.com`
     - password: `admin`
## API Reference

### Users

#### Register
```http request
POST /register
```
#### Login
```http request
POST /login
```
##### Send raw json body:
{ <br />
"username":"niks95@gmail.com", <br />
"password":"admin"
<br />}

##### Get token from headers
Click on headers, find header named token, copy string value

#### Send get request
Click on headers tab, as key enter `Authorization`, for value type `Bearer` ` ` `paste token value`
