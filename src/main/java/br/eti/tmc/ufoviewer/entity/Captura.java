package br.eti.tmc.ufoviewer.entity;

import java.util.Date;

/**
 * Created by tessomc on 04/12/16.
 */
public class Captura {

    Analise analise;
    private Date data;
    private String nomeArquivo;
    private String caminhoArquivo;
    private String caminhoImagem;
    private String caminhoThumbnail;

    private boolean remover;

    //todo buscar estacao pelo caminho

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getDia(){
        return this.data.getDay();
    }
    public Integer getMes(){
        return this.data.getMonth();
    }
    public Integer getAno(){
        return this.data.getYear();
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminhoImagem() {
        return this.caminhoArquivo.replace(".xml", "P.jpg");
    }

    public String getCaminhoThumbnail() {
        return this.caminhoArquivo.replace(".xml", "T.jpg");
    }

    public boolean isRemover() {
        return remover;
    }

    public void setRemover(boolean remover) {
        this.remover = remover;
    }

    @Override
    public String toString() {
        return "Captura{" +
                "analise=" + analise +
                ", data=" + data +
                ", caminhoArquivo='" + caminhoArquivo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Captura captura = (Captura) o;

        if (remover != captura.remover) return false;
        if (analise != null ? !analise.equals(captura.analise) : captura.analise != null) return false;
        if (data != null ? !data.equals(captura.data) : captura.data != null) return false;
        if (nomeArquivo != null ? !nomeArquivo.equals(captura.nomeArquivo) : captura.nomeArquivo != null) return false;
        if (caminhoArquivo != null ? !caminhoArquivo.equals(captura.caminhoArquivo) : captura.caminhoArquivo != null)
            return false;
        if (caminhoImagem != null ? !caminhoImagem.equals(captura.caminhoImagem) : captura.caminhoImagem != null)
            return false;
        return caminhoThumbnail != null ? caminhoThumbnail.equals(captura.caminhoThumbnail) : captura.caminhoThumbnail == null;

    }

    @Override
    public int hashCode() {
        int result = analise != null ? analise.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (nomeArquivo != null ? nomeArquivo.hashCode() : 0);
        result = 31 * result + (caminhoArquivo != null ? caminhoArquivo.hashCode() : 0);
        result = 31 * result + (caminhoImagem != null ? caminhoImagem.hashCode() : 0);
        result = 31 * result + (caminhoThumbnail != null ? caminhoThumbnail.hashCode() : 0);
        result = 31 * result + (remover ? 1 : 0);
        return result;
    }
}
