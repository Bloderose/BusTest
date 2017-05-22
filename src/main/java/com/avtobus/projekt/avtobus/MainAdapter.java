package com.avtobus.projekt.avtobus;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
        import java.util.List;

/**
 * Created by Jaka on 19.5.2017.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private int rowLayout;

    List<Maindata> GlavniPodatki, Arrivals = Collections.emptyList();
    Maindata current;
    int currentPos = 0;
    private RecyclerView mRecyclerView = null;





    public MainAdapter(Context context){
        this.context=context;
        this.GlavniPodatki = GlavniPodatki;
        this.rowLayout = rowLayout;
        inflater = LayoutInflater.from(context);

    }

    public void setData(List<Maindata> UpdateData, List<Maindata> arrivals) {
        GlavniPodatki = UpdateData; //ime npostaje
        Arrivals = arrivals; // seznam prihodov
        Log.d("Prihodi", String.valueOf(Arrivals));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.prikazpodatkov,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    private String ArrayToStr(JSONArray anArray) throws JSONException {
        String prazni = null;
        List<String> list = new ArrayList<String>();
        for (int i=0; i< anArray.length(); i++) {
            for(int j = 0; j < i; i++ ){

            }
        }

        return prazni;
    }

    // prikaz casov postaj NAPAKA NEMOGOCE POPRAVITI
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;        
        Maindata current=GlavniPodatki.get(position);
        Maindata prihodiCas = Arrivals.get(position);

        myHolder.imePostajalisca.setText(String.valueOf(prihodiCas.seznamPrihodov));
        myHolder.imePostajalisca.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        myHolder.idAvtobus.setText(current.nameStation);








//        Glide.with(context).load("http://192.168.1.7/test/images/" + current.fishImage)
//                .placeholder(R.drawable.ic_img_error)
//                .error(R.drawable.ic_img_error)
//                .into(myHolder.ivFish);



    }




    @Override
    public int getItemCount() {
        return GlavniPodatki == null ? 0 : GlavniPodatki.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
        //Log.i("MainAdapter","OnClickListener set");
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView idAvtobus;
        ImageView Slika;
        TextView Prihodi;
        TextView imePostajalisca;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            idAvtobus = (TextView) itemView.findViewById(R.id.idAvtobus);
            Slika= (ImageView) itemView.findViewById(R.id.Slika);
            Prihodi = (TextView) itemView.findViewById(R.id.prihodi);
            imePostajalisca = (TextView) itemView.findViewById(R.id.imePostajalisca);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
            Log.i("MainAdapter", "OnClick");
        }
    }


}
