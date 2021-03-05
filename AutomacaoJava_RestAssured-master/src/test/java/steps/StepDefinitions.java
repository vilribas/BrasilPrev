package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import getRequest.GetData;
import postRequest.PostData;
import utils.GeradorDeCpf;

public class StepDefinitions {
	static boolean verificador;
	static String mensagem;
	static int statusCode;
	static String verificarUsuarioCadastrado;

	@Given("^que o Usuario insere \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", (\\d+), \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" e \"([^\"]*)\" na Api$")
	public void inserirDadosNaApi(String nome, String cpf, String logradouro, int numero, String complemento,
			String bairro, String cidade, String estado, String ddd, String telefone) throws Throwable {
		try {
			statusCode = PostData.inserirUsuarioNaApi(nome, cpf, logradouro, numero, complemento, bairro, cidade, estado, ddd, telefone);
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado: "+e);
		}
	}

	@Then("^a API efetua o cadastro do Usuario com sucesso$")
	public void a_API_efetua_o_cadastro_do_Usuario_com_sucesso() throws Throwable {
		try {
			PostData.verificarSucessoNaInclusaoDeUsuario(statusCode);
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado: "+e);
		}
	}
	
	@Given("^que o Usuario insere CPF \"([^\"]*)\" duplicado$")
	public void que_o_Usuario_insere_CPF_duplicado(String cpf) throws Throwable {
		try {
			verificador = PostData.verificarDuplicidadeDeCPFNaInclusaoDeUsuario(cpf);
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado: "+e);
		}
	}
	
	@Then("^a API nao efetua o cadastro do Usuario com CPF \"([^\"]*)\" duplicado$")
	public void a_API_nao_efetua_o_cadastro_do_Usuario_com_CPF_duplicado(String cpfDuplicado) throws Throwable {
	    try {
			if (verificador == true) {
				System.out.println("Usuário duplicado na base de dados, CPF: "+cpfDuplicado);
			} else {
				System.out.println("Usuário não está com CPF duplicado na base de dados!");
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado: "+e);
		}
	}
	
	@Given("^que o Usuario insere DDD \"([^\"]*)\" e Telefone \"([^\"]*)\" duplicados$")
	public void que_o_Usuario_insere_DDD_e_Telefone_duplicados(String ddd, String telefone) throws Throwable {
		try {
			String cpfFake = GeradorDeCpf.retornarCpf();
			   verificador = PostData.verificarDuplicidadeDeTelefoneNaInclusaoDeUsuario(cpfFake, ddd, telefone);
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado: "+e);
		}
	}
	
	@Then("^a API nao efetua o cadastro do Usuario com DDD \"([^\"]*)\" e Telefone \"([^\"]*)\" duplicados$")
	public void a_API_nao_efetua_o_cadastro_do_Usuario_com_DDD_e_Telefone_duplicados(String ddd, String telefoneDuplicado) throws Throwable {
		try {
			if (verificador == true) {
				System.out.println("Usuário duplicado na base de dados, Telefone: "+"("+ddd+")"+telefoneDuplicado);
			} else {
				System.out.println("Usuário não está com Telefone duplicado na base de dados!");
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado: "+e);
		}
	}
	
	@Given("^que o Usuario insere DDD \"([^\"]*)\" e Telefone \"([^\"]*)\" na busca$")
	public void que_o_Usuario_insere_DDD_e_Telefone_na_busca(String ddd, String telefone) throws Throwable {
		try {
			verificarUsuarioCadastrado = GetData.BuscarUsuarioPorDDDeTelefone(ddd, telefone);
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado: "+e);
		}
	}

	@Then("^a API retorna o usuario cadastrado na base de dados com DDD \"([^\"]*)\" e Telefone \"([^\"]*)\"$")
	public void a_API_retorna_o_usuario_cadastrado_na_base_de_dados_com_DDD_e_Telefone(String ddd, String telefone) throws Throwable {
		try {
			if (verificarUsuarioCadastrado.contains("Não existe pessoa com o telefone")) {
				System.out.println("Usuário com DDD "+ddd+" e Telefone "+telefone+" não existe na base de dados");
			} else {
				System.out.println("Usuário "+verificarUsuarioCadastrado+" com DDD "+ddd+" e telefone "+telefone+" cadastado na base de dados");
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado: "+e);
		}
	}
}
