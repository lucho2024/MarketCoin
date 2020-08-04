package usc.app.coinmarket.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

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
import usc.app.coinmarket.adaptadores.Adaptador_evento;
import usc.app.coinmarket.objetos.Objeto_evento;


public class Evento extends Fragment {



    public Evento() {
        // Required empty public constructor
    }
    ArrayList<Objeto_evento> caratulaEve;
    Objeto_evento auxEvento;
    RecyclerView recyclerEvento;
    ProgressBar progesevento;

    public void iniciarUI2(View view){
       recyclerEvento = view.findViewById(R.id.idrecyclerevento);
        recyclerEvento.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = getWidth();
                return true;
            }
        });
        progesevento=view.findViewById(R.id.progresevento);
        new parseEvento().execute();

    }

    public void Intent( String url ){
        Intent i = new Intent(getContext(), WebView_Activity.class);
        i.putExtra("url", url);
        startActivity(i);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_evento, container, false);
        iniciarUI2(v);
        return v;
    }



    class parseEvento extends AsyncTask <Void,Void,ArrayList<Objeto_evento>> {
        @Override
        protected ArrayList<Objeto_evento> doInBackground(Void... voids) {
            caratulaEve = new ArrayList<>();
            String sql = "https://api.coingecko.com/api/v3/events";

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


                JSONObject obj = new JSONObject(json);
                jsonArr = obj.getJSONArray("data");
                for(int i = 0;i<jsonArr.length();i++){
                    auxEvento = new Objeto_evento();
                    JSONObject jsonObject = jsonArr.getJSONObject(i);
                    auxEvento.setDescripcion(jsonObject.getString("description"));
                    auxEvento.setImagen(jsonObject.getString("screenshot"));
                    auxEvento.setEnlace(jsonObject.getString("website"));
                    auxEvento.setOrganizador(jsonObject.getString("organizer"));
                    auxEvento.setTipo(jsonObject.getString("type"));
                    auxEvento.setTitulo(jsonObject.getString("title"));
                    auxEvento.setFechaF(jsonObject.getString("end_date"));
                    auxEvento.setFechaI(jsonObject.getString("start_date"));



                 //   Log.d("nombre", "doInBackground: "+aux4.getUrlN());

                   caratulaEve.add(auxEvento);
                }
                return caratulaEve;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(ArrayList<Objeto_evento> catalogo) {
            if(catalogo!=null){
                setReciclerview4(catalogo);
                progesevento.setVisibility( View.GONE );

            }
        }
        private void setReciclerview4(ArrayList<Objeto_evento> lista){
            Adaptador_evento adapter;
            adapter = new Adaptador_evento(lista);
            recyclerEvento.setAdapter(adapter);
            adapter.setOnClickListener(view -> {
                Intent(caratulaEve.get(recyclerEvento.getChildAdapterPosition(view)).getEnlace() );
                Toast.makeText(getContext(),"selection :"
                                +caratulaEve.get(recyclerEvento.getChildAdapterPosition(view)).getEnlace()
                        , Toast.LENGTH_LONG).show();
            });
        }
        }




}
