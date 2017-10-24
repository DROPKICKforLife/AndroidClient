package com.dropkick.soma.somaproject.keyboard;

/**
 * Created by junhoe on 2017. 10. 21..
 */

public interface LanguageUnit {
    void onLetterKey(BaseKeyboardView parent, int primaryCode);
    void onDeleteKey(BaseKeyboardView parent, int primaryCode);
}
