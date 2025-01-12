package user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import settings.Paths;
import settings.Specification;

import static io.restassured.RestAssured.given;
import static settings.Paths.USER_PATH;

public class CreateUserExample extends Specification {
    @Step("POST")
    public static Response createUser (User user) {
        Response response = given()
                .spec(Specification.getSpecification())
                .body(user)
                .log().all()
                .post(USER_PATH + "register");

        response.then().log().all();

        return response;

    }

    @Step("GET")
    public static Response getUser (String accessToken) {
        Response response = given()
                .spec(Specification.getSpecification())
                .header("Authorization", accessToken)
                .log().all()
                .get(USER_PATH);

        // Логирование ответа
        response.then().log().all();

        return response; // Возврат объекта Response
    }

    @Step("POST")
    public static Response loginUser (User user) {
        return given()
                .spec(Specification.getSpecification())
                .body(user)
                .when()
                .post(Paths.LOGIN_PATH);

    }

    @Step("POST")
    public static Response logoutUser (String refreshToken) {
        return given()
                .spec(Specification.getSpecification())
                .body(refreshToken)
                .log().all()
                .post(Paths.LOGOUT_PATH);
    }

    @Step("DELETE")
    public static Response deleteUser (int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(USER_PATH + "/" + id);
    }


    @Step("PATCH")
    public static Response updateUserWithAuthorization (User user, String accessToken) {
        Response response = given()
                .spec(Specification.getSpecification())
                .body(user)
                .auth().oauth2(accessToken)
                .log().all()
                .patch(USER_PATH); // Используем userId в пути

        response.then().log().all();

        return response; // Возврат объекта Response
    }

    @Step("PATCH")
    public  static Response updateUserWithoutAuthorization (User user) {
        Response response =  given()
                .spec(Specification.getSpecification())
                .body(user)
                .log().all()
                .patch(USER_PATH);

        response.then().log().all();

        return response; // Возврат объекта Response
    }
}
