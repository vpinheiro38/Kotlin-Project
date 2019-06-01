package com.kotlin.easycalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.text = ""
    }

    fun onClickEqual(view: View) {
        val aritArray = textView.text.split("(?<=[+\\-÷*])|(?=[+\\-÷*])".toRegex()).toMutableList()
        println(aritArray)

        if (aritArray.isNotEmpty()) {
            if (aritArray.size != 3)
                println("EXPRESSAO ERRADA")
            else {
                if (aritArray[0].isNumber() && aritArray[2].isNumber() && aritArray[1].isOperand()) {
                    var expResult = 0f

                    for (ind in aritArray.indices step 2)
                        if (aritArray[ind] == ".")
                            aritArray[ind] = "0"

                    when(aritArray[1]) {
                        "+" -> expResult = aritArray[0].toFloat() + aritArray[2].toFloat()
                        "-" -> expResult = aritArray[0].toFloat() - aritArray[2].toFloat()
                        "*" -> expResult = aritArray[0].toFloat() * aritArray[2].toFloat()
                        "÷" -> expResult = aritArray[0].toFloat() / aritArray[2].toFloat()
                    }

                    textView.text = expResult.toString()
                } else
                    println("EXPRESSAO ERRADA")
            }
        }

    }

    fun onClickNumber(view: View) {
        val btn: Button? = view as? Button
//        val strings = textView.text.split(" ")

//        if (btn?.text == "." && !strings[strings.lastIndex].contains('.') || btn?.text != ".")
        textView.text = "${textView.text}${btn?.text}"
    }

    fun onClickOperation(view: View) {
        val btn: Button? = view as? Button

        textView.text = "${textView.text}${btn?.text}"
    }

    fun onClickDelete(view: View) {
        textView.text = remLastChar(textView.text.toString())
        if (textView.text.isNotEmpty() && textView.text.last() == ' ')
            textView.text = remLastChar(textView.text.toString())
    }



    private fun remLastChar(str: String) : String {
        when(str.isNotEmpty()) {
            true -> return str.substring(0, str.lastIndex)
            false -> return ""
        }
    }
}

private fun String.isOperand(): Boolean {
    val regexOp = "[+-÷*]".toRegex()

    if (this.length > 1 || regexOp.find(this, 0) == null)
        return false
    return true
}

private fun String.isNumber(): Boolean {
    var hasDot = false

    for (char in this) {
        if (!char.isDigit() && char != '.')
            return false
        else if (char == '.')
            when (hasDot) {
                true -> return false
                false -> hasDot = true
            }
    }
    return true
}

