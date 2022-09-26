package com.example.nutechwallet.utils;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import nutechwallet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

        public class DialogModule {

        private Context context;
        private Dialog dialog = null, loading = null;
        private View view, viewProgress;
        private final DialogViews dialogViews = new DialogViews();
        private final LoadingViews loadingViews = new LoadingViews();

        class DialogViews{
        @BindView(R.id.imageView) ImageView imageView;
        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.txtNote) TextView txtNote;
        @BindView(R.id.txtValueRight) TextView txtValueRight;
        @BindView(R.id.txtValueLeft) TextView txtValueLeft;
        @BindView(R.id.cardViewLeft) CardView cardViewLeft;
        @BindView(R.id.cardViewRight) CardView cardViewRight;
        }

        public DialogModule(Context context){
        this.context = context;
        }

        public DialogModule setDialog(){
        if (dialog == null) {
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(context, R.layout.dialog_view, null);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        ButterKnife.bind(dialogViews, view);
        clear();
        }

        return this;
        }

        private void clear(){
        dialogViews.imageView.setVisibility(View.GONE);
        dialogViews.txtTitle.setVisibility(View.GONE);
        dialogViews.txtNote.setVisibility(View.GONE);
        dialogViews.cardViewLeft.setVisibility(View.GONE);
        dialogViews.cardViewRight.setVisibility(View.GONE);

        if (!dialog.isShowing())
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface e) {
        dialog = null;
        }
        });

        }


        class LoadingViews{
        @BindView(R.id.spinnerImageView) ImageView spinnerImageView;
        @BindView(R.id.message) TextView message;
        }

        public DialogModule setLoading(){
        if (loading == null) {
        loading = new Dialog(context);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        viewProgress = View.inflate(context, R.layout.loading, null);
        loading.setCancelable(false);
        loading.setContentView(viewProgress);

        ButterKnife.bind(loadingViews, viewProgress);
        }

        setAnimation(12, 1000);

        loading.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
        loading = null;
        }
        });

        return this;
        }

        public void showLoading(){
        if (!loading.isShowing())
        loading.show();
        }

        public void dismissLoading(){
        if (loading != null && loading.isShowing())
        loading.dismiss();
        }

        private void setAnimation(final int frameCount, final int duration) {
        Animation a = AnimationUtils.loadAnimation(context, R.anim.loading_anim);
        a.setDuration(duration);
        a.setInterpolator(new Interpolator() {

        @Override
        public float getInterpolation(float input) {
        return (float)Math.floor(input*frameCount)/frameCount;
        }
        });
        loadingViews.spinnerImageView.startAnimation(a);
        }

        public void dialogPlaystore(String link){
        title(context.getResources().getString(R.string.informasi));
        image(R.drawable.v1_ic_toa);
        message(context.getResources().getString(R.string.errorIncompatibleVersion));
        setNegativeButton(context.getResources().getString(R.string.cancel), new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, @Nullable int id) {
        dialog.dismiss();
        }
        });

        setPositiveButton(context.getResources().getString(R.string.buttonPerbarui), new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, @Nullable int id) {
        dialog.dismiss();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        context.startActivity(browserIntent);
//                final String appPackageName = context.getPackageName();
//                try {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Environment.GOOGLE_MARKET + appPackageName)));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Environment.GOOGLE_PLAYSTORE + appPackageName)));
//                }
        }
        });

        }

        public DialogModule dialogInfo(String message){
        title(context.getResources().getString(R.string.informasi));
        image(R.drawable.v1_ic_toa);
        message(message);

        return this;
        }

        public DialogModule dialogInfo2(String message){
        title(context.getResources().getString(R.string.informasi));
        image(R.drawable.v1_ic_toa);
        message2(message);

        return this;
        }


        public DialogModule title(String message){
        this.dialogViews.txtTitle.setText(message);
        this.dialogViews.txtTitle.setVisibility(View.VISIBLE);
        return this;
        }

        public DialogModule message(String message){
        this.dialogViews.txtNote.setText(message);
        this.dialogViews.txtNote.setVisibility(View.VISIBLE);
        return this;
        }

        public DialogModule message2(String message){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.dialogViews.txtNote.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
        } else {
        this.dialogViews.txtNote.setText(Html.fromHtml(message));
        }
        this.dialogViews.txtNote.setVisibility(View.VISIBLE);
        return this;
        }

        public DialogModule message(String message, Boolean isHtml){
        if (isHtml) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        dialogViews.txtNote.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY));
        } else {
        dialogViews.txtNote.setText(Html.fromHtml(message));
        }
        } else
        dialogViews.txtNote.setText(message);
        this.dialogViews.txtNote.setVisibility(View.VISIBLE);
        return this;
        }

        public DialogModule message(CharSequence message){
        this.dialogViews.txtNote.setText(message);
        this.dialogViews.txtNote.setVisibility(View.VISIBLE);
        return this;
        }
        public DialogModule messageAlign(int align){
        this.dialogViews.txtNote.setGravity(align);
        return this;
        }

        public DialogModule image(@DrawableRes int drawable){
        dialogViews.imageView.setVisibility(View.VISIBLE);
        dialogViews.imageView.setImageResource(drawable);

        return this;
        }
        public DialogModule noImage(){
        dialogViews.imageView.setVisibility(View.GONE);
        return this;
        }
        public DialogModule image(Bitmap drawable){
        dialogViews.imageView.setVisibility(View.VISIBLE);
        dialogViews.imageView.setImageBitmap(drawable);

        return this;
        }
        public DialogModule image(Drawable drawable){
        dialogViews.imageView.setVisibility(View.VISIBLE);
        dialogViews.imageView.setImageDrawable(drawable);

        return this;
        }

        public DialogModule setPositiveButton(CharSequence text, final OnClickListener onClickListener){
        dialogViews.cardViewLeft.setVisibility(View.VISIBLE);
        dialogViews.txtValueLeft.setText(text);
        dialogViews.cardViewLeft.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        onClickListener.onClick(dialog, v.getId());
        dialog.dismiss();
        }
        });

        return this;
        }
        public DialogModule setNegativeButton(CharSequence text, final OnClickListener onClickListener){
        dialogViews.cardViewRight.setVisibility(View.VISIBLE);
        dialogViews.txtValueRight.setText(text);
        dialogViews.cardViewRight.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        onClickListener.onClick(dialog, v.getId());
        dialog.dismiss();
        }
        });

        return this;
        }

        public interface OnClickListener{
        void onClick(DialogInterface dialog, @Nullable int id);
        }

        }
