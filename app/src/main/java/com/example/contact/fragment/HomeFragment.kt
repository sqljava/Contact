package com.example.contact.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
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

    lateinit var adapter:ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

         adapter = ContactAdapter(contacts, object : ContactAdapter.ContactInterface{
            override fun onClick(contact: Contact) {
                var bundle = bundleOf()
                //bundle.putSerializable("contact", contact)
                bundle.putInt("id", contact.id)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        var searchItem = menu.findItem(R.id.search)
        var searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                mySearch(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mySearch(newText.toString())
                return true
            }

        })
    }

    fun mySearch(query: String){
        var conts = arrayListOf<Contact>()

        for (i in contacts){
            if (i.name.contains(query)){
                conts.add(i)
            }
        }

        adapter = ContactAdapter(conts, object : ContactAdapter.ContactInterface{
            override fun onClick(contact: Contact) {
                var bundle = bundleOf()
                //bundle.putSerializable("contact", contact)
                bundle.putInt("id", contact.id)
                findNavController().navigate(R.id.action_homeFragment_to_contactInfoFragment, bundle)
            }
        })


    }

    companion object {
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