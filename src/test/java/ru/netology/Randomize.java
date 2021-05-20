package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class Randomize {
    private static Faker faker = new Faker(new Locale("ru"));

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void makeRegistration(Registration registration) {
        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static Registration generateNewActiveValidUser() {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new Registration(login, password, "active"));
        return new Registration(login, password, "active");
    }

    public static Registration generateNewBlockedUser() {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new Registration(login, password, "blocked"));
        return new Registration(login, password, "blocked");
    }

    public static Registration generateNewActiveUserInvalidLogin() {
        String password = faker.internet().password();
        String status = "active";
        makeRegistration(new Registration("vasiliy", password, status));
        return new Registration("login", password, status);
    }

    public static Registration generateNewActiveInvalidPassword() {
        String login = faker.name().firstName().toLowerCase();
        String status = "active";
        makeRegistration(new Registration(login, "password", status));
        return new Registration(login, "12345", status);
    }
}
