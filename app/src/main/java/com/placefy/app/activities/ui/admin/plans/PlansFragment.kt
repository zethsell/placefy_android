package com.placefy.app.activities.ui.admin.plans

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.placefy.app.activities.FormActivity
import com.placefy.app.databinding.FragmentPlansBinding


class PlansFragment : Fragment() {

    private var _binding: FragmentPlansBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(PlansViewModel::class.java)

        _binding = FragmentPlansBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(context, FormActivity::class.java))
        }

        return root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}