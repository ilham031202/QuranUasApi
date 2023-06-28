package com.app.quranilham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.app.quranilham.ModelArti.Arti;
import com.app.quranilham.ModelArti.TranslationsItem;
import com.app.quranilham.ModelAyat.Ayat;
import com.app.quranilham.ModelAyat.VersesItem;

import com.app.quranilham.Retrofit.ApiQuran;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSurah extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterAyat adapterAyat;

    private final List<VersesItem> results = new ArrayList<>();
    private final List<TranslationsItem> results2 = new ArrayList<>();

    List<VersesItem> ayat;
    List<TranslationsItem> arti;

    TextView tvTitle, tvPlace, tvJumlahAyat, tvArabic;
    Button btAudio;

    MediaPlayer mediaPlayer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surah);

        int id = getIntent().getIntExtra("id", 1);

        String tvtitle = getIntent().getStringExtra("name_complex");
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(tvtitle);

        String tvplace = getIntent().getStringExtra("revelation_place");
        tvPlace = findViewById(R.id.tvPlace);
        tvPlace.setText("Diturunkan di " + (tvplace));

        int tvjmlhayat = getIntent().getIntExtra("verses_count", 1);
        tvJumlahAyat = findViewById(R.id.tvJumlahAyat);
        tvJumlahAyat.setText("Jumlah Ayat " + (tvjmlhayat));

        String tvarabic = getIntent().getStringExtra("name_arabic");
        tvArabic = findViewById(R.id.tvArabic);
        tvArabic.setText("Nama Arabic   " + (tvarabic));

        mediaPlayer = new MediaPlayer();
        String btaudio = getIntent().getStringExtra("audio_url");
        btAudio = findViewById(R.id.btAudio);
        btAudio.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()){
                pauseAudio();
            } else {
                playAudio(btaudio);
            }
        });

        setUpView();
        setUpRecyclerView();
        getDataFromApi(id);
    }

    private void playAudio(String audio) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audio);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void pauseAudio() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void getDataFromApi(int id) {
        ApiQuran.endpoint().getAyat(id).enqueue(new Callback<Ayat>() {
            @Override
            public void onResponse(@NonNull Call<Ayat> call, @NonNull Response<Ayat> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    DetailSurah.this.ayat = response.body().getVerses();
                    getDataArtiFromApi(getIntent().getIntExtra("id", 1));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Ayat> call, @NonNull Throwable t) {
            }
        });
    }

    private void getDataArtiFromApi(int id){
        ApiQuran.endpoint().getArti(id).enqueue(new Callback<Arti>() {
            @Override
            public void onResponse(@NonNull Call<Arti> call, @NonNull Response<Arti> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    DetailSurah.this.arti = response.body().getTranslations();
                    adapterAyat.setData(ayat, arti);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Arti> call, @NonNull Throwable t) {

            }
        });
    }
    private void setUpRecyclerView() {
        adapterAyat = new AdapterAyat(results, results2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterAyat);
    }

    private void setUpView() {
        recyclerView = findViewById(R.id.rvAyat);
    }


}