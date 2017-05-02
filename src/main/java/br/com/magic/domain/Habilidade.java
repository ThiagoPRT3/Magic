package br.com.magic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Habilidade.
 */
@Entity
@Table(name = "habilidade")
public class Habilidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "habilidade_nome_br", nullable = false)
    private String habilidadeNomeBr;

    @NotNull
    @Column(name = "habilidade_name_ing", nullable = false)
    private String habilidadeNameIng;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHabilidadeNomeBr() {
        return habilidadeNomeBr;
    }

    public void setHabilidadeNomeBr(String habilidadeNomeBr) {
        this.habilidadeNomeBr = habilidadeNomeBr;
    }

    public String getHabilidadeNameIng() {
        return habilidadeNameIng;
    }

    public void setHabilidadeNameIng(String habilidadeNameIng) {
        this.habilidadeNameIng = habilidadeNameIng;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Habilidade habilidade = (Habilidade) o;
        if(habilidade.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, habilidade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Habilidade{" +
            "id=" + id +
            ", habilidadeNomeBr='" + habilidadeNomeBr + "'" +
            ", habilidadeNameIng='" + habilidadeNameIng + "'" +
            ", descricao='" + descricao + "'" +
            '}';
    }
}
