package order;
import generate.random.GenerateRandomUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.CreateUserExample;
import user.User;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;


public class CreateOrderTest {

    private CreateUserExample createUserExample;
    private CreateOrderExample createOrderExample;
    private int id;


    @Before
    public void setUp() {
        createUserExample = new CreateUserExample();

    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с авторизацией")
    public void createWithAuth() {
        // Генерация случайного пользователя
        User user = GenerateRandomUser.getRandomUser();

        // Создание пользователя и получение токена
        Response createResponse = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        String token = loginResponse.jsonPath().getString("accessToken");

        // Создание заказа с указанным ингредиентом
        Order order = new Order();
        String ingredientId = "61c0c5a71d1f82001bdaaa6d"; // ID ингредиента
        order.setIngredients(Collections.singletonList(ingredientId)); // Добавляем выбранный ингредиент

        // Отправка запроса на создание заказа
        Response createOrderResponse = createOrderExample.createOrder(order, token);

        // Проверка успешности создания заказа
        createOrderResponse.then()
                .statusCode(200) // Проверяем, что статус ответа 200 (Created)
                .body("success", equalTo(true)); // Проверяем true

    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа без авторизации")
    public void createWithOutAuth() {
        // Создание заказа с указанным ингредиентом
        Order order = new Order();
        String ingredientId = "61c0c5a71d1f82001bdaaa6d"; // ID ингредиента
        order.setIngredients(Collections.singletonList(ingredientId)); // Добавляем выбранный ингредиент

        // Отправка запроса на создание заказа без авторизации
        Response createOrderResponse = createOrderExample.createOrder(order, ingredientId);

        // Проверка успешности создания заказа
        createOrderResponse.then()
                .statusCode(200) // Проверяем, что статус ответа 200 (Created)
                .body("success", equalTo(true)); // Проверяем true
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients () {
        // Генерация случайного пользователя
        User user = GenerateRandomUser.getRandomUser();

        // Создание пользователя и получение токена
        Response createResponse = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        String token = loginResponse.jsonPath().getString("accessToken");

        // Создание заказа с указанным ингредиентом
        Order order = new Order();
        order.setIngredients(Collections.emptyList()); // пустой список ингредиентов


        // Отправка запроса на создание заказа
        Response createOrderResponse = createOrderExample.createOrder(order, token);

        // Проверка успешности создания заказа
        createOrderResponse.then()
                .statusCode(400) // Проверяем, что статус ответа 400
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с плохим хэшом")
    public void createOrderWithBadHash() {
        // Генерация случайного пользователя
        User user = GenerateRandomUser.getRandomUser();

        // Создание пользователя и получение токена
        Response createResponse = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        String token = loginResponse.jsonPath().getString("accessToken");

        // Создание заказа с указанным ингредиентом
        Order order = new Order();
        String ingredientId = "erer566"; // ID ингредиента
        order.setIngredients(Collections.singletonList(ingredientId)); // Добавляем выбранный ингредиент

        // Отправка запроса на создание заказа
        Response createOrderResponse = createOrderExample.createOrder(order, token);

        // Проверка успешности создания заказа
        createOrderResponse.then()
                .statusCode(500); // Проверяем, что статус ответа 500

    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с несколькими ингредиентами")
    public void createWithIngredients() {
        // Генерация случайного пользователя
        User user = GenerateRandomUser.getRandomUser();

        // Создание пользователя и получение токена
        Response createResponse = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        String token = loginResponse.jsonPath().getString("accessToken");

        // Создание заказа с указанным ингредиентом
        Order order = new Order();
        List<String> ingredientIds = Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d", // ID первого ингредиента
                "61c0c5a71d1f82001bdaaa6f", // ID второго ингредиента
                "61c0c5a71d1f82001bdaaa70"  // ID третьего ингредиента
        );
        order.setIngredients(ingredientIds); // Добавляем выбранные ингредиенты

        // Отправка запроса на создание заказа
        Response createOrderResponse = createOrderExample.createOrder(order, token);

        // Проверка успешности создания заказа
        createOrderResponse.then()
                .statusCode(200) // Проверяем, что статус ответа 200 (Created)
                .body("success", equalTo(true)); // Проверяем true

    }

    @After
    public void tearDown() {
        if (id != 0) {
            Response deleteResponse = createUserExample.deleteUser(id);
            deleteResponse
                    .then()
                    .assertThat()
                    .statusCode(SC_OK)
                    .body("success", equalTo(true));
        }
    }



}
