package com.dropkick.soma.somaproject.keyboard.units.korean;

public enum DualKoreanState {
	
	/**빈 상태 -> 암거나 입력가능*/
	//Empty(true,true,false), 
	
	/**초성만 들어있는 상태*/
//	Containing_Only_Initial(false, true, false),
	
	/**겹모음 될 수 있는 중성만*/
//	Containing_Only_Medial_Integrable(false, true, false),
	
	/**완성된 중성만*/
//	Containing_Only_Medial_Complete(false, false, false),
			
	/**초성, 겹모음 될 수 있는 중성*/
//	Containing_Initial_and_Medial_Integrable(false,true,true),
	
	/**초성, 완성된 중성 */
//	Containing_Initial_and_Medial_Complete(false,false,true),
	
	/**초성, 중성, 겹받침 될 수 있는 종성 */
//	Containing_Initial_Medial_and_Final_Integrable(false,false,true),
	
	/**초성, 중성, 완성 종성 */
//	Containing_Initial_Medial_and_Final_Complete(false,false,false);
	
/*
	DualKoreanState(boolean initialComes, boolean medialComes, boolean finalComes)
	{
		this.canInitialComes = initialComes;
		this.canMedialComes = medialComes;
		this.canFinalComes = finalComes;
	}
	
	private boolean canInitialComes;
	private boolean canMedialComes;
	private boolean canFinalComes;
	
	public boolean canInitialComes(){return this.canInitialComes;}
	public boolean canMedialComes(){return this.canMedialComes;}
	public boolean canFinalComes(){return this.canFinalComes;}
	*/
	
	Empty,
	Containing_Initial_only,
	Containing_Medial_only,
	Containing_Initial_and_Medial,
	Containing_Initial_Medial_and_Final,
	Containing_Initial_Medial_and_Final_Complex;

}
