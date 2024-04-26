package com.example.apiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.apiapplication.model.Charact;



import com.example.apiapplication.rest.HarryPotterApiService;
import com.example.apiapplication.util.CharacterAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CharacterAdapter adapter;
    private SearchView searchView;
    List<Charact> limitedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();


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
                int limit =25;
                List<Charact> characts = response.body();
                limitedList = characts.subList(0,Math.min(characts.size(),limit));
                adapter = new CharacterAdapter(MainActivity.this, limitedList);
                recyclerView.setAdapter(adapter);
                adapter.setOnCharacterClickListener(new CharacterAdapter.OnCharactClickListener() {
                    @Override
                    public void onCharactClick(String characterId) {
                        Intent intent = new Intent(MainActivity.this,CharacterDetailActivity.class);
                        intent.putExtra("character_id",characterId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Charact>> call, Throwable t) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Привет: " + t.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }

        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterList(newText);
//                return true;
//            }
//        });
    }

//    private void filterList(String newText) {
//        List<Charact> filteredList = new ArrayList<>();
//        for (Charact c:limitedList){
//            if (c.getName().toLowerCase().contains(newText.toLowerCase())){
//                filteredList.add(c);
//            }
//        }
//
//            adapter.setFilteredList(filteredList);
//
//    }
}

