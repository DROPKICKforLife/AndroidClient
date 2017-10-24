package com.dropkick.soma.somaproject.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;

import com.dropkick.soma.somaproject.keyboard.units.LatinUnit;
import com.dropkick.soma.somaproject.keyboard.units.korean.KoreanUnit;

/**
 * Created by junhoe on 2017. 10. 21..
 */

public class BaseKeyboard extends Keyboard {

    public enum KeyboardType {
        LATIN, KOREAN
    }
    private KeyboardType keyboardType;
    private LanguageUnit languageUnit;
    public BaseKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public BaseKeyboard(Context context, int xmlLayoutResId, KeyboardType type) {
        super(context, xmlLayoutResId);
        this.keyboardType = type;
        if (keyboardType == KeyboardType.LATIN) {
            languageUnit = new LatinUnit();
        } else if (keyboardType == KeyboardType.KOREAN) {
            languageUnit = new KoreanUnit();
        }
    }

    public KeyboardType getKeyboardType() {
        return keyboardType;
    }

    public LanguageUnit getLanguageUnit() {
        return languageUnit;
    }
}
