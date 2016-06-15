package com.evdokimoveu.aboutmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.evdokimoveu.aboutmovie.adapter.FilmListAdapter;
import com.evdokimoveu.aboutmovie.entity.FilmPoster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainWindowActivity extends AppCompatActivity implements FilmListFragment.ClickListener  {

    private ArrayList<FilmPoster> filmPosters;
    private GridView gridView;
    private String currentLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        filmPosters = new ArrayList<>();
        if(savedInstanceState != null){

        }
        else{
            currentLanguage = Locale.getDefault().getLanguage();
            new JSONParser(TheMoveDBConfig.URL
                    + TheMoveDBConfig.SORT_ORDER_BY_POPULAR
                    + "?api_key="
                    + TheMoveDBConfig.KEY
                    + "&language="
                    + currentLanguage).execute();
        }
    }

    @Override
    public void onClick(long id) {
        View fragmentContainer = findViewById(R.id.main_window_fragment);
        if(fragmentContainer != null){
            FilmDetailsFragment fragment = new FilmDetailsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment.setFilmId(id);
            transaction.replace(R.id.film_details_frame_layout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else{
            Intent intent = new Intent(this, FilmDetailsFragment.class);
            intent.putExtra(DetailsActivity.EXTRA_FILM_ID, id);
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

            try {
                JSONObject object = new JSONObject(strJson);
                JSONArray array = object.getJSONArray("results");
                for(int i = 0; i < array.length(); i++){
                    JSONObject filmObject = array.getJSONObject(i);
                    FilmPoster poster = new FilmPoster(
                            filmObject.getLong("id"),
                            filmObject.getString("poster_path")
                    );
                    filmPosters.add(poster);
                }
                gridView = (GridView) findViewById(R.id.film_grid_view);
                if(gridView != null){
                    final FilmListAdapter adapter = new FilmListAdapter(filmPosters, MainWindowActivity.this);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            onClick(adapter.getItemId((position)));
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
