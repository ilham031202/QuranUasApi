package com.app.quranilham;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.quranilham.ModelArti.TranslationsItem;
import com.app.quranilham.ModelAudio.AudioFilesItem;
import com.app.quranilham.ModelSurah.ChaptersItem;

import java.io.IOException;
import java.util.List;

public class AdapterSurah extends RecyclerView.Adapter<AdapterSurah.ViewHolder> {

    private final List<ChaptersItem> resultsSurah;
    private final List<AudioFilesItem> resultAudio;


    public AdapterSurah(List<ChaptersItem> resultSurah, List<AudioFilesItem> resultAudio) {
        this.resultsSurah = resultSurah;
        this.resultAudio = resultAudio;
    }
    @NonNull
    @Override
    public AdapterSurah.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_surah, parent, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterSurah.ViewHolder holder, int position) {
        ChaptersItem result = resultsSurah.get(position);
        AudioFilesItem resultA = resultAudio.get(position);

        holder.textViewSurahArab.setText(result.getNameArabic());
        holder.textViewSurahLatin.setText(result.getNameSimple());
        holder.textViewSurahNomor.setText(String.valueOf(result.getId()));
        holder.textViewTerjemahanSurah.setText(result.getTranslatedName().getName());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailSurah.class);

            intent.putExtra("id", result.getId());
            intent.putExtra("name_complex", result.getNameComplex());
            intent.putExtra("name_arabic", result.getNameArabic());
            intent.putExtra("revelation_place", result.getRevelationPlace());
            intent.putExtra("verses_count", result.getVersesCount());
            intent.putExtra("audio_url", resultA.getAudioUrl());
            holder.itemView.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return resultsSurah.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSurahLatin, textViewSurahArab, textViewTerjemahanSurah , textViewSurahNomor;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            textViewSurahNomor = itemView.findViewById(R.id.tvSurahNomor);
            textViewSurahArab = itemView.findViewById(R.id.tvSurahArab);
            textViewSurahLatin = itemView.findViewById(R.id.tvSurahLatin);
            textViewTerjemahanSurah = itemView.findViewById(R.id.tvTerjemahanSurah);
        }
    }
    public void setData(List<ChaptersItem> data, List<AudioFilesItem> data1) {
        resultsSurah.clear();
        resultsSurah.addAll(data);

        resultAudio.clear();
        resultAudio.addAll(data1);
        notifyDataSetChanged();
    }
}
