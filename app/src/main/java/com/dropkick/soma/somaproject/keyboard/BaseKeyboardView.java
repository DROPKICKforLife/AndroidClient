package com.dropkick.soma.somaproject.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

import com.dropkick.soma.somaproject.R;
import com.dropkick.soma.somaproject.keyboard.units.korean.KoreanUnit;
import com.dropkick.soma.somaproject.keyboard.units.LatinUnit;
import com.dropkick.soma.somaproject.util.SwapPair;

/**
 * Created by junhoe on 2017. 10. 21..
 */

public class BaseKeyboardView extends KeyboardView
        implements KeyboardView.OnKeyboardActionListener {

    private static final String TAG = "BaseKeyboardView";
    private InputConnection inputConnection;
    private SwapPair<BaseKeyboard> keyboardPair;

    public BaseKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnKeyboardActionListener(this);
        keyboardPair = SwapPair.of(new BaseKeyboard(context, R.xml.qwerty, BaseKeyboard.KeyboardType.LATIN),
                new BaseKeyboard(context, R.xml.korean, BaseKeyboard.KeyboardType.KOREAN));
        setKeyboard(keyboardPair.get());
    }

    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
    }

    public InputConnection getInputConnection() {
        return inputConnection;
    }

    @Override
    public void onPress(int i) {
    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int keyCode, int[] keyCodes) {
        switch (keyCode) {
            case Keyboard.KEYCODE_DELETE:
                keyboardPair.get().getLanguageUnit().onDeleteKey(this, keyCode);
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                setKeyboard(keyboardPair.swapAndGet());
                break;
            default:
                keyboardPair.get().getLanguageUnit().onLetterKey(this, keyCode);
                break;
        }
    }

    public void keyDownUp(int keyCode) {
        inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
        inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyCode));
    }

    /**
     * Helper to send a character to the editor as raw key events.
     */
    public void sendKey(int keyCode) {
        switch (keyCode) {
            case '\n':
                keyDownUp(KeyEvent.KEYCODE_ENTER);
                break;
            default:
                if (keyCode >= '0' && keyCode <= '9') {
                    keyDownUp(keyCode - '0' + KeyEvent.KEYCODE_0);
                } else {
                    inputConnection.commitText(String.valueOf((char) keyCode), 1);
                }
                break;
        }
    }

    @Override
    public void onText(CharSequence charSequence) {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeUp() {
    }
}
