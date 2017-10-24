package com.dropkick.soma.somaproject.keyboard.units.korean;

import android.view.inputmethod.InputConnection;

public class DualKoreanAutomata extends KoreanAutomata<DualKoreanState> {

	private static final char keyCodeTable[] = {'ㅂ','ㅈ','ㄷ','ㄱ','ㅅ',
		'ㅁ','ㄴ','ㅇ','ㄹ','ㅎ',
		'ㅋ','ㅌ','ㅊ','ㅍ','ㅃ',
		'ㅉ','ㄸ','ㄲ','ㅆ','ㅛ',
		'ㅕ','ㅑ','ㅐ','ㅔ','ㅗ',
		'ㅓ','ㅏ','ㅣ','ㅠ','ㅜ',
		'ㅡ','ㅒ','ㅖ'};

	public final static boolean isKoreanLetterCode(int keyCode){
		return (keyCode >= 200 && keyCode < 200+keyCodeTable.length);
	}

	public final static char getKeyboardLetter(int keyCode){
		return keyCodeTable[keyCode-200];
	}


	/**두 개의 모음을 합쳐 새로운 모음으로 만든다. 일반적으로 a가 왼쪽, b가 오른쪽**/
	public static char synthesizeVowel(char a, char b)
	{
		switch(a)
		{
		case 'ㅗ':
			switch(b)
			{
			case 'ㅏ': return 'ㅘ';
			case 'ㅐ': return 'ㅙ';
			case 'ㅣ': return 'ㅚ';
			}
			break;
		case 'ㅜ':
			switch(b)
			{
			case 'ㅓ': return 'ㅝ';
			case 'ㅔ': return 'ㅞ';
			case 'ㅣ': return 'ㅟ';
			}
			break;
		case 'ㅡ':
			switch(b)
			{
			case 'ㅣ': return 'ㅢ';
			}
			break;
		}

		return 0;
	}
	
	/**2벌식 자판에 맞게 자소를 분리합니다. 쌍자음, 쌍모음 때문에 최소 5칸의 char[]가 입력으로 들어와야 합니다.*/
	public static boolean getSeparatedLetters2Beolsik(char letter, char[] out)
	{
		boolean result = KoreanAutomata.getSeperatedLetters(letter, out);
		
		if(result==false) return false;
		else{
			
			int consonantIndex = 2;
			
			if(isSeparableVowel(out[1])==true)
			{
				out[3] = out[consonantIndex];
				consonantIndex ++;
				getSeparatedVowels(out[1], out, 1);
			}
			
			if(isSeparableConsonant(out[consonantIndex]))
			{
				getSeparatedConsonants(out[consonantIndex], out, consonantIndex);
			}
			
			return true;
		}
	}
	
	public static boolean isSeparableVowel(char vowel)
	{
		switch (vowel) {
		case 'ㅘ':			
		case 'ㅙ':
		case 'ㅚ':
		case 'ㅝ':
		case 'ㅞ':
		case 'ㅟ':
		case 'ㅢ':
			return true;
		default:
			return false;
		}
	}
	
	public static boolean getSeparatedVowels(char vowel, char[] out, int startOutIndex)
	{
		switch (vowel) {
		case 'ㅘ':
			out[startOutIndex] = 'ㅗ';
			out[startOutIndex + 1] = 'ㅏ';
			return true;
			
		case 'ㅙ':
			out[startOutIndex] = 'ㅗ';
			out[startOutIndex + 1] = 'ㅐ';
			return true;
			
		case 'ㅚ':
			out[startOutIndex] = 'ㅗ';
			out[startOutIndex + 1] = 'ㅣ';
			return true;

		case 'ㅝ':
			out[startOutIndex] = 'ㅜ';
			out[startOutIndex + 1] = 'ㅓ';
			return true;
			
		case 'ㅞ':
			out[startOutIndex] = 'ㅜ';
			out[startOutIndex + 1] = 'ㅔ';
			return true;
			
		case 'ㅟ':
			out[startOutIndex] = 'ㅜ';
			out[startOutIndex + 1] = 'ㅣ';
			return true;
			
		case 'ㅢ':
			out[startOutIndex] = 'ㅡ';
			out[startOutIndex + 1] = 'ㅣ';
			return true;

		default:
			return false;
		}
	}

	
	public static boolean isSeparableConsonant(char consonant)
	{
		switch (consonant) {
		case 'ㄳ':			
		case 'ㄵ':
		case 'ㄶ':
		case 'ㄺ':
		case 'ㄻ':
		case 'ㄼ':
		case 'ㄽ':
		case 'ㄾ':
		case 'ㄿ':
		case 'ㅀ':
		case 'ㅄ':
			return true;
		default:
			return false;
		}
	}
	
	public static boolean getSeparatedConsonants(char consonant, char[] out, int startOutIndex)
	{
		switch (consonant) {
		case 'ㄳ':
			out[startOutIndex] = 'ㄱ';
			out[startOutIndex + 1] = 'ㅅ';
			return true;
			
		case 'ㄵ':
			out[startOutIndex] = 'ㄴ';
			out[startOutIndex + 1] = 'ㅈ';
			return true;
			
		case 'ㄶ':
			out[startOutIndex] = 'ㄴ';
			out[startOutIndex + 1] = 'ㅎ';
			return true;
			
		case 'ㄺ':
			out[startOutIndex] = 'ㄹ';
			out[startOutIndex + 1] = 'ㄱ';
			return true;
			
		case 'ㄻ':
			out[startOutIndex] = 'ㄹ';
			out[startOutIndex + 1] = 'ㅁ';
			return true;
			
		case 'ㄼ':
			out[startOutIndex] = 'ㄹ';
			out[startOutIndex + 1] = 'ㅂ';
			return true;
			
		case 'ㄽ':
			out[startOutIndex] = 'ㄹ';
			out[startOutIndex + 1] = 'ㅅ';
			return true;
			
		case 'ㄾ':
			out[startOutIndex] = 'ㄹ';
			out[startOutIndex + 1] = 'ㅌ';
			return true;
			
		case 'ㄿ':
			out[startOutIndex] = 'ㄹ';
			out[startOutIndex + 1] = 'ㅍ';
			return true;
			
		case 'ㅀ':
			out[startOutIndex] = 'ㄹ';
			out[startOutIndex + 1] = 'ㅎ';
			return true;
			
		case 'ㅄ':
			out[startOutIndex] = 'ㅂ';
			out[startOutIndex + 1] = 'ㅅ';
			return true;
		default:
			return false;
		}
	}

	/**두 개의 자음을 합쳐 복자음을 만든다. 초성에 복자음이 없고, 쌍자음은 2벌식에서 기본입력이 되므로 받침에만 쓰임 */
	public static char synthesizeConsonant(char a, char b)
	{
		switch(a)
		{
		case 'ㄱ':
			if(b=='ㅅ') return 'ㄳ';
		case 'ㄴ':
			switch(b)
			{
			case 'ㅈ': return 'ㄵ';
			case 'ㅎ': return 'ㄶ';
			}
			break;
		case 'ㄹ':
			switch(b)
			{
			case 'ㄱ': return 'ㄺ';
			case 'ㅁ': return 'ㄻ';
			case 'ㅂ': return 'ㄼ';
			case 'ㅅ': return 'ㄽ';
			case 'ㅌ': return 'ㄾ';
			case 'ㅍ': return 'ㄿ';
			case 'ㅎ': return 'ㅀ';
			}
			break;
		case 'ㅂ':
			if(b=='ㅅ') return 'ㅄ';
		}

		return 0;
	}

	private Character initialLetter;
	private Character[] medialLetters;
	private Character[] finalLetters;

	public DualKoreanAutomata() {
		super(DualKoreanState.Empty);

		initialLetter = null;
		medialLetters = new Character[2]; // 중성은 2개까지 넣을 수 있음.
		finalLetters = new Character[2]; // 종성은 2개까지 넣을 수 있음.
		setCurrentState(DualKoreanState.Empty, false);
		onInitialized();
	}

	public void onInitialized()
	{
		initialLetter = null;

		medialLetters[0] = null;
		medialLetters[1] = null;

		finalLetters[0] = null;
		finalLetters[1] = null;

		setCurrentState(DualKoreanState.Empty, false);
	}

	private char integrateCurrentMedials(){
		if(medialLetters[0]==null) return 0;
		else if(medialLetters[1] != null)
			return synthesizeVowel(medialLetters[0], medialLetters[1]);
		else return medialLetters[0];
	}

	private char integrateCurrentFinals(){
		if(finalLetters[0]==null) return 0;
		else if(finalLetters[1] != null)
			return synthesizeConsonant(finalLetters[0], finalLetters[1]);
		else return finalLetters[0];
	}

	public boolean isInitial(){
		return initialLetter==null && medialLetters[0]==null && medialLetters[1] == null && finalLetters[0]==null && finalLetters[1] == null;
	}

	@Override
	protected char assembleCurrentLetters() {
		if(initialLetter == null)
		{
			return integrateCurrentMedials();
		}
		else{
			char initialAssembled, medialAssembled, finalAssembled;
			initialAssembled = initialLetter;
			medialAssembled = integrateCurrentMedials();
			finalAssembled = integrateCurrentFinals();
			
			if(medialAssembled ==0 ) return initialAssembled;
			else if(finalAssembled == 0) return KoreanAutomata.assembleLetter(initialAssembled, medialAssembled, ' ');
			else return KoreanAutomata.assembleLetter(initialAssembled, medialAssembled, finalAssembled);
		}
	}

	@Override
	public void input(InputConnection inputConnection, char inputLetter) {
		switch(this.getCurrentState())
		{
		case Empty:
			if(isInitial(inputLetter)) // 초성이 들어왔을 경우
			{
				this.initialLetter = inputLetter;
				this.setCurrentState(DualKoreanState.Containing_Initial_only, true);
				this.replaceCurrentCursorLetterTo(inputConnection, this.assembleCurrentLetters());
			}
			else if(isMedial(inputLetter)) // 중성이 들어온 경우
			{
				this.medialLetters[0] = inputLetter;
				this.setCurrentState(DualKoreanState.Containing_Medial_only, true);
				this.replaceCurrentCursorLetterTo(inputConnection, this.assembleCurrentLetters());
			}
			else // 종성만 가능한 문자가 들어온 경우
			{
				;
			}
			break;

		case Containing_Initial_only:
			if(isMedial(inputLetter) == false) //자음
			{
				this.commitCurrentContent(inputConnection,true);
				input(inputConnection,inputLetter);
			}
			else{ // 모음
				this.medialLetters[0] = inputLetter;
				this.setCurrentState(DualKoreanState.Containing_Initial_and_Medial, true);
				this.replaceCurrentCursorLetterTo(inputConnection,this.assembleCurrentLetters());
			}
			
			break;
		case Containing_Medial_only:
			
			if(isMedial(inputLetter) == false) //자음
			{
				this.commitCurrentContent(inputConnection,true);
				input(inputConnection,inputLetter);
			}
			else{ // 모음
				
				if(this.medialLetters[1] != null) // 현재 겹모음 상태
				{
					this.commitCurrentContent(inputConnection,true);
					input(inputConnection,inputLetter);
				}
				else if(synthesizeVowel(this.medialLetters[0], inputLetter) == 0) //겹모음 상태가 아니고 겹모음이 만들어질 수 없을 때
				{
					this.commitCurrentContent(inputConnection,true);
					input(inputConnection,inputLetter);
				}
				else // 겹모음 상태가 아니고 겹모음이 만들어질 수 있을 때
				{
					this.medialLetters[1] = inputLetter;
					this.setCurrentState(DualKoreanState.Containing_Medial_only, true);
					this.replaceCurrentCursorLetterTo(inputConnection,this.assembleCurrentLetters());
				}
			}
			break;
		case Containing_Initial_and_Medial:
			if(isFinal(inputLetter))
			{
				this.finalLetters[0] = inputLetter;
				this.setCurrentState(DualKoreanState.Containing_Initial_Medial_and_Final, true);
				this.replaceCurrentCursorLetterTo(inputConnection,this.assembleCurrentLetters());
			}
			else if (isInitial(inputLetter))
			{
				this.commitCurrentContent(inputConnection,true);
				input(inputConnection,inputLetter);
			}
			else if(isMedial(inputLetter))
			{
				if(this.medialLetters[1] != null) // 현재 겹모음 상태
				{
					this.commitCurrentContent(inputConnection,true);
					input(inputConnection,inputLetter);
				}
				else if(synthesizeVowel(this.medialLetters[0], inputLetter) == 0) //겹모음 상태가 아니고 겹모음이 만들어질 수 없을 때
				{
					this.commitCurrentContent(inputConnection,true);
					input(inputConnection,inputLetter);
				}
				else // 겹모음 상태가 아니고 겹모음이 만들어질 수 있을 때
				{
					this.medialLetters[1] = inputLetter;
					this.setCurrentState(DualKoreanState.Containing_Initial_and_Medial, true);
					this.replaceCurrentCursorLetterTo(inputConnection,this.assembleCurrentLetters());
				}
			}
			
			break;
		case Containing_Initial_Medial_and_Final:
		//	android.os.Debug.waitForDebugger();
			if(isFinal(inputLetter))//종성문자가 들어왔을 때
			{
				if(synthesizeConsonant(this.finalLetters[0], inputLetter)==0) //종성 합치기 안 될 때
				{
					this.commitCurrentContent(inputConnection,true);
					input(inputConnection,inputLetter);
				}
				else //종성합치기 될 때
				{
					this.finalLetters[1] = inputLetter;
					this.setCurrentState(DualKoreanState.Containing_Initial_Medial_and_Final_Complex, true);
					this.replaceCurrentCursorLetterTo(inputConnection,this.assembleCurrentLetters());
				}
			}
			else if(isMedial(inputLetter)) // 중성일 때 연음처리
			{
				char lastLetter = this.rollBack();
				this.commitCurrentContent(inputConnection,true);
				input(inputConnection,lastLetter);
				input(inputConnection,inputLetter);
			}
			else{
				this.commitCurrentContent(inputConnection,true);
				input(inputConnection,inputLetter);
			}
			break;
		case Containing_Initial_Medial_and_Final_Complex:
			if(isMedial(inputLetter)) // 중성일 때 연음처리
			{
				char lastLetter = this.rollBack();
				this.commitCurrentContent(inputConnection,true);
				input(inputConnection,lastLetter);
				input(inputConnection,inputLetter);
			}
			else{
				this.commitCurrentContent(inputConnection,true);
				input(inputConnection,inputLetter);
			}
			break;
		}
	}

	@Override
	protected char removeLastInputLetter() {
		char result = 0;
		if(finalLetters[1] != null)
		{
			result = finalLetters[1];
			finalLetters[1]=null;
		}
		else if(finalLetters[0] != null)
		{
			result = finalLetters[0];
			finalLetters[0]=null;
		}
		else if(medialLetters[1] != null)
		{
			result = medialLetters[1];
			medialLetters[1]=null;
		}
		else if(medialLetters[0] != null)
		{
			result = medialLetters[0];
			medialLetters[0]=null;
		}
		else if(initialLetter != null)
		{
			result = initialLetter;
			initialLetter=null;
		}
		return result;
	}
}
