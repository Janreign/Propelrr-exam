package com.example.propelrrprogrammingexam

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.propelrrprogrammingexam.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mobileNumberText: TextInputEditText
    private lateinit var mobileNumberLayout: TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val emailEditText = binding.emailaddresstext
        val emailInputLayout = binding.emailaddressinput
        val submitButton = binding.btnSubmit
        mobileNumberText = findViewById(R.id.mobilenumbertext)
        mobileNumberLayout = findViewById(R.id.mobilenumberinput)

        mobileNumberText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateMobileNumber(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        submitButton.setOnClickListener {
            validateEmail(emailEditText.text.toString(), emailInputLayout)
        }

        val spinnerId = findViewById<Spinner>(R.id.spGender)
        val genders = arrayOf("Male", "Female")
        val arrayAdp
        = ArrayAdapter (this@MainActivity,android.R.layout.simple_spinner_dropdown_item,genders)
        spinnerId.adapter = arrayAdp

        spinnerId?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@MainActivity,"Item is ${genders[p2]}", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

                Toast.makeText(this@MainActivity,"Nothing selected", Toast.LENGTH_LONG).show()
            }
        }

        val datePickerButton: Button = findViewById(R.id.datePickerButton)

        datePickerButton.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                val dateOfBirthText: TextView? = findViewById(R.id.dateofbirthtext)
                dateOfBirthText?.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
    private fun validateEmail(email: String, emailInputLayout: TextInputLayout) {
        if (isValidEmail(email)) {
            emailInputLayout.error = null
        } else {
            emailInputLayout.error = "Invalid email address"
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateMobileNumber(input: String) {
        val mobileNumberRegex = Regex("^0[9]\\d{9}$")

        if (input.isEmpty()) {
            mobileNumberLayout.error = "Mobile number is required"
        } else if (!mobileNumberRegex.matches(input)) {
            mobileNumberLayout.error = "Invalid mobile number format"
        } else {
            mobileNumberLayout.error = null // Clear the error
        }
    }
}



