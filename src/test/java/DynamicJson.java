import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJson {

    @Test(dataProvider="BooksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(payload.AddBook(isbn, aisle))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = ReusableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);

    }

    @DataProvider(name="BooksData")
    public Object[][] getData() {
        // array is collection of elements
        // multidimensional array is collection of arrays
        return new Object[][]{{"lsdmvskldv", "3409023"}, {"sdkvdfkjv", "9234923"}, {"sdjvbds", "28934293"}};
    }
}
