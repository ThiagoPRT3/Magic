package br.com.magic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.com.magic.domain.enumeration.TIPO_CARTA;

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
    private String carta_nome_br;

    @NotNull
    @Column(name = "carta_name_en", nullable = false)
    private String carta_name_en;

    @NotNull
    @Column(name = "edicao", nullable = false)
    private String edicao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TIPO_CARTA tipo;

    @ManyToMany(mappedBy = "cartas")
    @JsonIgnore
    private Set<Habilidade> cartaHabilidades = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarta_nome_br() {
        return carta_nome_br;
    }

    public void setCarta_nome_br(String carta_nome_br) {
        this.carta_nome_br = carta_nome_br;
    }

    public String getCarta_name_en() {
        return carta_name_en;
    }

    public void setCarta_name_en(String carta_name_en) {
        this.carta_name_en = carta_name_en;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public TIPO_CARTA getTipo() {
        return tipo;
    }

    public void setTipo(TIPO_CARTA tipo) {
        this.tipo = tipo;
    }

    public Set<Habilidade> getCartaHabilidades() {
        return cartaHabilidades;
    }

    public void setCartaHabilidades(Set<Habilidade> cartaHabilidades) {
        this.cartaHabilidades = cartaHabilidades;
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
            ", carta_nome_br='" + carta_nome_br + "'" +
            ", carta_name_en='" + carta_name_en + "'" +
            ", edicao='" + edicao + "'" +
            ", tipo='" + tipo + "'" +
            '}';
    }
}
