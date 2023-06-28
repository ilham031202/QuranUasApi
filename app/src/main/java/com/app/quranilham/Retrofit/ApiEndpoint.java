package com.app.quranilham.Retrofit;

import com.app.quranilham.ModelArti.Arti;
import com.app.quranilham.ModelAudio.Audio;
import com.app.quranilham.ModelAyat.Ayat;
import com.app.quranilham.ModelSurah.Surah;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndpoint {
    @GET("chapters?language=id")
    Call<Surah> getSurah();
    @GET("chapter_recitations/33?")
    Call<Audio> getAudio();
    @GET("quran/verses/uthmani?")
    Call<Ayat> getAyat(@Query("chapter_number") int id);
    @GET("quran/translations/33?")
    Call<Arti> getArti(@Query("chapter_number")int id);

}
