package kingmo.kkk.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kingmo.kkk.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")
    private val decimalFormat = DecimalFormat("#,###")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // 숫자 클릭 이벤트
    fun numberClicked(view: View) {
        val numberString = (view as? Button)?.text?.toString() ?: ""
        var numberText = if (operatorText.isEmpty()) firstNumberText else secondNumberText

        numberText.append(numberString)
        updateEquationTextView()
    }

    // 초기화
    fun clearClicked(view: View) {
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()
        updateEquationTextView()
        binding.resultTextView.text = ""
    }

    // "="버튼 클릭 이벤트
    fun equalClicked(view: View) {
        if (firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()) {
            Toast.makeText(this, "올바르지 않은 수식 입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val firstNumber = firstNumberText.toString().toBigDecimal()
        val secondNumber = secondNumberText.toString().toBigDecimal()

        val result = when (operatorText.toString()) {
            "+" -> decimalFormat.format(firstNumber + secondNumber)
            "-" -> decimalFormat.format(firstNumber - secondNumber)
            else -> "잘못된 수식 입니다."
        }
        binding.resultTextView.text = result
    }

    // 연산자 버튼 클릭 이벤트
    fun operatorClicked(view: View) {
        val operatorString = (view as? Button)?.text?.toString() ?: ""

        if (firstNumberText.isEmpty()) {
            Toast.makeText(this, "먼저 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (secondNumberText.isNotEmpty()) {
            Toast.makeText(this, "1개의 연산자에 대해서만 연산이 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        operatorText.append(operatorString)
        updateEquationTextView()
    }

    // 계산식 UI 업데이트
    private fun updateEquationTextView() {
        val firstFormattedNumber = if (firstNumberText.isNotEmpty()) decimalFormat.format(
            firstNumberText.toString().toBigDecimal()
        ) else ""

        val firstSecondNumberText = if (secondNumberText.isNotEmpty()) decimalFormat.format(
            secondNumberText.toString().toBigDecimal()
        ) else ""

        binding.equationTextView.text = "$firstFormattedNumber $operatorText $firstSecondNumberText"
    }
}