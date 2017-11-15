# README #

### Summary ###

* Application: PRODUCT-API is a Restful service using JAX-RS to perform CRUD operations on a Product resource using Image as a sub-resource of Product.
* Version : 1.0.0-SNAPSHOT
* Test Coverage: 83,8%

### General Considerations ###

* In addition to the requested MER, it was considered that the physical image files would be stored on an image server, for example FTP. So in the database would be stored only the reference to the actual file. Thus the system was developed considering the existence of an additional field called 'path' in the Image table.
* The application uses de embbeded in-memory database H2.
* The database model is auto generated when the application up and run.
* For tests purposes there two files in tests/resources: schema.sql and data.sql, to create and populate the database for some unit tests.



### Set Up and Running ###

* This a maven based application. To compile, test and run de application we can use maven 'mvn' commands.

* To Compile (without test) : Run the follow command line
```
mvn clean install -DskipTests
```

* To Compile with test : Run the follow command line
```
mvn clean install
```

* To run just automated tests: Run the follow command line
```
mvn test
```

* To run application with spring-boot maven plugin:  Run the follow command line
```
mvn spring-boot:run
```

* To run application as a packaged application:  Run the follow command line
```
java -jar ${target}/product-api-1.0.0-SNAPSHOT.jar
```

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

Success
```
Status Code: 201
Headers: content-type: application/json, content-location: localhost:8080/products/1

{
	"id:" 1,
	"name": "Name of the Product",
	"description": "You can register a description"
}
```

##### Create a new product child#####

```
Uri: /products/{id}
Method: [POST] 
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
	"name": "Name of the Product Child",
	"description": "You can register a description"
}
```

Success
```
Status Code: 201
Headers: content-type: application/json, content-location: localhost:8080/products/1/2

{
	"id:" 2,
	"name": "Name of the Product Child",
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

Success
```
Status Code: 200
Headers: content-type: application/json

{
	"id:" 1,
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

Success
```
Status Code: 204
```

Error (Not Found)
```
Status Code: 404
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

Success
```
Status Code: 200
Headers: content-type: application/json

[
    {
        "id": 1,
        "name": "PS4",
        "description": "Game Console",
        "children": null,
        "images": null
    },
    {
        "id": 2,
        "name": "PS4 - Controller",
        "description": "The Game Console Controller",
        "children": null,
        "images": null
    },
    {
        "id": 3,
        "name": "Iphone X",
        "description": "Apple celular gadget",
        "children": null,
        "images": null
    }
]
```
----------

##### Get all products WITH RELATED ENTITIES (child product and/or images) #####

```
Uri: /products?images=true&children=true
Method: [GET] 
```

Call
```
${RequestPath}/products?images=true&children=true
```

Headers
```
Content-Type: application/json
```

Success
```
Status Code: 200
Headers: content-type: application/json

[
    {
        "id": 1,
        "name": "PS4",
        "description": "Game Console",
        "children": [
            {
                "id": 2,
                "name": "PS4 - Controller",
                "description": "The Game Console Controller",
                "children": [],
                "images": [
                    {
                        "id": 2,
                        "path": "ftp://location/IMAGE002"
                    }
                ]
            }
        ],
        "images": [
            {
                "id": 1,
                "path": "ftp://location/IMAGE001"
            }
        ]
    },
    {
        "id": 2,
        "name": "PS4 - Controller",
        "description": "The Game Console Controller",
        "children": [],
        "images": [
            {
                "id": 2,
                "path": "ftp://location/IMAGE002"
            }
        ]
    },
    {
        "id": 3,
        "name": "Iphone X",
        "description": "Apple celular gadget",
        "children": [],
        "images": [
            {
                "id": 3,
                "path": "ftp://location/IMAGE003"
            },
            {
                "id": 4,
                "path": "ftp://location/IMAGE004"
            }
        ]
    }
]
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
```

Success
```
Status Code: 200
Headers: content-type: application/json

[
    {
        "id": 1,
        "name": "PS4",
        "description": "Game Console",
        "children": null,
        "images": null
    }
]
```
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
```

Success
```
Status Code: 200
Headers: content-type: application/json

[
    {
        "id": 1,
        "name": "PS4",
        "description": "Game Console",
        "children": [
            {
                "id": 2,
                "name": "PS4 - Controller",
                "description": "The Game Console Controller",
                "children": null,
                "images": null
            }
        ],
        "images": null
    }
]
```

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

Success
```
Status Code: 200
Headers: content-type: application/json

[
    {
        "id": 2,
        "name": "PS4 - Controller",
        "description": "The Game Console Controller",
        "children": null,
        "images": null
    }
]
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

Success
```
Status Code: 200
Headers: content-type: application/json

[
    {
        "id": 1,
        "path": "ftp://location/IMAGE001"
    }
]
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

Success
```
Status Code: 201
Headers: content-type: application/json, content-location: localhost:8080/products/1/images/1

{
	"id:" 1,
	"path": "Name with alteration"
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

Success
```
Status Code: 200
Headers: content-type: application/json

{
	"id:" 1,
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

Success
```
Status Code: 204
```

Error (Not Found)
```
Status Code: 404
```

----------
