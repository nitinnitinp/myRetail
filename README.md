# MyRetail Restful Service 

This project contains source code of MyRestail Restful Service. The restful service provides GET and PUT requests on product resource.  


### Perquisite Dependencies: 

+  JDK 1.8  : Set the JAVA_HOME in your environment variable 
+  Maven 3.3 : Require to build and run the application
+  Mongodb  : Install to localhost and run it on port 27017. Create db “myRetail”  in mongodb. If you want to change the mongodb default connection configuration in application, then change the following properties in myRetail\src\main\resources\application.properties
``` 
            #mongodb
            spring.data.mongodb.host=localhost
            spring.data.mongodb.port=27017
            spring.data.mongodb.database=myRetail
```

### Build, Test and Run : 
If you have GIT installed on your system, you can clone the project from git repository, otherwise you can download it from given download link

Use below command to clone the project  
```
git clone https://github.com/nitinnitinp/myRetail.git
```
   OR

Download from below link and then extract it 
```
https://github.com/nitinnitinp/myRetail/archive/master.zip 
```
Now go to project directory /myRetail
```
cd <project location>/myRetail 

OR 

cd <extracted dir>
```
Build project using below command. It also runs the test cases as a part of build to check everything is good.
```
mvn clean install
```
To host the rest service, run below command . Currently default port is 8081, if you want to change it, update property server.port='<port>' in application.properties. 
Url format : http://<host:port>/myRetail/products/<id>
```
mvn spring-boot:run
```
### MyRetail Product Rest APIs
 #### Get Product Information
----
  Returns json data for a single product for a given id .

* **URL**
    `http://<host:port>/myRetail/products/<id>`
* **Method:**
  `GET`
*  **URL Params**
   **Required:** `id=[String]`
* **Request Body**
  None
* **Success Response:**
    **Content:** `{"id":"13860428","name":"The Big Lebowski (Blu-ray)","current_price":{"value":50.0,"currency_code":"USD"}}`
 
* **Error Response:**
    **Content:** `{"status":404,"errorMessage":"Product not found"}`
* **Sample Call:**

  ```
  curl -X GET --header 'Accept: application/json' 'http://localhost:8081/myRetail/products/13860428'
  ```
#### Update Product Price Information
----
Returns 202 OK when update the price information for a single product for a given id .

* **URL**
    `http://<host:port>/myRetail/products/<id>`
* **Method:**
  `PUT`
*  **URL Params**
   **Required:** `id=[String]`
* **Request Body**
  `{"id":"<Required string value, not a optional>",
   "name":"<Required string value, not a optional>",
   "current_price":{"value":<Required decimal value, not a optional>,"currency_code":"<Required string value, not a optional>"}}`
* **Success Response:**
    **Content:** `{"id":"13860428","name":"The Big Lebowski (Blu-ray)","current_price":{"value":50.0,"currency_code":"USD"}}`
 
* **Error Response:**
    **Content:** `{"status":404,"errorMessage":"Requested resource not found"}`
    
    OR
   
   **Content:** `{
"status": 400,
"errorMessage": "Please check your request body, either it is empty or entered wrong"
}`

    OR
    
    
   **Content:**  `{
"fieldErrors": {
"price.value": "Product price must be greater than 0"
}
}`

* **Sample Call:**

  ``` 
  curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.50,"currency_code":"USD"}} \ ' 'http://localhost:8081/myRetail/products/13860428'
  ```
 

