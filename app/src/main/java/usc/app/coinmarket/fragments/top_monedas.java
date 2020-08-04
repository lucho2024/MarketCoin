package usc.app.coinmarket.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import usc.app.coinmarket.R;

import usc.app.coinmarket.activitys.Moneda_Activity;
import usc.app.coinmarket.adaptadores.Adaptador_vista_todo;
import usc.app.coinmarket.objetos.Caratula_todo;


public class top_monedas extends Fragment {

    public top_monedas() {
        // Required empty public constructor
    }
    InterstitialAd interstitialAd;
    RecyclerView recycler;
    ArrayList<Caratula_todo> caratulaVtodo;
    Caratula_todo aux;
    SwipeRefreshLayout swipeRefres;
    ImageButton adelante,atras;
    int paginaActual=1;
    ProgressBar cargando;

    public void anuncio(){
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId("ca-app-pub-9849152997898900/2459549751");
        AdRequest adRequest1 = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest1);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }
        });
    }
    public void iniciarUI( View v )
    {
        recycler = v.findViewById(R.id.recycleridtodo);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = getWidth();
                return true;
            }
        });

       // ad =v.findViewById()
        swipeRefres = v.findViewById(R.id.swipe);

        atras = v.findViewById(R.id.idbotonatras);
        adelante= v.findViewById(R.id.idbotonadelante);
        cargando = v.findViewById( R.id.cargando );
        anuncio();

        obtenercatalogo();
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restar();
            }
        });
        adelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumar();
            }
        });
    }

    public void sumar(){
        if(paginaActual<25){
            paginaActual++;
        }
        Log.d("paginaactual", "sumar: "+paginaActual);

        obtenercatalogo();

    }
    public void restar(){
        if(paginaActual>1){
            paginaActual--;
        }
        obtenercatalogo();
    }

    public void obtenercatalogo(){
        new parseTop().execute();
        swipeRefres.setOnRefreshListener(() -> {
            new parseTop().execute();

            new Handler().postDelayed(() -> swipeRefres.setRefreshing(false),300);
        });
    }
    class parseTop extends AsyncTask<Void,Void,ArrayList<Caratula_todo>>{


        @Override
        protected ArrayList <Caratula_todo> doInBackground(Void... voids) {
            caratulaVtodo = new ArrayList <>();
            String pagina = String.valueOf(paginaActual);

          String sql = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=" +
                    "market_cap_desc&per_page=100&page="+pagina+"&sparkline=false&price_change_percentage=1h%2C24h%2C7d";

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

                JSONArray jsonArr = null;

                jsonArr = new JSONArray(json);
                for(int i = 0;i<jsonArr.length();i++){

                    aux = new Caratula_todo();
                    JSONObject jsonObject = jsonArr.getJSONObject(i);
                    aux.setNombre(jsonObject.getString("name"));
                    aux.setHoras24(jsonObject.getDouble("price_change_percentage_24h"));
                    aux.setFoto(jsonObject.getString("image"));
                    aux.setUrl(jsonObject.getString("id"));
                    aux.setPrecio(jsonObject.getDouble("current_price"));
                    aux.setMcap(jsonObject.getLong("market_cap"));
                    aux.setRanking(jsonObject.getString("market_cap_rank"));
                    //aux.setHoras1(jsonObject.getDouble("price_change_percentage_1h_in_currency"));
                    aux.setAth(jsonObject.getDouble("ath"));

                    caratulaVtodo.add(aux);
                    Log.d("prueba", "jsonPar: "+aux.getNombre());
                    Log.d("prueba 1", "jsonPar: "+aux.getHoras24());
                }
                return caratulaVtodo;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                Log.d("test_scrape",e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(ArrayList<Caratula_todo> catalogo) {
            if(catalogo!=null && catalogo.size()>0){
                setReciclerview(catalogo);
                cargando.setVisibility( View.GONE );
            }
        }

        private void setReciclerview(ArrayList<Caratula_todo> lista){
            Adaptador_vista_todo adapter;
            adapter = new  Adaptador_vista_todo(lista);
            recycler.setAdapter(adapter);
            adapter.setOnClickListener(view -> {
                Intent(caratulaVtodo.get(recycler.getChildAdapterPosition(view)).getUrl() );
                Toast.makeText(getContext(), "selection :"
                                + caratulaVtodo.get(recycler.getChildAdapterPosition(view)).getUrl()
                        , Toast.LENGTH_LONG).show();
            });
        }
    }

    public void Intent( String url ){
        Intent i = new Intent(getContext(), Moneda_Activity.class);
        i.putExtra("url", url);
        startActivity(i);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_topmonedas, container, false);
        iniciarUI( v );
        return v;
    }

}
