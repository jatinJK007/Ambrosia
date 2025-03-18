package com.example.ambrosia

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ambrosia.databinding.ActivityDetailedBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        binding.saveIconHollow.setOnClickListener {
            binding.saveIconHollow.visibility = View.INVISIBLE
            binding.saveIconFilled.visibility = View.VISIBLE
//            binding.saveIconHollow.display
        }
        binding.saveIconFilled.setOnClickListener {
            binding.saveIconHollow.visibility = View.VISIBLE
            binding.saveIconFilled.visibility = View.INVISIBLE
        }

        binding.downlaodIcon.setOnClickListener {
            downloadImage(imageUrl)
        }
        // Load data into views
        loadImage(imageUrl)
        binding.TitleTextView.text = title
        binding.descTextView.text = description
        setupTextWithCopy(binding.TitleTextView, title, "Title")
        setupTextWithCopy(binding.descTextView, description, "Description")
    }

    private fun downloadImage(imageurl:String) {
        lifecycleScope.launch {
            try {
                val downloadedFile = withContext(Dispatchers.IO) {
                    downloadImageFromUrl(imageurl)
                }
                showToast("Image saved to: ${downloadedFile.absolutePath}")
            } catch (e: Exception) {
                showToast("Download failed: ${e.message}")
            }
        }
    }
    private suspend fun downloadImageFromUrl(urlString: String): File {
        val url = URL(urlString)
        val connection = url.openConnection()
        val inputStream: InputStream = connection.getInputStream()

        // Create target directory
        val storageDir = File(getExternalFilesDir(null), "Ambrosia/Images")
        if (!storageDir.exists()) storageDir.mkdirs()

        // Create unique filename
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "IMG_${timeStamp}.jpg"
        val outputFile = File(storageDir, fileName)

        // Save the file
        FileOutputStream(outputFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return outputFile
    }
    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@DetailedActivity, message, Toast.LENGTH_LONG).show()
        }
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
        if(url.isNotBlank()){
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.round_person_24)                // Add a placeholder drawable
                .error(R.drawable.ic_launcher_background)               // Add an error drawable
                .into(binding.imageView)

        }
        else{
            binding.imageView.setImageResource(R.drawable.ic_launcher_background)
        }

    }
}