package com.example.a79875.todaynews.helper;

// ViewHolder 被选中 以及拖拽释放 触发监听器
public interface OnDragVHListener {
    // Item 被选中时触发
    void onItemSelected();

    // Item 在拖拽结束/滑动结束后触发
    void onItemFinish();
}
