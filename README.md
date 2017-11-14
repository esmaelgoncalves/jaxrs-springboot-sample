# README #

### Summary ###

* Application: PRODUCT-API is a Restful service using JAX-RS to perform CRUD operations on a Product resource using Image as a sub-resource of Product.
* Version : 1.0.0-SNAPSHOT

### General Considerations ###

* In addition to the requested MER, it was considered that the physical image files would be stored on an image server, for example FTP. So in the database would be stored only the reference to the actual file. Thus the system was developed considering the existence of an additional field called 'path' in the Image table.
* 


### Set Up and Running ###

* This a maven based application. To compile, test and run de application we can use maven 'mvn' commands.

* To Compile (without test) : Run the follow command line
```
mvn clean install -DskipTests
```

* To run automated tests: Run the follow command line
```
mvn test
```

* To run application:  Run the follow command line

### EndPoint Resources ###

####Follows some samples how to use the api to CRUD operations over the resources####
* Consider the RequestPath = localhost:8080

##### Create a new product #####

```
Uri: /products
Method: [POST] 
```

Call
```
${RequestPath}/products
```

Headers
```
Content-Type: application/json
```

Body
```json
{
	"name": "Name of the Product",
	"description": "You can register a description"
}
```

----------

##### Update a product #####

```
Uri: /products/{id} 
Method: [PUT] 
```

Call
```
${RequestPath}/products/1
```

Headers
```
Content-Type: application/json
```

Body
```json
{
	"name": "Name with alteration",
	"description": "Description with alteration"
}
```

----------

##### Delete a product #####

```
Uri: /products/{id} 
Method: [DELETE] 
```

Call
```
${RequestPath}/products/1
```

Headers
```
Content-Type: application/json
```

##### Get all products WITHOUT RELATED ENTITIES #####

```
Uri: /products
Method: [GET] 
```

Call
```
${RequestPath}/products
```

Headers
```
Content-Type: application/json
```
----------

##### Get all products WITH RELATED ENTITIES (child product and/or images) #####

```
Uri: /products?images=true&children=true
Method: [GET] 
```

Call
```
${RequestPath}/products?images=true&children=false
```

Headers
```
Content-Type: application/json
```

----------

##### Get a product WITHOUT RELATED ENTITIES #####

```
Uri: /products/{id}
Method: [GET] 
```

Call
```
${RequestPath}/products/1
```

Headers
```
Content-Type: application/json

----------

##### Get a product WITH RELATED ENTITIES (child product and/or images) #####

```
Uri: /products/{id}?images=true&children=true
Method: [GET] 
```

Call
```
${RequestPath}/products/1?images=false&children=true
```

Headers
```
Content-Type: application/json

----------

##### Get set of child products for specific product #####

```
Uri: /products/{id}/children
Method: [GET] 
```

Call
```
${RequestPath}/products/1/children
```

Headers
```
Content-Type: application/json
```

----------

##### Get set of images for specific product #####

```
Uri: /products/{id}/images
Method: [GET] 
```

Call
```
${RequestPath}/products/1/images
```

Headers
```
Content-Type: application/json
```

----------

#### Images is a sub-resource of product####

##### Create a new image#####

```
Uri: /products/{productId}/images
Method: [POST] 
```

Call
```
${RequestPath}/products/1/images
```

Headers
```
Content-Type: application/json
```

Body
```json
{
	"path": "Name of the Product",
}
```

----------

##### Update a image#####

```
Uri: /products/{productId}/images/{id}
Method: [PUT] 
```

Call
```
${RequestPath}/products/1/images/1
```

Headers
```
Content-Type: application/json
```

Body
```json
{
	"path": "Name with alteration"
}
```

----------

##### Delete a image#####

```
Uri: /products/{productId}/images/{id}
Method: [DELETE] 
```

Call
```
${RequestPath}/products/1/images/1
```

Headers
```
Content-Type: application/json
```

----------
