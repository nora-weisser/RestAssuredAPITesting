package EcommerceAPI.Tests;

import EcommerceAPI.pojoClasses.LoginRequest;
import EcommerceAPI.pojoClasses.LoginResponse;
import EcommerceAPI.pojoClasses.OrdersItemsRequest;
import EcommerceAPI.pojoClasses.OrdersRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class Test {
    public static void main(String[] args) {
        RequestSpecification request = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom").setContentType(ContentType.JSON).build();

        // Login and retrieve token and userId
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("rest.assured.test@test.com");
        loginRequest.setUserPassword("PracticeRest12@");
        RequestSpecification reqLogin = given().log().all().spec(request).body(loginRequest);
        LoginResponse response = reqLogin.when().post("/auth/login").then().log().all().extract().response().as(LoginResponse.class);
        String token = response.getToken();
        String userId = response.getUserId();
        System.out.println(token);
        System.out.println(userId);

        // Add Product
        RequestSpecification addProductBaseRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom").addHeader("Authorization", token).build();
        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseRequest).param("productName", "Laptop")
                .param("productAddedBy", userId).param("productCategory", "fashion").param("productSubCategory", "shirts")
                .param("productPrice", "11500").param("productDescription", "Addias Originals")
                .param("productFor", "women").multiPart("productImage", new File("/Users/eleonora.belova/Documents/Repositories/RestAssuredAPITesting/src/test/java/EcommerceAPI/Tests/laptop.jpg"));
        String addProductResponse = reqAddProduct.when().post("/product/add-product").then().extract().response().asString();
        JsonPath js = new JsonPath(addProductResponse);
        String productId = js.get("productId");
        System.out.println(productId);

        // Create an order
        RequestSpecification createOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom").addHeader("Authorization", token).setContentType(ContentType.JSON).build();

        OrdersItemsRequest orderItem = new OrdersItemsRequest();
        orderItem.setCountry("Armenia");
        orderItem.setProductOrderedId(productId);

        List<OrdersItemsRequest> orderItemsList = new ArrayList<OrdersItemsRequest>();
        orderItemsList.add(orderItem);
        OrdersRequest orders = new OrdersRequest();
        orders.setOrders(orderItemsList);

        RequestSpecification reqCreateOrder = given().log().all().spec(createOrder).body(orders);
        String responseAddOrder = reqCreateOrder.when().post("/order/create-order").then().log().all().extract().response().asString();

        System.out.println(responseAddOrder);

        // delete product
        RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
        RequestSpecification deleteProductRequest = given().log().all().spec(deleteProductBaseReq).pathParams("productId", productId);
        String deleteProductResponse = deleteProductRequest.when().delete("/product/delete-product/{productId}").then().log().all().extract().response().asString();
        JsonPath deleteProductResponseParsed = new JsonPath(deleteProductResponse);
        Assert.assertEquals("Product Deleted Successfully", deleteProductResponseParsed.get("message"));
    }
}
