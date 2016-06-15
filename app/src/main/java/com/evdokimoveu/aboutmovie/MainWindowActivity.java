package com.evdokimoveu.aboutmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.evdokimoveu.aboutmovie.entity.FilmPoster;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainWindowActivity extends AppCompatActivity implements FilmListFragment.ClickListener  {

    private int filmId;
    private ArrayList<FilmPoster> filmPosters;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
    }

    @Override
    public void onClick(long id) {
        View fragmentContainer = findViewById(R.id.main_window_fragment);
        if(fragmentContainer != null){
            FilmDetailsFragment fragment = new FilmDetailsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment.setFilmId(filmId);
            transaction.replace(R.id.film_details_frame_layout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else{
            Intent intent = new Intent(this, FilmDetailsFragment.class);
            intent.putExtra(DetailsActivity.EXTRA_FILM_ID, filmId);
            startActivity(intent);
        }
    }

    private class JSONParser extends AsyncTask<String, String, String>{

        private String resultJson;
        private String urlRequest;
        private OkHttpClient client;

        public JSONParser(String urlRequest) {
            this.urlRequest = urlRequest;
            this.resultJson = "";
            this.client = new OkHttpClient();
        }

        @Override
        protected String doInBackground(String... params) {
            Request request = new Request.Builder().url(urlRequest).build();
            try {
                Response response = client.newCall(request).execute();
                resultJson = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);


        }
    }

}
