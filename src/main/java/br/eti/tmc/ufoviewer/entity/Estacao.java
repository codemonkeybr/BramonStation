package br.eti.tmc.ufoviewer.entity;

/**
 * Created by tessomc on 20/10/16.
 */
public class Estacao {

    private Long id;
    private String nome;
    private String latitude;
    private String longitude;
    private String camera;
    private String lente;
    private String captura;
    private String azimute;
    private String elevacao;
    private String rotacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getLente() {
        return lente;
    }

    public void setLente(String lente) {
        this.lente = lente;
    }

    public String getCaptura() {
        return captura;
    }

    public void setCaptura(String captura) {
        this.captura = captura;
    }

    public String getAzimute() {
        return azimute;
    }

    public void setAzimute(String azimute) {
        this.azimute = azimute;
    }

    public String getElevacao() {
        return elevacao;
    }

    public void setElevacao(String elevacao) {
        this.elevacao = elevacao;
    }

    public String getRotacao() {
        return rotacao;
    }

    public void setRotacao(String rotacao) {
        this.rotacao = rotacao;
    }

    @Override
    public String toString() {
        return "Estacao{" + "id=" + id + ", nome='" + nome + ", latitude='" + latitude + ", longitude='" + longitude + '\'' +
                ", camera='" + camera + ", lente='" + lente + "', captura='" + captura + '\'' + '}';
    }
}
