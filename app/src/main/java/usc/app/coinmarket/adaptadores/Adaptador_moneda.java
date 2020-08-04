package usc.app.coinmarket.adaptadores;

import android.graphics.Color;

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
import usc.app.coinmarket.objetos.Caratula_individual;


public class Adaptador_moneda extends RecyclerView.Adapter<Adaptador_moneda.ViewHolder> {
    ArrayList<Caratula_individual> caratulaI;

    public Adaptador_moneda(ArrayList<Caratula_individual> caratulaI) {
        this.caratulaI = caratulaI;
    }

    @NonNull
    @Override
    public Adaptador_moneda.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moneda,null,false);

        return new Adaptador_moneda.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_moneda.ViewHolder holder, int position) {
        Caratula_individual objeto_actual1 = caratulaI.get(position);
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        holder.nombrei.setText(objeto_actual1.getNombreI());
        holder.precioo.setText(objeto_actual1.getPrecioI()+" usd");
        holder.rankingi.setText("# "+objeto_actual1.getRankingI());
        holder.porcentajei.setText(String.format("%.2f",Double.parseDouble(objeto_actual1.getPorcentajeI()))+" %");
        holder.cirulating.setText(formatea.format(Double.parseDouble(objeto_actual1.getCirculating())));
        holder.marcap.setText(formatea.format(Double.parseDouble(objeto_actual1.getCap()))+" usd");
        holder.ath.setText(objeto_actual1.getAth()+" usd");
        holder.high.setText(objeto_actual1.getHigh()+" usd");
        holder.low.setText(objeto_actual1.getLow()+" usd");
        if(!objeto_actual1.getTotals().equals("null")){
            holder.tsupl.setText(formatea.format(Double.parseDouble(objeto_actual1.getTotals())));

        }else{
            holder.tsupl.setText(objeto_actual1.getTotals()+"");
        }

        holder.dias.setText(String.format("%.2f",Double.parseDouble(objeto_actual1.getDias7()))+" %");
        holder.anio.setText(String.format("%.2f",Double.parseDouble(objeto_actual1.getAnio()))+" %");
        if (Double.parseDouble(objeto_actual1.getPorcentajeI())<0){
            holder.porcentajei.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.porcentajei.setTextColor(Color.parseColor("#10C84B"));
        }
        if (Double.parseDouble(objeto_actual1.getDias7())<0){
            holder.dias.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.dias.setTextColor(Color.parseColor("#10C84B"));
        }
        if (Double.parseDouble(objeto_actual1.getAnio())<0){
            holder.anio.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.anio.setTextColor(Color.parseColor("#10C84B"));
        }

        Picasso
                .get()
                .load(objeto_actual1.getFotoI())
                .into(holder.imageni);

    }
    @Override
    public int getItemCount() {
        return caratulaI.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView nombrei;
    TextView precioo;
    TextView rankingi;
    TextView porcentajei;
    TextView circulatingNombre;
    TextView cirulating;
    ImageView imageni;
    ImageButton fav;
    TextView marcap;
    TextView ath;
    TextView high;
    TextView low;
    TextView tsupl;
    TextView dias;

    TextView anio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombrei = itemView.findViewById(R.id.idnombreI);
            precioo = itemView.findViewById(R.id.idprecioI);
            imageni = itemView.findViewById(R.id.idfoto);
            rankingi = itemView.findViewById(R.id.idrankingI);
            porcentajei=itemView.findViewById(R.id.idporcentajeI);
            circulatingNombre=itemView.findViewById(R.id.idCirculatingnombre);
            cirulating=itemView.findViewById(R.id.idcirculating);
            fav=itemView.findViewById(R.id.idimgfv);
            marcap=itemView.findViewById(R.id.idcapi);
            ath=itemView.findViewById(R.id.idath);
            high=itemView.findViewById(R.id.idhigh);
            low=itemView.findViewById(R.id.idlow);
            tsupl=itemView.findViewById(R.id.idtsupply);
            dias=itemView.findViewById(R.id.id7dias);
            anio=itemView.findViewById(R.id.idaño);
        }
    }
}
