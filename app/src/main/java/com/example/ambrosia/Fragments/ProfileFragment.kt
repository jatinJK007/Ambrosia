package com.example.ambrosia.Fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ambrosia.R
import com.example.ambrosia.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment() {

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {uri->
        uri?.let {
//            val galleryUri = it
            try{
//                set image to imageview
                binding.profileImg.setImageURI(uri)
//                save image to internal storage
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val bitmap= BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

//                save bitmap to internal storage
                val file = File(requireContext().filesDir,"profile_img.jpg")
                val outputStream= FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream)
                outputStream.flush()
                outputStream.close()


                val sharedPref = requireContext().getSharedPreferences("profile_prefs",Context.MODE_PRIVATE)
                sharedPref.edit().putString("profile_image_path",file.absolutePath).apply()

            }catch(e:Exception){
                e.printStackTrace()
            }
        }

    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref=requireContext().getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
        val imagePath = sharedPref.getString("profile_image_path", null)

        if (imagePath != null) {
            val file = File(imagePath)
            if (file.exists()) {
                binding.profileImg.setImageURI(Uri.fromFile(file))
            }
        }

        binding.profileImg.setOnClickListener {
            galleryLauncher.launch("image/*")
        }


        binding.btnSaved.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, SaveFragment())
                addToBackStack("save_fragment_tag")
                commit()
            }
        }
    }
}