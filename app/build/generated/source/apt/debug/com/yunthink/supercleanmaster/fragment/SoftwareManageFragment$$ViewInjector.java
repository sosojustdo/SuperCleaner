// Generated code from Butter Knife. Do not modify!
package com.yunthink.supercleanmaster.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SoftwareManageFragment$$ViewInjector {
  public static void inject(Finder finder, final com.yunthink.supercleanmaster.fragment.SoftwareManageFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624122, "field 'listview'");
    target.listview = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131624196, "field 'topText'");
    target.topText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624125, "field 'mProgressBar'");
    target.mProgressBar = view;
    view = finder.findRequiredView(source, 2131624127, "field 'mProgressBarText'");
    target.mProgressBarText = (android.widget.TextView) view;
  }

  public static void reset(com.yunthink.supercleanmaster.fragment.SoftwareManageFragment target) {
    target.listview = null;
    target.topText = null;
    target.mProgressBar = null;
    target.mProgressBarText = null;
  }
}
