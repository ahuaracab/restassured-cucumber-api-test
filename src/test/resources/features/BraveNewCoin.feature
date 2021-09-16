
Feature: BraveNewCoin API escenarios: POST GetToken

  Rule: When I send a POST request to the endpoint, I receive a Token I can use to make further authenticated calls

    @BraveNewCoin
    Scenario: As a user, I can retrieve a Token when making a valid POST request

      Given I have a valid API key for the https://bravenewcoin.p.rapidapi.com URI
      When I send a POST request with a valid TokenRequestBody payload to the /oauth/token endpoint
      Then I can validate I receive a valid token in the response

    Scenario: As a user, when I use an invalid API key, I get an HTTP Status Code 403

      Given I have an invalid API key for the https://bravenewcoin.p.rapidapi.com URI
      When I send a POST request with a valid body to the /oauth/token endpoint
      Then I receive an HTTP Status Code 403

    Scenario: As a user, when I send the POST request without "grant_type", I get an HTTP Status Code 400

      Given I have a valid API key for the https://bravenewcoin.p.rapidapi.com URI
      When I send a POST request without "grant_type" in it's body
      Then I receive an HTTP Status Code 400