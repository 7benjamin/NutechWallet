// Generated code from Butter Knife. Do not modify!
package com.example.nutechwallet.screen;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nutechwallet.R;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view7f09005e;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    target.linearEmail = Utils.findRequiredViewAsType(source, R.id.linearEmail, "field 'linearEmail'", LinearLayout.class);
    target.inputEmail = Utils.findRequiredViewAsType(source, R.id.inputEmail, "field 'inputEmail'", EditText.class);
    target.inputPassword = Utils.findRequiredViewAsType(source, R.id.inputPassword, "field 'inputPassword'", EditText.class);
    target.inputNamaDepan = Utils.findRequiredViewAsType(source, R.id.inputNamaDepan, "field 'inputNamaDepan'", EditText.class);
    target.inputNamaBelakang = Utils.findRequiredViewAsType(source, R.id.inputNamaBelakang, "field 'inputNamaBelakang'", EditText.class);
    view = Utils.findRequiredView(source, R.id.cardView, "method 'regisUser'");
    view7f09005e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.regisUser();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linearEmail = null;
    target.inputEmail = null;
    target.inputPassword = null;
    target.inputNamaDepan = null;
    target.inputNamaBelakang = null;

    view7f09005e.setOnClickListener(null);
    view7f09005e = null;
  }
}
