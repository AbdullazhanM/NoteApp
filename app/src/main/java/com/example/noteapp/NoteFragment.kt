package com.example.noteapp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.noteapp.databinding.FragmentNoteBinding


class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: NoteAdapter
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            requireActivity().
                supportFragmentManager.
                findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController

        adapter = NoteAdapter(this)
        binding.recycler.adapter = adapter
        binding.add.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, AddFragment())
                .commit()
        }
        adapter.setList((requireActivity() as MainActivity).list)
    }

    fun delete(pos: Int) {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Delete")
        alert.setMessage("Are you sure to delete")
        alert.setNegativeButton("Cancel", null)
        alert.setPositiveButton("Delete"){_, _->
            adapter.remove(pos)
        }
        alert.show()
    }

    fun edit(pos: Int) {
        val bundle = Bundle()
        bundle.putSerializable("edit", adapter.getItem(pos))
        val fragment = AddFragment()
        fragment.arguments = bundle
        navController.navigate(R.id.addFragment, bundle)
    }

    fun share(pos: Int) {

    }
}