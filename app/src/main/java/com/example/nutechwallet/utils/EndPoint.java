package com.example.nutechwallet.utils;

import com.example.nutechwallet.model.BalanceJson;
import com.example.nutechwallet.model.HistoryTransJson;
import com.example.nutechwallet.model.ProfileJson;
import com.example.nutechwallet.model.RegisterJson;
import com.example.nutechwallet.model.SignInJson;
import com.example.nutechwallet.model.TopupJson;
import com.example.nutechwallet.model.UpdateProfileJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EndPoint {


    @POST("registration")
    Call<RegisterJson.RootCallback> register(@Body RegisterJson.Root request);

    @POST("login")
    Call<SignInJson.RootCallback> signInNew(@Body SignInJson.Root request);

    @GET("balance")
    Call<BalanceJson.RootCallback> getBalance(@Header("Authorization") String token);

    @GET("transactionHistory")
    Call<HistoryTransJson.RootCallback> getListTransaksi(@Header("Authorization") String token);

    @POST("topup")
    Call<TopupJson.RootCallback> topup(@Body TopupJson.Root request);

    @GET("getProfile")
    Call<ProfileJson.RootCallback> getProfile(@Header("Authorization") String token);

    @POST("updateProfile")
    Call<UpdateProfileJson.RootCallback> updateProfile(@Body UpdateProfileJson.Root request);


}
