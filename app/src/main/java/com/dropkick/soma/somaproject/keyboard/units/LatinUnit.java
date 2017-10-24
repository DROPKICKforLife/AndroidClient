package com.dropkick.soma.somaproject.keyboard.units;

import com.dropkick.soma.somaproject.keyboard.BaseKeyboardView;
import com.dropkick.soma.somaproject.keyboard.LanguageUnit;

/**
 * Created by junhoe on 2017. 10. 21..
 */

public class LatinUnit implements LanguageUnit {

    public LatinUnit() {

    }

    @Override
    public void onLetterKey(BaseKeyboardView parent, int primaryCode) {
        int code = primaryCode;
        if (parent.getKeyboard().isShifted()){
            code = Character.toUpperCase((char) code);
        }
        parent.sendKey(code);
    }

    @Override
    public void onDeleteKey(BaseKeyboardView parent, int primaryCode) {
        parent.keyDownUp(primaryCode);
    }
}
