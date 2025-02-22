package com.example.ambrosia

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ambrosia.databinding.ActivityDetailedBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailedBinding
    private lateinit var clipboard: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("IMAGE_URL") ?: ""
        val title = intent.getStringExtra("TITLE") ?: "No Title"
        val description = intent.getStringExtra("DESCRIPTION") ?: "No Description Available"

        // Load data into views
        loadImage(imageUrl)
        binding.TitleTextView.text = title
        binding.descTextView.text = description
        setupTextWithCopy(binding.TitleTextView, title, "Title")
        setupTextWithCopy(binding.descTextView, description, "Description")
//        setupCopyButton(binding.descTextView,binding.descTextView,"DESCRIPTION")
//        setupCopyButton(binding.btnCopyDescription, binding.detailDescription, "Description")
    }

    private fun setupTextWithCopy(textView: TextView, text: String, label: String) {
        textView.text = text

        // Long-press to copy
        textView.setOnLongClickListener {
            copyToClipboard(label, text)
            showCopyFeedback(textView)
            true
        }

    }
    private fun copyToClipboard(label: String, text: String) {
        if (text.isBlank()) {
            showCopyError()
            return
        }
        else {
            val clip = ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)
        }
    }

    private fun showCopyFeedback(view: View) {
        // Snackbar is better than Toast as it's positionally relevant
        Snackbar.make(view, "copied to clipboard", Snackbar.LENGTH_SHORT)
            .setAnchorView(view)  // Align with the copied text
            .show()
    }

    private fun showCopyError() {
        Snackbar.make(binding.root, "Nothing to copy", Snackbar.LENGTH_SHORT).show()
    }

    private fun loadImage(url: String) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.round_person_24)                // Add a placeholder drawable
            .error(R.drawable.ic_launcher_background)               // Add an error drawable
            .into(binding.imageView)

    }
}