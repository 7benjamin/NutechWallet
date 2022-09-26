// Generated code from Butter Knife. Do not modify!
package com.example.nutechwallet.screen;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nutechwallet.R;

public class UpdateProfileActivity_ViewBinding implements Unbinder {
  private UpdateProfileActivity target;

  private View view7f09005e;

  @UiThread
  public UpdateProfileActivity_ViewBinding(UpdateProfileActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UpdateProfileActivity_ViewBinding(final UpdateProfileActivity target, View source) {
    this.target = target;

    View view;
    target.inputNamaDepan = Utils.findRequiredViewAsType(source, R.id.inputNamaDepan, "field 'inputNamaDepan'", EditText.class);
    target.inputNamaBelakang = Utils.findRequiredViewAsType(source, R.id.inputNamaBelakang, "field 'inputNamaBelakang'", EditText.class);
    view = Utils.findRequiredView(source, R.id.cardView, "method 'updateProfile'");
    view7f09005e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.updateProfile();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UpdateProfileActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.inputNamaDepan = null;
    target.inputNamaBelakang = null;

    view7f09005e.setOnClickListener(null);
    view7f09005e = null;
  }
}
