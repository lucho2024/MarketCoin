package usc.app.coinmarket.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Favorito.class},version =2)
public abstract class MyAppData extends RoomDatabase {
    public abstract MyDao myDao();
}
