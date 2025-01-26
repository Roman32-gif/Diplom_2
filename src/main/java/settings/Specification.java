package settings;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specification {
    public static final String BASE_URL ="https://stellarburgers.nomoreparties.site/";

    protected static RequestSpecification getSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
}
}
