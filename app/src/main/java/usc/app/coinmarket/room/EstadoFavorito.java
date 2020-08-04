package usc.app.coinmarket.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

public class EstadoFavorito extends AsyncTask<Void, Void, Boolean> {

    private IN_boolean delegate;
    private MyDao bd;
    private String nombre;

    public EstadoFavorito(IN_boolean delegate, Context ctx, String nombre)
    {
        this.delegate = delegate;
        MyAppData aux = Room.databaseBuilder(ctx,MyAppData.class,"favorito")
                .allowMainThreadQueries().build();
        this.bd = aux.myDao();
        this.nombre = nombre;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Favorito evalua = bd.buscarpornombre( nombre );
        if ( evalua != null )
            return true;
        else
            return false;
    }

    @Override
    protected void onPostExecute(Boolean in_boolean) {
        delegate.resultado( in_boolean );
    }
}
