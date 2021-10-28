package com.fingerprintjs.android.pro.playgroundpro.dialogs


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.widget.EditText
import com.fingerprintjs.android.pro.playgroundpro.ApplicationPreferences
import com.fingerprintjs.android.pro.playgroundpro.R
import android.content.DialogInterface
import androidx.core.content.ContextCompat


class SettingsDialog(
    private val activity: Activity,
    private val preferences: ApplicationPreferences
) {

    private var dialog: AlertDialog? = null

    private val accentColor = ContextCompat.getColor(activity, R.color.orange)

    @SuppressLint("InflateParams")
    fun showSettings() {
        val builder: AlertDialog.Builder = activity.let {
            AlertDialog.Builder(it)
        }

        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_settings, null)

        val endpointUrlEditText = view.findViewById<EditText>(R.id.edit_text_endpoint_url)
        val apiTokenEditText = view.findViewById<EditText>(R.id.edit_text_api_token)

        endpointUrlEditText.text = SpannableStringBuilder(preferences.getEndpointUrl())
        apiTokenEditText.text = SpannableStringBuilder(preferences.getApiToken())

        dialog = builder
            .setTitle("Settings")
            .setPositiveButton(
                "Apply"
            ) { _, _ ->
                applyPreferences(
                    endpointUrlEditText.text.toString(),
                    apiTokenEditText.text.toString()
                )
            }
            .setNegativeButton(
                "Cancel"
            ) { _, _ ->
                dismiss()
            }
            .setView(view)

            .create()

        dialog?.show()

        val negativeButton = dialog?.getButton(DialogInterface.BUTTON_NEGATIVE)
        negativeButton?.setTextColor(accentColor)

        val positiveButton = dialog?.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton?.setTextColor(accentColor)
    }

    fun dismiss() {
        dialog?.dismiss()
    }

    private fun applyPreferences(
        endpointUrl: String,
        apiToken: String
    ) {
        preferences.setEndpointUrl(endpointUrl)
        preferences.setApiToken(apiToken)
    }
}