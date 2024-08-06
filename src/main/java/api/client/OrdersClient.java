package api.client;

import api.model.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersClient {

    @Step("Send to get /api/v1/orders")
    public void sendGetRequestOrders() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .get("/api/v1/orders");
        response.then().assertThat().statusCode(200)
                .body("orders",notNullValue());
    }

    @Step("Send to post /api/v1/orders")
    public void sendPostRequestOrder(Order order) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().statusCode(201)
                .body("track",notNullValue());
    }
}
