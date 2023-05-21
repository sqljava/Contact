package com.example.contact.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.contact.R
import com.example.contact.databinding.FragmentContactInfoBinding
import com.example.contact.model.Contact
import com.example.contact.sql.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ContactInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentContactInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactInfoBinding.inflate(inflater, container, false)

        var db = DBHelper(requireContext())

        var contact:Contact = arguments?.getSerializable("contact") as Contact

        binding.infoName.text = contact.name

        binding.infoNumber.text = contact.number

        var dialog = Dialog(requireContext())
        var dialodView = layoutInflater.inflate(R.layout.delete_dialog, null)
        var yes = dialodView.findViewById<TextView>(R.id.dialog_text_yes)
        var no = dialodView.findViewById<TextView>(R.id.dialog_text_no)

        binding.deleteImg.setOnClickListener {

            dialog.setContentView(dialodView)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            no.setOnClickListener{
                dialog.hide()
            }

            yes.setOnClickListener {
                db.deleteContact(contact.id)
                findNavController().navigate(R.id.action_contactInfoFragment_to_homeFragment)
                dialog.hide()
            }
        }



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}