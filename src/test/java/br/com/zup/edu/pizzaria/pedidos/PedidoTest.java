package br.com.zup.edu.pizzaria.pedidos;

import org.springframework.util.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Endereco endereco = new Endereco("Teste", "10", "sem complemento", "0001");


    @Test
    public void deveCriarUmPedido(){
        Pedido pedido = new Pedido(endereco);
        Assert.isTrue(pedido != null, "Pedido deve ser criado");
    }
    @Test
    public void deveAdicionarItens(){
        Pedido pedido = new Pedido(endereco);

        Assert.isTrue(pedido != null, "Pedido deve ser criado");
    }


}