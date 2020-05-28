package com.example.todoboom.todoiteminfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.todoboom.R
import com.example.todoboom.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)

        val args = InfoFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "${args.todoDesc}", Toast.LENGTH_LONG).show()


        return binding.root

    }
}