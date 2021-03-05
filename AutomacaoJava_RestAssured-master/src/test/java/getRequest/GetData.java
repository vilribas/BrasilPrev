package getRequest;

import static org.junit.Assert.assertTrue;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetData {

	static String jsonendpoint = "http://localhost:8080/pessoas";

	static JsonPath jsonPath;

	public static String BuscarUsuarioPorDDDeTelefone(String ddd, String numero) {

		try {

			Response response = RestAssured.given().pathParam("ddd", ddd).pathParam("numero", numero).when()
					.get(jsonendpoint + "/{ddd}/{numero}").andReturn();
			jsonPath = new JsonPath(response.body().asString());

			String nome_do_usuario = jsonPath.getString("nome");

			assertTrue(jsonPath.getString("nome").contains(nome_do_usuario));

			return jsonPath.getString("nome");

		} catch (Exception e) {
			return jsonPath.getString("erro");
		}
	}
}
