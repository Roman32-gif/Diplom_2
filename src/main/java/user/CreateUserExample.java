package user;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import settings.Paths;
import settings.Specification;
import static io.restassured.RestAssured.given;
import static settings.Paths.*;

public class CreateUserExample extends Specification {
    @Step("POST")
    public static Response createUser(User user) {
        Response response = given()
                .spec(Specification.getSpecification())
                .header("Content-type", "application/json")
                .body(user)
                .log().all()
                .post(USER_PATH + "register");

        response.then().log().all();

        return response;

    }


    @Step("POST")
    public static Response loginUser(User user) {
        return given()
                .spec(Specification.getSpecification())
                .body(user)
                .when()
                .post(Paths.LOGIN_PATH);

    }


    @Step("DELETE")
    public static Response deleteUser(int id) {
        return given()
                .spec(Specification.getSpecification())
                .header("Content-type", "application/json")
                .when()
                .delete(USER_PATH + id);
    }

    @Step("DELETE")
    public Response deleteNewUser( String token) {
        Response response = RestAssured.given()
                .spec(Specification.getSpecification())
                .header("Authorization", token)
                .header("Content-type", "application/json")
                .when()
                .delete(USER_PATH + "user");

        System.out.println("Delete response: " + response.asString()); // Логирование ответа
        return response;
    }




    @Step("PATCH")
    public static Response updateUserWithAuthorization(User user, String accessToken) {
        return given()
                .spec(Specification.getSpecification())
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .log().all()
                .body(user)
                .when()
                .patch(UPDATE_PATH);

    }

    @Step("POST")
    public static Response resetPassword (User user, String accessToken) {
        return given()
                .spec(Specification.getSpecification())
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .log().all()
                .body(user)
                .when()
                .post(RESET_PATH);

    }

    @Step("PATCH")
    public static Response updateUserWithoutAuthorization(User user) {
        return given()
                .spec(Specification.getSpecification())
                .header("Content-type", "application/json")
                .log().all()
                .body(user)
                .when()
                .patch(USER_PATH + "user");

    }
}
