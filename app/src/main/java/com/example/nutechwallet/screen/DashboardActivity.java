package com.example.nutechwallet.screen;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.OnClick;
import nutechwallet.R;
import com.example.nutechwallet.base.BaseActivity;
import com.example.nutechwallet.model.BalanceJson;
import com.example.nutechwallet.model.HistoryTransJson;
import com.example.nutechwallet.model.HistoryTransModel;
import com.example.nutechwallet.model.RegisterJson;
import com.example.nutechwallet.model.SignInJson;
import com.example.nutechwallet.utils.Config;
import com.example.nutechwallet.utils.CurrencyUtils;
import com.example.nutechwallet.utils.DialogModule;
import com.example.nutechwallet.utils.EndPoint;
import com.example.nutechwallet.utils.NumberSeparator;
import com.example.nutechwallet.utils.PrefManager;

import com.example.nutechwallet.utils.database.UserData;
import com.example.nutechwallet.utils.database.UserMetaData;
import com.example.nutechwallet.utils.network.NetworkClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardActivity extends BaseActivity {

    protected UserData userData;
    protected List<UserMetaData> listUserMetaData;
    protected UserMetaData activeUserMetaData;
    private EndPoint endPoint;
    private Adapter adapter;
    protected List<HistoryTransModel> list = new ArrayList<HistoryTransModel>();
    protected String saldo = "0";
    private Boolean doubleBackToExitPressedOnce = false;




    @BindView(R.id.saldoTxt ) TextView saldoTxt;
    @BindView(R.id.accountName ) TextView accountName;
    @BindView(R.id.recyclerView ) RecyclerView recyclerView;
    @BindView(R.id.linearNoData ) LinearLayout linearNoData;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
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

        callSaldo();


    }

    protected void initiateViews(){

        accountName.setText(accountName.getText().toString() + " "+ activeUserMetaData.getFirstName());
        saldoTxt.setText(getString(R.string.rupiahCurrency, new NumberSeparator(DashboardActivity.this).replaceComma(CurrencyUtils.thousandFormat((Double.valueOf(saldo))))));

        adapter = new Adapter(list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        if (list == null || list.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            linearNoData.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            linearNoData.setVisibility(View.VISIBLE);
        }

    }

    protected void callSaldo(){
        showLoading();
        Call<BalanceJson.RootCallback> call = endPoint.getBalance(activeUserMetaData.getToken());
        call.enqueue(new Callback<BalanceJson.RootCallback>() {
            @Override
            public void onResponse(Call<BalanceJson.RootCallback> call, Response<BalanceJson.RootCallback> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus() == 0){
                        if (response.body().getData().balance != null)
                            saldo = response.body().getData().balance;
                        else
                            saldo = "0";

                        callHistory();
                    }else if(response.body().getStatus() == 108){
                        dialog(response.body().getMessage()).setPositiveButton(getResources().getString(R.string.close), new DialogModule.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, @Nullable int id) {
                                forceLogout();
                            }
                        });
                    }else{
                        dismissLoading();
                        initiateViews();
                    }
                }else{
                    dismissLoading();
                    initiateViews();
                }
            }
            @Override
            public void onFailure(Call<BalanceJson.RootCallback> call, Throwable t) {
                dismissLoading();
                initiateViews();
            }
        });
    }

    protected void callHistory(){
        Call<HistoryTransJson.RootCallback> call = endPoint.getListTransaksi(activeUserMetaData.getToken());
        call.enqueue(new Callback<HistoryTransJson.RootCallback>() {
            @Override
            public void onResponse(Call<HistoryTransJson.RootCallback> call, Response<HistoryTransJson.RootCallback> response) {
                dismissLoading();
                if (response.isSuccessful()) {
                    if(response.body().getStatus() == 0){
                            setHistory(response.body());
                    }else if(response.body().getStatus() == 108){
                        dialog(response.body().getMessage()).setPositiveButton(getResources().getString(R.string.close), new DialogModule.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, @Nullable int id) {
                                forceLogout();
                            }
                        });
                    }else{
                        dismissLoading();
                        initiateViews();
                    }
                }else{
                    initiateViews();
                }
            }
            @Override
            public void onFailure(Call<HistoryTransJson.RootCallback> call, Throwable t) {
                dismissLoading();
                initiateViews();
            }
        });
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

    @OnClick(R.id.imgTopup)
    public void logout(){
        Intent intent = new Intent(DashboardActivity.this, TopupActivity.class);
        startActivity(intent);
        finish();
    }



        protected void forceLogout(){
            userData.deleteAll();
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    @OnClick(R.id.imageAccount)
    public void profileActivity(){
        Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.imgTransfer)
    public void transferActivity(){
        dialog("Fitur dalam tahap pengembangan");
    }


    public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        protected List<HistoryTransModel> list;

        public Adapter(List<HistoryTransModel> list) {
            this.list = list;
        }

        public void updateList(List<HistoryTransModel> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view;
//                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.v1_loan_history_content, viewGroup, false);
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_list, viewGroup, false);
            return new Content(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder view, final int pos) {

            ((Content) view).txtId.setText(list.get(pos).getTransaction_id());
            ((Content) view).txtTime.setText(list.get(pos).getTransaction_time());
            ((Content) view).txtType.setText(list.get(pos).getTransaction_type());
            ((Content) view).txtAmount.setText(getString(R.string.rupiahCurrency, new NumberSeparator(DashboardActivity.this).replaceComma(CurrencyUtils.thousandFormat((Double.valueOf(list.get(pos).getAmount()))))));

        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }


        public class Content extends RecyclerView.ViewHolder {
            @BindView(R.id.txtId) TextView txtId;
            @BindView(R.id.txtTime) TextView txtTime;
            @BindView(R.id.txtType) TextView txtType;
            @BindView(R.id.txtAmount) TextView txtAmount;

            public Content(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    protected void setHistory(HistoryTransJson.RootCallback data){

        list = new ArrayList<HistoryTransModel>();


        if (data.getData().size() != 0){

            for (HistoryTransJson.RootCallback.Data metadata : data.getData()){
                HistoryTransModel model = new HistoryTransModel();


                if (metadata.transaction_id != null)
                    model.setTransaction_id(metadata.transaction_id);
                else
                    model.setTransaction_id("");

                if (metadata.transaction_time != null)
                    model.setTransaction_time(metadata.transaction_time);
                else
                    model.setTransaction_time("");

                if (metadata.transaction_type != null)
                    model.setTransaction_type(metadata.transaction_type);
                else
                    model.setTransaction_type("");

                if (metadata.transaction_type != null)
                    model.setTransaction_type(metadata.transaction_type);
                else
                    model.setTransaction_type("");

                if (metadata.amount != null)
                    model.setAmount(metadata.amount);
                else
                    model.setAmount("");


                list.add(model);


            }
            initiateViews();
        }else{
            initiateViews();
        }

    }


}
