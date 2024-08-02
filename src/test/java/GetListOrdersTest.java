import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetListOrdersTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Send to get /api/v1/orders")
    public void sendGetRequestOrders() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .get("/api/v1/orders")  ;
        response.then().assertThat().statusCode(200)
                .body("orders",notNullValue());
    }
    @Test
    public void checkGetListOrdersTest() {
        sendGetRequestOrders();
    }
}
