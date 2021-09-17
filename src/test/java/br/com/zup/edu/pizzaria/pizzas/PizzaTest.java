package br.com.zup.edu.pizzaria.pizzas;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {




    @Test
    @DisplayName("deve impedir ciracao de pizza com sem ingredientes")
    void deveImpedirCiracaoDePizzaComSemIngredientes() {
        Pizza pizza = new Pizza("Maçã", Arrays.asList());
        Assert.isNull(pizza, "pizza não pode ser sem ingredientes");
    }


    @Test
    @DisplayName("deve criar uma pizza com sucesso")
    void deveCriarUmaPizzaComSucesso() {
        Ingrediente i1 = new Ingrediente("ingrediente 1", 10, new BigDecimal(1.30));
        Ingrediente i2 = new Ingrediente("ingrediente 1", 200, new BigDecimal(0.30));
        List<Ingrediente> listaIngredientes = Arrays.asList(i1, i2);
        Pizza pizza = new Pizza("Maçã", listaIngredientes);
        Assertions.assertTrue(pizza != null);
    }


    @Test
    @DisplayName("preco mínimo da pizza deve ser maior que total dos ingredientes")
    void precoMínimoDaPizzaDeveSerIgualAoTotalDosIngredientes(){
        Ingrediente i1 = new Ingrediente("ingrediente 1", 10, new BigDecimal(0.10));
        Ingrediente i2 = new Ingrediente("ingrediente 1", 20, new BigDecimal(0.15));
        List<Ingrediente> listaIngredientes = Arrays.asList(i1, i2);

        Pizza pizza = new Pizza("Maçã", listaIngredientes);

        BigDecimal totalIngredientes = new BigDecimal("0");
        BigDecimal valorTotalPizza = pizza.getPreco();

        for (Ingrediente i: listaIngredientes) {
            totalIngredientes = totalIngredientes.add(i.getPreco());
        }

        Assert.isTrue( valorTotalPizza.compareTo(totalIngredientes) == 1, "Pizza tem valor maior que os ingredientes");
    }



    @Test
    @DisplayName("Preco da pizza dever ser a soma dos ingrediente mais massa e mo")
    void precoDaPizzaDeverSerASomaDosIngredienteMaisMassaEMo(){
        Ingrediente i1 = new Ingrediente("ingrediente 1", 10, new BigDecimal(5.00));
        Ingrediente i2 = new Ingrediente("ingrediente 1", 20, new BigDecimal(20.00));
        List<Ingrediente> listaIngredientes = Arrays.asList(i1, i2);

        Pizza pizza = new Pizza("Maçã", listaIngredientes);

        BigDecimal totalIngredientes = new BigDecimal("0");
        BigDecimal valorTotalPizza = pizza.getPreco();

        for (Ingrediente i: listaIngredientes) {
            totalIngredientes = totalIngredientes.add(i.getPreco());
        }

        Assert.isTrue( valorTotalPizza.compareTo(new BigDecimal(45)) == 0, "Pizza tem valor maior que os ingredientes");
    }



}