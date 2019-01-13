package com.example.a79875.todaynews.widegt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.adapter.CommentRecyclerViewAdapter;
import com.example.a79875.todaynews.enity.CommentBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 你是我的 on 2019/1/7.
 */

public class SmallVideoCommentContentBottomDialog extends DialogFragment
        implements View.OnClickListener {
    private BottomSheetBehavior<FrameLayout> behavior;
    RecyclerView recyclerView;
    RelativeLayout layoutCommentInput;
    ImageView ivExpressionEmojiInput;
    ImageView ivExitCommentContentDialog;
    List<CommentBean> commentList = new ArrayList<>();
    CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    LinearLayoutManager manager;
    CommentDialog commentDialog;
    // 顶部向下偏移量
    private int topOffest = 610;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getContext() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext(), R.style.TransparentBottomSheetStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.small_video_comment_content_bottom_dialog, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        setList();// 设置评论假数据

        recyclerView = view.findViewById(R.id.recycler_view_small_video_comment_content);
        layoutCommentInput = view.findViewById(R.id.layout_small_video_comment_input);
        ivExitCommentContentDialog = view.findViewById(R.id.iv_exit_comment_content);
        ivExpressionEmojiInput = view.findViewById(R.id.iv_expresstion_emoji_input);

        commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(commentList, getActivity());
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(commentRecyclerViewAdapter);

        commentDialog = new CommentDialog();

        if (getTag().equals("DialogAndInput")){// 判断是否是要同时打开该dialog和软键盘
            commentDialog.show(getChildFragmentManager(), "smallVideosoftInput");
        }

        commentDialog.setSendListener(new CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                List<CommentBean> list = new ArrayList<>();
                list.addAll(commentList);
                commentList.clear();
                CommentBean commentBean = new CommentBean();
                commentBean.setUserName("你是我的");
                commentBean.setUserComment(inputText);
                commentList.add(commentBean);
                commentList.addAll(list);

                commentRecyclerViewAdapter.notifyDataSetChanged();

                recyclerView.scrollToPosition(0);

                commentDialog.dismiss();
            }
        });

        ivExitCommentContentDialog.setOnClickListener(this);
        ivExpressionEmojiInput.setOnClickListener(this);
        layoutCommentInput.setOnClickListener(this);
    }

    public List<CommentBean> setList() {
        commentList.clear();
        for (int i = 0; i < 20; i++) {
            CommentBean commentBean = new CommentBean();
            commentBean.setUserName(i + "号选手");
            commentBean.setUserComment((1 + i) + "楼说的真好");
            commentList.add(commentBean);
        }
        return commentList;
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
        switch (v.getId()){
            case R.id.iv_exit_comment_content:
                dismiss();
                getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.layout_small_video_comment_input:
                commentDialog.show(getChildFragmentManager(), "smallVideosoftInput");
                break;
            case R.id.iv_expresstion_emoji_input:
                commentDialog.show(getChildFragmentManager(), "smallVideoemojiInput");
                break;
        }
    }
}
