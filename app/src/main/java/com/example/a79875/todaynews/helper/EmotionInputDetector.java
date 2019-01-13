package com.example.a79875.todaynews.helper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by 你是我的 on 2018/12/30.
 */

// 表情输入监视器
public class EmotionInputDetector {
    private static final String SHARE_PREFERENCE_NAME = "com.mumu.easyemoji";
    private static final String SHARE_PREFERENCE_TAG = "soft_input_height";

    private Activity mActivity;
    private InputMethodManager mInputManager;
    private SharedPreferences sp;
    private View mEmotionLayout;
    private CheckBox mEmojiView;
    private EditText mEditText;
    private View mContentView;
    private View mSendButton;
    private String layoutType;

    private EmotionInputDetector() {
    }

    public static EmotionInputDetector with(Activity activity) {
        EmotionInputDetector emotionInputDetector = new EmotionInputDetector();
        emotionInputDetector.mActivity = activity;
        emotionInputDetector.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        emotionInputDetector.sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return emotionInputDetector;
    }

    public EmotionInputDetector bindToContent(View contentView) {
        mContentView = contentView;
        return this;
    }

    public EmotionInputDetector bindSendButton(View contentView) {
        mSendButton = contentView;
        return this;
    }

    public EmotionInputDetector bindParentLayout(String layoutType) {
        this.layoutType = layoutType;
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    public EmotionInputDetector bindToEditText(EditText editText) {
        mEditText = editText;
        editText.addTextChangedListener(new EditWatcher(mSendButton, editText));
        mEditText.requestFocus();
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mEmotionLayout.isShown()) {
                    lockContentHeight();
                    hideEmotionLayout(true);
                    mEmojiView.setChecked(false);
                    mEditText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unlockContentHeightDelayed();
                        }
                    }, 200L);
                }
                return false;
            }
        });
        return this;
    }

    public EmotionInputDetector bindToEmotionButton(final CheckBox emotionButton) {
        mEmojiView = emotionButton;
        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmotionLayout.isShown()) {
                    lockContentHeight();
                    hideEmotionLayout(true);
                    mEmojiView.setChecked(false);
                    unlockContentHeightDelayed();
                } else {
                    if (isSoftInputShown()) {
                        lockContentHeight();
                        showEmotionLayout();
                        mEmojiView.setChecked(true);
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();
                    }
                }
            }
        });
        return this;
    }

    // 用于直接底部表情按钮直接显示表情键盘
    public EmotionInputDetector bindToEmotionButton1(final CheckBox emotionButton) {
        mEmojiView = emotionButton;
        // checkbox 设置为true 为键盘图标
        mEmojiView.setChecked(true);

        // 先隐藏软键盘
        mContentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                lockContentHeight();
                showEmotionLayout();
                unlockContentHeightDelayed();
            }
        }, 200);

        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmotionLayout.isShown()) {
                    lockContentHeight();
                    hideEmotionLayout(true);
                    mEmojiView.setChecked(false);// expression
                    unlockContentHeightDelayed();
                } else {
                    if (isSoftInputShown()) {
                        lockContentHeight();
                        showEmotionLayout();
                        mEmojiView.setChecked(true);// keyboard
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();
                    }
                }
            }
        });
        return this;
    }

    public EmotionInputDetector bindToEmotionButton3(final CheckBox emotionButton) {
        mEmojiView = emotionButton;
        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmotionLayout.isShown()) {
                    hideEmotionLayout(true);
                    mEmojiView.setChecked(false);
                    unlockContentHeightDelayed();
                } else {
                    if (isSoftInputShown()) {
                        showEmotionLayout();
                        mEmojiView.setChecked(true);
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();
                    }
                }
            }
        });
        return this;
    }

    public EmotionInputDetector bindToEmotionButton4(final CheckBox emotionButton) {
        mEmojiView = emotionButton;
        // checkbox 设置为true 为键盘图标
        mEmojiView.setChecked(true);

        // 先隐藏软键盘
        mContentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*lockContentHeight();*/
                showEmotionLayout();
                unlockContentHeightDelayed();
            }
        }, 200);

        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmotionLayout.isShown()) {
                    /*lockContentHeight();*/
                    hideEmotionLayout(true);
                    mEmojiView.setChecked(false);// expression
                    unlockContentHeightDelayed();
                } else {
                    if (isSoftInputShown()) {
                        /*lockContentHeight();*/
                        showEmotionLayout();
                        mEmojiView.setChecked(true);// keyboard
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();
                    }
                }
            }
        });
        return this;
    }


    public EmotionInputDetector setEmotionView(View emotionView) {
        mEmotionLayout = emotionView;
        return this;
    }

    public EmotionInputDetector build() {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        hideSoftInput();
        return this;
    }

    public boolean interceptBackPress() {
        // TODO: 15/11/2 change this method's name
        mEmojiView.setChecked(!mEmojiView.isChecked());
        if (mEmotionLayout.isShown()) {
            hideEmotionLayout(false);
            return true;
        }
        return false;
    }

    private void showEmotionLayout() {
        int softInputHeight = getSupportSoftInputHeight();
        if (softInputHeight == 0) {
            softInputHeight = sp.getInt(SHARE_PREFERENCE_TAG, 200);
        }
        mEmotionLayout.getLayoutParams().height = softInputHeight;
        mEmotionLayout.setVisibility(View.VISIBLE);
        hideSoftInput();
    }

    private void hideEmotionLayout(boolean showSoftInput) {
        if (mEmotionLayout.isShown()) {
            mEmotionLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    private void lockContentHeight() {
        if (layoutType.equals("RelativeLayout")) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mContentView.getLayoutParams();
            params.height = mContentView.getHeight();
        } else {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mContentView.getLayoutParams();
            params.height = mContentView.getHeight();
            /* params.weight = 0.0F;*/
        }
    }

    private void unlockContentHeightDelayed() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*  ((LinearLayout.LayoutParams) mContentView.getLayoutParams()).weight = 1.0F;*/
            }
        }, 200L);
    }

    private void showSoftInput() {
        mEditText.requestFocus();
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        });
    }

    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - r.bottom;
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }
        if (softInputHeight < 0) {
            Log.w("EmotionInputDetector", "Warning: value of softInputHeight is below zero!");
        }
        if (softInputHeight > 0) {
            sp.edit().putInt(SHARE_PREFERENCE_TAG, softInputHeight).apply();
        }
        return softInputHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

}
