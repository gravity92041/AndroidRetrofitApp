package com.example.apiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apiapplication.model.Charact;
import com.example.apiapplication.rest.HarryPotterApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterDetailActivity extends AppCompatActivity {
    private ImageView imageView2;
    private TextView nameTextView,houseTextView,actorTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        imageView2 = findViewById(R.id.imageView2);
        nameTextView = findViewById(R.id.nameTextView);
        houseTextView = findViewById(R.id.houseTextView);
        actorTextView = findViewById(R.id.actorTextView);
//        displayCharacterInfo();

        String characterId = getIntent().getStringExtra("character_id");

        // Создание объекта для интерфейса сервиса
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hp-api.onrender.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HarryPotterApiService service = retrofit.create(HarryPotterApiService.class);

// Вызов метода API для получения персонажа по его ID
        Call<List<Charact>> call = service.getCharactById(characterId);
        call.enqueue(new Callback<List<Charact>>() {
            @Override
            public void onResponse(Call<List<Charact>> call, Response<List<Charact>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("ОШИБКА ИЗ НЕУДАЧНОГО РЕСПОНСА");
                    return;
                }

                List<Charact> charact = response.body();
                Charact character = charact.get(0);
                if (character.getGender().equals("female")){
                    actorTextView.setText("Играющая актриса: " + character.getActor());
                }
                else {
                    actorTextView.setText("Играющий актер: "+ character.getActor());
                }
                nameTextView.setText("Имя: "+ character.getName());
                houseTextView.setText("Факультет: "+ character.getHouse());
                Picasso.get().load(character.getImage()).into(imageView2);
                // Отобразите информацию о персонаже
            }

            @Override
            public void onFailure(Call<List<Charact>>call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });



//        loadCharacterById(characterId);
    }
    private void displayCharacterInfo() {
        // В этом примере используется заглушка для изображения и имя персонажа
        String imageUrl = "https://ik.imagekit.io/hpapi/harry.jpg";
        String characterName = "Harry Potter";

        // Загрузка изображения с помощью Picasso
        Picasso.get().load(imageUrl).into(imageView2);

        // Установка имени персонажа
        nameTextView.setText(characterName);
    }

}