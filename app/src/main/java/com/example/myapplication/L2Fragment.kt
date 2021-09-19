package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentL2Binding
import androidx.navigation.fragment.navArgs

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class L2Fragment : Fragment() {

private var _binding: FragmentL2Binding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentL2Binding.inflate(inflater, container, false)
      return binding.root

    }

    val args: L2FragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.l2backButton.setOnClickListener {
           findNavController().navigate(R.id.action_L2Fragment_to_L1Fragment)
        }

        binding.l2nextButton.setOnClickListener {
            val devTextVal = view.findViewById<TextView>(R.id.l2deviceEditText)
            val devValString = devTextVal.text.toString()
            val action = L2FragmentDirections.actionL2FragmentToL3Fragment(devValString)
            findNavController().navigate(action)
        }

        val catArg = args.categoryName
        view.findViewById<TextView>(R.id.l2categoryText).text = catArg
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}