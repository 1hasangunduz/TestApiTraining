import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class restAssuredTest {

    @Test
    public void singleUserBddTest() {
        when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.email", equalTo("janet.weaver@reqres.in")) // data altındaki emaili kontrol ediyoruz aslında.
                .time(lessThan(1000L))//L long oluyor 1 saniyeden kısa sürsün istiyoruz. Fazla olursa fail versin.
;

    }

    @Test
    public void anotherTypeApiRequest(){
        Response response = get("https://reqres.in/api/users/2");

        System.out.println("Page status code;"+response.getStatusCode());
        System.out.println("Body: "+ response.then().body("data.email",equalTo("janet.weaver@reqres.in")));
        System.out.println("Content Type: "+ response.getContentType());
        System.out.println("Cookie: "+ response.getDetailedCookie("hasan"));
        System.out.println("Header: "+ response.getHeader("data"));
        System.out.println("not found 404: "+ get("https://reqres.in/api/users/23").getStatusCode());
        System.out.println("id control: "+ response.then().body("data.id",equalTo(2)));
        System.out.println("deneme: "+ response.getBody().asString());//body içindeki verileri aldım.


    }

    @Test
    public void  differentPerspective(){

       RestAssured.baseURI="https://reqres.in";
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get("/api/users/2");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        System.out.println(bodyAsString);
        Assert.assertTrue(bodyAsString.contains("janet.weaver@reqres.in"),"Succesful! ");

       //System.out.println("hasan: "+get("/api/users/2").statusCode());

    }

    @Test
    public void  createUsersTest(){
        String postData  = " {\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given() //sana veri göndereceğim diyor.
                .contentType(ContentType.JSON) //ben sana json formatında bir veri gönderiyorum diyor.
                .body(postData)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name",equalTo("morpheus"));
                System.out.println(postData);
    }



}

