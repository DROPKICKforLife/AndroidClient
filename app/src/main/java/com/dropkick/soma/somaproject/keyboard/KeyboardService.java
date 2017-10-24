package com.dropkick.soma.somaproject.keyboard;

import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.dropkick.soma.somaproject.R;

/**
 * Created by junhoe on 2017. 10. 21..
 */

public class KeyboardService extends InputMethodService {

    private static final String TAG = "KeyboardService";
    private InputMethodManager mInputMethodManager;

    private BaseKeyboardView mInputView;
    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        Log.i(TAG, "onCreate()");
    }

    @Override
    public View onCreateInputView() {
        mInputView = (BaseKeyboardView) getLayoutInflater().inflate(R.layout.keyboardview, null);
        Log.i(TAG, "onCreateInputView");
        return mInputView;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        Log.i(TAG, "onStartInputView");
        mInputView.setInputConnection(getCurrentInputConnection());
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();
        Log.i(TAG, "onFinishInput");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }
}
