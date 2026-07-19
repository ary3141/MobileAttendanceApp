package com.example.mobilesurapp.UIApp.components.input

import com.example.mobilesurapp.ui.theme.MobileSurAppTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,

    modifier: Modifier = Modifier,

    label: String? = null,
    placeholder: String? = null,

    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,

    isPassword: Boolean = false,
    passwordVisible: Boolean = false,

    isError: Boolean = false,
    readOnly: Boolean = false,
    singleLine: Boolean = true,

    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,

        modifier = modifier.fillMaxWidth(),

        singleLine = singleLine,

        readOnly = readOnly,

        isError = isError,

        keyboardOptions = keyboardOptions,

        shape = RoundedCornerShape(16.dp),

        visualTransformation =
            if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,

        label = {
            label?.let {
                Text(it)
            }
        },

        placeholder = {
            placeholder?.let {
                Text(it)
            }
        },

        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        },

        trailingIcon = {
            trailingIcon?.let {
                if (onTrailingIconClick != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(
                            imageVector = it,
                            contentDescription = null
                        )
                    }
                } else {
                    Icon(
                        imageVector = it,
                        contentDescription = null
                    )
                }
            }
        },

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}
@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    MaterialTheme {
        AppTextField(
            value = "",
            onValueChange = {},
            label = "Email"
        )
    }
}