package com.example.nutechwallet.utils;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;


import nutechwallet.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BehaviorButton {

    private Context context;

    public BehaviorButton(Context context){
        this.context = context;

        if (context instanceof Activity)
            this.context = (Activity) context;
    }

    public void disableButton(CardView cardView){

        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorLightWhite));
        cardView.setForeground(null);
        cardView.setEnabled(false);
    }
    public void enableButton(CardView cardView){
        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        cardView.setForeground(getSelectedItemDrawable());
        cardView.setEnabled(true);
    }
    public void enableButtonAccent(CardView cardView){
        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        cardView.setForeground(getSelectedItemDrawable());
        cardView.setEnabled(true);
    }
    private Drawable getSelectedItemDrawable() {
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        Drawable selectedItemDrawable = ta.getDrawable(0);
        ta.recycle();
        return selectedItemDrawable;
    }



    public void focusBehavior(final ImageView image, final View line,
                              final EditText editText, final TextInputLayout textInputLayout,
                              final LinearLayout linearLayout){
        if (image != null)
            image.setColorFilter(context.getResources().getColor(R.color.colorDarkGrey), PorterDuff.Mode.SRC_ATOP);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (image != null)
                        image.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

                    if (line != null)
                        line.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));

                    editText.setHintTextColor(context.getResources().getColor(R.color.colorAccent));

                    if (textInputLayout != null)
                        textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorAccent)));
                    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_custom_focused));
                    } else {
                        linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_custom_focused));
                    }

                } else {
                    if (image != null)
                        image.setColorFilter(context.getResources().getColor(R.color.colorDarkGrey), PorterDuff.Mode.SRC_ATOP);
                    if (line != null)
                        line.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGrey));
                    editText.setHintTextColor(context.getResources().getColor(R.color.colorDarkGrey));
                    if (textInputLayout != null)
                        textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorDarkGrey)));
                    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_custom_normal));
                    } else {
                        linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_custom_normal));
                    }
                }
            }
        });

    }
    public void focusBehavior(final ImageView image, final View line,
                              final AppCompatAutoCompleteTextView editText, final TextInputLayout textInputLayout,
                              final LinearLayout linearLayout){
        image.setColorFilter(context.getResources().getColor(R.color.colorDarkGrey), PorterDuff.Mode.SRC_ATOP);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Drawable[] compoundDrawables = editText.getCompoundDrawables();

                if (hasFocus){
                    compoundDrawables[2].mutate().setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    image.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    line.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                    editText.setHintTextColor(context.getResources().getColor(R.color.colorAccent));
                    textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorAccent)));
                    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_custom_focused));
                    } else {
                        linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_custom_focused));
                    }

                } else {
                    compoundDrawables[2].mutate().setColorFilter(context.getResources().getColor(R.color.colorDarkGrey), PorterDuff.Mode.SRC_ATOP);
                    image.setColorFilter(context.getResources().getColor(R.color.colorDarkGrey), PorterDuff.Mode.SRC_ATOP);
                    line.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGrey));
                    editText.setHintTextColor(context.getResources().getColor(R.color.colorDarkGrey));
                    textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorDarkGrey)));
                    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_custom_normal));
                    } else {
                        linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_custom_normal));
                    }
                }
            }
        });

    }


    public void disableEdit(final ImageView image, final View line,
                            final EditText editText, final TextInputLayout textInputLayout,
                            final LinearLayout linearLayout){
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        image.setColorFilter(context.getResources().getColor(R.color.colorDarkGrey), PorterDuff.Mode.SRC_ATOP);
        line.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGrey));
        editText.setHintTextColor(context.getResources().getColor(R.color.colorDarkGrey));
        editText.setTextColor(context.getResources().getColor(R.color.colorDarkGrey));
        textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorDarkGrey)));
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_custom_disable));
        } else {
            linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_custom_disable));
        }

    }


    public void enableEdit(final ImageView image, final View line,
                           final EditText editText, final TextInputLayout textInputLayout,
                           final LinearLayout linearLayout){
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        image.setColorFilter(context.getResources().getColor(R.color.colorDarkGrey), PorterDuff.Mode.SRC_ATOP);
        line.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGrey));
        editText.setHintTextColor(context.getResources().getColor(R.color.colorDarkGrey));
        editText.setTextColor(context.getResources().getColor(R.color.colorDarkGrey));
        textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorDarkGrey)));
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_custom_normal));
        } else {
            linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_custom_normal));
        }

    }

    public void setKeyFilter(EditText editText, String regex){
//        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        editText.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        boolean keepOriginal = true;
                        StringBuilder sb = new StringBuilder(end - start);
                        for (int i = start; i < end; i++) {
                            char c = src.charAt(i);
                            if (isCharAllowed(c))
                                sb.append(c);
                            else
                                keepOriginal = false;
                        }
                        if (keepOriginal)
                            return null;
                        else {
                            if (src instanceof Spanned) {
                                SpannableString sp = new SpannableString(sb);
                                TextUtils.copySpansFrom((Spanned) src, start, sb.length(), null, sp, 0);
                                return sp;
                            } else {
                                return sb;
                            }
                        }
                    }

                    private boolean isCharAllowed(char c) {
                        Pattern ps = Pattern.compile("^"+regex+"$");
                        Matcher ms = ps.matcher(String.valueOf(c));
                        return ms.matches();
                    }
                }

        });
    }


    public void selectDate(Context context, final EditText textView){
        if (context instanceof Activity)
            context = (Activity) context;

        final String tvStr = textView.getText().toString();
        textView.setInputType(InputType.TYPE_NULL);
        textView.setCursorVisible(false);
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        final Date dt= null;

        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yy, int mm, int dd) {
                mm = mm+1;
                String newDate = String.valueOf(dd);
                String newMonth = String.valueOf(mm);
                if (newDate.length() == 1)
                    newDate = "0"+String.valueOf(dd);
                if (newMonth.length() == 1)
                    newMonth = "0"+String.valueOf(mm);
                textView.setText(newDate + "/" + newMonth + "/" + yy);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(yy, mm, dd);
            }
        }, yy, mm, dd){{
            if (textView.getHint().toString().contains("Lahir")){calendar.set(yy - 17, mm, dd);getDatePicker().setMaxDate(calendar.getTimeInMillis());} else getDatePicker().setMaxDate(new Date().getTime());}
        }.show();
    }


//    public void customCheckBox(ImageView imageView, Boolean isCheck){
//        if (imageView != null) {
//            if (isCheck == null) {
//                imageView.setImageResource(R.drawable.v1_ic_baseline_clear_24dp);
//                imageView.setColorFilter(context.getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_ATOP);
//            } else {
//                imageView.setImageResource(R.drawable.v1_ic_check_black_24dp);
//                if (isCheck)
//                    imageView.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//                else {
//                    try {
//                        ColorDrawable drawable = (ColorDrawable) imageView.getBackground();
//                        imageView.setColorFilter(drawable.getColor(), PorterDuff.Mode.SRC_ATOP);
//                    } catch (ClassCastException e) {
//                        imageView.setColorFilter(context.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
//                    }
//                }
//            }
//        }
//    }
}
