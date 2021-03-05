package postRequest;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PostData {
	
	static Response response;
	
	static int statusCode;

	public static void setup() {
		
		RestAssured.baseURI = "http://localhost:8080/pessoas";
	}

	public static int inserirUsuarioNaApi(String nome, String cpf, String logradouro, int numero,
			String complemento, String bairro, String cidade, String estado, String ddd, String telefone) {
		try {
			response = RestAssured.
					given()
					.relaxedHTTPSValidation()
					.header("Content-Type","application/json")
					.body("{\r\n" + 
							"   \"codigo\":0,\r\n" + 
							"   \"nome\":\""+nome+"\",\r\n" + 
							"   \"cpf\":\""+cpf+"\",\r\n" + 
							"   \"enderecos\":[\r\n" + 
							"      {\r\n" + 
							"         \"logradouro\":\""+logradouro+"\",\r\n" + 
							"         \"numero\":"+numero+",\r\n" + 
							"         \"complemento\":\""+complemento+"\",\r\n" + 
							"         \"bairro\":\""+bairro+"\",\r\n" + 
							"         \"cidade\":\""+cidade+"\",\r\n" + 
							"         \"estado\":\""+estado+"\"\r\n" + 
							"      }\r\n" + 
							"   ],\r\n" + 
							"   \"telefones\":[\r\n" + 
							"      {\r\n" + 
							"         \"ddd\":\""+ddd+"\",\r\n" + 
							"         \"numero\":\""+telefone+"\"\r\n" + 
							"      }\r\n" + 
							"   ]\r\n" + 
							"}")
					.log().all()
					.when()
					.post("pessoas")
					.then()
					.extract()
					.response();
			
			System.out.println(response.body().asString());
			
			statusCode = response.getStatusCode();
			
			System.out.println("Código de status de retorno da Api: "+statusCode);
			
		} catch (Exception e) {
			System.out.println("Erro ao cadastrar usuário na Api: "+e);
		}
		return statusCode;
	}
	
	public static void verificarSucessoNaInclusaoDeUsuario(int codigoStatus) {
		try {
			if (codigoStatus == 201) {
				System.out.println("Usuário registrado com sucesso na Api com código de status:" + statusCode);
			} else {
				System.out.println("Não foi possível registrar o usuário!");
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado: "+e);
		}
	}
	
	public static boolean verificarDuplicidadeDeCPFNaInclusaoDeUsuario(String cpf) {
		try {
			Response response = RestAssured.
					given()
					.relaxedHTTPSValidation()
					.header("Content-Type","application/json")
					.body("{\r\n" + 
							"    \"codigo\":0,\r\n" + 
							"    \"nome\":\"Leandro Lucas\",\r\n" + 
							"    \"cpf\":\""+cpf+"\",\r\n" + 
							"    \"enderecos\": [\r\n" + 
							"        {\r\n" + 
							"            \"logradouro\":\"Rua Teste Dois\",\r\n" + 
							"            \"numero\":333,\r\n" + 
							"            \"complemento\":\"Empresa\",\r\n" + 
							"            \"bairro\":\"Novo Mundo Dois\",\r\n" + 
							"            \"cidade\":\"Valinhos\",\r\n" + 
							"            \"estado\":\"SP\"\r\n" + 
							"        }\r\n" + 
							"    ],\r\n" + 
							"    \"telefones\": [\r\n" + 
							"        {\r\n" + 
							"            \"ddd\":\"12\",\r\n" + 
							"            \"numero\":\"909000987\"\r\n" + 
							"        }\r\n" + 
							"    ]\r\n" + 
							"}")
					.log().all()
					.when()
					.post("pessoas")
					.then()
					.extract()
					.response();
			
			String cpfDuplicadoUsuario = response.jsonPath().getString("erro");
			
			System.out.println("Informando duplicidade de CPF do usuário: "+cpfDuplicadoUsuario);
			
			assertTrue(cpfDuplicadoUsuario.contains(cpf));
			
			return true;
			
		} catch (Exception e) {
			Assert.fail("Ocorreu um erro inesperado: "+e);
			return false;
		}
	}
	
	public static boolean verificarDuplicidadeDeTelefoneNaInclusaoDeUsuario(String cpf, String ddd, String telefone) {
		try {
			Response response = RestAssured.
					given()
					.relaxedHTTPSValidation()
					.header("Content-Type","application/json")
					.body("{\r\n" + 
							"    \"codigo\":0,\r\n" + 
							"    \"nome\":\"Leandro Lucas\",\r\n" + 
							"    \"cpf\":\""+cpf+"\",\r\n" + 
							"    \"enderecos\": [\r\n" + 
							"        {\r\n" + 
							"            \"logradouro\":\"Rua Teste Dois\",\r\n" + 
							"            \"numero\":333,\r\n" + 
							"            \"complemento\":\"Empresa\",\r\n" + 
							"            \"bairro\":\"Novo Mundo Dois\",\r\n" + 
							"            \"cidade\":\"Valinhos\",\r\n" + 
							"            \"estado\":\"SP\"\r\n" + 
							"        }\r\n" + 
							"    ],\r\n" + 
							"    \"telefones\": [\r\n" + 
							"        {\r\n" + 
							"            \"ddd\":\""+ddd+"\",\r\n" + 
							"            \"numero\":\""+telefone+"\"\r\n" + 
							"        }\r\n" + 
							"    ]\r\n" + 
							"}")
					.log().all()
					.when()
					.post("pessoas")
					.then()
					.extract()
					.response();
			
			String telefoneDuplicadoUsuario = response.jsonPath().getString("erro");
			
			System.out.println("Informando duplicidade de Telefone do usuário: "+telefoneDuplicadoUsuario);
			
			assertTrue(telefoneDuplicadoUsuario.contains(telefone));
			
			return true;
			
		} catch (Exception e) {
			Assert.fail("Ocorreu um erro inesperado: "+e);
			return false;
		}
	}
}
