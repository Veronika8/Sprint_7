import api.model.Courier;
import api.client.CourierClient;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest extends BaseTest {

    String login = "rwerewfkhkwerjhkjhkjhklkj";
    String password="1224324789";
    String firstName="saske";

    CourierClient courierClient=new CourierClient();
    Courier courier = new Courier(login,password,firstName);

    @Test
    public void checkLoginCourierTest()
    {
        Response response = courierClient.sendPostRequestCourier(courier);
        response.then().assertThat().statusCode(201)
                .body("ok",equalTo(true));

        courierClient.sendDeleteCourier(courier);
    }

    @Test
    public void forbiddenLoginCourierWithoutLoginTest()
    {
        Response response1 = courierClient.sendPostRequestCourier(courier);
        response1.then().assertThat().statusCode(201)
                .body("ok",equalTo(true));


        Courier courier1=new Courier(2,password);
        Response response2 = given()
                .header("Content-type", "application/json")
                .body(courier1)
                .when()
                .post("/api/v1/courier/login");
        response2.then().assertThat().statusCode(400)
                .and().body("message",equalTo("Недостаточно данных для входа"));

        courierClient.sendDeleteCourier(courier);
    }

    @Test
    public void forbiddenLoginCourierWithoutPasswordTest()
    {
        //Courier courier1 = new Courier(login,password,firstName);
        //CourierClient courierClient=new CourierClient();

        Response response1 = courierClient.sendPostRequestCourier(courier);
        response1.then().assertThat().statusCode(201)
                .body("ok",equalTo(true));

        Courier courier=new Courier(1,login);
        Response response2 = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response2.then().assertThat().statusCode(504);

        courierClient.sendDeleteCourier(courier);
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
