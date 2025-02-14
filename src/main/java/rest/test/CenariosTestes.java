package rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.response.Response;
import rest.core.BaseTest;

public class CenariosTestes extends BaseTest{  // Classe Cenários foi extendida á classe BaseTest
	@Test
	public void naoDevoAcessarAPISemToken() { // Cenário que tenta acessar a rota contas sem estar autenticado na API
		 given()
		.when()
			.get("/contas")                 //  Realiza um get na rota contas
		.then()
			.statusCode(401)               // Verifica a resposta da requisição , 401 não autorizado 
		;
		
	}
	
	
	@Test
	public void incluirContaComSucesso() { // Cenário para incluir uma nova conta 
		//.body("{\"email\", \"eduardo@teste\"}, {\"senha\", \"@teste\"}")  // Outra forma de enviar os dados , agora pelo própio body
		Map<String, String> login = new HashMap<>(); // Usando o Map para armazenar chave e valor que serão enviados no body 
		login.put("email","edu@testerest.com.br"); // atravez da variável login que foi criada com o Map é possível passar email e senha 
		login.put("senha","123456");
		
		 String token = given()  // Armazema o tokem após realizar o login na variável token 
			//.contentType(ContentType.JSON)
		 	.body(login)        //  Enviando os dados de login no body passando a varível login
		.when()
			.post("/signin")    // Rota para fazer o login 
		.then()
			.statusCode(200)  // verifica o status da requisição 
			.extract().path("token");  // Extrai o token após realizar o login
			
		given()
			.header("Authorization", "JWT " + token) // Envia o token que f0i extraído
			.body("{\"nome\":\"Sexta Conta\"}")  // Envia o nome da conta que será criada 
		.when()
			.post("/contas")                        // Rota para fazer o post 
		.then()
			.log().all()
			.statusCode(201)                       //  Verifica que a resposta da requisição , conta criada com sucesso 201 Created
	
			
		;
		
	}
	

	@Test
	public void consultaPessoa() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("usuario", "85496485401");
		login.put("senha", "teste@123");
		
		String token = given()
			.log().all()
			.body(login)
		
		.when()
			.post("https://gedave-proxydev.agricultura.sp.gov.br/usuarios/login")
		
		.then()
			.log().all()
			.statusCode(200)
			.extract().response().asString();
			System.out.println("token capturado" + token);
		;
		
		Response response = given()
			.log().all()
			.header("Authorization", "Bearer " + token)
			
		.when()
			.get("https://gedave-proxydev.agricultura.sp.gov.br/pessoas-fisica/filtros/avancada?idSituacao=101")

		.then()
			.log().all()
			.statusCode(200)
			.extract()
            .response();
		
		 given();
         	response.andReturn()
         .then()
         	.body("content.nome", hasItem("ADÃO MARIN [ TESTE ]"))
         	.body("content.nome", hasItems("Administrador Cda 1 Equideo Robot", "Administrador 1 Cda Vegetal Robot"))
	
			;
	}
	
}
	
