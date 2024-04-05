package com.intelliswift.mvvmsetupapps.utils

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.lang.Exception

class Helper {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun showToast(activity: Activity, message: String) {
            val toast = Toast.makeText(activity, message, Toast.LENGTH_LONG) // in Activity
            toast.show()
        }

        fun isValidPhoneNumber(phoneNumber: String): Boolean {
            return Patterns.PHONE.matcher(phoneNumber).matches()
        }

        fun hideKeyboard(view: View){
            try {
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }catch (e: Exception){

            }
        }
    }

}