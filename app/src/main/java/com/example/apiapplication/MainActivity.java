package com.example.apiapplication;

import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.apiapplication.model.Charact;



import com.example.apiapplication.rest.HarryPotterApiService;
import com.example.apiapplication.util.CharacterAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CharacterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Создание экземпляра Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hp-api.onrender.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Создание объекта для интерфейса сервиса
        HarryPotterApiService service = retrofit.create(HarryPotterApiService.class);

        // Вызов метода API для получения списка персонажей
        Call<List<Charact>> call = service.getCharacters();
        call.enqueue(new Callback<List<Charact>>() {
            @Override
            public void onResponse(Call<List<Charact>> call, Response<List<Charact>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Ошибка: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<Charact> characts = response.body();
                adapter = new CharacterAdapter(MainActivity.this, characts);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Charact>> call, Throwable t) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Привет: " + t.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }

        });
    }
}
