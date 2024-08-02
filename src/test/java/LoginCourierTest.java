import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {

    String login = "rwerewfkhkjhkjhkjhklkj";
    String password="12324789";
    String firstName="saske";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Send to post /api/v1/courier")
    public void sendPostRequestCourier() {
        Courier courier = new Courier(login,password,firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().statusCode(201)
                .body("ok",equalTo(true));
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
        return response  ;
    }

    @Step("Send to delete /api/v1/courier/id")
    public void sendDeleteCourier(int id) {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/"+id)
                .then().assertThat().statusCode(200);
    }

    @Test
    public void checkLoginCourierTest()
    {
        sendPostRequestCourier();
        Response response = sendPostRequestLogin();
        int id = response.then().extract().body().path("id");
        sendDeleteCourier(id);
    }

    @Test
    public void forbiddenLoginCourierWithoutLoginTest()
    {
        sendPostRequestCourier();
        Response response = sendPostRequestLogin();
        int id = response.then().extract().body().path("id");


        Courier courier=new Courier(2,password);
        Response response2 = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response2.then().assertThat().statusCode(400)
                .and().body("message",equalTo("Недостаточно данных для входа"));

        sendDeleteCourier(id);
    }

    @Test
    public void forbiddenLoginCourierWithoutPasswordTest()
    {
        sendPostRequestCourier();
        Response response = sendPostRequestLogin();
        int id = response.then().extract().body().path("id");

        Courier courier=new Courier(1,login);
        Response response2 = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response2.then().assertThat().statusCode(504);

        sendDeleteCourier(id);
    }

    @Test
    public void NonExistentUserTest() {
        Courier courier=new Courier(login+"1313",password+"23123");
        Response response1 = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response1.then().assertThat().statusCode(404)
                .and().body("message",equalTo("Учетная запись не найдена"));
    }
}
