package com.example.exercicio_obtem_filmes_populares;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Movie> movieList;
    private ListView movieListView;
    private  MovieAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        movieListView = findViewById(R.id.movieListView);
        movieList = new LinkedList<>();
        adapter =
                new MovieAdapter(
                        this,
                        movieList
                );
        movieListView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((v) -> {
            @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String end =
                    getString(
                            R.string.web_service_url,
                            getString(
                                    R.string.api_key
                            )
                    );

            new Thread (
                    () -> {
                        try{
                            URL url = new URL (end);
                            HttpURLConnection conn =
                                    (HttpURLConnection) url.openConnection();
                            InputStream is =
                                    conn.getInputStream();
                            InputStreamReader isr =
                                    new InputStreamReader(is);
                            BufferedReader reader =
                                    new BufferedReader(isr);
                            String linha = null;
                            StringBuilder resultado =
                                    new StringBuilder("");
                            while ((linha = reader.readLine()) != null){
                                resultado.append(linha);
                            }
                            reader.close();
                            conn.disconnect();
                            /*runOnUiThread( () -> {
                                Toast.makeText(
                                        this,
                                        resultado.toString(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            });*/
                            //lidaComJSON(resultado.toString());
                        }
                        catch (MalformedURLException e){
                            e.printStackTrace();
                            runOnUiThread(() ->{
                                Toast.makeText(
                                        this,
                                        getString(
                                                R.string.invalid_url
                                        ),
                                        Toast.LENGTH_SHORT
                                ).show();
                            });

                        }
                        catch (IOException e){
                            e.printStackTrace();
                            runOnUiThread(() ->{
                                Toast.makeText(
                                        this,
                                        getString(
                                                R.string.connect_error
                                        ),
                                        Toast.LENGTH_SHORT
                                ).show();
                            });
                        }
                    }
            ).start();


            //Log.i("meulog", end);
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
