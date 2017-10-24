package com.dropkick.soma.somaproject.keyboard.units.korean;

import android.view.KeyEvent;

import com.dropkick.soma.somaproject.keyboard.BaseKeyboardView;
import com.dropkick.soma.somaproject.keyboard.LanguageUnit;

/**
 * Created by junhoe on 2017. 10. 21..
 */

public class KoreanUnit implements LanguageUnit {

	private DualKoreanAutomata automata;
	public KoreanUnit() {
		automata = new DualKoreanAutomata();
	}

    @Override
    public void onLetterKey(BaseKeyboardView parent, int primaryCode) {
		if (DualKoreanAutomata.isKoreanLetterCode(primaryCode)) {
			// Log.i(LOGTAG, DualKoreanAutomata.getKeyboardLetter(primaryCode)
			// +" was pressed.");
			automata.input(parent.getInputConnection(),
					DualKoreanAutomata.getKeyboardLetter(primaryCode));
		} else {
			// 엔터나 스페이스 처리
			automata.commitCurrentContent(parent.getInputConnection(),
					true);
			parent.sendKey(primaryCode);
		}
    }

	@Override
	public void onDeleteKey(BaseKeyboardView parent, int primaryCode) {
		if (automata.isInInitialState()) {
			parent.keyDownUp(KeyEvent.KEYCODE_DEL);
		} else {
			automata.inputDelete(parent.getInputConnection());
		}
	}
}
