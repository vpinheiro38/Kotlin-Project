package com.kotlin.easycalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var strings: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.text = ""
        strings.add("")
    }

    fun onClickEqual(view: View) {

    }

    fun onClickNumber(view: View) {
        val btn: Button? = view as? Button
//        val strings = textView.text.split(" ")

//        if (btn?.text == "." && !strings[strings.lastIndex].contains('.') || btn?.text != ".")
//            textView.text = "${textView.text}${btn?.text}"
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
