package com.placefy.app.activities.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.placefy.app.databinding.FragmentIndexTopBarBinding
import com.placefy.app.enums.Register


class IndexTopBarFragment : Fragment() {

    private lateinit var binding: FragmentIndexTopBarBinding

    private lateinit var title: String
    private lateinit var description: String
    private var action: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("title").toString()
        description = arguments?.getString("description").toString()
        action = arguments?.getInt("action") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndexTopBarBinding.inflate(inflater, container, false)
        load()
        return binding.root
    }

    private fun load() {
        binding.title.text = title
        binding.description.text = description
        setBtnRegisterEvents()
    }


    private fun setBtnRegisterEvents() {
        binding.btnRegister.setOnClickListener {
            when (action) {
                Register.USER.ordinal -> {

                }

                Register.USER_TO_APPROVE.ordinal -> {

                }

                Register.PLAN.ordinal -> {

                }

                Register.PROPERTY.ordinal -> {

                }
            }
        }
    }

}