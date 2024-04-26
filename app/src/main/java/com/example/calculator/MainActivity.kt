package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.calculator.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.*
import java.lang.Exception
import java.text.DecimalFormat
import kotlin.math.exp

lateinit var binding: ActivityMainBinding
//var isNightMoodeOn : Boolean = false
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//dark and light mode in case of for controlling it using button
//        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
//            isNightMoodeOn = false
//        }else if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
//            isNightMoodeOn = true
//        }
        //night mode unspecified
//        else{
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }
        binding.buttonClear.setOnClickListener{
            binding.input.text = ""
            binding.output.text = ""
        }
        binding.button0.setOnClickListener{
            binding.input.text = addToInputText("0")
        }
        binding.button1.setOnClickListener{
            binding.input.text = addToInputText("1")
        }
        binding.button2.setOnClickListener{
            binding.input.text = addToInputText("2")
        }
        binding.button3.setOnClickListener{
            binding.input.text = addToInputText("3")
        }
        binding.button4.setOnClickListener{
            binding.input.text = addToInputText("4")
        }
        binding.button5.setOnClickListener{
            binding.input.text = addToInputText("5")
        }
        binding.button6.setOnClickListener{
            binding.input.text = addToInputText("6")
        }
        binding.button7.setOnClickListener{
            binding.input.text = addToInputText("7")
        }
        binding.button8.setOnClickListener {
            binding.input.text = addToInputText("8")
        }
        binding.button9.setOnClickListener{
            binding.input.text = addToInputText("9")
        }
        binding.buttonDot.setOnClickListener{
            binding.input.text = addToInputText(".")
        }
        binding.buttonPlus.setOnClickListener{
            binding.input.text = addToInputText("+")
        }
        binding.buttonDivision.setOnClickListener{
            binding.input.text = addToInputText("÷")
        }
        binding.buttonMinus.setOnClickListener{
            binding.input.text = addToInputText("-")
        }
        binding.buttonMultiply.setOnClickListener{
            binding.input.text = addToInputText("×")
        }
        binding.buttonRemove.setOnClickListener {
            setButtonRemoveClickListener()
        }
        binding.buttonPercent.setOnClickListener {
            setButtonPercentClickListener()
        }
        binding.buttonEqual.setOnClickListener{
            showResult()
        }
//        binding.themeSwitcher.setOnClickListener {
//            changeTheme()
//        }
        binding.buttonD0.setOnClickListener {
            binding.input.text = addToInputText("00")
        }
    }

    //function to convert the number from percent
    private fun setButtonPercentClickListener() {
        // Using trim to remove any spaces
        val currentInput = binding.input.text.toString().trim()
        // Check if the input is a single number or an expression
        val containsOperator = currentInput.contains('+') || currentInput.contains('-') || currentInput.contains('÷') || currentInput.contains('×')
        if (containsOperator) {
            val operatorIndex = currentInput.indexOfLast { it in "+-×÷" }
            // If the operator is at the beginning of the string, operatorIndex will be 0
            val updatedInput = if (operatorIndex == 0) {
                val lastNumber = currentInput.substring(1).toDoubleOrNull() ?: return
                val percentValue = lastNumber / 100.0
                currentInput.substring(1) + DecimalFormat("0.######").format(percentValue)
            } else {
                val lastNumber = currentInput.substring(operatorIndex + 1).toDoubleOrNull() ?: return
                val percentValue = lastNumber / 100.0
                currentInput.substring(0, operatorIndex + 1) + DecimalFormat("0.######").format(percentValue)
            }
            binding.input.text = updatedInput
        } else {
            val currentInputAsDouble = currentInput.toDoubleOrNull() ?: return
            val percentValue = currentInputAsDouble / 100.0
            // Construct the updated input with the percentage appended to the old input text
            val updatedInput = DecimalFormat("0.######").format(percentValue)
            binding.input.text = updatedInput
        }
    }
//    private fun changeTheme(){
//        if(isNightMoodeOn){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            isNightMoodeOn = false
//        }else{
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            isNightMoodeOn = true
//        }
//    }

    // function to delete the last number
    private fun setButtonRemoveClickListener() {
            if (binding.input.text.isNotEmpty()) {
                binding.input.text = binding.input.text.substring(0, binding.input.text.length - 1)
            }
    }
    //function to put each number or operator into the input text
    private fun addToInputText(buttonValue : String): String {
        return "${binding.input.text}$buttonValue"
    }
    //convert the operators
    private fun getInputExpression(): String {
        // Regular expressions are used to replace the display-specific division and multiplication symbols with the corresponding mathematical operators.
        var expression = binding.input.text
        expression = expression.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("×"), "*")
        return expression
    }


    private fun showResult(){
        //try and catch to handle any errors not to cause any crash in the App
        try{
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            if(result.isNaN()){
                //show Error Message
                binding.output.text = "Error"
                binding.output.setTextColor(ContextCompat.getColor(this,R.color.red))
            }else{
                //show Result
                binding.output.text = DecimalFormat("0.######").format(result).toString()
                binding.output.setTextColor(ContextCompat.getColor(this,R.color.orange))
            }
            //catch any exceptions like divided by zero, etc..
        }catch (e: Exception){
            //show Error Message
            binding.output.text = "Error"
            binding.output.setTextColor(ContextCompat.getColor(this,R.color.red))
        }
    }
}