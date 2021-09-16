package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class BraveNewCoinSteps {
    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    @Given("^I have a valid API key for the (.+) URI$")
    public void iSetTheRequestParams(String URI) {
        request = given()
                .relaxedHTTPSValidation()
                .header("x-rapidapi-host", "bravenewcoin.p.rapidapi.com")
                .header("x-rapidapi-key", "6105fd816fmshe47bd6bbe34a47ap19d56fjsn3558561c7121")
                .contentType(ContentType.JSON)
                .baseUri(URI)
                .log().all();
    }

    @When("^I send a POST request with a valid (.+) payload to the (.+) endpoint$")
    public void sendPOSTRequest(String payload, String endpoint) {
        File requestBody = new File("src/test/resources/payloads/" + payload + ".json");
        response = request.when()
                .body(requestBody)
                .post(endpoint).prettyPeek(); //prettypeek te imprime la respuesta
    }

    @Then("^I can validate I receive a valid token in the response$")
    public void validateTheToken() {
        String jsonResponse = response.jsonPath().getString("$");
        assertTrue("No se ha generado ningun token",jsonResponse.contains("access_token"));
    }
}
