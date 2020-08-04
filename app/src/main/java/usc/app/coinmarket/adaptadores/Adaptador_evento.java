package usc.app.coinmarket.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import usc.app.coinmarket.R;
import usc.app.coinmarket.objetos.Objeto_evento;

public class Adaptador_evento extends RecyclerView.Adapter<Adaptador_evento.ViewHolder> implements View.OnClickListener{
    ArrayList<Objeto_evento> caratulaevento;
    private  View.OnClickListener listenerE;

    public Adaptador_evento(ArrayList <Objeto_evento> caratulaevento) {
        this.caratulaevento = caratulaevento;
    }

    @NonNull
    @Override
    public Adaptador_evento.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista_evento,null,false);
            view.setOnClickListener(this);
        return new Adaptador_evento.ViewHolder(view);
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listenerE=listener;
    }
    @Override
    public void onClick(View view) {
        if(listenerE!=null){
            listenerE.onClick(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull Adaptador_evento.ViewHolder holder, int position) {
        Objeto_evento objecto_actual = caratulaevento.get(position);
        holder.tipo.setText(objecto_actual.getTipo());
        holder.titulo.setText(objecto_actual.getTitulo());
        holder.descripcion.setText(objecto_actual.getDescripcion());
        holder.fechaF.setText(objecto_actual.getFechaF());
        holder.fechaI.setText(objecto_actual.getFechaI());
        Picasso
                .get()
                .load(objecto_actual.getImagen())
                .resize(2048, 1600)
                .onlyScaleDown()
                .into(holder.imagen);

    }

    @Override
    public int getItemCount() {
        return caratulaevento.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipo;
        TextView titulo;
        TextView descripcion;
        ImageView imagen;
        TextView fechaI;
        TextView fechaF;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo=itemView.findViewById(R.id.idtype);
            titulo=itemView.findViewById(R.id.idtitulo);
            descripcion=itemView.findViewById(R.id.idDescripcion);
            imagen=itemView.findViewById(R.id.imagenevento);
            fechaF=itemView.findViewById(R.id.idfechaF);
            fechaI=itemView.findViewById(R.id.idfechai);
        }
    }
}
