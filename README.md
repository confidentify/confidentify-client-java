# Conf·ident·ify client for Java

This project provides a Java client for the [Conf·ident·ify](https://confidentify.com) APIs. 

[![Build Status: Linux](https://travis-ci.org/confidentify/confidentify-client-java.svg?branch=master)](https://travis-ci.org/confidentify/confidentify-client-java)

## Using the client

First, add the project as a Maven dependency in your project:

```xml
<dependency>
  <groupId>com.confidentify</groupId>
  <artifactId>confidentify-client-java</artifactId>
  <version>1.0.0</version>
<dependency>
``` 

The client provides the key class `ApiClient` as well as a set of classes specific to each tagged part of the API, for instance `ProcessApi`.

Here's an example which assumes you have your account username/password in variables with corresponding names:

```java
final ApiClient apiClient = new ApiClient();

// Authenticate
final AuthApi authApi = new AuthApi(apiClient);
final AuthResponse authResponse = authApi.authPost(new AuthRequest().username(USERNAME).password(PASSWORD));
final String accessToken = authResponse.getAccessToken();
apiClient.setAccessToken(accessToken);

// Use the 'process' APIs
final ProcessApi processApi = new ProcessApi(apiClient);
```


## Building

The project is built using Apache Maven. A large portion of the project is code-generated from the [Confidentify OpenAPI spec](https://api.confidentify.com/openapi?format=yaml). For example, compiling would be:

```
mvn compile
```

## Testing

The tests in this project are integration tests, that require credentials. You can provide the credentials using either:

 * System properties `confidentify.username` and `confidentify.password`, or
 * Environment variables `CONFIDENTIFY_USERNAME` and `CONFIDENTIFY_PASSWORD`. 

Or you can tell Maven to skip tests:

```
mvn clean install -DskipTests
```
