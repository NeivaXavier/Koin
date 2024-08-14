package Steps;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;

import Utils.PDFReport;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.es.Dado;
import io.cucumber.java.it.Quando;
import io.cucumber.java.pt.Então;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Steps_API {

    private String baseUrl;
    private String payload;
    private Response response;
    private StringBuilder stepsStatus = new StringBuilder();

    @Dado("que eu tenha um payload válido para criar um novo post")
    public void que_eu_tenha_um_payload_válido_para_criar_um_novo_post() throws Exception {
        baseUrl = "https://jsonplaceholder.typicode.com/posts";
        payload = new String(Files.readAllBytes(Paths.get("src/test/resources/data/post.json")));
        stepsStatus.append("Dado que eu tenha um payload válido para criar um novo post: PASSED\n");
    }

    @Quando("eu enviar uma requisição POST para o endpoint")
    public void eu_enviar_uma_requisição_post_para_o_endpoint() {
        response = RestAssured.given()
            .header("Content-type", "application/json; charset=UTF-8")
            .body(payload)
            .post(baseUrl);
        stepsStatus.append("Quando eu enviar uma requisição POST para o endpoint: PASSED\n");
        System.out.println("Resposta da API: " + response.asString());
    }

    @Então("o status code da resposta deve ser {int}")
    public void o_status_code_da_resposta_deve_ser(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
        stepsStatus.append("Então o status code da resposta deve ser " + statusCode + ": PASSED\n");
    }

    @Então("a resposta deve conter os dados corretos")
    public void a_resposta_deve_conter_os_dados_corretos() {
        response.then().body("title", equalTo("foo"));
        response.then().body("body", equalTo("bar"));
        response.then().body("userId", equalTo(1));
        stepsStatus.append("Então a resposta deve conter os dados corretos: PASSED\n");
    }

    @Dado("que eu tenha um payload válido para atualizar um post")
    public void que_eu_tenha_um_payload_válido_para_atualizar_um_post() throws Exception {
        baseUrl = "https://jsonplaceholder.typicode.com/posts/1";
        payload = new String(Files.readAllBytes(Paths.get("src/test/resources/data/put.json")));
        stepsStatus.append("Dado que eu tenha um payload válido para atualizar um post: PASSED\n");
    }

    @Quando("eu enviar uma requisição PUT para o endpoint")
    public void eu_enviar_uma_requisição_put_para_o_endpoint() {
        response = RestAssured.given()
            .header("Content-type", "application/json; charset=UTF-8")
            .body(payload)
            .put(baseUrl);
        stepsStatus.append("Quando eu enviar uma requisição PUT para o endpoint: PASSED\n");
        System.out.println("Resposta da API: " + response.asString());
    }

    @Então("a resposta deve conter os dados atualizados corretamente")
    public void a_resposta_deve_conter_os_dados_atualizados_corretamente() {
        response.then().body("id", equalTo(1));
        response.then().body("title", equalTo("foo"));
        response.then().body("body", equalTo("bar"));
        response.then().body("userId", equalTo(1));
        stepsStatus.append("Então a resposta deve conter os dados atualizados corretamente: PASSED\n");
    }

    @Quando("eu enviar uma requisição GET para listar todos os posts")
    public void eu_enviar_uma_requisição_get_para_listar_todos_os_posts() {
        baseUrl = "https://jsonplaceholder.typicode.com/posts";
        response = RestAssured.given()
            .header("Content-type", "application/json; charset=UTF-8")
            .get(baseUrl);
        stepsStatus.append("Quando eu enviar uma requisição GET para listar todos os posts: PASSED\n");
        System.out.println("Resposta da API: " + response.asString());
    }

    @Então("o status code da resposta da consulta deve ser {int}")
    public void o_status_code_da_resposta_da_consulta_deve_ser(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
        stepsStatus.append("Então o status code da resposta da consulta deve ser " + statusCode + ": PASSED\n");
    }

    @Então("a resposta deve conter a lista de posts")
    public void a_resposta_deve_conter_a_lista_de_posts() {
        response.then().body("$", org.hamcrest.Matchers.not(org.hamcrest.Matchers.empty()));
        stepsStatus.append("Então a resposta deve conter a lista de posts: PASSED\n");
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            stepsStatus.append(scenario.getName()).append(": FAILED\n");
        }

        // Gera o PDF com base no nome do cenário e no status dos steps
        PDFReport.generatePDF(scenario.getName(), stepsStatus.toString());
    }
}
