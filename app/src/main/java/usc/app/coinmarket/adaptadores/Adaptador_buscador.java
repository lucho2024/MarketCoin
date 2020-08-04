package usc.app.coinmarket.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import usc.app.coinmarket.R;
import usc.app.coinmarket.objetos.Objeto_buscador;

public class Adaptador_buscador extends RecyclerView.Adapter<Adaptador_buscador.ViewHolder>
        implements View.OnClickListener {
    ArrayList<Objeto_buscador> caratulaBuscador;
    private  View.OnClickListener listenerB;

    public Adaptador_buscador(ArrayList <Objeto_buscador> caratulaBuscador) {
        this.caratulaBuscador = caratulaBuscador;
    }



    @NonNull
    @Override
    public Adaptador_buscador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista_buscador,null,false);
        view.setOnClickListener(this);
        return new Adaptador_buscador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_buscador.ViewHolder holder, int position) {
        Objeto_buscador objecto_actual = caratulaBuscador.get(position);
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        holder.nombre.setText(objecto_actual.getNombreBuscador());
        holder.cap.setText(String.valueOf(formatea.format(objecto_actual.getMbuscador())));
        holder.precio.setText(String.format("%.2f",objecto_actual.getPrecioBuscador()));
        Picasso
                .get()
                .load(objecto_actual.getFotoBuscador())
                .into(holder.imagen);

    }

    @Override
    public int getItemCount() {
        return caratulaBuscador.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listenerB=listener;
    }
    @Override
    public void onClick(View view) {
        if(listenerB!=null){
            listenerB.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView precio;
        TextView nombre;
        TextView cap;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen=itemView.findViewById(R.id.idimagenBuscador);
            precio=itemView.findViewById(R.id.idprecioBuscador);
            nombre=itemView.findViewById(R.id.idnombreBuscador);
            cap=itemView.findViewById(R.id.idmcapBuscador);
        }
    }
}
