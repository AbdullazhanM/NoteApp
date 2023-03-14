package com.example.noteapp
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.joma.notes.databinding.ItemNoteBinding

class NoteAdapter(val iOnItem: IOnItem): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var list: MutableList<NoteModel> = ArrayList()

    fun setList(list: MutableList<NoteModel>){
        this.list = list
        notifyDataSetChanged()
    }

    fun getItem(pos:Int): NoteModel{
        return list[pos]
    }

    fun remove(pos: Int){
        list.removeAt(pos)
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(pos: Int) {
            itemView.context.getString(R.string.delete)
            binding.title.text = list[pos].title
            Glide.with(itemView)
                .load(list[pos].imageUri)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.image)
            binding.delete.setOnClickListener {
                iOnItem.delete(pos)
            }
            binding.share.setOnClickListener {
                iOnItem.share(pos)
            }
            binding.edit.setOnClickListener {
                iOnItem.edit(pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    interface IOnItem{
        fun delete(pos: Int)
        fun edit(pos: Int)
        fun share(pos: Int)
    }
}