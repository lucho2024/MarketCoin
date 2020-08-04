package usc.app.coinmarket.adaptadores;

import android.graphics.Color;
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
import usc.app.coinmarket.objetos.Objeto_Favorito;

public class Adaptador_favoritos extends RecyclerView.Adapter<Adaptador_favoritos.ViewHolder>  implements View.OnClickListener{
    ArrayList<Objeto_Favorito> CaratultaRecientes;
    private  View.OnClickListener listener1;

    public Adaptador_favoritos(ArrayList<Objeto_Favorito> caratultaRecientes) {
        CaratultaRecientes = caratultaRecientes;
    }

    @NonNull
    @Override
    public Adaptador_favoritos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista_favorito,null,false);
        view.setOnClickListener(this);
        return new Adaptador_favoritos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_favoritos.ViewHolder holder, int position) {
        Objeto_Favorito objeto_actual1 = CaratultaRecientes.get(position);
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        holder.nombreR.setText(objeto_actual1.getNombre());
        holder.precioR.setText(objeto_actual1.getPrecioR());
        holder.h24R.setText(String.format("%.2f",objeto_actual1.getPorcentaje())+" %");
        if (objeto_actual1.getPorcentaje()<0){
            holder.h24R.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.h24R.setTextColor(Color.parseColor("#10C84B"));
        }
        Picasso
                .get()
                .load(objeto_actual1.getFotoR())
                .into(holder.imagenR);
    }

    @Override
    public int getItemCount() {
        return CaratultaRecientes.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener1=listener;
    }
    @Override
    public void onClick(View view) {
        if(listener1!=null){
            listener1.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenR;
        TextView precioR;
        TextView nombreR;
        TextView h24R;
        TextView capR;
        TextView dcapR;
        ImageView graficaR;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenR=itemView.findViewById(R.id.imagenidR);
            precioR=itemView.findViewById(R.id.idprecioR);
            nombreR = itemView.findViewById(R.id.idnombreR);
            h24R = itemView.findViewById(R.id.id24hR);
        }
    }
}
