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
import usc.app.coinmarket.objetos.Objeto_exchange;

public class Adaptador_exchange extends RecyclerView.Adapter<Adaptador_exchange.ViewHolder> implements
        View.OnClickListener {

    ArrayList<Objeto_exchange>CaratulaExchange;
    private  View.OnClickListener listenerExchange;
    public Adaptador_exchange(ArrayList <Objeto_exchange> caratulaExchange) {
        CaratulaExchange = caratulaExchange;
    }

    @NonNull
    @Override
    public Adaptador_exchange.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista_exchange,null,false);
        view.setOnClickListener(this);
        return new Adaptador_exchange.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_exchange.ViewHolder holder, int position) {
        Objeto_exchange objeto_actual = CaratulaExchange.get(position);
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        holder.nombreExchange.setText(objeto_actual.getNombre_E());
        holder.paisExchange.setText(objeto_actual.getPais_E());
        holder.volumenExchange.setText(String.format("%.2f",objeto_actual.getVolumen_E()));
        Picasso
                .get()
                .load(objeto_actual.getImagen_E())
                .into(holder.imagenExchange);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listenerExchange=listener;
    }
    @Override
    public int getItemCount() {
        return CaratulaExchange.size();
    }

    @Override
    public void onClick(View view) {
        if(listenerExchange!=null){
            listenerExchange.onClick(view);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreExchange;
        TextView paisExchange;
        ImageView imagenExchange;
        TextView volumenExchange;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreExchange=itemView.findViewById(R.id.idnombreE);
            paisExchange=itemView.findViewById(R.id.idpaisE);
            imagenExchange=itemView.findViewById(R.id.imagenidE);
            volumenExchange=itemView.findViewById(R.id.idvolumenE);
        }
    }
}
