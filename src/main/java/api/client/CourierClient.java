package api.client;

import api.model.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierClient {

    @Step("Send to post /api/v1/courier")
    public Response sendPostRequestCourier(Courier courier){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        return response;
    }

    @Step("Send to post /api/v1/courier/login")
    public Response sendPostRequestLogin(Courier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(200)
                .and().body("id",notNullValue());
        return response;
    }
    @Step("Send to delete /api/v1/courier/id")
    public void sendDeleteCourier(Courier courier) {
        int id = sendPostRequestLogin(courier).then().extract().body().path("id");
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/"+id)
                .then().assertThat().statusCode(200);
    }
}
