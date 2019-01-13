package com.example.a79875.todaynews.widegt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.ListDataSaveWithSharedPreferences;
import com.example.a79875.todaynews.adapter.ChannelAdapter;
import com.example.a79875.todaynews.helper.ItemDragHelperCallback;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class BottomMoreDialog extends BottomSheetDialogFragment implements View.OnClickListener, ChannelAdapter.UpdateData {
    List<String> selectedChannelList;
    List<String> unSelectecChannelList;
    ImageView ivCancel;
    RecyclerView recyclerView;
    ChannelAdapter adapter;
    UpdateTablayout updateTablayout;

    // 顶部向下偏移量
    private int topOffest = 0;
    private BottomSheetBehavior<FrameLayout> behavior;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext(), R.style.TransparentBottomSheetStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_bottom_dialog, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        ivCancel = view.findViewById(R.id.iv_cancel);
        recyclerView = view.findViewById(R.id.recycler_view);

        selectedChannelList = ListDataSaveWithSharedPreferences.getList(getContext(), "selectedChannel");
        unSelectecChannelList = ListDataSaveWithSharedPreferences.getList(getContext(), "unSelectedChannel");

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        adapter = new ChannelAdapter(getActivity()
                , helper, selectedChannelList, unSelectecChannelList);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                int viewType = adapter.getItemViewType(i);
                return viewType == ChannelAdapter.TYPE_MY ||
                        viewType == ChannelAdapter.TYPE_OTHER ?
                        1 : 4;
            }
        });
        recyclerView.setAdapter(adapter);

        // 我的频道 item 点击事件
        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });

        ivCancel.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置软键盘不自动弹出
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getHeight();
            behavior = BottomSheetBehavior.from(bottomSheet);
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setSkipCollapsed(true);
        }
    }

    private int getHeight() {
        int height = 1920;
        if (getContext() != null) {
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            if (windowManager != null) {
                // 使用point已经减去了状态栏的距离
                windowManager.getDefaultDisplay().getSize(point);
                height = point.y - getTopOffest();
            }
        }
        return height;
    }

    private int getTopOffest() {
        return topOffest;
    }

    public void setTopOffest(int topOffest) {
        this.topOffest = topOffest;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
                getDialog().dismiss();
                getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
        }
    }

    @Override
    public void onDestroy() {
        adapter.setUpdateData(this);// 销毁fragment后更新 频道编辑数据
        super.onDestroy();

    }

    @Override
    public void update(List<String> myChannelList, List<String> otherChannelList) {
        // 存入xml文件
        ListDataSaveWithSharedPreferences.saveList(getActivity(), "selectedChannel", myChannelList);
        ListDataSaveWithSharedPreferences.saveList(getActivity(), "unSelectedChannel", otherChannelList);

        // 更新 tablayout
        updateTablayout.updateTablayout(myChannelList,otherChannelList);
    }

    public interface UpdateTablayout{
        void updateTablayout(List<String> myChannelList, List<String> otherChannelList);
    }

    public void setUpdateTablayout(UpdateTablayout updateTablayout) {
        this.updateTablayout = updateTablayout;
    }
}
