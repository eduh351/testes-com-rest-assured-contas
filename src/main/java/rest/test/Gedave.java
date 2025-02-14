package rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.response.Response;
import rest.core.BaseTest;

public class Gedave extends BaseTest{  // Classe Cenários foi extendida á classe BaseTest
	

	
	@Test
	public void consultaPessoa() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("usuario", "85496485401");
		login.put("senha", "teste@123");
		
		String token = given()
			.body(login)
		
		.when()
			.post("https://gedave-proxydev.agricultura.sp.gov.br/usuarios/login")
		
		.then()
			.statusCode(200)
			.extract().response().asString();
		
		;
		
		given()
			.header("Authorization", "Bearer " + token)
			
		.when()
			.get("https://gedave-proxydev.agricultura.sp.gov.br/pessoas-fisica/filtros/avancada?idSituacao=101")

		.then()
			.statusCode(200)
			.body("content.nome", hasItem(" teste tetetet"))
			.body("content.nome", hasItems("Administrador Cda 1 Equideo Robot", "Administrador 1 Cda Vegetal Robot"))
		
	;

	}
	
	
	/*  Outra maneira de realizar a consulta e verificação
	
	@Test
	public void consultaPessoa2() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("usuario", "85496485401");
		login.put("senha", "teste@123");
		
		String token = given()
			.body(login)
		
		.when()
			.post("https://gedave-proxydev.agricultura.sp.gov.br/usuarios/login")
		
		.then()
			.statusCode(200)
			.extract().response().asString();
			System.out.println("token capturado" + token);
		;
		
		Response response = given()
			.header("Authorization", "Bearer " + token)
			
		.when()
			.get("https://gedave-proxydev.agricultura.sp.gov.br/pessoas-fisica/filtros/avancada?idSituacao=101")

		.then()
			.statusCode(200)
			.extract()
            .response();
		
		 given();
         	response.andReturn()
         .then()
         	.body("content.nome", hasItem(" teste tetetet"))
         	.body("content.nome", hasItems("Administrador Cda 1 Equideo Robot", "Administrador 1 Cda Vegetal Robot"))
	
			;
	}
	*/
	
	
	
	
}
	
