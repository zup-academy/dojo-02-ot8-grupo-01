package br.com.zup.edu.pizzaria.pizzas;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pizza {

    private final static BigDecimal PRECO_MASSA = new BigDecimal("15.0");
    private final static BigDecimal PRECO_MAO_DE_OBRA = new BigDecimal("5.0");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sabor;

    private BigDecimal preco;

    @ManyToMany
    @Size(min=1)
    @NotNull
    private List<Ingrediente> ingredientes = new ArrayList<>();

    public Pizza(String sabor,  List<Ingrediente> ingredientes) {
        this.sabor = sabor;
        this.ingredientes = ingredientes;
        calcularPreco();
    }

    /**
     * @deprecated apenas para uso do hibernate
     */
    @Deprecated
    public Pizza() {
    }

    //Reporovado no teste, porque BigDecimal tem valor imutável
    //Se for preciso fazer cálculo com BigDecimal, é preciso usar o valor retornado e fazer nova atribuição, porque
    //O objeto BigDecimal é imutável
    private void calcularPreco() {
        this.preco = this.PRECO_MASSA;
        this.preco = this.preco.add(PRECO_MAO_DE_OBRA);
        for (Ingrediente ingrediente: ingredientes){
            this.preco = this.preco.add(ingrediente.getPreco());
        }
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPreco() {
        return preco;
    }

}
