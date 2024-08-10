import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojoGoogleMaps.AddPlace;
import pojoGoogleMaps.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;


public class GoggleMaps {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setName("Frontline house");
        p.setPhone_number("(+91) 983 893 393");
        p.setAddress("29, side layout, cohen 09");
        p.setWebsite("http://google.com");
        p.setLanguage("French-IN");
        List<String> types = new ArrayList<String>();
        types.add("shoe park");
        types.add("shop");
        p.setTypes(types);
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        p.setLocation(location);

        Response response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(p).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .extract().response();
        System.out.println(response.asString());
    }
}
