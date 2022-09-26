// Generated code from Butter Knife. Do not modify!
package com.example.nutechwallet.screen;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nutechwallet.R;

public class ProfileActivity_ViewBinding implements Unbinder {
  private ProfileActivity target;

  private View view7f09005e;

  private View view7f0900e4;

  @UiThread
  public ProfileActivity_ViewBinding(ProfileActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfileActivity_ViewBinding(final ProfileActivity target, View source) {
    this.target = target;

    View view;
    target.userEmail = Utils.findRequiredViewAsType(source, R.id.userEmail, "field 'userEmail'", TextView.class);
    target.userFirstName = Utils.findRequiredViewAsType(source, R.id.userFirstName, "field 'userFirstName'", TextView.class);
    target.userLastName = Utils.findRequiredViewAsType(source, R.id.userLastName, "field 'userLastName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.cardView, "method 'logOut'");
    view7f09005e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.logOut();
      }
    });
    view = Utils.findRequiredView(source, R.id.linearUpdate, "method 'updateProfile'");
    view7f0900e4 = view;
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
    ProfileActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userEmail = null;
    target.userFirstName = null;
    target.userLastName = null;

    view7f09005e.setOnClickListener(null);
    view7f09005e = null;
    view7f0900e4.setOnClickListener(null);
    view7f0900e4 = null;
  }
}
