package rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import rest.core.BaseTest;

public class CenariosTestes extends BaseTest{  // Classe Cenários foi extendida á classe BaseTest
	
	private String token; // Declarando a variável token como global para poder ser ultilizada em todos os testes
	
	@Before
	public void login() {
				//.body("{\"email\", \"eduardo@teste\"}, {\"senha\", \"@teste\"}")  // Outra forma de enviar os dados , agora pelo própio body
				Map<String, String> login = new HashMap<>(); // Usando o Map para armazenar chave e valor que serão enviados no body 
				login.put("email","edu@testerest.com.br"); // atravez da variável login que foi criada com o Map é possível passar email e senha 
				login.put("senha","123456");
				
				token = given()  // Armazema o tokem após realizar o login na variável token 
					//.contentType(ContentType.JSON)
				 	.body(login)        //  Enviando os dados de login no body passando a varível login
				.when()
					.post("/signin")    // Rota para fazer o login 
				.then()
					.statusCode(200)  // verifica o status da requisição 
					.extract().path("token");  // Extrai o token após realizar o login
	}
	
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
			
		given()
			.header("Authorization", "JWT " + token) // Envia o token que f0i extraído
			.body("{\"nome\":\"Sétima Conta\"}")  // Envia o nome da conta que será criada 
		.when()
			.post("/contas")                        // Rota para fazer o post 
		.then()
			.statusCode(201)                       //  Verifica que a resposta da requisição , conta criada com sucesso 201 Created
			
		;
		
	}
	
	@Test
	public void alterandoContaComSucesso() {        // Cenário para alterar uma conta 
		
		given()
			.header("Authorization", "JWT " + token) // Envia o token que f0i extraído
			.body("{\"nome\":\"Conta Alterada de novo\"}")  // Envia o nome da conta que será criada 
		.when()
			.put("/contas/2381880")                 // Rota para fazer o post 
		.then()
			.statusCode(200)                       //  Verifica que a resposta da requisição , conta criada com sucesso 200
			.body("nome", is("Conta Alterada de novo")) // verifica o nome da conta que foi alterada
		;
		
	}
	
	@Test
	public void naoDevoIncluirContaComMesmoNome() { // Cenário para tentar incluir uma conta com  mesmo nome
		
		given()
			.header("Authorization", "JWT " + token) // Envia o token que f0i extraído
			.body("{\"nome\":\"Conta Alterada de novo\"}")  // Envia o nome da conta que será criada 
		.when()
			.post("/contas")                        // Rota para fazer o post 
		.then()
			.statusCode(400)                       //  Verifica que a resposta da requisição , conta criada com sucesso 201 Created
			.body("error", is("Já existe uma conta com esse nome!")) // verifica a mensagem de erro que existe na chave "error"
		;
		
	}
	
	@Test
	public void inserindoMovimetacaoComSucesso() { // Cenário para incluir uma movimentação
		Movimentacao mov = new Movimentacao();  // Montando um objeto de movimentação
		mov.setConta_id(2381880);               // Enviando o ID da conta que será incluída a movimentação
		//mov.setUsuario_id(usuraio_id);
		mov.setDescricao("Movimentação Suspeita"); 
		mov.setEnvolvido("Possoa teste");
		mov.setTipo("REC");
		mov.setData_transacao("01/02/2024");
		mov.setData_pagamento("10/02/2025");
		mov.setValor(1000f);
		mov.setStatus(true);
		
		
		
		given()
			.header("Authorization", "JWT " + token) // Envia o token que f0i extraído
			.body(mov)  // Envia o nome da conta que será criada 
		.when()
			.post("/transacoes")                        // Rota para fazer o post 
		.then()
			.statusCode(201)                       //  Verifica que a resposta da requisição , conta criada com sucesso 201 Created
			
		;
		
	}
	
}
	
