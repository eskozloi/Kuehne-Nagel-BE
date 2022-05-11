# Kuehne+Nagel Assessment Task - BE

## How-to

1. **copy** project
2. **change** properties in src/main/resources/**application.properties**
3. **create** database
4. **have** **_fun_**

## Project Features

- **SpringBoot** framework to be able to make the program faster and better structured

- **Liquibase** for flexible database data processing

- **MVC** pattern for well-structured code

## Project Structure

### Models

> Database data representatives

- **_Customer_**

  - **id**(PK, AI, NotNull, int)
  - **registrationCode**(Unique, NotNull, varchar(31))
  - **fullName**(varchar(255))
  - **email**(Unique, varchar(255))
  - **telephone**(varchar(31))

- **_Product_**

  - **id**(PK, AI, NotNull, int)
  - **name**(varchar(255))
  - **skuCode**(Unique, varchar(31))
  - **unitPrice**(varchar(31))

- **_Order_**

  - **id**(PK, AI, NotNull, int)
  - **customerId**(int)
  - **submissionDate**(date)

- **_OrderLine_**
  - **id**(PK, AI, NotNull, int)
  - **productId**(int)
  - **quantity**(int)
  - **orderId**(int)

### Controllers

> Responsible for request handling

- **_CustomerController_** | **/customer** |

  - **getAllCustomers()** | **GET \*/** |
  - **getCustomerById()** | **GET \*/{id}** | - takes **id** as an Integer
  - **createCustomer()** | **POST \*/** | - takes JSON body as a **Customer**

- **_ProductController_** | **/product** |

  - **getAllProducts()** | **GET \*/** |
  - **getProductById()** | **GET \*/{id}** | - takes **id** as an Integer
  - **createProduct()** | **POST \*/** | - takes JSON body as a **Product**

- **OrderController** | **/order** |
  - **getAllOrders()** | **GET \*/** | - takes JSON body (not necessary)
  - **getOrderById()** | **GET \*/{id}** | - takes **id** as an Integer
  - **getOrderLinesByOrderId()** | **GET \*/{id}/line** | - takes **id** as an Integer
  - **createOrder()** | **POST \*/** | - takes JSON body as a **Order**
  - **getAllOrderLines()** | **GET \*/line** |
  - **getOrderLineById()** | **GET \*/line/{id}** | - takes **id** as an Integer
  - **updateOrderLineById()** | **PUT \*/line/{id}** | - takes **id** as an Integer and JSON body
  - **createOrderLine()** | **POST \*/line** | - takes JSON body as a **OrderLine**

### Services

> Manipulates with its Model

There are too many different functions and I am too lazy to write them all, but will say that they have **self-explanatory names** so you will instantly understand what they responsibilities are.

Actualy will say one more thing - In **OrderController getAllOrders()** method can take JSON body and there is a reason for that - **deep sorting opportunity**. So you can search for **any** parrameters of **other** Models that anyhow connected to Order or OrderLine. e.g. -
![filterExample](./gitsrc/filterExample.png)

### Repositories

> Query level Model manipulating

Repositories **closest** layer for Models manipulating, which is done with functions that _querying_ informations directly using SQL Queries.

## UnitTests

> You can find REST API Unit Tests in src/**test**/java/com.fk.knp/**Controllers**
