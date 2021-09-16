package steps;


import static io.restassured.RestAssured.given;

public class SOAPRequest {

    public void POSTRequest() {

        String xmlEnvelope = "<soap:Envelope xmlns:..... >";
        given()
                .header("SOAPAction", "Define")
                .baseUri("https://api.github.com")
                .when()
                .body(xmlEnvelope)
                .post("/endpoint");
    }
}
