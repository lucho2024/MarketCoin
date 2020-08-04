package usc.app.coinmarket.objetos;

public class Caratula_todo {
    private String nombre;
    private Double precio;
    private String foto;
    private String grafica;
    private String dias7;
    private Double horas24;
    private Double ath;
    private String url;
    private Long mcap;
    private String foto1;
    private String tipo;
    private String ranking;

    public Caratula_todo() {

    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getAth() {
        return ath;
    }

    public void setAth(Double ath) {
        this.ath = ath;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public Long getMcap() {
        return mcap;
    }

    public void setMcap(Long mcap) {
        this.mcap = mcap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDias7() {
        return dias7;
    }

    public void setDias7(String dias7) {
        this.dias7 = dias7;
    }

    public Double getHoras24() {
        return horas24;
    }

    public void setHoras24(Double horas24) {
        this.horas24 = horas24;
    }


    public String getGrafica() {
        return grafica;
    }

    public void setGrafica(String grafica) {
        this.grafica = grafica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
