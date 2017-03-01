package br.eti.tmc.ufoviewer.entity;

/**
 * Created by tessomc on 20/10/16.
 */
public class Analise {

    private Long id;
    private String observador;
    private Estacao estacao;


    private Objeto objeto;

    public Objeto getObjeto() {
        if (this.objeto == null)
            this.objeto=  new Objeto();
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public String getObservador() {
        return observador;
    }

    public void setObservador(String observador) {
        this.observador = observador;
    }

    public Estacao getEstacao() {
        if(this.estacao == null)
            this.estacao = new Estacao();
        return estacao;
    }

    public void setEstacao(Estacao estacao) {
        this.estacao = estacao;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Analise{" +
                "id=" + id +
                ", observador='" + observador + '\'' +
                ", estacao=" + estacao +
                ", objeto=" + objeto +
                '}';
    }
}
