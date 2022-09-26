package com.example.nutechwallet.base;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.nutechwallet.utils.BehaviorButton;
import com.example.nutechwallet.utils.DialogModule;
import com.example.nutechwallet.utils.LocationModule;
import com.google.gson.JsonObject;

import nutechwallet.R;
import retrofit2.Call;
import retrofit2.Callback;

public abstract class BaseActivity extends AppCompatActivity implements  LocationModule.LocationResult {

    private String[] location = new String[2];
    private Boolean dictNull = false, isLocEnable = true;
    protected Bundle bundle;
    private String[] request;
    private String nekot;
    private SingletonApp singletonApp;
    protected Call call;
    private JsonObject jsonObject;
    protected byte[][] fileImage = new byte[10][];
    public String username;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        bundle = new Bundle();
        singletonApp = (SingletonApp) getApplication();
//        singletonApp.getApi().setOnResultApi(this);
        singletonApp.initDialog(this);
        singletonApp.initLocation(this);
        singletonApp.getLocModule().setOnResultApi(this);

    }



    public <T> void setError(String callback) {
        if (!callback.equalsIgnoreCase(""))
            onError(callback);
    }



    public <T> void invalidVersion(String link) {
        dismissLoading();
        singletonApp.getDialog().dialogPlaystore(link);
    }

    public void showLoading(){
        singletonApp.getLoading().showLoading();

    }


    public void dismissLoading(){
        singletonApp.getLoading().dismissLoading();
    }

    public BehaviorButton getBehavior(){
        return singletonApp.getBehavior();
    }





    protected <T> void onNext(){}
    protected <T> void onError(String message){}

    protected <T> void onRetry(String message, Call call, Callback callback){}

    protected <T> DialogModule dialogRetry(final String message, final Call call, final Callback callback){
        return singletonApp.getDialog().dialogInfo(message).setPositiveButton(getResources().getString(R.string.retry), new DialogModule.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, @Nullable int id) {
                setApi();
            }
        }).setNegativeButton(getResources().getString(R.string.cancel), new DialogModule.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, @Nullable int id) {
                dismissLoading();
            }
        });
    }

    public  <T> DialogModule dialog(final String message){
        return singletonApp.getDialog().dialogInfo(message).setPositiveButton(getResources().getString(R.string.close), new DialogModule.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, @Nullable int id) {
                dialog.dismiss();
                //onPositiveButton();
            }
        });
    }

    public  <T> DialogModule dialog2(final String message){
        return singletonApp.getDialog().dialogInfo2(message).setPositiveButton(getResources().getString(R.string.close), new DialogModule.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, @Nullable int id) {
                dialog.dismiss();
                //onPositiveButton();
            }
        });
    }


    protected void setApi(){}
    protected void onPositiveButton(){}
    public void autoChecked(){}
    public void resendOtp(String phoneNumber){}
    public void validOtp(String otp, String noReff, String phone){}
    public void dismissOtp(){}

    public void hideSoftKeyboard(View input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }
    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive())
            if (getCurrentFocus() != null) imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


    public void startLocation(){
        singletonApp.getLocModule().getLocation();
    }

    @Override
    public void onMockLocationsDetected(String message){
        setLocEnable(true);
        onError(message);
    }

    @Override
    public void onGetLocation(Location location){
        setLocEnable(false);
        this.location = new String[2];
        this.location[0] = String.valueOf(location.getLatitude());
        this.location[1] = String.valueOf(location.getLongitude());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        singletonApp.getLocModule().onPermissionsUpdated(requestCode,grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        singletonApp.getLocModule().onActivityResult(requestCode, resultCode);
    }

    public String[] getLocation(){
        return location;
    }

    public Boolean isLocEnable(){
        return isLocEnable;
    }

    public void setLocEnable(Boolean isLocEnable){
        this.isLocEnable = isLocEnable;
    }

}
