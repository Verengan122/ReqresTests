import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.nio.file.Paths.get;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

public class TestsReqresin {
    @Test
    void succsessfulTest() {
        String user = "{\"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\"}";
        given()
                .body(user)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", Matchers.is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccsessfulTest() {
        String user = "{\"email\": \"sydney@fife\"}";
        given()
                .body(user)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", Matchers.is("Missing password"));
    }

    @Test
    void createTest() {
        String user = "{\"name\": \"morpheus\", " +
                "\"job\": \"leader\"}";
        given()
                .body(user)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", Matchers.is("morpheus"), "job", Matchers.is("leader"));
    }

    @Test
    void listTest() {
        RestAssured.get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    void notFoundTest() {
        RestAssured.get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404);

    }
}



