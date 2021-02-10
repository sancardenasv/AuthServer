# Authorization Server
Project on how to set up OAuth2 authorization server with JWT token using Spring Boot 2, JPA, Hibernate and MySQL.

---

## Related projects
* Resource server: Pending
* [Spring Boot Angular](https://github.com/sancardenasv/spring-boot-angular)
---

## Repositories
Project includes external repositories using MySql for:
* Clients: Credentials and configurations for authentication. Allows the client to request authentication for a user.
* Users: Credentials, roles and permissions used to authorize in the server. Authorization will be returned as a JWT.

MySql schema initialization and example data can be found in `initDB.sql` file under resources folder.
###### Client
* Client ID: client
* Secret: secret
###### Users
* Usernames: admin@mail.com, audit@mail.com
* Password: password
###### Roles and Permissions
There are two roles `ADMINISTRATOR` and `AUDITOR`. Permissions are associated to 
these roles using `permissionToRole` table; currently are related as:
* ADMIN: CREATE_NOTE, EDIT_NOTE, DELETE_NOTE, VIEW_ALL_NOTE, VIEW_NOTE
* AUDIT: VIEW_ALL_NOTE, VIEW_NOTE

Users are assigned to a Role using `userToRole` table; currently admin and audit users are
set to `ADMINISTRATOR` and `AUDITOR` roles respectively.

## Installing
Just clone or download the repo and import it as an existing maven project.
Project uses Lombok, you should install the plugin/library and enable annotation processing;
you can also remove the associated annotations from the code and write the getters, setters, constructors, etc.

Project can be run as java application from project root directory:
```
mvn clean install
java -jar target/authserver-0.0.1-SNAPSHOT.jar
```

##Use
JWT authorization can be obtained using a POST request with valid set of credentials.

### Keystore
To properly use the server, you should first generate your own java keystore and replace the one under resources.

Using java `keytool` utility, you can run the following command to generate a private and public key/certificate;
keytool is available in the bin folder of your java installation folder.
```
keytool -genkeypair -alias jwt -keyalg RSA -keypass "changeMe" -keystore jwt.jks -storepass "changeMe"
```
This will create a keystore as `jwt.jks` and a certificate as `jwt.pem` using RSA;
note the password used `changeMe` as it will be used later.

> **NOTE:**  The command will request some organization information to sign the key, this can be all skipped.

It is recommended to use **PKCS12**; use the following command to perform:
```
keytool -importkeystore -srckeystore jwt.jks -destkeystore jwt.jks -deststoretype pkcs12
```
This will request the password used to create the keystore; will also create a new JKS file,
use this one to replace the current under `resources` directory.

To get the public key and certificate in order to use them in the resources/client side, use the following command:
```
keytool.exe -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey
```
This will require the password again and will print the public key and certificate values on the console.

### Authorize Request
The authentication endpoint is `/oauth/token` and must use basic authorization, where its header "Authorization"
must contain "Basic " + Base64 encoding of "clientId:clientSecret"; here is an example of the request using the exaple user and client data:
```
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=B78CD9B5F25EA37D00BFB7387C503FA1' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=admin@mail.com' \
--data-urlencode 'password=password'
```
This will return the access and refresh tokens along with the additional information:
```
{
    "access_token": "TOKEN WILL BE HERE",
    "token_type": "bearer",
    "refresh_token": "TOKEN WILL BE HERE",
    "expires_in": 3599,
    "scope": "read write",
    "id": 1,
    "name": "Admin",
    "username": "admin@mail.com",
    "jti": "77cfef93-6f67-4009-8a57-8c56d8936ffb"
}
```

## Configuration
Extra information can be added in the JWT using `CustomTokenEnhancer` class
