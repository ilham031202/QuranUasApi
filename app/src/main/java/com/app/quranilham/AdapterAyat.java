package com.app.quranilham;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.quranilham.ModelArti.TranslationsItem;
import com.app.quranilham.ModelAyat.VersesItem;

import java.util.List;

public class AdapterAyat extends RecyclerView.Adapter<AdapterAyat.AyatViewHolder> {

    private static List<VersesItem> results;
    private static List<TranslationsItem> results2;

    public AdapterAyat(List<VersesItem> results, List<TranslationsItem> result2) {
        this.results2 = result2;
        this.results = results;
    }

    @NonNull
    @Override
    public AyatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AyatViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ayat, parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterAyat.AyatViewHolder holder, int position) {
        VersesItem ayat = results.get(position);
        TranslationsItem arti = results2.get(position);

        holder.textViewAyat.setText(ayat.getTextUthmani());
        holder.tvTerjemahan.setText(arti.getText());
        holder.tvNomor.setText(String.valueOf(position + 1));
    }
    @Override
    public int getItemCount() {
        return results.size()  ;
    }
    public class AyatViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAyat, tvTerjemahan, tvNomor;
        public AyatViewHolder(View itemView){
            super(itemView);

            tvNomor = itemView.findViewById(R.id.tvNomorAyat);
            textViewAyat = itemView.findViewById(R.id.tvAyat);
            tvTerjemahan = itemView.findViewById(R.id.tvTerjemahanAyat);
        }
    }
    public void setData(List<VersesItem> data, List<TranslationsItem> data1){
        results.clear();
        results.addAll(data);

        results2.clear();
        results2.addAll(data1);
        notifyDataSetChanged();
    }

}
