package usc.app.coinmarket.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    void addfavarito(Favorito fav);

    @Query("select * from favoritos")
    List<Favorito> getFavoritos();

    @Delete
    void deletefavorito(Favorito fav);

    @Query("select * from favoritos WHERE nombre= :nombre LIMIT 1 ")
    Favorito buscarpornombre(String nombre);


}
