package com.example.nutechwallet.screen;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

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


import com.example.nutechwallet.model.UpdateProfileJson;
import com.example.nutechwallet.utils.Config;
import com.example.nutechwallet.utils.DialogModule;
import com.example.nutechwallet.utils.EndPoint;
import com.example.nutechwallet.utils.database.UserData;
import com.example.nutechwallet.utils.database.UserMetaData;
import com.example.nutechwallet.utils.network.NetworkClient;

import java.util.List;


public class UpdateProfileActivity extends BaseActivity {
    private Boolean isValid = false;
    private EndPoint endPoint;
    protected UserData userData;
    protected List<UserMetaData> listUserMetaData;
    protected UserMetaData activeUserMetaData;


    @BindView(R.id.inputNamaDepan) EditText inputNamaDepan;
    @BindView(R.id.inputNamaBelakang) EditText inputNamaBelakang;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
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

        inputNamaDepan.setText(getIntent().getExtras().getString("first"));
        inputNamaBelakang.setText(getIntent().getExtras().getString("last"));

    }


    private boolean isValid(){
        if (inputNamaDepan.getText().toString().length() == 0) {
            dialog("Harap isi " + inputNamaDepan.getHint().toString());
        }else if (inputNamaBelakang.getText().toString().length() == 0) {
            dialog("Harap isi " + inputNamaBelakang.getHint().toString());
        }else{
            isValid = true;
        }
        return isValid;
    }

    @OnClick(R.id.cardView)
    public void updateProfile(){

        if (isValid()){
            callUpdate();
        }

    }

    private void callUpdate(){
        showLoading();
        final UpdateProfileJson.Root request = new UpdateProfileJson().new Root();
        request.setFirst_name(inputNamaDepan.getText().toString());
        request.setLast_name(inputNamaBelakang.getText().toString());

        Call<UpdateProfileJson.RootCallback> call = endPoint.updateProfile(request);
        call.enqueue(new Callback<UpdateProfileJson.RootCallback>() {
            @Override
            public void onResponse(Call<UpdateProfileJson.RootCallback> call, Response<UpdateProfileJson.RootCallback> response) {
                dismissLoading();
                if (response.isSuccessful()) {
                    if(response.body().getStatus() == 0){
                        dialog(response.body().getMessage()).setPositiveButton(getResources().getString(R.string.close), new DialogModule.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, @Nullable int id) {
                                onPositiveButton(false);
                            }
                        });
                    }else if(response.body().getStatus() == 102){
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
            public void onFailure(Call<UpdateProfileJson.RootCallback> call, Throwable t) {
                dismissLoading();
                dialog("Failed to connect");
            }
        });
    }

    public void onPositiveButton(boolean error){
        if(error) {
            userData.deleteAll();
            startActivity(new Intent(UpdateProfileActivity.this, LoginActivity.class));
            finish();
        }else{
            startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateProfileActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

}

