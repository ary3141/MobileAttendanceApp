package com.example.mobilesurapp.UIApp.add_employee.validation

data class ValidationResult(

    val fullNameError: String? = null,

    val emailError: String? = null,

    val phoneError: String? = null

){

    val isValid
        get() =
            fullNameError == null &&
            emailError == null &&
            phoneError == null

}

object EmployeeValidator {

    fun validate(
        fullName:String,
        email:String,
        phone:String
    ): ValidationResult{

        val fullNameError =
            if(!fullName.matches("^[a-zA-Z\\s]{1,50}$".toRegex()))
                "Name is invalid."
            else
                null

        val emailError =
            if(
                !email.matches(
                    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
                        .toRegex()
                )
            )
                "Invalid email."
            else
                null

        val phoneError =
            if(
                !phone.matches(
                    "^(08|628)[0-9]{8,12}$"
                        .toRegex()
                )
            )
                "Invalid phone number."
            else
                null

        return ValidationResult(

            fullNameError,

            emailError,

            phoneError

        )

    }

}