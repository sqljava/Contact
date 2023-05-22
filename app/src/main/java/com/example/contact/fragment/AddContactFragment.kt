
package com.example.contact.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.contact.R
import com.example.contact.databinding.FragmentAddContactBinding
import com.example.contact.model.Contact
import com.example.contact.sql.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentAddContactBinding

    var name = ""
    var surname = ""
    var number = ""

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
        binding = FragmentAddContactBinding.inflate(inflater, container, false)

        var db = DBHelper(requireContext())





        binding.btnDone.setOnClickListener {
            //if (isEntered()){

            name = binding.addContactName.text.toString()
            surname = binding.addContactSurname.text.toString()
            number = binding.addContactNumber.text.toString()

                var contact = Contact(name =  name+" "+surname, number = number)
                db.addContact(contact)
                findNavController().navigate(R.id.action_addContactFragment_to_homeFragment)
            //}
        }




        return binding.root
    }

    fun isEntered():Boolean{
        var a = true

        if (name.isEmpty()){
            Toast.makeText(requireContext(), "Fill completely", Toast.LENGTH_SHORT).show()
            a = false
        }
        if (number.isEmpty()){
            Toast.makeText(requireContext(), "Fill completely", Toast.LENGTH_SHORT).show()
            a = false
        }
        return a
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}