package order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import settings.Specification;
import settings.Paths;
import static io.restassured.RestAssured.given;

public class CreateOrderExample extends Specification{
    @Step("GET")
    public static Response getAllOrders () {
        Response response = given()
                .spec(Specification.getSpecification())
                .log().all()
                .get(Paths.ORDERALL_PATH);
        response.then().log().all();

        return response;
    }

    @Step("POST")
    public static Response createOrderWithAuthorization (Order order, String accessToken ) {
        Response response = given()
                .spec(Specification.getSpecification())
                .body(order)
                .auth().oauth2(accessToken)
                .log().all()
                .post(Paths.ORDER_PATH);
        response.then().log().all();

        return response;
    }

    @Step("POST")
    public static Response createOrderWithoutAuthorization (Order order) {
        Response response = given()
                .spec(Specification.getSpecification())
                .body(order)
                .log().all()
                .post(Paths.ORDER_PATH);
        response.then().log().all();

        return response;
    }

    public Response getAllIngredients() {
        return given()
                .spec(Specification.getSpecification())
                .log().all()
                .get(Paths.INGREDIENTS_PATH);
    }
}
