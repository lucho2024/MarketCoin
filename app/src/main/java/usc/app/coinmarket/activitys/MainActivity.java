package usc.app.coinmarket.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import usc.app.coinmarket.R;
import usc.app.coinmarket.fragments.Buscador;
import usc.app.coinmarket.fragments.Evento;
import usc.app.coinmarket.fragments.Exchange;
import usc.app.coinmarket.fragments.Noticias;
import usc.app.coinmarket.fragments.fragmen_favoritos;
import usc.app.coinmarket.fragments.top_monedas;
import usc.app.coinmarket.room.MyAppData;

public class MainActivity extends AppCompatActivity
{

    InterstitialAd interstitialAd;
   static public MyAppData Db;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    remplazarFragmento( new top_monedas() );
                }else  if (id == R.id.navigation_dashboard) {
                        remplazarFragmento(new fragmen_favoritos());
                }else  if (id == R.id.navigation_notifications) {
                    remplazarFragmento( new Exchange() );
                }else  if (id == R.id.navigation_noticias) {
                    remplazarFragmento( new Noticias() );
                }else if (id==R.id.evento){
                    remplazarFragmento(new Evento());
                }

                return true;
            };

    public void remplazarFragmento(Fragment fragment)
    {
        FragmentManager cv = getSupportFragmentManager();
        cv
                .beginTransaction()
                .replace( R.id.idprueba,  fragment)
                .addToBackStack( null )
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        remplazarFragmento( new top_monedas() );
        anuncio();
        basedatos();




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


    public void basedatos(){
        Db=Room.databaseBuilder(getApplicationContext(),MyAppData.class,"favorito")
                .allowMainThreadQueries().build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Buscador fragment = new Buscador();
               Bundle bundle = new Bundle();
                bundle.putString("buscador",s);

                fragment.setArguments(bundle);

                FragmentManager cv = getSupportFragmentManager();
                cv
                        .beginTransaction()
                        .replace( R.id.idprueba,  fragment)
                        .addToBackStack( null )
                        .commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("buscador","bitcoin");
                Buscador fragment = new Buscador();
                fragment.setArguments(bundle);
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        /*
        if (id == R.id.action_settings) {

        }
        */

        return super.onOptionsItemSelected(item);
    }


}
