package com.chuckerteam.chucker.internal.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider

internal abstract class BaseChuckerActivity : AppCompatActivity() {
    /**
     * Override setTheme so we can also apply opt out to edgeToEdge enforcement style to avoid
     * our UI not being laid out correctly in API 35+.
     */
    override fun setTheme(resId: Int) {
        super.setTheme(resId)
        theme.applyStyle(R.style.chucker_OptOutEdgeToEdgeEnforcement, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RepositoryProvider.initialize(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isInForeground = true
    }

    override fun onPause() {
        super.onPause()
        isInForeground = false
    }

    companion object {
        var isInForeground: Boolean = false
            private set
    }

    fun showToast(
        message: String,
        toastDuration: Int = Toast.LENGTH_SHORT,
    ) {
        Toast.makeText(this.applicationContext, message, toastDuration).show()
    }
}
