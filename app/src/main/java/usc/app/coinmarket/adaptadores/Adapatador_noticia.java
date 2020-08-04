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
import usc.app.coinmarket.objetos.Objeto_noticias;

public class Adapatador_noticia extends RecyclerView.Adapter<Adapatador_noticia.ViewHolder> implements  View.OnClickListener{
     ArrayList<Objeto_noticias> caratula_noticas;
    private  View.OnClickListener listener2;
    public Adapatador_noticia(ArrayList<Objeto_noticias> caratula_noticas) {
        this.caratula_noticas = caratula_noticas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista_noticia,null,false);
        view.setOnClickListener(this);
        return new Adapatador_noticia.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Objeto_noticias objeto_actualN = caratula_noticas.get(position);
    holder.tituloN.setText(objeto_actualN.getNombreN());
    holder.fechaN.setText(objeto_actualN.getTiempoN());
    holder.autorN.setText(objeto_actualN.getAutorN());
        Picasso
                .get()
                .load(objeto_actualN.getImagenN())
                .into(holder.imagenN);
    }

    @Override
    public int getItemCount() {
        return caratula_noticas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener2=listener;
    }
    @Override
    public void onClick(View view) {
        if(listener2!=null){
            listener2.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenN;
        TextView tituloN;
        TextView fechaN;
        TextView autorN;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenN=itemView.findViewById(R.id.idImagenN);
            tituloN=itemView.findViewById(R.id.idTituloN);
            fechaN=itemView.findViewById(R.id.idFechaN);
            autorN=itemView.findViewById(R.id.idAutor);
        }
    }
}
