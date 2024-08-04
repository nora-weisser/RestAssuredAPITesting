import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        // validate if Add Place API is working as expected
        // Add place -> Update Place with New Address -> Get Place to validate if New Address is present in response
        // given - all input details
        // when - submit the API, resource, http method
        // then - validate the response

        // convert json to string - new String(Files.readAllBytes(Paths.get("fdvbdfgbv d")))


        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.AddPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
        System.out.println(response);
        JsonPath js = new JsonPath(response); // for parsing JSON
        String placeId = js.getString("place_id");

        System.out.println(placeId);

        String newAddress = "70 winter walk, USA";

        // Update Place
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        // GET Place
        String getPlaceResponse = given().log().all().queryParam("place_id", placeId).queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).body("address", equalTo(newAddress)).extract().response().asString();
        JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);

        // Cucumber JUnit, TestNG
        Assert.assertEquals(actualAddress, newAddress);
    }
}
