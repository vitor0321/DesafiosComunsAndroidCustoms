package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.check_edit_text

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.data.StorageType
import com.example.desafioscomunsandroidcustoms.databinding.FragmentCheckEditTextBinding
import com.example.desafioscomunsandroidcustoms.util.shake
import com.example.desafioscomunsandroidcustoms.util.vibrate
import com.example.desafioscomunsandroidcustoms.util.viewBinding
import org.koin.android.ext.android.inject
import java.util.regex.Pattern

class CheckEditTextFragment : Fragment(R.layout.fragment_check_edit_text) {

    private val binding by viewBinding(FragmentCheckEditTextBinding::bind)

    private val sharedPreferences: StorageType by inject()

    private var isAtLeast8 = false
    private var hasUppercase: Boolean = false
    private var hasNumber: Boolean = false
    private var hasSymbol: Boolean = false
    private var isRegistrationClickable: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnGetPassword.setOnClickListener {
               val getEmail = etGetEmail.text.toString()
                getPassword(getEmail)
            }
            btnRegister.setOnClickListener(View.OnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                when {
                    email.isNotEmpty() && password.isNotEmpty() -> {
                        if (isRegistrationClickable) {
                            btnRegister.setOnClickListener {
                                savaPassword(email, password)
                                etEmail.text?.clear()
                                etPassword.text?.clear()
                            }
                        }
                    }
                    email.isEmpty() -> {
                        etEmail.error = "Check email"
                        textInputEmail.shake()
                        vibrate(500L)
                    }
                    password.isEmpty() -> {
                        etPassword.error = "Check Password"
                        textInputPassword.shake()
                        vibrate(500L)
                    }
                }
            })
            inputChange()
        }
    }

    private fun inputChange() {
        binding.apply {
            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    registrationDataCheck()
                }
                override fun afterTextChanged(editable: Editable) {}
            })
            etPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    registrationDataCheck()
                }
                override fun afterTextChanged(s: Editable) {}
            })
        }
    }

    private fun registrationDataCheck() {
        binding.apply {
            val password: String = etPassword.text.toString()
            val email: String = etEmail.text.toString()

            when {
                password.length >= 8 -> {
                    isAtLeast8 = true
                    frameOne.setCardBackgroundColor(resources.getColor(R.color.green))
                }
                password.length < 8 -> {
                    isAtLeast8 = false
                    frameOne.setCardBackgroundColor(resources.getColor(R.color.red))
                }
            }
            when {
                Pattern.matches("(.*[A-Z].*)", password) -> {
                    hasUppercase = true
                    frameTwo.setCardBackgroundColor(resources.getColor(R.color.green))
                }
                !Pattern.matches("(.*[A-Z].*)", password) -> {
                    hasUppercase = true
                    frameTwo.setCardBackgroundColor(resources.getColor(R.color.red))
                }
            }
            when {
                Pattern.matches("(.*[0-9].*)", password) -> {
                    hasNumber = true
                    frameThree.setCardBackgroundColor(resources.getColor(R.color.green))
                }
                !Pattern.matches("(.*[0-9].*)", password) -> {
                    hasNumber = false
                    frameThree.setCardBackgroundColor(resources.getColor(R.color.red))
                }
            }
            when {
                Pattern.matches("^(?=.*[_.()@#$%&/?*+-]).*$", password) -> {
                    hasSymbol = true
                    frameFour.setCardBackgroundColor(resources.getColor(R.color.green))
                }
                !Pattern.matches("^(?=.*[_.()@#$%&/?*+-]).*$", password) -> {
                    hasSymbol = false
                    frameFour.setCardBackgroundColor(resources.getColor(R.color.red))
                }
            }
            checkEnableButton(email)
        }
    }

    private fun checkEnableButton(email: String) {
        binding.apply {
            when{
                isAtLeast8 && hasUppercase && hasNumber && hasSymbol && email.isNotEmpty() ->{
                    isRegistrationClickable = true
                    btnRegister.setBackgroundColor(resources.getColor(R.color.green))
                }
                else -> {
                    isRegistrationClickable = false
                    btnRegister.setBackgroundColor(resources.getColor(R.color.gray))
                }
            }
        }
    }

    private fun savaPassword(email: String, password: String) {
        sharedPreferences.putString(email, password)
    }

    private fun getPassword(email: String) {
        binding.apply {
            val password = sharedPreferences.getString(email)
            password?.let {
                tvPassword.text = "Password = $it"
            } ?: run {
                tvPassword.text = "Não tem password registrada"
            }
        }
    }
}