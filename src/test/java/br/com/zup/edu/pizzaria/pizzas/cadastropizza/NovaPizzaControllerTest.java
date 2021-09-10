package br.com.zup.edu.pizzaria.pizzas.cadastropizza;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.ingredientes.IngredienteRepository;
import br.com.zup.edu.pizzaria.ingredientes.cadastrodeingredientes.NovoIngredienteRequest;
import br.com.zup.edu.pizzaria.pizzas.Pizza;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class NovaPizzaControllerTest {



    Ingrediente ingrediente1 = new Ingrediente("farinha", 10, new BigDecimal(10.50) );
    Ingrediente ingrediente2 = new Ingrediente("molho", 5, new BigDecimal(1.50) );
    Ingrediente ingrediente3 = new Ingrediente("oregano", 5, new BigDecimal(3.50) );

    @Autowired
    IngredienteRepository ingredienteRepository;


    @Test
    public void cadastrarPizzaComSucesso(){

            Long id1 =  ingredienteRepository.save(ingrediente1).getId();
            Long id2 =  ingredienteRepository.save(ingrediente2).getId();
            Long id3 =  ingredienteRepository.save(ingrediente3).getId();

            List<Long> list = List.of(id1, id2, id3);

            NovaPizzaRequest pizzaRequest = new NovaPizzaRequest("Sem graca", list);

        MockHttpServletRequestBuilder request = post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(pizzaRequest));

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("/api/ingredientes/{id}"));



    }

}
