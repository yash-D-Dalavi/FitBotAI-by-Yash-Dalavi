package com.mit.fitbotai;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etQuery;
    private Button btnAskAI;
    private TextView tvResponse;
    private OpenAiApi openAiApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etQuery = findViewById(R.id.etQuery);
        btnAskAI = findViewById(R.id.btnAskAI);
        tvResponse = findViewById(R.id.tvResponse);

        // Setup Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openAiApi = retrofit.create(OpenAiApi.class);

        btnAskAI.setOnClickListener(v -> getApiResponse());
    }

    private void getApiResponse() {
        String query = etQuery.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a query", Toast.LENGTH_SHORT).show();
            return;
        }

        tvResponse.setText("Thinking...");

        // Create the request body
        Message userMessage = new Message("user", query);
        ChatRequest request = new ChatRequest("gpt-3.5-turbo", Collections.singletonList(userMessage));

        // Use the API key from BuildConfig
        String apiKey = BuildConfig.OPENAI_API_KEY;

        Call<ChatResponse> call = openAiApi.createChatCompletion("Bearer " + apiKey, request);

        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().choices.isEmpty()) {
                    String aiResponse = response.body().choices.get(0).message.content;
                    tvResponse.setText(aiResponse.trim());
                } else {
                    tvResponse.setText("Error: Failed to get response. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                tvResponse.setText("Failure: " + t.getMessage());
            }
        });
    }
}