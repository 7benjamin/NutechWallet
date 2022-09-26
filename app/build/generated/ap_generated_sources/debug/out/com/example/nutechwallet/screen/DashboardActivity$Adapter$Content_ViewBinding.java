// Generated code from Butter Knife. Do not modify!
package com.example.nutechwallet.screen;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nutechwallet.R;

public class DashboardActivity$Adapter$Content_ViewBinding implements Unbinder {
  private DashboardActivity.Adapter.Content target;

  @UiThread
  public DashboardActivity$Adapter$Content_ViewBinding(DashboardActivity.Adapter.Content target,
      View source) {
    this.target = target;

    target.txtId = Utils.findRequiredViewAsType(source, R.id.txtId, "field 'txtId'", TextView.class);
    target.txtTime = Utils.findRequiredViewAsType(source, R.id.txtTime, "field 'txtTime'", TextView.class);
    target.txtType = Utils.findRequiredViewAsType(source, R.id.txtType, "field 'txtType'", TextView.class);
    target.txtAmount = Utils.findRequiredViewAsType(source, R.id.txtAmount, "field 'txtAmount'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DashboardActivity.Adapter.Content target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtId = null;
    target.txtTime = null;
    target.txtType = null;
    target.txtAmount = null;
  }
}
