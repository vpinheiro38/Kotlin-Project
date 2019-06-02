package com.kotlin.easycalc

import kotlin.math.pow
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            Toast.makeText(applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT).show()
        }

        builder = AlertDialog.Builder(this)

        builder.setTitle("Operação Inválida")
        builder.setMessage("Por favor digite uma operação válida")
        builder.setPositiveButton("OK") {_,_ -> }

        textView.text = ""
    }

    fun onClickEqual(view: View) {
        val aritArray = textView.text.split("(?<=[+\\-÷*])|(?=[+\\-÷*])".toRegex()).toMutableList()
        println(aritArray)

        if (aritArray.size > 3) {
            if (aritArray[0].isEmpty() && aritArray[1] == "-" && aritArray[2].isNumber()) {
                aritArray.removeAt(0)
                aritArray.removeAt(0)
                aritArray[0] = "-${aritArray[0]}"
            }
            if (aritArray[2] == "-" && aritArray[3].isNumber()) {
                aritArray[2] = "-${aritArray[3]}"
                aritArray.removeAt(3)
            }
        }
        println(aritArray)

        if (aritArray.isNotEmpty()) {
            if (aritArray.size != 3) {
                builder.show()
            }
            else {
                if (aritArray[0].isNumber() && aritArray[2].isNumber() && aritArray[1].isOperand()) {
                    var expResult = 0f

                    for (ind in aritArray.indices step 2) {
                        if (aritArray[ind] == ".")
                            aritArray[ind] = "0"
                        if (aritArray[ind].hasExp()) {
                            aritArray[ind] = applyExp(aritArray[ind])
                        }
                    }

                    when(aritArray[1]) {
                        "+" -> expResult = aritArray[0].toFloat() + aritArray[2].toFloat()
                        "-" -> expResult = aritArray[0].toFloat() - aritArray[2].toFloat()
                        "*" -> expResult = aritArray[0].toFloat() * aritArray[2].toFloat()
                        "÷" -> expResult = aritArray[0].toFloat() / aritArray[2].toFloat()
                    }

                    textView.text = expResult.toString()
                } else
                    builder.show()
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

    private fun applyExp(str: String) : String {
        val strSplit = str.split('E')

        return (strSplit[0].toFloat() * 10f.pow(strSplit[1].toFloat())).toString()
    }

    private fun remLastChar(str: String) : String {
        when(str.isNotEmpty()) {
            true -> return str.substring(0, str.lastIndex)
            false -> return ""
        }
    }
}

private fun String.hasExp(): Boolean {
    for (char in this)
        if (char == 'E')
            return true
    return false
}

private fun String.isOperand(): Boolean {
    val regexOp = "[+-÷*]".toRegex()

    if (this.length > 1 || regexOp.find(this, 0) == null)
        return false
    return true
}

private fun String.isNumber(): Boolean {
    var hasDot = false
    var hasMinus = false
    var hasExp = false

    if (this[this.lastIndex] == 'E')
        return false

    for (char in this) {
        if (!char.isDigit() && char != '.' && char != '-' && char != 'E')
            return false
        else if (char == '.')
            when (hasDot) {
                true -> return false
                false -> hasDot = true
            }
        else if (char == '-')
            when (hasMinus) {
                true -> return false
                false -> hasMinus = true
            }
        else if (char == 'E')
            when (hasExp) {
                true -> return false
                false -> hasExp = true
            }
    }
    return true
}

