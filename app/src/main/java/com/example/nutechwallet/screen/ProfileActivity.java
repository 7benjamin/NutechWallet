package com.example.nutechwallet.screen;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.OnClick;
import nutechwallet.R;
import com.example.nutechwallet.base.BaseActivity;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.example.nutechwallet.model.ProfileJson;
import com.example.nutechwallet.utils.Config;
import com.example.nutechwallet.utils.DialogModule;
import com.example.nutechwallet.utils.EndPoint;
import com.example.nutechwallet.utils.database.UserData;
import com.example.nutechwallet.utils.database.UserMetaData;
import com.example.nutechwallet.utils.network.NetworkClient;

import java.util.List;


public class ProfileActivity extends BaseActivity {
    private Boolean isValid = false;
    private EndPoint endPoint;
    protected UserData userData;
    protected List<UserMetaData> listUserMetaData;
    protected UserMetaData activeUserMetaData;

    protected String email = "";
    protected String firstName = "";
    protected String lastName = "";


    @BindView(R.id.userEmail) TextView userEmail;
    @BindView(R.id.userFirstName) TextView userFirstName;
    @BindView(R.id.userLastName) TextView userLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_card);
        ButterKnife.bind(this);
        userData = new UserData(this);
        listUserMetaData = userData.selectList();
        activeUserMetaData = listUserMetaData.get(0);


        Retrofit retrofitB = new Retrofit.Builder()
                .baseUrl(Config.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClientForGet(activeUserMetaData.getToken()))
                .build();
        endPoint = retrofitB.create(EndPoint.class);

        callProfile();
    }

    protected void initiateViews(){

        userEmail.setText("Email: " +email);
        userFirstName.setText("Nama Depan: " +firstName);
        userLastName.setText("Nama Belakang: " +lastName);

    }


    @OnClick(R.id.cardView)
    public void logOut(){
        onPositiveButton(true);
    }

    @OnClick(R.id.linearUpdate)
    public void updateProfile(){
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        intent.putExtra("first", firstName);
        intent.putExtra("last", lastName);
        startActivity(intent);
        finish();
    }


    private void callProfile(){
        showLoading();

        Call<ProfileJson.RootCallback> call = endPoint.getProfile(activeUserMetaData.getToken());
        call.enqueue(new Callback<ProfileJson.RootCallback>() {
            @Override
            public void onResponse(Call<ProfileJson.RootCallback> call, Response<ProfileJson.RootCallback> response) {
                dismissLoading();
                if (response.isSuccessful()) {
                    if(response.body().getStatus() == 0){
                        email = response.body().getData().email;
                        firstName = response.body().getData().first_name;
                        lastName = response.body().getData().last_name;
                        initiateViews();
                    }else if(response.body().getStatus() == 108){
                        dialog(response.body().getMessage()).setPositiveButton(getResources().getString(R.string.close), new DialogModule.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, @Nullable int id) {
                                onPositiveButton(true);
                            }
                        });
                    }else{
                        dismissLoading();
                        dialog("Failed No Status or Message");
                    }
                }else{
                    dialog("Failed");
                }
            }
            @Override
            public void onFailure(Call<ProfileJson.RootCallback> call, Throwable t) {
                dismissLoading();
                dialog("Failed to connect");
            }
        });
    }

    public void onPositiveButton(boolean error){
        if(error) {
            userData.deleteAll();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }else{
            startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

}

