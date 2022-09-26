package com.example.nutechwallet.screen;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;


import nutechwallet.R;
import com.example.nutechwallet.base.BaseActivity;
import com.example.nutechwallet.model.SignInJson;
import com.example.nutechwallet.utils.Config;
import com.example.nutechwallet.utils.DialogModule;
import com.example.nutechwallet.utils.EndPoint;
import com.example.nutechwallet.utils.database.UserData;
import com.example.nutechwallet.utils.database.UserMetaData;
import com.example.nutechwallet.utils.network.NetworkClient;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {



    @BindView(R.id.linearEmail) LinearLayout linearEmail;
    @BindView(R.id.linearPassword) LinearLayout linearPassword;

    @BindView(R.id.inputEmail) EditText inputEmail;
    @BindView(R.id.inputPassword) EditText inputPassword;

    @BindView(R.id.inputLayoutError) TextInputLayout inputLayoutError;
    @BindView(R.id.inputLayoutError2) TextInputLayout inputLayoutError2;

    @BindView(R.id.txtValue) TextView txtValue;
    @BindView(R.id.textLink) TextView textLink;

    @BindView(R.id.cardView) CardView cardView;

    @BindView(R.id.parent) ConstraintLayout parent;

    private EndPoint endPoint;
    private String HERE = "disini",custom, error = "", error2 = "";
    private SpannableString ss, us;
    private ClickableSpan clickSpan;
    private Boolean doubleBackToExitPressedOnce = false;
    protected UserData userData;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        us = new SpannableString(HERE);
        us.setSpan(new UnderlineSpan(), 0, us.length(), 0);

        custom = textLink.getText().toString()+" "+us;
        ss = new SpannableString(custom);
        clickSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        };
        userData = new UserData(this);


        textLink.setHighlightColor(Color.TRANSPARENT);
        ss.setSpan(clickSpan, custom.indexOf(HERE) , custom.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textLink.setText(ss);
        textLink.setMovementMethod(LinkMovementMethod.getInstance());
        initiateViews();

        Retrofit retrofitB = new Retrofit.Builder()
                .baseUrl(Config.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();
        endPoint = retrofitB.create(EndPoint.class);
    }

    private void initiateViews(){

        parent.requestFocus();
        noDoubleSpace(inputEmail);
    }

    private void noDoubleSpace(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll("\\s+", "");
                if (!s.toString().equals(result)) {
                    editText.setText(result);
                    editText.setSelection(result.length());
                }

            }
        });

    }

    protected void callSignIn(final String email, final String password) {
        showLoading();
            final SignInJson.Root request = new SignInJson().new Root();
            request.setEmail(email);
            request.setPassword(password);

            Call<SignInJson.RootCallback> call = endPoint.signInNew(request);
            call.enqueue(new Callback<SignInJson.RootCallback>() {
                @Override
                public void onResponse(Call<SignInJson.RootCallback> call, Response<SignInJson.RootCallback> response) {
                    dismissLoading();
                    if (response.isSuccessful()) {
                        if(response.body().getStatus().equalsIgnoreCase("0"))
                            if(response.body().getData().token != null) {
                                userData.deleteAll();
                                saveSignIn(response.body());
                            }else{
                                dialog("Tidak ada token");
                            }
                        else if(response.body().getStatus().equalsIgnoreCase("102"))
                            dialog(response.body().getMessage());
                        else
                            dialog("Format Email Tidak Sesuai");
                    }else{
                        dismissLoading();
                        dialog("Response Failed");
                    }
                }

                @Override
                public void onFailure(Call<SignInJson.RootCallback> call, Throwable t) {
                    dismissLoading();
                    dialog("Failed to Connect");
                }
            });
    }

    private void saveSignIn(SignInJson.RootCallback data){
        UserMetaData metaData = new UserMetaData();

        if (data.getData().email != null)
            metaData.setEmail(data.getData().email);
        else
            metaData.setEmail("");

        if (data.getData().first_name != null)
            metaData.setFirstName(data.getData().first_name);
        else
            metaData.setFirstName("");

        if (data.getData().last_name != null)
            metaData.setLastName(data.getData().last_name);
        else
            metaData.setLastName("");

        if (data.getData().token != null)
            metaData.setToken(data.getData().token);
        else
            metaData.setToken("");
        userData.save(metaData);

        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        finish();

    }


    @OnClick(R.id.cardView)
    public void isValid(){

        if (inputEmail.getText().toString().length() == 0){
            dialog("Harap isi "+ inputEmail.getHint().toString());
        }else if (inputPassword.getText().toString().length() == 0){
            dialog("Harap isi "+ inputPassword.getHint().toString());
        }else if ((inputEmail.getText().toString().length() > 0
                && !Config.EMAIL_PATTERN.matcher(inputEmail.getText().toString()).matches())){
            dialog("Format Email Tidak Sesuai");
        }else
            callSignIn(inputEmail.getText().toString(),inputPassword.getText().toString());
    }



    @Override
    public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.doubleBackPressConfirmation, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

    }


}
