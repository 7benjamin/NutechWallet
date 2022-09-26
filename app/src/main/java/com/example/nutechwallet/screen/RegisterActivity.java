package com.example.nutechwallet.screen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

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

import com.example.nutechwallet.model.RegisterJson;
import com.example.nutechwallet.model.SignInJson;
import com.example.nutechwallet.utils.Config;
import com.example.nutechwallet.utils.DialogModule;
import com.example.nutechwallet.utils.EndPoint;
import com.example.nutechwallet.utils.network.NetworkClient;


public class RegisterActivity extends BaseActivity {
    private Boolean isValid = false;
    private EndPoint endPoint;

    @BindView(R.id.linearEmail) LinearLayout linearEmail;



    @BindView(R.id.inputEmail) EditText inputEmail;
    @BindView(R.id.inputPassword) EditText inputPassword;
    @BindView(R.id.inputNamaDepan) EditText inputNamaDepan;
    @BindView(R.id.inputNamaBelakang) EditText inputNamaBelakang;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        Retrofit retrofitB = new Retrofit.Builder()
                .baseUrl(Config.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();
        endPoint = retrofitB.create(EndPoint.class);
    }


    private boolean isValid(){
        if (inputEmail.getText().toString().length() == 0) {
            dialog("Harap isi " + inputEmail.getHint().toString());
        }else if (inputPassword.getText().toString().length() == 0) {
            dialog("Harap isi " + inputPassword.getHint().toString());
        }else if (inputNamaDepan.getText().toString().length() == 0) {
            dialog("Harap isi " + inputNamaDepan.getHint().toString());
        }else if (inputNamaBelakang.getText().toString().length() == 0) {
            dialog("Harap isi " + inputNamaBelakang.getHint().toString());
        }else if ((inputEmail.getText().toString().length() > 0
                && !Config.EMAIL_PATTERN.matcher(inputEmail.getText().toString()).matches())){
            dialog("Format Email Tidak Sesuai");
        }else{
            isValid = true;
        }
        return isValid;
    }

    @OnClick(R.id.cardView)
    public void regisUser(){

        if (isValid()){
            callRegis();
        }

    }

    private void callRegis(){
            showLoading();
            final RegisterJson.Root request = new RegisterJson().new Root();
            request.setEmail(inputEmail.getText().toString());
            request.setPassword(inputPassword.getText().toString());
            request.setFirst_name(inputNamaDepan.getText().toString());
            request.setLast_name(inputNamaBelakang.getText().toString());

            Call<RegisterJson.RootCallback> call = endPoint.register(request);
            call.enqueue(new Callback<RegisterJson.RootCallback>() {
                @Override
                public void onResponse(Call<RegisterJson.RootCallback> call, Response<RegisterJson.RootCallback> response) {
                    dismissLoading();
                    if (response.isSuccessful()) {
                        if(response.body().getStatus().equalsIgnoreCase("0")){
                            dialog(response.body().getMessage()).setPositiveButton(getResources().getString(R.string.close), new DialogModule.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, @Nullable int id) {
                                    onPositiveButton();
                                }
                            });
                        }else if(response.body().getStatus().equalsIgnoreCase("103")){
                            dialog(response.body().getMessage());
                        }else{
                            dismissLoading();
                            dialog("Failed No Status or Message");
                        }
                    }else{
                        dialog("Failed");
                    }
                }
                @Override
                public void onFailure(Call<RegisterJson.RootCallback> call, Throwable t) {
                    dismissLoading();
                    dialog("Failed to connect");
                }
            });
    }

    public void onPositiveButton(){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
