# Confidentify client for Java

This project provides a Java client for the Confidentify APIs. 

## How to use

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
