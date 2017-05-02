package br.com.magic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CartaHabilidade.
 */
@Entity
@Table(name = "carta_habilidade")
public class CartaHabilidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "carta_nome", nullable = false)
    private String cartaNome;

    @NotNull
    @Column(name = "habilidade_nome", nullable = false)
    private String habilidadeNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartaNome() {
        return cartaNome;
    }

    public void setCartaNome(String cartaNome) {
        this.cartaNome = cartaNome;
    }

    public String getHabilidadeNome() {
        return habilidadeNome;
    }

    public void setHabilidadeNome(String habilidadeNome) {
        this.habilidadeNome = habilidadeNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartaHabilidade cartaHabilidade = (CartaHabilidade) o;
        if(cartaHabilidade.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cartaHabilidade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CartaHabilidade{" +
            "id=" + id +
            ", cartaNome='" + cartaNome + "'" +
            ", habilidadeNome='" + habilidadeNome + "'" +
            '}';
    }
}
