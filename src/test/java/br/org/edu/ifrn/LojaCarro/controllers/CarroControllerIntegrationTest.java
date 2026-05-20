package br.org.edu.ifrn.LojaCarro.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/test-data-carros.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CarroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void buscarIdInexistente_deveRetornarNotFound() throws Exception {
        mockMvc.perform(get("/carro/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void salvarCarroSemModelo_deveRetornarBadRequest() throws Exception {
        String carroJson = "{\"ano\":2022,\"preco\":55000.00}";

        mockMvc.perform(post("/carro/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carroJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void salvarCarroPrecoNegativo_deveRetornarBadRequest() throws Exception {
        String carroJson = "{\"modelo\":\"Palio\",\"ano\":2020,\"preco\":-1000.00}";

        mockMvc.perform(post("/carro/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carroJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void salvarCarroModeloX_deveRetornarBadRequestComExcecao() throws Exception {
        String carroJson = "{\"modelo\":\"x\",\"ano\":2022,\"preco\":55000.00}";

        mockMvc.perform(post("/carro/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carroJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("CARRO_INVALIDO"))
                .andExpect(jsonPath("$.detail").value("Modelo 'x' não pode ser utilizado"));
    }

    @Test
    void salvarCarroModeloY_deveRetornarBadRequestComExcecao() throws Exception {
        String carroJson = "{\"modelo\":\"y\",\"ano\":2022,\"preco\":55000.00}";

        mockMvc.perform(post("/carro/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carroJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("CARRO_INVALIDO"))
                .andExpect(jsonPath("$.detail").value("Modelo 'y' não pode ser utilizado"));
    }

    @Test
    void dadosInseridosPorSql_deveEstarDisponiveisAntesDosTestes() throws Exception {
        mockMvc.perform(get("/carro/{id}", 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Fiesta"))
                .andExpect(jsonPath("$.ano").value(2019));
    }
}
