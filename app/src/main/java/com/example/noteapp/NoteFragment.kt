package com.example.noteapp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapp.AddFragment
import com.example.noteapp.NoteAdapter
import com.example.noteapp.R


class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun delete(pos: Int) {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle(R.string.delete)
        alert.setMessage("Are you sure to delete")
        alert.setNegativeButton("Cancel", null)
        alert.setPositiveButton("Delete"){_, _->
            adapter.remove(pos)
        }
        alert.show()
    }

    override fun edit(pos: Int) {
        val bundle = Bundle()
        bundle.putSerializable("edit", adapter.getItem(pos))
        val fragment = AddFragment()
        fragment.arguments = bundle
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    override fun share(pos: Int) {

    }
}