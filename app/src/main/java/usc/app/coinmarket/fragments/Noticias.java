package usc.app.coinmarket.fragments;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import usc.app.coinmarket.R;
import usc.app.coinmarket.activitys.WebView_Activity;
import usc.app.coinmarket.adaptadores.Adapatador_noticia;
import usc.app.coinmarket.objetos.Objeto_exchange;
import usc.app.coinmarket.objetos.Objeto_noticias;


public class Noticias extends Fragment {

    public Noticias() {
        // Required empty public constructor
    }

    ArrayList<Objeto_noticias> caratula_N;
    RecyclerView recycler4;
    Objeto_noticias aux4;
    ProgressBar cargando3;
    SwipeRefreshLayout swipeRefres2;

    public void iniciarUI2(View view){
        recycler4=view.findViewById(R.id.idRecyclernoticia);
        recycler4.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = getWidth();
                return true;
            }
        });
        cargando3 = view.findViewById( R.id.cargando3 );

        new scrapeNoticias().execute();
        swipeRefres2 = view.findViewById(R.id.swipeNoticias);
        swipeRefres2.setOnRefreshListener(() -> {
            new scrapeNoticias().execute();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefres2.setRefreshing(false);
                }
            },1500);
        });


    }
    class scrapeNoticias extends AsyncTask<Void,Void,ArrayList<Objeto_noticias>> {
        @Override
        protected ArrayList<Objeto_noticias> doInBackground(Void... voids) {
            caratula_N = new ArrayList<>();
            try{
                Document html = Jsoup.connect("https://www.coingecko.com/en/news").get();
                Elements nombre = html.select("div[class=my-4]");
                Log.d("consola_pruebas4", "resultados: " + nombre.size() );
                for(int i=0;i<nombre.size();i++){

                    aux4 = new Objeto_noticias();
                    aux4.setImagenN(nombre.eq(i).select("img").attr("src"));
                    aux4.setNombreN(nombre.eq(i).select("h2[class]").select("a").text());
                    aux4.setUrlN(nombre.eq(i).select("h2[class]").select("a").attr("href"));
                    aux4.setTiempoN(nombre.eq(i).select("p[style]").select("span").eq(1).text());
                    aux4.setAutorN(nombre.eq(i).select("p[style]").select("span").eq(0).text());
                    Log.d("consola_pruebas5", "resultados: " + aux4.getImagenN() );
                    caratula_N.add(aux4);
                }
                return caratula_N;
            }catch (Exception e) {
                Log.d("test_scrape",e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(ArrayList<Objeto_noticias> catalogo) {
            if(catalogo!=null){
                setReciclerview4(catalogo);
                cargando3.setVisibility( View.GONE );

            }
        }
        private void setReciclerview4(ArrayList<Objeto_noticias> lista){
            Adapatador_noticia adapter;
            adapter = new Adapatador_noticia(lista);
            recycler4.setAdapter(adapter);
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent(caratula_N.get(recycler4.getChildAdapterPosition(view)).getUrlN() );
                }
            });
        }
    }
    public void Intent( String url ){
        Intent i = new Intent(getContext(), WebView_Activity.class);
        i.putExtra("url", url);
        startActivity(i);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_noticias, container, false);
        iniciarUI2(v);
        return v;
    }

}


