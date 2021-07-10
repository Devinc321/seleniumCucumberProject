package api_tests;

import static org.testng.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class PetStoreTests {

	String url;
	File requestBody = new File("./src/test/resources/jsonFiles/AddNewPet.json");
	Response response;

	@Test
	public void f() {
		// structure request url with parameters
		// Basic Get command
//	//	given().queryParam("status", "available").accept(ContentType.JSON)
//	  .when().get(url)
//	  .then().statusCode(200)
//	  .and().contentType("application/json");  
		url = "https://petstore.swagger.io/v2/pet/findByStatus";
		// validating the response using RestAssured build in validation
		response = given().queryParam("status", "available").accept(ContentType.JSON).when().get(url);
		System.out.println(response.getContentType());
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString()); // prints all responses if in sysout
		// response.prettyPrint(); // prints all the responses

		// Validating the response using testng assertion
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.contentType(), "application/json");
	}

	public void findByWithInvalidUrl() {
		url = "https://petstore.swagger.io/v2/pet/findByStats"; // invalid test: missing u in status
		response = given().queryParam("status", "available").accept(ContentType.JSON).when().get(url);

		// option 1 to assert
		response.prettyPrint();
		response.then().assertThat().statusCode(404).and().assertThat().contentType("application/json");

		//// option 2 to assert
		assertEquals(response.getStatusCode(), 404);
		assertEquals(response.getContentType(), "application/json");

	}

	/*
	 * Scenario: As a user, I should be able to perform GET request to find a pet by
	 * id Given I have the GET request URL When I perform GET request to URL with
	 * pet id 227007 Then Response status code should be 200 And content type should
	 * be "application/json" And pet id is 227007, pet name is "booboo" status is
	 * "available" And validate category id is 5, category name is dog And validate
	 * tags id is 1234, and tags name is booboodc
	 */

	@Test
	public void findByID() {
		int id = 227007;
		url = "https://petstore.swagger.io/v2/pet/";

		response = given().accept(ContentType.JSON).when().get(url + id);

		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");

		// storing the path of the parent node into actualID, actualName, and
		// actualStatus
		int actualID = response.path("id");
		System.out.println("Actual Id is: " + actualID);
		String actualName = response.path("name");
		System.out.println("Actual name is: " + actualName);
		String actualStatus = response.path("status");
		System.out.println("Actual status is: " + actualStatus);

		// Validating with assertions
		assertEquals(actualID, id);
		assertEquals(actualName, "booboo");
		assertEquals(actualStatus, "available");

		// category parent node is not an array so no need to index
		int categoryID = response.path("category.id");
		String categoryName = response.path("category.name");

		System.out.println("CategoryId is: " + categoryID);
		System.out.println("CategoryName is: " + categoryName);

		assertEquals(categoryID, 5);
		assertEquals(categoryName, "dog");

		// tags parent node is an array so you need to specify the index
		int tagsID = response.path("tags[0].id");
		String tagsName = response.path("tags[0].name");

		System.out.println("Tags name is: " + tagsName);
		System.out.println("Tags ID is: " + tagsID);

		assertEquals(tagsID, 1234);
		assertEquals(tagsName, "booboodc");
	}

	@Test
	public void findByInvalidID() {
		int invalidID = 2792200;
		url = "https://petstore.swagger.io/v2/pet/";

		response = given().accept(ContentType.JSON).when().get(url + invalidID);

		assertEquals(response.statusCode(), 404);
		assertEquals(response.contentType(), "application/json");

		// validating the response message for when a pet is not found
		assertEquals(response.path("message"), "Pet not found");

	}

	@Test
	public void addNewPetWithJsonFile() throws IOException {
		String url = "https://petstore.swagger.io/v2/pet";
		File requestBody = new File("./src/test/resources/jsonFiles/AddNewPet.json");

		String content = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsonFiles/AddNewPet.json")));

		response = given().contentType("application/json").accept(ContentType.JSON).body(requestBody).when().post(url);

		assertEquals(response.statusCode(), 200);
		assertEquals(response.contentType(), "application/json");
		String responseBody = response.body().asPrettyString();
		response.body().prettyPrint();

		//assertEquals(responseBody, content);
	}

	@Test
	public void addNewPetWithChainValidation() throws IOException {
		String url = "https://petstore.swagger.io/v2/pet";
		File requestBody = new File("./src/test/resources/jsonFiles/AddNewPet.json");

	//	String content = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsonFiles/AddNewPet.json")));

		given().contentType("application/json").accept(ContentType.JSON).body(requestBody).when().post(url).then()
				.assertThat().statusCode(200)
				.and().assertThat().contentType("application/json")
				.and().assertThat().body("id", equalTo(3305474))
				.and().assertThat().body("category.id", equalTo(2))
				.and().assertThat().body("category.name", equalTo("cat"));
			

		
	}

}
