package com.app.quranilham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.app.quranilham.ModelAudio.Audio;
import com.app.quranilham.ModelAudio.AudioFilesItem;
import com.app.quranilham.ModelSurah.ChaptersItem;
import com.app.quranilham.ModelSurah.Surah;
import com.app.quranilham.Retrofit.ApiQuran;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AdapterSurah adapterSurah;
    private RecyclerView rv;
    RecyclerView.LayoutManager lm;
    private List<ChaptersItem> results = new ArrayList<>();
    private List<AudioFilesItem> list = new ArrayList<>();

    List<AudioFilesItem> audio;
    List<ChaptersItem> chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAudioFromApi();
        setUpView();
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        adapterSurah = new AdapterSurah(results, list);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapterSurah);
    }
    private void setUpView() {
        rv = findViewById(R.id.rvSurah);
    }

    private void getAudioFromApi() {
        ApiQuran.endpoint().getAudio().enqueue(new Callback<Audio>() {
            @Override
            public void onResponse(@NonNull Call<Audio> call, @NonNull Response<Audio> response) {
                assert response.body() != null;
                MainActivity.this.audio = response.body().getAudioFiles();
                getDataFromApi();
            }
            @Override
            public void onFailure(@NonNull Call<Audio> call, @NonNull Throwable t) {

            }
        });
    }

    private void getDataFromApi() {
        ApiQuran.endpoint().getSurah().enqueue(new Callback<Surah>() {
            @Override
            public void onResponse(@NonNull Call<Surah> call, @NonNull Response<Surah> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    MainActivity.this.chapter = response.body().getChapters();
                    adapterSurah.setData(chapter, audio);
                }
            }

            @Override
            public void onFailure(Call<Surah> call, Throwable t) {
                Log.d("ErrorMain", t.toString());
            }
        });
    }
}