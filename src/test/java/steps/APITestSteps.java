package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class APITestSteps {

    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;


    @When("^I send a GET request to the (.+) URI$")
    public void sendGETRequest(String URI){
        request = given()
                .baseUri(URI)
                .contentType(ContentType.JSON);
    }

    @Then("^I get a (\\d+) status code$")
    public void getStatusCode(int expectedStatusCode) {
        response = request
                .when()
                .get("/users/ahuaracab/repos");
        json = response.then().statusCode(expectedStatusCode);
    }

    @Then("^I validate there are (\\d+) items on the (.+) endpoint$")
    public void validateSize(int expectedSize, String endpoint) {
        response = request
                .when()
                .get(endpoint);
        List<String> jsonResponse = response.jsonPath().getList("$");
        System.out.println(jsonResponse);
        int actualSize = jsonResponse.size();

        assertEquals(expectedSize, actualSize);
    }

    @Then("^I validate there is a value: (.+) in the response at (.+) endpoint$")
    public void validateValue(String expectedValue, String endpoint) {
        response = request
                .when()
                .get(endpoint);
        List<String> jsonResponse = response.jsonPath().getList("username");
        System.out.println(jsonResponse);
        //String actualValue = jsonResponse.get(0);
        //assertEquals(expectedValue, actualValue);
        assertTrue("El valor "+expectedValue+" no se encuentra en la lista",jsonResponse.contains(expectedValue));
    }

    @Then("^I can validate the nested value: (.+) in the response at (.+) endpoint$")
    public void validateNestedValue(String expectedStreet, String endpoint) {
        response = request
                .when()
                .get(endpoint);
        String jsonResponse = response.jsonPath().getString("address.street");
        //List<String> jsonResponse = response.jsonPath().getList("address.street");
        System.out.println(jsonResponse);
        assertTrue("La calle "+expectedStreet+" no pertenece a ningun usuario",jsonResponse.contains(expectedStreet));
    }


}
