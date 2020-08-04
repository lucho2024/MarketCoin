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
import android.widget.ProgressBar;
import android.widget.Toast;

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
import usc.app.coinmarket.activitys.WebView_Activity;
import usc.app.coinmarket.adaptadores.Adaptador_exchange;
import usc.app.coinmarket.objetos.Objeto_exchange;


public class Exchange extends Fragment {
    public Exchange() {
        // Required empty public constructor
    }
    ArrayList<Objeto_exchange> Caratularecientes2;
    RecyclerView recycler3;
    Objeto_exchange aux3;
    ProgressBar cargando2;
    SwipeRefreshLayout swipeRefres3;

    public void iniciarUI(View view){
        recycler3=view.findViewById(R.id.recycleridtodo2);
        recycler3.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = getWidth();
                return true;
            }
        });
        cargando2 = view.findViewById( R.id.cargando2 );
        swipeRefres3 = view.findViewById(R.id.swipeRecientes);
        new jsonparExc().execute();
        swipeRefres3.setOnRefreshListener(() -> {
            new jsonparExc().execute();

            new Handler().postDelayed(() -> swipeRefres3.setRefreshing(false),300);
        });
    }
    public void Intent( String url ){
        Intent i = new Intent(getContext(), WebView_Activity.class);
        i.putExtra("url", url);
        startActivity(i);

    }

    class jsonparExc extends AsyncTask<Void,Void,ArrayList<Objeto_exchange>>{

        @Override
        protected ArrayList <Objeto_exchange> doInBackground(Void... voids) {
            Caratularecientes2 = new ArrayList <>();

            String sql = "https://api.coingecko.com/api/v3/exchanges";

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
                    aux3 = new Objeto_exchange();
                    JSONObject jsonObject = jsonArr.getJSONObject(i);
                    aux3.setNombre_E(jsonObject.getString("name"));
                    aux3.setImagen_E(jsonObject.getString("image"));
                    aux3.setPais_E(jsonObject.getString("country"));
                    aux3.setEnlace_E(jsonObject.getString("url"));
                    aux3.setVolumen_E(jsonObject.getDouble("trade_volume_24h_btc"));
                    Caratularecientes2.add(aux3);
                    Log.d("exchange", "jsonPar: "+aux3.getNombre_E());

                }
                return Caratularecientes2;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(ArrayList <Objeto_exchange> catalogo) {
            if(catalogo!=null){
                setReciclerview3(catalogo);
                cargando2.setVisibility( View.GONE );

            }
        }
        private void setReciclerview3(ArrayList <Objeto_exchange> lista){
            Adaptador_exchange adapter;
            adapter = new Adaptador_exchange(lista);
            recycler3.setAdapter(adapter);
            adapter.setOnClickListener(view -> {
                Intent(Caratularecientes2.get(recycler3.getChildAdapterPosition(view)).getEnlace_E() );
                Toast.makeText(getContext(),"selection :"
                                +Caratularecientes2.get(recycler3.getChildAdapterPosition(view)).getEnlace_E()
                        , Toast.LENGTH_LONG).show();
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exchange, container, false);
        iniciarUI(v);
        return v;
    }

}
