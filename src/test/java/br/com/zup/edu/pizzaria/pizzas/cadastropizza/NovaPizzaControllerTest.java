package br.com.zup.edu.pizzaria.pizzas.cadastropizza;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.ingredientes.IngredienteRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//Isso aqui é pra eu poder usar um método no @BeforeAll que nõa seja estático
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NovaPizzaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    //A lista de ingredientes para as pizzas, ficou como atributo da classe, pra eu poder aproveitar em
    // cada método de teste
    private List<Long> ingredientesLongDaClasse = new ArrayList<>();

    private List<Long> ingredientesLongDaClassePrecoZerado = new ArrayList<>();



    //Isso tudo aí dentro do méodo abaixo, acontece só uma vez para a instância. Assim eu posso aproveitar os ingredientes
    //Não corro o risco de cadastrar ingredientes novos e repetidos, nem de saturar o banco com um monte de
    //ingredientes com nome parecido
    @BeforeAll
    void init(){

        //concateno o momento atual, com o nome do ingrediente, pra nunca cair na validação de nome repetido
        //do ingrediente
        String d = LocalDateTime.now().toString();

        //preparo uma lista de ingrediente, para usar nas pizzas dos testes seguintes
        //Os nomes dos ingredientes, são compostos por uma string fixa e o momento atual, pra nõa encontrar
        //conflito no banco de dados
        Ingrediente ingrediente1 = new Ingrediente("farinha" + d, 10, new BigDecimal(10.50) );
        Ingrediente ingrediente2 = new Ingrediente("molho" + d, 5, new BigDecimal(1.50) );
        Ingrediente ingrediente3 = new Ingrediente("oregano" + d, 5, new BigDecimal(3.50) );

        Long id1 = ingredienteRepository.save(ingrediente1).getId();
        Long id2 = ingredienteRepository.save(ingrediente2).getId();
        Long id3 = ingredienteRepository.save(ingrediente3).getId();

        List<Long> ingredientesLongDoMetodo = List.of(id1, id2, id3);
        ingredientesLongDaClasse.addAll(ingredientesLongDoMetodo);

        ingrediente1 = new Ingrediente("farinha" + d, 10, new BigDecimal(2) );
        ingrediente2 = new Ingrediente("molho" + d, 5, new BigDecimal(4) );
        ingrediente3 = new Ingrediente("oregano" + d, 5, new BigDecimal(5) );

        id1 = ingredienteRepository.save(ingrediente1).getId();
        id2 = ingredienteRepository.save(ingrediente2).getId();
        id3 = ingredienteRepository.save(ingrediente3).getId();

        ingredientesLongDoMetodo = List.of(id1, id2, id3);
        ingredientesLongDaClassePrecoZerado.addAll(ingredientesLongDoMetodo);

    }

    @Test
    public void cadastrarPizzaComSucesso() throws Exception {
            String d = LocalDateTime.now().toString();
            NovaPizzaRequest pizzaRequest = new NovaPizzaRequest("Sem graca" + d, ingredientesLongDaClasse);

        MockHttpServletRequestBuilder request = post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(pizzaRequest));

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("/api/pizzas/{id}"));
    }



    @Test
    @DisplayName("deve impedir o cadastro de pizza com nome repetido")
    void deveImpedirOCadastroDePizzaComNomeRepetido() throws Exception {
        //Cria um instancia de pizza com nome composto pelo momento atual, pra não conflitar com os testes
        //Anteriores
        String d = LocalDateTime.now().toString();
        NovaPizzaRequest pizzaRequest = new NovaPizzaRequest("Sem graca" + d, ingredientesLongDaClassePrecoZerado);

        //Cadastra a primeira pizza
        MockHttpServletRequestBuilder request = post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(pizzaRequest));

        mvc.perform(request)
                .andExpect(status().isCreated()); // aqui eu espero um duzentão!

        //Agora cadastra a segunda pizza, com o mesmo nome da primeira (mesma instância, inclusive)
        MockHttpServletRequestBuilder request2 = post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(pizzaRequest));

        mvc.perform(request)
                .andExpect(status().isBadRequest()); //Aqui eu quero ver um quatrocentos!
    }

}
