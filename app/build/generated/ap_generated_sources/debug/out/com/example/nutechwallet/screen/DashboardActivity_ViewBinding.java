// Generated code from Butter Knife. Do not modify!
package com.example.nutechwallet.screen;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nutechwallet.R;

public class DashboardActivity_ViewBinding implements Unbinder {
  private DashboardActivity target;

  private View view7f0900b8;

  private View view7f0900b1;

  private View view7f0900b9;

  @UiThread
  public DashboardActivity_ViewBinding(DashboardActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DashboardActivity_ViewBinding(final DashboardActivity target, View source) {
    this.target = target;

    View view;
    target.saldoTxt = Utils.findRequiredViewAsType(source, R.id.saldoTxt, "field 'saldoTxt'", TextView.class);
    target.accountName = Utils.findRequiredViewAsType(source, R.id.accountName, "field 'accountName'", TextView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.linearNoData = Utils.findRequiredViewAsType(source, R.id.linearNoData, "field 'linearNoData'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.imgTopup, "method 'logout'");
    view7f0900b8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.logout();
      }
    });
    view = Utils.findRequiredView(source, R.id.imageAccount, "method 'profileActivity'");
    view7f0900b1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.profileActivity();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgTransfer, "method 'transferActivity'");
    view7f0900b9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.transferActivity();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DashboardActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.saldoTxt = null;
    target.accountName = null;
    target.recyclerView = null;
    target.linearNoData = null;

    view7f0900b8.setOnClickListener(null);
    view7f0900b8 = null;
    view7f0900b1.setOnClickListener(null);
    view7f0900b1 = null;
    view7f0900b9.setOnClickListener(null);
    view7f0900b9 = null;
  }
}
