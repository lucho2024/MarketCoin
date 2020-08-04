package usc.app.coinmarket.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
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
import java.util.List;
import java.util.Map;

import usc.app.coinmarket.R;
import usc.app.coinmarket.adaptadores.Adaptador_moneda;
import usc.app.coinmarket.objetos.Caratula_individual;

import usc.app.coinmarket.room.EstadoFavorito;
import usc.app.coinmarket.room.Favorito;


public class Moneda_Activity extends AppCompatActivity {
    ArrayList<Caratula_individual> caratulaIndividual;
    RecyclerView recycler2;
    Caratula_individual aux1;
    LineChart lineChart;
    String nombre2;
    ImageButton btnfav;
    ProgressBar cargando;
    SharedPreferences preferences;
    InterstitialAd interstitialAd;


    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moneda_activity);
        recycler2= findViewById(R.id.idrecyclermoneda);
        recycler2.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = getWidth();
                return true;
            }
        });
        anuncio();
        new parseindi().execute();
        cargando=findViewById(R.id.cargandomoneda);
        lineChart=findViewById(R.id.graficaid);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setTextSize(17f);
        lineChart.getDescription().setTextSize(15f);


        lineChart.getDescription().setText("Sparkline 7 days");
        lineChart.setBackground(getDrawable(R.color.sinColor));



        preferences = getPreferences(MODE_PRIVATE);
        btnfav=findViewById(R.id.idimgfv);

        btnfav.setOnClickListener(view -> cambiarEstadoFavorito( nombre2 ));
    }
    public void anuncio(){
        interstitialAd = new InterstitialAd(this);
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
    class parseindi extends AsyncTask<Void,Void,ArrayList<Caratula_individual>>{

        @SuppressLint("WrongThread")
        @Override
        protected ArrayList <Caratula_individual> doInBackground(Void... voids) {
            caratulaIndividual = new ArrayList <>();
            String  id=getIntent().getStringExtra("url");

            String sql = "https://api.coingecko.com/api/v3/coins/"+id+"?market_data=true&sparkline=true";

            Log.d("id_int", "jsonPar: "+id);
            Log.d("enlace", "jsonParIndi: "+sql);

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

                aux1 = new Caratula_individual();
                JSONObject obj = new JSONObject(json);
                JSONObject obj2 = new JSONObject(json).getJSONObject("market_data")
                        .getJSONObject("sparkline_7d");
                aux1.setNombreI(obj.getString("name"));
                aux1.setFotoI(obj.getJSONObject("image").getString("large"));
                aux1.setCirculating(String.valueOf(obj.getJSONObject("market_data")
                        .getLong("circulating_supply")));
                aux1.setTotals(obj.getJSONObject("market_data").getString("total_supply"));
                aux1.setFavid(obj.getString("id"));
                aux1.setRankingI(obj.getString("market_cap_rank"));
                aux1.setPrecioI(obj.getJSONObject("market_data").getJSONObject("current_price")
                        .getString("usd"));
                aux1.setCap(obj.getJSONObject("market_data").getJSONObject("market_cap")
                        .getString("usd"));
                aux1.setHigh(obj.getJSONObject("market_data").getJSONObject("high_24h")
                        .getString("usd"));
                aux1.setLow(obj.getJSONObject("market_data").getJSONObject("low_24h").getString("usd"));
                aux1.setAth(obj.getJSONObject("market_data").getJSONObject("ath")
                        .getString("usd"));
                aux1.setPorcentajeI(obj.getJSONObject("market_data")
                        .getString("price_change_percentage_24h"));
                aux1.setDias7(obj.getJSONObject("market_data")
                        .getString("price_change_percentage_7d"));
                aux1.setAnio(obj.getJSONObject("market_data")
                        .getString("price_change_percentage_1y"));

                caratulaIndividual.add(aux1);
                nombre2 = aux1.getNombreI();


                JSONArray jsonArr = null;
                ArrayList<Entry> lineEntries = new ArrayList<Entry>();
                jsonArr = obj2.getJSONArray("price");
                for (int i=0;i<jsonArr.length();i++){
                    float valor = (float) jsonArr.getDouble(i);

                    lineEntries.add(new Entry((float) i,(float)valor));
                }
                Log.d("arra", "jsonParIndi: "+jsonArr);

                LineDataSet lineDataSet = new LineDataSet(lineEntries,"Price");
                ArrayList<ILineDataSet> dataSets = new ArrayList <>();
                lineDataSet.setDrawCircles(false);

                dataSets.add(lineDataSet);

                LineData data = new LineData(dataSets);
                lineChart.setData(data);
                lineChart.postInvalidate();




                Log.d("foto", "jsonParIndi: "+aux1.getFotoI());
                return caratulaIndividual;
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
            return null;
        }

        protected void onPostExecute(ArrayList<Caratula_individual> catalogo) {
            if(catalogo!=null){
                setReciclerview2(catalogo);
                cargando.setVisibility(View.GONE);
                estadoFavorito( nombre2 );
            }
        }
        private void setReciclerview2(ArrayList<Caratula_individual> lista){
            Adaptador_moneda adapter;
            adapter = new Adaptador_moneda(lista);
            recycler2.setAdapter(adapter);

        }
    }

    public void borrar(){
        String nombre = aux1.getNombreI();
        Favorito Bfav = new Favorito();

        Bfav.setNombreR(nombre);
        MainActivity.Db.myDao().deletefavorito(Bfav);
        Toast.makeText(Moneda_Activity.this,"REMOVE :"
                        +aux1.getNombreI()
                , Toast.LENGTH_LONG).show();

        estadoFavorito( nombre );

    }
    public void inva(){
        lineChart.invalidate();
    }
    public void guardar(){

        if(aux1!=null){
            String nombreD=aux1.getNombreI();
            String precioD=aux1.getPrecioI();
            String fotoD=aux1.getFotoI();
            Double h24D=1.5;
            String urlD="dfdf";
            String mcD=aux1.getCirculating();
            String id = aux1.getFavid();

            Favorito mifav = new Favorito();

            mifav.setNombreR(nombreD);
            mifav.setPrecioR(precioD);
            mifav.setFotoR1(fotoD);
            mifav.setHoras24R("");
            mifav.setUrlR(urlD);
            mifav.setMcapR(mcD);
            mifav.setIdF(id);

            MainActivity.Db.myDao().addfavarito(mifav);
            Toast.makeText(Moneda_Activity.this,"ADD :"
                            +aux1.getNombreI()
                    , Toast.LENGTH_LONG).show();

            estadoFavorito( nombreD );

        }

    }

    private void estadoFavorito( String nombre )
    {
        new EstadoFavorito(
                estado -> {
                    if ( estado )
                        btnfav.setImageResource(R.drawable.confav);
                    else
                        btnfav.setImageResource(R.drawable.fav);
                },
                this,
                nombre
        ).execute();

    }

    private void cambiarEstadoFavorito( String nombre )
    {
        new EstadoFavorito(
                estado -> {
                    if ( estado ){
                        borrar();
                        estadoFavorito( nombre );
                    } else
                        guardar();
                    estadoFavorito( nombre );
                },
                this,
                nombre
        ).execute();

    }
}
