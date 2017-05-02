package br.com.magic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import br.com.magic.domain.enumeration.Raridade;

/**
 * A Carta.
 */
@Entity
@Table(name = "carta")
public class Carta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "carta_nome_br", nullable = false)
    private String cartaNomeBr;

    @NotNull
    @Column(name = "carta_name_ing", nullable = false)
    private String cartaNameIng;

    @NotNull
    @Column(name = "edicao", nullable = false)
    private String edicao;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "raridade", nullable = false)
    private Raridade raridade;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ataque")
    private String ataque;

    @Column(name = "defesa")
    private String defesa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartaNomeBr() {
        return cartaNomeBr;
    }

    public void setCartaNomeBr(String cartaNomeBr) {
        this.cartaNomeBr = cartaNomeBr;
    }

    public String getCartaNameIng() {
        return cartaNameIng;
    }

    public void setCartaNameIng(String cartaNameIng) {
        this.cartaNameIng = cartaNameIng;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public void setRaridade(Raridade raridade) {
        this.raridade = raridade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAtaque() {
        return ataque;
    }

    public void setAtaque(String ataque) {
        this.ataque = ataque;
    }

    public String getDefesa() {
        return defesa;
    }

    public void setDefesa(String defesa) {
        this.defesa = defesa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Carta carta = (Carta) o;
        if(carta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, carta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Carta{" +
            "id=" + id +
            ", cartaNomeBr='" + cartaNomeBr + "'" +
            ", cartaNameIng='" + cartaNameIng + "'" +
            ", edicao='" + edicao + "'" +
            ", tipo='" + tipo + "'" +
            ", raridade='" + raridade + "'" +
            ", descricao='" + descricao + "'" +
            ", ataque='" + ataque + "'" +
            ", defesa='" + defesa + "'" +
            '}';
    }
}
