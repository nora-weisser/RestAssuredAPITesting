import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;

public class BugTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://eleonorabelova16.atlassian.net";
        String createIssueResponse = given().header("Content-Type", "application/json").header("Accept", "application/json")
                .header("Authorization", "Basic ZWxlb25vcmEuYmVsb3ZhLjE2QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjB5eE5Zc2QtcDVmQkp6WUNjZ1R6Z1ZXdHZfdnR1MlFMUi04aGE2bEIyOHdTTXRTSHdwdkZ3a3dPT181ZXFvYU8wc1lpVEtyUXNXQjdvVEhVMkhtSzM1dlh0clZCa2t0SlJEQzd5cVNZZ1NKcGVqbzRfazV0V0Z2b2FRWTQtMURJaElLeEtET29HdnAyOWxTX2lKVWRRRVdyZy03TXZsLUNKa19sMFNiWUNQNms9MDNFQTU4QUM=")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Menu tabs are not working - automation\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .log().all()
                .when().post("/rest/api/3/issue").then().log().all().assertThat().statusCode(201)
                .extract().response().asString();
        JsonPath js = new JsonPath(createIssueResponse);
        String issueId = js.getString("id");
        System.out.println(issueId);

        // add attachments
        given().header("X-Atlassian-Token", "no-check")
                .header("Authorization", "Basic ZWxlb25vcmEuYmVsb3ZhLjE2QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjB5eE5Zc2QtcDVmQkp6WUNjZ1R6Z1ZXdHZfdnR1MlFMUi04aGE2bEIyOHdTTXRTSHdwdkZ3a3dPT181ZXFvYU8wc1lpVEtyUXNXQjdvVEhVMkhtSzM1dlh0clZCa2t0SlJEQzd5cVNZZ1NKcGVqbzRfazV0V0Z2b2FRWTQtMURJaElLeEtET29HdnAyOWxTX2lKVWRRRVdyZy03TXZsLUNKa19sMFNiWUNQNms9MDNFQTU4QUM=")
                .multiPart("file", new File("src/docs/attachment.png")).pathParams("key", issueId).log().all().post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
    }
}
