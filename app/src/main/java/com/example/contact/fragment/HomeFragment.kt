package com.example.contact.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.R
import com.example.contact.adapter.ContactAdapter
import com.example.contact.databinding.FragmentHomeBinding
import com.example.contact.model.Contact
import com.example.contact.sql.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentHomeBinding
    var contacts = arrayListOf<Contact>()

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        var db = DBHelper(requireContext())

        contacts = db.getAllContacts()

        //contacts.add(Contact(0,"qwer", "+9983165432"))

        var adapter = ContactAdapter(contacts, object : ContactAdapter.ContactInterface{
            override fun onClick(contact: Contact) {
                var bundle = bundleOf()
                bundle.putSerializable("contact", contact)
                findNavController().navigate(R.id.action_homeFragment_to_contactInfoFragment, bundle)
            }

        })
        var manager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)


        binding.rvContact.adapter = adapter
        binding.rvContact.layoutManager = manager


        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addContactFragment)
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}