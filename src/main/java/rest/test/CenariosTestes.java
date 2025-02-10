package rest.test;

import org.junit.Test;

import io.restassured.http.ContentType;
import rest.core.BaseTest;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

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
			.log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "JWT " + token) // Envia o token que f0i extraído
			.body("{\"nome\":\"Quarta Conta\"}")  // Envia o nome da conta que será criada 
		.when()
			.post("/contas")                        // Rota para fazer o post 
		.then()
			.log().all()
			.statusCode(201)                       //  Verifica que a resposta da requisição , conta criada com sucesso 201 Created
	
	
	
	
	
	
	/*
	
	@Test
	public void incluirContaComSucessoCasoPreciseDeToken() { // Cenário para incluir uma nova conta 
		//.body("{\"email\", \"eduardo@teste\"}, {\"senha\", \"@teste\"}")  // Outra forma de enviar os dados , agora pelo própio body
		Map<String, String> login = new HashMap<>(); // Usando o Map para armazenar chave e valor que serão enviados no body 
		login.put("email", "eduardo@teste"); // atravez da variável login que foi criada com o Map é possível passar email e senha 
		login.put("senha", "@teste");
		
		 String token = given()  // Armazema o tokem após realizar o login na variável token 
		 	.body(login)        //  Enviando os dados de login no body passando a varível login
		.when()
			.post("logar")    // Rota para fazer o login 
		.then()
			.statusCode(200)  // verifica o status da requisição 
			.extract().path("token");  // Extrai o token após realizar o login
			
			
		given()
			.header("Authorization", "JWT" + token) // Envia o token que fi extraído  no header 
			.body("{\"nome\":\"Primeira Conta\"}")  // Envia o nome da conta que será criada 
		.when()
			.post("/contas")                        // Rota para fazer o post 
		.then()
			.statusCode(201)                       //  Verifica que a resposta da requisição , conta criada com sucesso 201 Create 
		*/	
			
		;
		
	}}
