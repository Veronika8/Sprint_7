import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateCourierTest {

    String login = "hkhdfdk";
    String password="123789";
    String firstName="saske";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

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
    public Response sendPostRequestLogin() {
        Courier courier=new Courier(login,password);
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
    public void sendDeleteCourier(int id) {
        given()
                .header("Content-type" , "application/json")
                .when()
                .delete("/api/v1/courier/"+id)
                .then().assertThat().statusCode(200);
    }

    @Test
    public void checkCreateCourierTest() {
        Courier courier = new Courier(login,password,firstName) ;
        Response response1 = sendPostRequestCourier(courier);
        response1.then().assertThat().statusCode(201)
                .body("ok",equalTo(true));

        Response response = sendPostRequestLogin();
        int id = response.then().extract().body().path("id");
        sendDeleteCourier(id);
    }

    @Test
    public void forbiddenCreateTwoCouriersTest() {
        Courier courier = new Courier(login,password,firstName);
        Response response = sendPostRequestCourier(courier);
        response.then().assertThat().statusCode(201)
                .body("ok",equalTo(true));

        Response response1 = sendPostRequestCourier(courier);
        response1.then().assertThat().statusCode(409)
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));

        Response response2 = sendPostRequestLogin();
        int id = response2.then().extract().body().path("id");
        sendDeleteCourier(id);
    }

    @Test
    public void forbiddenCreateCourierWithoutLoginTest() {
        Courier courier = new Courier(2,password);
        Response response = sendPostRequestCourier(courier);
        response.then().assertThat().statusCode(400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void forbiddenCreateCourierWithoutPasswordTest() {
        Courier courier = new Courier(1,login);
        Response response = sendPostRequestCourier(courier);
        response.then().assertThat().statusCode(400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
}
