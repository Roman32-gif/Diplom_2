package order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import settings.Specification;

import static io.restassured.RestAssured.given;
import static settings.Paths.GET_ALL_ORDERS_PATH;

public class CreateOrderExample extends Specification{

    @Step("GET")
    public static Response getOrders(String accessToken) {
        // Создаем запрос с использованием спецификации
        RequestSpecification request = given()
                .spec(Specification.getSpecification())
                .log().all();

        // Добавляем заголовок Authorization только если accessToken не null
        if (accessToken != null) {
            request.header("Authorization", accessToken);
        }

        // Выполняем GET-запрос
        Response response = request.get(GET_ALL_ORDERS_PATH);
        response.then().log().all();

        return response;
    }

    @Step("POST")
    public static Response createOrder(Order order, String accessToken) {
        // Создаем запрос с использованием спецификации
        RequestSpecification request = given()
                .spec(Specification.getSpecification())
                .log().all()
                .header("Content-type", "application/json")
                .body(order);

        // Добавляем заголовок Authorization только если accessToken не null
        if (accessToken != null) {
            request.header("Authorization", accessToken);
        }

        // Выполняем POST-запрос
        Response response = request.post(GET_ALL_ORDERS_PATH);
        response.then().log().all();

        return response;
    }


}
