package usc.app.coinmarket.adaptadores;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.util.ArrayList;


import usc.app.coinmarket.R;
import usc.app.coinmarket.objetos.Caratula_todo;

public class Adaptador_vista_todo extends RecyclerView.Adapter<Adaptador_vista_todo.ViewHolder> implements View.OnClickListener{

    ArrayList<Caratula_todo> caratulaVistaTodo;
    private  View.OnClickListener listener;

    public Adaptador_vista_todo(ArrayList<Caratula_todo> caratulaVistaTodo) {
        this.caratulaVistaTodo = caratulaVistaTodo;
    }

    @NonNull
    @Override
    public Adaptador_vista_todo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vistatodo,null,false);
        view.setOnClickListener(this);
        return new Adaptador_vista_todo.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_vista_todo.ViewHolder holder, int position) {
        Caratula_todo objeto_actual1 = caratulaVistaTodo.get(position);
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        holder.nombre.setText(objeto_actual1.getNombre());
        holder.precio.setText(String.valueOf(objeto_actual1.getPrecio())+" usd" );
        holder.h24.setText(String.format("%.2f", objeto_actual1.getHoras24())+" %");
        holder.dcap.setText(String.valueOf(formatea.format(objeto_actual1.getMcap())));
        holder.ath.setText(String.format("%.2f",objeto_actual1.getAth())+" usd");
        holder.rank.setText(objeto_actual1.getRanking());

       if (objeto_actual1.getHoras24()<0){
            holder.h24.setTextColor(Color.parseColor("#FF0000"));
       }else{
            holder.h24.setTextColor(Color.parseColor("#10C84B"));
        }

        Picasso
                .get()
                .load(objeto_actual1.getFoto())
                .into(holder.imagen);
       /* Picasso
                .get()
                .load(objeto_actual1.getGrafica())
                .into(holder.grafica);*/

    }

    @Override
    public int getItemCount() {
        return caratulaVistaTodo.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView precio;
        TextView nombre;
        TextView h24;
        TextView cap;
        TextView dcap;
        TextView ath;
        TextView rank;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen=itemView.findViewById(R.id.imagenidt);
            precio=itemView.findViewById(R.id.idpreciot);
            nombre = itemView.findViewById(R.id.idnombret);
            h24 = itemView.findViewById(R.id.id24ht);
            cap= itemView.findViewById(R.id.idCap);
            dcap = itemView.findViewById(R.id.idcapdinero);
            ath=itemView.findViewById(R.id.idath);
            rank=itemView.findViewById(R.id.idrankingT);

        }
    }
}
