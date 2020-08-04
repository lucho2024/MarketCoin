package usc.app.coinmarket.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import usc.app.coinmarket.R;
import usc.app.coinmarket.activitys.MainActivity;
import usc.app.coinmarket.activitys.Moneda_Activity;
import usc.app.coinmarket.adaptadores.Adaptador_favoritos;
import usc.app.coinmarket.adaptadores.Adaptador_vista_todo;
import usc.app.coinmarket.objetos.Caratula_individual;
import usc.app.coinmarket.objetos.Caratula_todo;
import usc.app.coinmarket.objetos.Objeto_Favorito;
import usc.app.coinmarket.room.Favorito;


public class fragmen_favoritos extends Fragment {
    RecyclerView recycler5;
    ArrayList<Objeto_Favorito> caratulafav;
    ProgressBar cargando5;
    public fragmen_favoritos() {
        // Required empty public constructor
    }
    public void iniciarUI3(View view){
        recycler5=view.findViewById(R.id.idfavoritos);
        recycler5.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = getWidth();
                return true;
            }
        });
        cargando5=view.findViewById(R.id.cargando5);
        new favo().execute();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragmen_favoritos, container, false);
        iniciarUI3(v);

        return v;
    }
    class favo extends AsyncTask<Void,Void,ArrayList<Objeto_Favorito>>{

        @Override
        protected ArrayList <Objeto_Favorito> doInBackground(Void... voids) {
            try{
                List<Favorito>favoritos1 = MainActivity.Db.myDao().getFavoritos();
                //consumiendo api
                caratulafav = new ArrayList<>();
                for(int i = 0; i < favoritos1.size(); i++ )
                {
                    Objeto_Favorito aux = new Objeto_Favorito();
                    Favorito fa = favoritos1.get( i );
                    String sql = "https://api.coingecko.com/api/v3/coins/"+fa.getIdF()+"?tickers=false&developer_data=false";

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    URL url = null;
                    HttpURLConnection conn;
                    try {
                        url = new URL(sql);
                        conn = (HttpURLConnection) url.openConnection();

                        conn.setRequestMethod("GET");

                        conn.connect();

                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        String inputLine;

                        StringBuffer response = new StringBuffer();

                        String json = "";

                        while((inputLine = in.readLine()) != null){
                            response.append(inputLine);
                        }

                        json = response.toString();
                        Log.d("json", "jsonParIndi: "+json);


                        JSONObject obj = new JSONObject(json);
                        JSONObject obj2 = new JSONObject(json).getJSONObject("image");
                        JSONObject obj3 = new JSONObject(json).getJSONObject("market_data").getJSONObject("current_price");
                        JSONObject obj4 = new JSONObject(json).getJSONObject("market_data");
                        aux.setFotoR(obj2.getString("small"));
                        aux.setIdfav(obj.getString("id"));
                        aux.setPrecioR(String.valueOf(obj3.getString("usd")));
                        aux.setPorcentaje(obj4.getDouble("price_change_percentage_24h"));


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        Log.d("error1", "jsonParIndi: erorr");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("error2", "jsonParIndi: erorr");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("error3", "jsonParIndi: erorr "+e);
                    }
                    aux.setNombre(fa.getNombreR());

                    caratulafav.add(aux);

                }
                return caratulafav;
            }catch (Exception e){
                Log.d("test_scrape",e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(ArrayList<Objeto_Favorito> catalogo) {
            if(catalogo!=null){
                setReciclerview(catalogo);
                cargando5.setVisibility( View.GONE );
            }
        }

        private void setReciclerview(ArrayList<Objeto_Favorito> lista){
            Adaptador_favoritos adapter;
            adapter = new Adaptador_favoritos(lista);
            recycler5.setAdapter(adapter);
            adapter.setOnClickListener(view -> {
                Intent(caratulafav.get(recycler5.getChildAdapterPosition(view)).getIdfav() );
                Toast.makeText(getContext(),"selection :"
                                +caratulafav.get(recycler5.getChildAdapterPosition(view)).getIdfav()
                        , Toast.LENGTH_LONG).show();
            });

        }
        public void Intent( String url ){
            Intent i = new Intent(getContext(), Moneda_Activity.class);
            i.putExtra("url", url);
            startActivity(i);
        }


    }
}
