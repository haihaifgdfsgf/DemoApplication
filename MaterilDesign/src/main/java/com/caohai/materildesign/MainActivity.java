package com.caohai.materildesign;

import android.media.MediaCodec;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout mTextInputLayout;
    private EditText mEditText;
    private String RULE = "^[\\u2E80-\\uFE4F\\\\w.+()（）\\\\-\\\\—/&:：#&*《》、，,.。~·•“”\\\"\\\"【】\\\\[\\\\]]+$";
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextInputLayout = (TextInputLayout) findViewById(R.id.text);
        mEditText = mTextInputLayout.getEditText();
        mButton = (Button) findViewById(R.id.button);
        //设置对EditText输入的监听事件
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //这儿判断操作，如果输入错误可以给用户提示
                if (s.length() < 5) {
                    mTextInputLayout.setErrorEnabled(true);
                    mTextInputLayout.setError("用户名不能小于6位");
                } else {
                    mTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is = isString(mEditText.getText().toString());
                Log.i("caohaimm", "mEditText.getText().toString():" + mEditText.getText().toString());
                Log.i("caohaimm", "is:" + is);
                Toast.makeText(MainActivity.this, "is:" + is, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isString(String mobiles) {
        Pattern p = Pattern
                .compile(RULE);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
