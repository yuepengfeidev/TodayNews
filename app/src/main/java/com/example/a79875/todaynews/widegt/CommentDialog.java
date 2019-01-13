package com.example.a79875.todaynews.widegt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.KeyBordUtil;
import com.example.a79875.todaynews.adapter.EmojiViewPagerAdapter;
import com.example.a79875.todaynews.helper.EmojiHelper;
import com.example.a79875.todaynews.helper.EmotionInputDetector;

import java.util.Objects;

/**
 * Created by 你是我的 on 2018/12/30.
 */
public class CommentDialog extends DialogFragment implements View.OnClickListener {
    //点击发表，内容不为空时的回调
    public SendListener sendListener;

    Dialog dialog;
    EditText editText;
    TextView tvIssueSend;
    CheckBox checkBox;
    ImageView ivAlbum;
    ImageView ivAite;
    CheckBox expressionInputIcon;
    CircleIndicator circleIndicator;
    ViewPager viewPager;
    LinearLayout llEmojiInput;
    private EmotionInputDetector detector;
    NestedScrollView nestedScrollView;
   View contentView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.Comment_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置Content前设定
        contentView = View.inflate(getActivity(), R.layout.comment_dialog, null);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(true);// 外部点击取消

        String dialogType = this.getTag();

        // 设置宽度为屏宽，靠近屏幕底部
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;// 紧贴底部
        layoutParams.alpha = 1;
        layoutParams.dimAmount = 0.1f;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;// 宽度持平
        window.setAttributes(layoutParams);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        editText = contentView.findViewById(R.id.edit_text);
        expressionInputIcon = contentView.findViewById(R.id.expression_input_icon);
        tvIssueSend = contentView.findViewById(R.id.tv_issue);
        circleIndicator = contentView.findViewById(R.id.circle_indicator_2);
        viewPager = contentView.findViewById(R.id.view_pager_2);
        llEmojiInput = contentView.findViewById(R.id.ll_emoji_input_2);
        assert dialogType != null;
        if (dialogType.contains("video")){
            nestedScrollView = getActivity().findViewById(R.id.nested_scroll_view_video);
        }else {
            nestedScrollView = getActivity().findViewById(R.id.nested_scroll_view);
        }

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        final Handler handler = new Handler();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        KeyBordUtil.hideInput(getActivity(),contentView);
                    }
                },200);
            }
        });


        // 判断是否是 smallvideo活动中弹出的评价弹窗，这下面写的的代码是真的烂！！！！
        if (dialogType.contains("small")){
            RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view_small_video_content);
            if (dialogType.contains("softInput")) {// 直接显示软键盘
                detector = EmotionInputDetector.with(getActivity())
                        .bindSendButton(tvIssueSend)
                        .bindToEditText(editText)
                        .setEmotionView(llEmojiInput)
                        .bindToContent(recyclerView)
                        .bindToEmotionButton3(expressionInputIcon);
            }else if (dialogType.contains("emojiInput")){// 直接显示自定义表情软件盘
                detector = EmotionInputDetector.with(getActivity())
                        .bindSendButton(tvIssueSend)
                        .bindToEditText(editText)
                        .setEmotionView(llEmojiInput)
                        .bindToContent(recyclerView)
                        .bindToEmotionButton4(expressionInputIcon)
                ;
            }
        }else if (dialogType.contains("softInput")) {
            detector = EmotionInputDetector.with(getActivity())
                    .bindSendButton(tvIssueSend)
                    .bindToEditText(editText)
                    .setEmotionView(llEmojiInput)
                    .bindToContent(nestedScrollView)
                    .bindToEmotionButton(expressionInputIcon);
        }else if (dialogType.contains("emojiInput")){
            detector = EmotionInputDetector.with(getActivity())
                    .bindSendButton(tvIssueSend)
                    .bindToEditText(editText)
                    .setEmotionView(llEmojiInput)
                    .bindToContent(nestedScrollView)
                    .bindToEmotionButton1(expressionInputIcon)
                    ;
        }

        // 绑定判断父布局
        if (dialogType.contains("video")){
            detector.bindParentLayout("RelativeLayout");
        }else {
            detector.bindParentLayout("CoordinatorLayout");
        }

        EmojiHelper emojiHelper = new EmojiHelper(1,getActivity(),editText);
        EmojiViewPagerAdapter adapter = new EmojiViewPagerAdapter(emojiHelper.getPagers());
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);

        tvIssueSend.setOnClickListener(this);

      /*  setSoftKeyBordListener();*/ // 设置监听软件盘是否关闭
        return dialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_issue:
                sendListener.sendComment(editText.getText().toString());
                break;
        }
    }



    public interface SendListener {
        void sendComment(String inputText);
    }

    public void setSendListener(SendListener sendListener) {
        this.sendListener = sendListener;
    }


    // 监听软件盘是否关闭的接口 ： 没有用到
    private void setSoftKeyBordListener(){
        KeyBordUtil.SoftKeyBoardListener softKeyBoardListener = new KeyBordUtil.SoftKeyBoardListener(getActivity());
        softKeyBoardListener.setListener(new KeyBordUtil.SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                dismiss();
            }
        });
    }

    // 重写 dismiss 设置延迟，否则会又残留dialog不能及时关闭
    @Override
    public void dismiss() {
        new Thread(){
            @Override
            public void run() {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
            }
        };
        editText.setText("");
    }
}
