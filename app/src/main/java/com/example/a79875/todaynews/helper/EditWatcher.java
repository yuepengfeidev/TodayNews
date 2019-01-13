package com.example.a79875.todaynews.helper;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by 你是我的 on 2018/12/30.
 */
public class EditWatcher implements TextWatcher {
    private View view;
    private EditText editText;

    public EditWatcher(View view, EditText editText) {
        this.view = view;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
       /* if (count > 0 || s.length() >0){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }*/
        if (s.length() > 0) {
            view.setEnabled(true);
            ((TextView)view).setTextColor(Color.BLACK);
        } else {
            ((TextView)view).setEnabled(false);
            ((TextView)view).setTextColor(Color.parseColor("#fbdadada"));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
