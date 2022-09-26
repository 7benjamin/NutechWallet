package com.example.nutechwallet.base;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.nutechwallet.utils.BehaviorButton;
import com.example.nutechwallet.utils.DialogModule;
import com.example.nutechwallet.utils.LocationModule;


public final class SingletonApp extends Application {

//    private ApiModule apiModule;
    private DialogModule dialogModule;
    private LocationModule locationModule;
    private BehaviorButton behaviorButton;
//    private Database database;

    @Override
    public void onCreate(){
        super.onCreate();

//        apiModule = new ApiModule(this);
        behaviorButton = new BehaviorButton(this);
//        database = new Database(this);
//        if (database.countPostalCode() == 0)
//            database.savePostalCode();

    }

//    public Database getDatabase(){return database;}
    public BehaviorButton getBehavior(){
        return behaviorButton;
    }
//    public Boolean checkDictionary(){return getApi().getSizeHash() > 0;}
//    public ApiModule getApi(){ return apiModule;}
    public DialogModule initDialog(Activity activity){return dialogModule = new DialogModule(activity);}
    public DialogModule getDialog(){return dialogModule.setDialog();}
    public DialogModule getLoading(){return dialogModule.setLoading();}
    public LocationModule initLocation(Context context){return locationModule = new LocationModule(context);};
    public LocationModule getLocModule(){return locationModule;};
}
