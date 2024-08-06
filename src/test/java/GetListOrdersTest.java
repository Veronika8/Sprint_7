import api.client.OrdersClient;
import org.junit.Test;

public class GetListOrdersTest extends BaseTest {

    @Test
    public void checkGetListOrdersTest() {
        OrdersClient ordersClient=new OrdersClient();
        ordersClient.sendGetRequestOrders();
    }
}
