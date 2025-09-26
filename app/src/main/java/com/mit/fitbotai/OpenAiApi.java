package com.mit.fitbotai;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenAiApi {
    @POST("v1/chat/completions")
    Call<ChatResponse> createChatCompletion(
            @Header("Authorization") String authHeader,
            @Body ChatRequest chatRequest
    );
}