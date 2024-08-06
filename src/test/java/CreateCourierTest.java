import api.model.Courier;
import api.client.CourierClient;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends BaseTest {

    String login = "hkhgdgfdfdk";
    String password="1237343489";
    String firstName="saske";

    CourierClient courierClient=new CourierClient();
    Courier courier = new Courier(login,password,firstName);

    @Test
    public void checkCreateCourierTest() {

        Response response1 = courierClient.sendPostRequestCourier(courier);
        response1.then().assertThat().statusCode(201)
                .body("ok",equalTo(true));

        courierClient.sendDeleteCourier(courier);
    }

    @Test
    public void forbiddenCreateTwoCouriersTest() {

        Response response = courierClient.sendPostRequestCourier(courier);
        response.then().assertThat().statusCode(201)
                .body("ok",equalTo(true));

        Response response1 = courierClient.sendPostRequestCourier(courier);
        response1.then().assertThat().statusCode(409)
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));

        courierClient.sendDeleteCourier(courier);
    }

    @Test
    public void forbiddenCreateCourierWithoutLoginTest() {
        Courier courier = new Courier(2,password);

        Response response = courierClient.sendPostRequestCourier(courier);
        response.then().assertThat().statusCode(400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void forbiddenCreateCourierWithoutPasswordTest() {
        Courier courier = new Courier(1,login);

        Response response = courierClient.sendPostRequestCourier(courier);
        response.then().assertThat().statusCode(400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
}
