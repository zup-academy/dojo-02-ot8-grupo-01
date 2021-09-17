package br.com.zup.edu.pizzaria.pedidos;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.pizzas.Pizza;
import org.springframework.util.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Endereco endereco = new Endereco("Teste", "10", "sem complemento", "0001");

    Ingrediente ingrediente1 = new Ingrediente("farinha", 1, new BigDecimal(10.50) );

    private Pizza pizza = new Pizza("Teste", List.of(ingrediente1));


    @Test
    public void deveCriarUmPedido(){
        Pedido pedido = new Pedido(endereco);
        Assert.isTrue(pedido != null, "Pedido deve ser criado");
    }

    @Test
    public void deveAdicionarItens(){
        Pedido pedido = new Pedido(endereco);
        Item item = new Item(pedido,TipoDeBorda.TRADICIONAL,pizza);
        pedido.adicionarItem(item);

        Assert.isTrue(pedido.getTotal().compareTo(new BigDecimal(30.50)) == 0 );
    }


}