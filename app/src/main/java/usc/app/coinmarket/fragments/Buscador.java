package usc.app.coinmarket.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import usc.app.coinmarket.activitys.MainActivity;
import usc.app.coinmarket.activitys.Moneda_Activity;
import usc.app.coinmarket.activitys.WebView_Activity;
import usc.app.coinmarket.adaptadores.Adaptador_buscador;
import usc.app.coinmarket.objetos.Objeto_buscador;


public class Buscador extends Fragment {
    MainActivity buscadorobj = new MainActivity();

    public Buscador() {
        // Required empty public constructor
    }
    ArrayList<Objeto_buscador> caratulabuscador;
    RecyclerView recyclerBuscador;
    Objeto_buscador auxBuscador;
    String id;

    public void iniciarUI(View view){
        recyclerBuscador=view.findViewById(R.id.idrecylerbuscador);
        recyclerBuscador.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = getWidth();
                return true;
            }
        });

        new parserBuscador().execute();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buscador2, container, false);

        iniciarUI(v);

        Log.d("buscador", "onCreateView: "+id);
        return v;
    }
    public void Intent( String url ){
        Intent i = new Intent(getContext(), Moneda_Activity.class);
        i.putExtra("url", url);
        startActivity(i);

    }
    public  String cambiar(String s){
        return s.replace(" ","-");
    }
    class parserBuscador extends AsyncTask<Void,Void,ArrayList<Objeto_buscador>>{

        @Override
        protected ArrayList <Objeto_buscador> doInBackground(Void... voids) {
            caratulabuscador = new ArrayList <>();
            Bundle args = getArguments();
            if(args!=null){
                id=args.getString("buscador","bitcoin");
            }
            String id2= cambiar(id);
            Log.d("rempl", "doInBackground: "+id2);
            String sql = "https://api.coingecko.com/api/v3/coins/" + id2 + "?tickers=false&developer_data=false";

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

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                json = response.toString();

                auxBuscador = new Objeto_buscador();
                JSONObject obj = new JSONObject(json);


                JSONObject obj2 = new JSONObject(json).getJSONObject("image");
                JSONObject obj3 = new JSONObject(json).getJSONObject("market_data").getJSONObject("current_price");
                JSONObject obj4 = new JSONObject(json).getJSONObject("market_data").getJSONObject("market_cap");
                auxBuscador.setFotoBuscador(obj2.getString("small"));
                auxBuscador.setIdBuscador(obj.getString("id"));
                auxBuscador.setNombreBuscador(obj.getString("name"));
                auxBuscador.setPrecioBuscador(obj3.getDouble("usd"));
                auxBuscador.setMbuscador(obj4.getDouble("usd"));


                caratulabuscador.add(auxBuscador);
                return caratulabuscador;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("error1", "jsonParIndi: erorr");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("error2", "jsonParIndi: erorr");

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error3", "jsonParIndi: erorr " + e);
            }
            return null;
        }
        protected void onPostExecute(ArrayList<Objeto_buscador> catalogo) {
            if(catalogo!=null){
                setReciclerview2(catalogo);

            }
        }
        private void setReciclerview2(ArrayList<Objeto_buscador> lista){
            Adaptador_buscador adapter;
            adapter = new Adaptador_buscador(lista);
            recyclerBuscador.setAdapter(adapter);
            adapter.setOnClickListener(view -> {
                Intent(caratulabuscador.get(recyclerBuscador.getChildAdapterPosition(view)).getIdBuscador() );
                Toast.makeText(getContext(),"selection :"
                                +caratulabuscador.get(recyclerBuscador.getChildAdapterPosition(view)).getIdBuscador()
                        , Toast.LENGTH_LONG).show();
            });

        }

    }
}
