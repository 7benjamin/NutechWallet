// Generated code from Butter Knife. Do not modify!
package com.example.nutechwallet.screen;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.IllegalStateException;
import java.lang.Override;
import nutechwallet.R;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f09005e;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.linearEmail = Utils.findRequiredViewAsType(source, R.id.linearEmail, "field 'linearEmail'", LinearLayout.class);
    target.linearPassword = Utils.findRequiredViewAsType(source, R.id.linearPassword, "field 'linearPassword'", LinearLayout.class);
    target.inputEmail = Utils.findRequiredViewAsType(source, R.id.inputEmail, "field 'inputEmail'", EditText.class);
    target.inputPassword = Utils.findRequiredViewAsType(source, R.id.inputPassword, "field 'inputPassword'", EditText.class);
    target.inputLayoutError = Utils.findRequiredViewAsType(source, R.id.inputLayoutError, "field 'inputLayoutError'", TextInputLayout.class);
    target.inputLayoutError2 = Utils.findRequiredViewAsType(source, R.id.inputLayoutError2, "field 'inputLayoutError2'", TextInputLayout.class);
    target.txtValue = Utils.findRequiredViewAsType(source, R.id.txtValue, "field 'txtValue'", TextView.class);
    target.textLink = Utils.findRequiredViewAsType(source, R.id.textLink, "field 'textLink'", TextView.class);
    view = Utils.findRequiredView(source, R.id.cardView, "field 'cardView' and method 'isValid'");
    target.cardView = Utils.castView(view, R.id.cardView, "field 'cardView'", CardView.class);
    view7f09005e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.isValid();
      }
    });
    target.parent = Utils.findRequiredViewAsType(source, R.id.parent, "field 'parent'", ConstraintLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linearEmail = null;
    target.linearPassword = null;
    target.inputEmail = null;
    target.inputPassword = null;
    target.inputLayoutError = null;
    target.inputLayoutError2 = null;
    target.txtValue = null;
    target.textLink = null;
    target.cardView = null;
    target.parent = null;

    view7f09005e.setOnClickListener(null);
    view7f09005e = null;
  }
}
