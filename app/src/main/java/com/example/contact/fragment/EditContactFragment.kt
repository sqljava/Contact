package com.example.contact.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.contact.R
import com.example.contact.databinding.FragmentEditContactBinding
import com.example.contact.model.Contact
import com.example.contact.sql.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentEditContactBinding

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
        binding = FragmentEditContactBinding.inflate(inflater, container, false)

        var db = DBHelper(requireContext())

        var id :Int = arguments?.getInt("id") as Int

        var contact: Contact = db.getContact(id)

        binding.editContactName.setText(contact.name)
        binding.editContactNumber.setText(contact.number)

        binding.btnOk.setOnClickListener {
            var name = binding.editContactName.text.toString()
            var number = binding.editContactNumber.text.toString()

            var con = Contact(id, name, number)

            db.updateContact(con)

            var bundle = bundleOf()
            bundle.putInt("id", id)

            findNavController().navigate(R.id.action_editContactFragment_to_contactInfoFragment, bundle)

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
         * @return A new instance of fragment EditContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}