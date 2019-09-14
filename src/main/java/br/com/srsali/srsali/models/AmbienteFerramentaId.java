package br.com.srsali.srsali.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class AmbienteFerramentaId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    @JoinColumn(name = "ferramenta_id")
    @NotNull(message = "Ferramenta é obrigatório.")
    private Ferramenta ferramenta;

    @ManyToOne
    @JoinColumn(name = "ambiente_id")
    @NotNull(message = "Ambiente é obrigatório.")
    private Ambiente ambiente;

    public Ferramenta getFerramenta() {
        return ferramenta;
    }

    public void setFerramenta(Ferramenta ferramenta) {
        this.ferramenta = ferramenta;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }
    
}
