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

public class TopupActivity_ViewBinding implements Unbinder {
  private TopupActivity target;

  private View view7f09005e;

  @UiThread
  public TopupActivity_ViewBinding(TopupActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TopupActivity_ViewBinding(final TopupActivity target, View source) {
    this.target = target;

    View view;
    target.inputTopup = Utils.findRequiredViewAsType(source, R.id.inputTopup, "field 'inputTopup'", EditText.class);
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
    TopupActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.inputTopup = null;

    view7f09005e.setOnClickListener(null);
    view7f09005e = null;
  }
}
