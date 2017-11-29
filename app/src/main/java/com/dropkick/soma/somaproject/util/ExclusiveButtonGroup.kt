package com.dropkick.soma.somaproject.util

import android.view.View
import android.widget.Button

/**
 * Created by junhoe on 2017. 11. 25..
 */
class ExclusiveButtonGroup : View.OnClickListener {

   companion object {
       const val CONST_BACKGROUND = -1
   }

   private val buttonMap: MutableMap<String, Button> = mutableMapOf()
   private val selectedResMap: MutableMap<String ,Int> = mutableMapOf()
   private val unselectedResMap: MutableMap<String, Int> = mutableMapOf()
   private var selectListener: ((button: Button, key: String) -> Unit)? = null

   fun setSelectListener(listener: (button: Button, key: String) -> Unit) {
      selectListener = listener
   }

   fun addButton(button: Button, key: String, selectedRes: Int, unselectedRes: Int): ExclusiveButtonGroup {
      buttonMap.put(key, button)
      button.setOnClickListener(this)
      selectedResMap.put(key, selectedRes)
      unselectedResMap.put(key, unselectedRes)
      return this
   }

   fun addButton(button: Button, key: String): ExclusiveButtonGroup {
      buttonMap.put(key, button)
      button.setOnClickListener(this)
      selectedResMap.put(key, CONST_BACKGROUND)
      unselectedResMap.put(key, CONST_BACKGROUND)
      return this
   }

   override fun onClick(v: View) {
      buttonMap.entries.forEach {
         entry ->
         val button: Button = entry.value
         if (entry.value == v) {
               selectedResMap[entry.key]?.let {
                  if (it != CONST_BACKGROUND) {
                     button.setBackgroundResource(it)
                  }
               }
               selectListener?.invoke(v as Button, entry.key)
         } else {
            unselectedResMap[entry.key]?.let {
               if (it != CONST_BACKGROUND) {
                  button.setBackgroundResource(it)
               }
            }
         }
      }
   }
}