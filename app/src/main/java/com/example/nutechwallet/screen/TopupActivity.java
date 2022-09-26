package com.example.nutechwallet.screen;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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


import com.example.nutechwallet.model.TopupJson;
import com.example.nutechwallet.utils.Config;
import com.example.nutechwallet.utils.DialogModule;
import com.example.nutechwallet.utils.EndPoint;
import com.example.nutechwallet.utils.database.UserData;
import com.example.nutechwallet.utils.database.UserMetaData;
import com.example.nutechwallet.utils.network.NetworkClient;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class TopupActivity extends BaseActivity {
    private Boolean isValid = false;
    private EndPoint endPoint;
    protected UserData userData;
    protected List<UserMetaData> listUserMetaData;
    protected UserMetaData activeUserMetaData;
    private String current = "";


    @BindView(R.id.inputTopup) EditText inputTopup;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topup);
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


        inputTopup.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                inputTopup.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    inputTopup.setText(formattedString);
                    inputTopup.setSelection(inputTopup.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                inputTopup.addTextChangedListener(this);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
        });
    }


    private boolean isValid(){
        if (inputTopup.getText().toString().length() == 0) {
            dialog("Harap isi " + inputTopup.getHint().toString());
        }else if (inputTopup.getText().toString().equalsIgnoreCase("0")) {
            dialog("Harap isi nominal lebih dari 0");
        }else{
            isValid = true;
        }
        return isValid;
    }

    @OnClick(R.id.cardView)
    public void regisUser(){

        if (isValid()){
            callTopup();
        }

    }

    private void callTopup(){
        showLoading();
        final TopupJson.Root request = new TopupJson().new Root();
        request.setAmount(Integer.valueOf(inputTopup.getText().toString()));

        Call<TopupJson.RootCallback> call = endPoint.topup(request);
        call.enqueue(new Callback<TopupJson.RootCallback>() {
            @Override
            public void onResponse(Call<TopupJson.RootCallback> call, Response<TopupJson.RootCallback> response) {
                dismissLoading();
                if (response.isSuccessful()) {
                    if(response.body().getStatus().equalsIgnoreCase("0")){
                        dialog(response.body().getMessage()).setPositiveButton(getResources().getString(R.string.close), new DialogModule.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, @Nullable int id) {
                                onPositiveButton(false);
                            }
                        });
                    }else if(response.body().getStatus().equalsIgnoreCase("108")){
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
            public void onFailure(Call<TopupJson.RootCallback> call, Throwable t) {
                dismissLoading();
                dialog("Failed to connect");
            }
        });
    }

    public void onPositiveButton(boolean error){
        if(error) {
            userData.deleteAll();
            startActivity(new Intent(TopupActivity.this, LoginActivity.class));
            finish();
        }else{
            startActivity(new Intent(TopupActivity.this, DashboardActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TopupActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

}

