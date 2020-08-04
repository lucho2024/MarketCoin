package usc.app.coinmarket.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favoritos")
public class Favorito {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombreR ="" ;
    @ColumnInfo(name = "precio")
    private String precioR;
    @ColumnInfo(name = "foto_m")
    private String fotoR1;
    @ColumnInfo(name = "24horas")
    private String horas24R;
   @ColumnInfo(name = "urlR")
    private String urlR;
   @ColumnInfo(name = "capitalizacion")
    private String mcapR;
    @ColumnInfo(name = "id")
    private String idF;

    public String getIdF() {
        return idF;
    }

    public void setIdF(String idF) {
        this.idF = idF;
    }

    public String getNombreR() {
        return nombreR;
    }

    public void setNombreR(String nombreR) {
        this.nombreR = nombreR;
    }

    public String getPrecioR() {
        return precioR;
    }

    public void setPrecioR(String precioR) {
        this.precioR = precioR;
    }

    public String getFotoR1() {
        return fotoR1;
    }

    public void setFotoR1(String fotoR1) {
        this.fotoR1 = fotoR1;
    }

    public String getHoras24R() {
        return horas24R;
    }

    public void setHoras24R(String horas24R) {
        this.horas24R = horas24R;
    }

    public String getUrlR() {
        return urlR;
    }

    public void setUrlR(String urlR) {
        this.urlR = urlR;
    }

    public String getMcapR() {
        return mcapR;
    }

    public void setMcapR(String mcapR) {
        this.mcapR = mcapR;
    }
}
