package com.dzg.takeaway.ui.view.dragflowlayout;

import android.view.View;

public interface IViewObserver {

    void onAddView(View child, int index);

    void onRemoveView(View child, int index);
}
