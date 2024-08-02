import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    String firstName="Иван";
    String lastName="Иванов";
    String address="Москва Ленина 11"  ;
    int metroStation=2;
    String phone="+7 888 355 35 35";
    int rentTime=2;
    String deliveryDate="2025-06-06";
    String comment="Коммент";
    private final String colors;
    private final int number;

    public CreateOrderTest(int number, String colors) {
        this.number=number;
        this.colors = colors;
    }

    @Parameterized.Parameters // добавили аннотацию
    public static Object[][] getOrderData() {
        return new Object[][]{
                {1,"GREY"},
                {1,"BLACK"},
                {1,"\"BLACK\",\"GREY\""},
                {2,""},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void checkCreateOrderWithColorsTest() {
        Order order=new Order(number,firstName,lastName,address,metroStation,phone,rentTime,deliveryDate,comment,colors);
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
