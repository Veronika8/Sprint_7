import java.util.Arrays;
import java.util.List;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> colors ;

    public Order(int number,String firstName, String lastName, String address, int metroStation, String phone, int rentTime,
                 String deliveryDate, String comment, String colors) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.metroStation=metroStation;
        this.phone=phone;
        this.rentTime=rentTime;
        this.deliveryDate=deliveryDate;
        this.comment=comment;
        if(number==1) {
            this.colors=Arrays.asList(colors);
        }
    }
}
