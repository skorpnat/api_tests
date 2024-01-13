package tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class UserApiTest {

  String  URI = "https://reqres.in";
    @Test
    @DisplayName("Получение данных пользователя")
    public void testGetUserData() {
        given().
                baseUri(URI).
        when().
                log().all().
                get("/api/users/2").
        then().
                assertThat().
                statusCode(200).
                body("data.id", is(2)).
                body("data.email", is("janet.weaver@reqres.in")).
                body("data.first_name", is("Janet")).
                body("data.last_name", is("Weaver"));

    }
    @Test
    @DisplayName("Получение данных из раздела support про пользователя")
    public void testGetUserSupportData() {
        given().
                baseUri(URI).
        when().
                log().all().
                get("/api/users/3").
        then().
                assertThat().
                statusCode(200).
                body("support.url", is("https://reqres.in/#support-heading")).
                body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));               ;

    }
    @Test
    @DisplayName("Запрос данных на не существующего пользователя")
    public void testGetNotExistingUser() {
        given().
                baseUri(URI).
        when().
                log().all().
                get("/api/users/15").
        then().
                assertThat().
                statusCode(404).log().body();
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void testPostNewUser() {
      File jsonData = new File("src/test/resources/postuser.json");

        given().
                baseUri(URI).contentType(ContentType.JSON).
                body(jsonData).
        when().
                log().all().
                post("/api/users").
       then().
                assertThat().
                statusCode(201).log().all().
                body("name", is("morpheus")).
                body("job", is("leader"));
    }

  @Test
  @DisplayName("Удаление пользователя")
  public void testDeleteUser() {

    given().
            baseUri(URI).
    when().
            log().all().
            delete("/api/users/2").
    then().
            assertThat().
            statusCode(204);
  }

}
