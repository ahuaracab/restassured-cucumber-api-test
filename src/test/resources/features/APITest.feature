Feature: Ejemplo de Request

    Scenario: Prueba GET al endpoint

        When I send a GET request to the https://api.github.com URI
        Then I get a 400 status code

    Scenario: Validar la cantidad de entradas en la respuesta

        When I send a GET request to the https://jsonplaceholder.typicode.com URI
        Then I validate there are 10 items on the /users endpoint

    Scenario: Validar que un elemento esta en la respuesta

        When I send a GET request to the https://jsonplaceholder.typicode.com URI
        Then I validate there is a value: Brent in the response at /users endpoint

    @API
    Scenario: Validar un valor anidado dentro de la respuesta

        When I send a GET request to the https://jsonplaceholder.typicode.com URI
        Then I can validate the nested value: Kattie Turnpike in the response at /users endpoint