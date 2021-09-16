package steps;

import static io.restassured.RestAssured.given;

import java.util.Base64;

import org.apache.http.HttpStatus;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestAssuredOnly {

    public void GETRequest() {
        given()
                .baseUri("https://api.github.com")
                .when()
                .get("/users/ahuaracab/repos")
                .getBody()
                .toString();
    }

    public void POSTRequest() {
        given()
                .baseUri("https://api.github.com")
                .when()
                .post("/posts", "Titulo:Texto");
    }

    public void PUTRequest() {
        given()
                .baseUri("https://api.github.com")
                .when()
                .put("", "");
    }

    public void DELETERequest() {
        given()
                .baseUri("https://api.github.com/posts/Texto")
                .when()
                .delete();
    }

    public void basicAuth(String username, String password) {
        given().auth()
                .basic(username, password)
                .when()
                .get("http://localhost:8080/spring-security-rest-basic-auth/api/foos/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    public void formAuth(String username, String password) {
        given()
                .auth().form(username, password)
                .when()
                .get("AUTH_ENDPOINT")
                .then()
                .assertThat().statusCode(200);
    }

    /*
        1. Obtener el código del servicio original para obtener el token
        2. Obtener el token, intercambiando el código que obtuvimos
        3. Acceder al recurso protegido con nuestro token
    */

    public static String clientId = "";
    public static String redirectUri = "";
    public static String scope = "";
    public static String userName = "";
    public static String password = "";
    public static String granType = "";
    public static String accessToken = "";

    

    public static String encode(String str1, String str2) {
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }

    public static Response getCode() {
        String authorization = encode(userName, password);
        return
                given()
                        .header("authorization", "Basic " + authorization)
                        .contentType(ContentType.URLENC)
                        .formParam("response_type", "code")
                        .queryParam("cliente_id" + clientId)
                        .queryParam("redirect_uri" + redirectUri)
                        .queryParam("scope" + scope)
                        .post("/oauth/authorize")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    public static String parseForOAuthCode(Response response){
        return response.jsonPath().getString("code");
    }

    public static Response getToken(String authCode) {
        String authorization = encode(userName, password);
        return
                given()
                        .header("authorization", "Basic " + authorization)
                        .contentType(ContentType.URLENC)
                        .formParam("response_type", authCode)
                        .queryParam("redirect_uri" + redirectUri)
                        .queryParam("grantType" + granType)
                        .post("/oauth/token")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    public static String parseForToken(Response loginResponse){
        return loginResponse.jsonPath().getString("access_token");
    }

    public static void getFinalService(){
        given().auth()
        .oauth2(accessToken)
        .when()
        .get("/service")
        .then()
        .statusCode(200);
 //o también se puede pasar como header
       /* given()
        .header("Authorization", "Bearer " + accessToken)
        .when()
        .get("/service")
        .then()
        .statusCode(200);*/
    }

}
