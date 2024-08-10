import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import java.util.List;

import static io.restassured.RestAssured.given;

/*
Get Courses Details Endpoint:
URL: https://rahulshettyacademy.com/oauthapi/getCourseDetails
HTTP Method: GET
Query Parameter: access_key

Authorization Server Endpoint:
URL: https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token
Form Parameters:
1. client_id = 692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com
2. client_secret = erZOWM9g3UtwNRj340YYaK_W
3. grant_type = client_credentials
4. scope = trust
 */

public class GetCourseDetails {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com/oauthapi/";
        String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust").when().post("oauth2/resourceOwner/token")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js1 = ReusableMethods.rawToJson(response);
        String accessToken = js1.getString("access_token");
        System.out.println(response);
        System.out.println(accessToken);

        // get Java object = deserialization
        GetCourse coursesDetails = given().log().all().queryParam("access_token", accessToken).when().get("getCourseDetails")
                .then().log().all().log().all()
                .extract().response().as(GetCourse.class);

        System.out.println(coursesDetails.getLinkedIn());
        System.out.println(coursesDetails.getInstructor());

        System.out.println(coursesDetails.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = coursesDetails.getCourses().getApi();
        for(int i = 0; i < apiCourses.size(); i++) {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }
    }
}
