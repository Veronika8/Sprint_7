package api.model;

public class Courier {

    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login=login;
        this.password=password;
        this.firstName=firstName;
    }
    public Courier(String login, String password) {
        this.login=login;
        this.password=password;
    }

    public Courier(int number, String parametr) {
        if(number==1)
            this.login=parametr;
        else if(number==2)
            this.password=parametr;
    }
}
