import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPath {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(payload.CoursePrice());

        // print number of courses
        int count = js.getInt("courses.size()");
        System.out.println(count);

        // print purchase amount
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println((purchaseAmount));

        // print title of the first course
        String firstCourseTitle = js.getString("courses[0].title");
        System.out.println((firstCourseTitle));

        // print all courses titles and their respective Prices
        for (int i = 0; i < count; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            int price = js.getInt("courses[" + i + "].price");
            System.out.println(courseTitle);
            System.out.println(price);
        }

        // print number of courses sold by RPA Course
        for (int i = 0; i < count; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copies = js.getInt("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }
    }
}
