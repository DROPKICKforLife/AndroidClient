package com.dropkick.soma.somaproject.keyboard.units.korean;

import android.view.inputmethod.InputConnection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public abstract class KoreanAutomata<StateEnum extends Enum<StateEnum>> {

	// ======================================================================================
	/*
	 * ㅂㅈㄷㄱㅅ 0 1 2 3 4 ㅁㄴㅇㄹㅎ 5 6 7 8 9 ㅋㅌㅊㅍㅃ 10 11 12 13 14 ㅉㄸㄲㅆㅛ 15 16 17 18 19
	 * ㅕㅑㅐㅔㅗ 20 21 22 23 24 ㅓㅏㅣㅠㅜ 25 26 27 28 29 ㅡㅒㅖ 30 31 32
	 */

	public static final List<Character> koreanInitialUnits = Collections
			.unmodifiableList(Arrays.asList(new Character[] { 'ㄱ', 'ㄲ', 'ㄴ',
					'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
					'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' }));

	public static final List<Character> koreanMedialUnits = Collections
			.unmodifiableList(Arrays.asList(new Character[] { 'ㅏ', 'ㅐ', 'ㅑ',
					'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ',
					'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ' }));

	public static final List<Character> koreanFinalUnits = Collections
			.unmodifiableList(Arrays.asList(new Character[] { ' ', 'ㄱ', 'ㄲ',
					'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ',
					'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ',
					'ㅎ' }));

	public final static boolean isInitial(char unit) {
		for (Character c : koreanInitialUnits) {
			if (c.charValue() == unit)
				return true;
		}
		return false;
	}

	public final static boolean isMedial(char unit) {
		for (Character c : koreanMedialUnits) {
			if (c.charValue() == unit)
				return true;
		}
		return false;
	}

	public final static boolean isFinal(char unit) {
		for (Character c : koreanFinalUnits) {
			if (c.charValue() == unit)
				return true;
		}
		return false;
	}

	enum LetterType {
		INITIAL, MEDIAL, FINAL;
	}

	/** 한글 유니코드 초중종성 조합 함수에서만 사용한다. */
	protected final static int getNumber(char character, LetterType type) {
		if (type == LetterType.MEDIAL) // 모음일 때
		{
			return character - 12623;
		} else if (type == LetterType.INITIAL)// 초성일 때
		{
			switch (character) {
			case 'ㄱ':
				return 0;
			case 'ㄲ':
				return 1;
			case 'ㄴ':
				return 2;
			case 'ㄷ':
				return 3;
			case 'ㄸ':
				return 4;

			case 'ㄹ':
				return 5;
			case 'ㅁ':
				return 6;
			case 'ㅂ':
				return 7;
			case 'ㅃ':
				return 8;
			case 'ㅅ':
				return 9;

			case 'ㅆ':
				return 10;
			case 'ㅇ':
				return 11;
			case 'ㅈ':
				return 12;
			case 'ㅉ':
				return 13;
			case 'ㅊ':
				return 14;

			case 'ㅋ':
				return 15;
			case 'ㅌ':
				return 16;
			case 'ㅍ':
				return 17;
			case 'ㅎ':
				return 18;
			}
		} else if (type == LetterType.FINAL) {
			switch (character) {
			case 'ㄱ':
				return 1;
			case 'ㄲ':
				return 2;
			case 'ㄳ':
				return 3;
			case 'ㄴ':
				return 4;
			case 'ㄵ':
				return 5;

			case 'ㄶ':
				return 6;
			case 'ㄷ':
				return 7;
			case 'ㄹ':
				return 8;
			case 'ㄺ':
				return 9;
			case 'ㄻ':
				return 10;

			case 'ㄼ':
				return 11;
			case 'ㄽ':
				return 12;
			case 'ㄾ':
				return 13;
			case 'ㄿ':
				return 14;
			case 'ㅀ':
				return 15;

			case 'ㅁ':
				return 16;
			case 'ㅂ':
				return 17;
			case 'ㅄ':
				return 18;
			case 'ㅅ':
				return 19;
			case 'ㅆ':
				return 20;

			case 'ㅇ':
				return 21;
			case 'ㅈ':
				return 22;
			case 'ㅊ':
				return 23;
			case 'ㅋ':
				return 24;
			case 'ㅌ':
				return 25;

			case 'ㅍ':
				return 26;
			case 'ㅎ':
				return 27;
			}
		}

		return 0;
	}

	/** 초중종성을 합쳐 새로운 글자를 만든다. **/
	public static char assembleLetter(char initial, char medial,
			char finalElement) {
		int unicode = getNumber(initial, LetterType.INITIAL) * 588
				+ getNumber(medial, LetterType.MEDIAL) * 28
				+ getNumber(finalElement, LetterType.FINAL) + 44032;

		return (char) unicode;
	}

	public static boolean isCompleteKorean(char ch) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);

		if (block == Character.UnicodeBlock.HANGUL_SYLLABLES) {
			return true;
		} else
			return false;
	}

	public static boolean getSeperatedLetters(char letter, char[] out) {
		char temp = letter;

		if (isCompleteKorean(temp)) {

			temp -= 0xAC00;
			int finalValue = temp % 28;

			char finalLetter = koreanFinalUnits.get(finalValue);

			int medialValue = ((temp - finalValue) / 28) % 21;
			char medialLetter = koreanMedialUnits.get(medialValue);

			int initialValue = ((temp - finalValue) / 28) / 21;
			char initialLetter = koreanInitialUnits.get(initialValue);

			if (finalValue != 0) {
				out[0] = initialLetter;
				out[1] = medialLetter;
				out[2] = finalLetter;

			} else {
				out[0] = initialLetter;
				out[1] = medialLetter;
				out[2] = 0;
			}

			return true;
		} else
			return false;
	}

	// ===================================================================================

	// 현재 들어온 키들 : 최대값 : 초성1 + 중성 3 + 종성 2 => 6

	private StateEnum stateForInitial;

	private StateEnum currentState;
	private Stack<StateEnum> stateStack;

	public KoreanAutomata(StateEnum stateForInitial) {
		this.stateStack = new Stack<StateEnum>();
		this.stateForInitial = stateForInitial;
	}

	public StateEnum getCurrentState() {
		return currentState;
	}

	protected void setCurrentState(StateEnum newState, boolean saveLast) {
		if (saveLast) {
			this.stateStack.push(currentState);
		}
		this.currentState = newState;
	}

	protected char rollBack() {
		if (this.stateStack.size() == 0) {
			this.currentState = stateForInitial;
			return '\0';
		} else {
			this.currentState = stateStack.pop();
			return removeLastInputLetter();
		}
	}

	/*
	 * protected void stackClear(){ this.stateStack.clear(); }
	 * 
	 * protected StateEnum stackPop(){ return this.stateStack.pop(); }
	 */

	protected int stackSize() {
		return this.stateStack.size();
	}

	/** 강제로 현재 상태의 문자를 커밋 */
	protected void forceCommit(InputConnection inputConnection, char letter) {
		String string = String.valueOf(letter);
		if (letter == 0)
			string = "";
		inputConnection.commitText(string, 1);
	}

	/** 문자 삭제 명령 */
	public void inputDelete(InputConnection inputConnection) {
		if (rollBack() != '\0')
			this.replaceCurrentCursorLetterTo(inputConnection,
					assembleCurrentLetters());
	}

	public boolean isInInitialState() {
		return this.currentState == this.stateForInitial;
	}

	public void initialize() {
		this.onInitialized();
		this.stateStack.clear();
	}

	protected void replaceCurrentCursorLetterTo(
			InputConnection inputConnection, char newLetter) {
		String composingText = String.valueOf(newLetter);

		if (newLetter == 0)
			composingText = "";

		inputConnection.setComposingText(composingText, 1);

	}

	protected abstract char assembleCurrentLetters();

	protected abstract void onInitialized();

	/**
	 * 오토마타에 새로운 한글 자소를 입력. 성공적으로 오토마타에 들어갔으면 true, 실패하면 false. 한글이 아닌 일반문자로
	 * 취급해서 처리.
	 */
	public abstract void input(InputConnection inputConnection, char inputLetter);

	protected void commitCurrentContent(InputConnection inputConnection,
			boolean initialize) {
		this.forceCommit(inputConnection, this.assembleCurrentLetters());
		if (initialize) {
			initialize();
		}

		inputConnection.finishComposingText();
	}

	protected abstract char removeLastInputLetter();
}
