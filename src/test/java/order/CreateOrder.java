package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import user.CreateUserExample;
import order.CreateOrder;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;

public class CreateOrder {

    private CreateUserExample createUserExample;
    private CreateOrderExample createOrderExample;
    private int id;
    private Order order;


    @Before
    public void setUp() {
        createUserExample = new CreateUserExample();
        Order order = new Order();

    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с авторизацией")
    public void createWithAuth() {
        String username = "your_username";
        String password = "your_password";

        String token = loginAndGetToken(username, password);

        Order order = new Order();
        Response response = CreateOrderExample.createOrderWithAuthorization(order, token);


        response.then()
                .statusCode(SC_CREATED);
    }

    private String loginAndGetToken(String username, String password) {
        Response loginResponse = given()
                .contentType(ContentType.JSON)
              //  .body(new LoginRequest(username, password))
                .when()
                .post("/api/login");

        return loginResponse.jsonPath().getString("token");

        }
    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа без авторизации")
    public void createWithOutAuth() {
        Response response = createOrderExample.getAllIngredients();
       // Response response = CreateOrderExample.createOrderWithoutAuthorization(order);
        response.then().log().all()
                .assertThat()
                .statusCode(401);
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients () {

    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с плохим хэшом")
    public void createOrderWithBadHash() {


    }



}
