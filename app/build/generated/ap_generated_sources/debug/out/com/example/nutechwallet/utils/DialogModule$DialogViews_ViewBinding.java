// Generated code from Butter Knife. Do not modify!
package com.example.nutechwallet.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nutechwallet.R;

public class DialogModule$DialogViews_ViewBinding implements Unbinder {
  private DialogModule.DialogViews target;

  @UiThread
  public DialogModule$DialogViews_ViewBinding(DialogModule.DialogViews target, View source) {
    this.target = target;

    target.imageView = Utils.findRequiredViewAsType(source, R.id.imageView, "field 'imageView'", ImageView.class);
    target.txtTitle = Utils.findRequiredViewAsType(source, R.id.txtTitle, "field 'txtTitle'", TextView.class);
    target.txtNote = Utils.findRequiredViewAsType(source, R.id.txtNote, "field 'txtNote'", TextView.class);
    target.txtValueRight = Utils.findRequiredViewAsType(source, R.id.txtValueRight, "field 'txtValueRight'", TextView.class);
    target.txtValueLeft = Utils.findRequiredViewAsType(source, R.id.txtValueLeft, "field 'txtValueLeft'", TextView.class);
    target.cardViewLeft = Utils.findRequiredViewAsType(source, R.id.cardViewLeft, "field 'cardViewLeft'", CardView.class);
    target.cardViewRight = Utils.findRequiredViewAsType(source, R.id.cardViewRight, "field 'cardViewRight'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DialogModule.DialogViews target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView = null;
    target.txtTitle = null;
    target.txtNote = null;
    target.txtValueRight = null;
    target.txtValueLeft = null;
    target.cardViewLeft = null;
    target.cardViewRight = null;
  }
}
