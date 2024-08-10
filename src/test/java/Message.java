import static io.restassured.RestAssured.given;
// POJO Classes

public static class Message {
    private String message;
    private String greet;

    public String getMessage() {
        return message;
    }

    public String getGreet() {
        return greet;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setGreet(String greet) {
        this.greet = greet;
    }
};

public void main() {
    Message m = new Message();
    m.setMessage("Hello");
    m.setGreet("Hi");

    given().body(m).when().post("/message");
}

