package usc.app.coinmarket.objetos;

public class Objeto_Favorito {
    private String nombre;
    private String fotoR;
    private Double volumen;
    private Double porcentaje;
    private String urlR;
    private String precioR;
    private String idfav;

    public Objeto_Favorito() {

    }

    public String getIdfav() {
        return idfav;
    }

    public void setIdfav(String idfav) {
        this.idfav = idfav;
    }

    public String getPrecioR() {
        return precioR;
    }

    public void setPrecioR(String precioR) {
        this.precioR = precioR;
    }

    public String getUrlR() {
        return urlR;
    }

    public void setUrlR(String urlR) {
        this.urlR = urlR;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoR() {
        return fotoR;
    }

    public void setFotoR(String fotoR) {
        this.fotoR = fotoR;
    }

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
