// Generated code from Butter Knife. Do not modify!
package com.example.nutechwallet.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nutechwallet.R;

public class DialogModule$LoadingViews_ViewBinding implements Unbinder {
  private DialogModule.LoadingViews target;

  @UiThread
  public DialogModule$LoadingViews_ViewBinding(DialogModule.LoadingViews target, View source) {
    this.target = target;

    target.spinnerImageView = Utils.findRequiredViewAsType(source, R.id.spinnerImageView, "field 'spinnerImageView'", ImageView.class);
    target.message = Utils.findRequiredViewAsType(source, R.id.message, "field 'message'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DialogModule.LoadingViews target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spinnerImageView = null;
    target.message = null;
  }
}
