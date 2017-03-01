package br.eti.tmc.ufoviewer.entity;

import java.math.BigDecimal;

/**
 * Created by tessomc on 20/10/16.
 */
public class Objeto {

    private Long id;
    private String classe;
    private BigDecimal magnitude;
    private BigDecimal cdeg;

    public BigDecimal getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(BigDecimal magnitude) {
        this.magnitude = magnitude;
    }

    public BigDecimal getCdeg() {
        return cdeg;
    }

    public void setCdeg(BigDecimal cdeg) {
        this.cdeg = cdeg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "Objeto{" + "id=" + id + ", classe='" + classe + "', magnitude=" + magnitude + ", cdeg=" + cdeg + '}';
    }
}
