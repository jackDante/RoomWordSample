package edu.self.roomwordsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class WordListAdapter internal constructor(context: Context) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>() // Cached copy of words

    private lateinit var wordViewModel: WordViewModel

    private lateinit var viewModelStoreOwner: ViewModelStoreOwner

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    //in order to obtain the Owner from MainActivity (jack)
    internal fun setviewModelStoreOwner(viewModelStoreOwner: ViewModelStoreOwner) {
        this.viewModelStoreOwner = viewModelStoreOwner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        itemView.setOnLongClickListener{
            Toast.makeText(this.inflater.context, "Delete task: " + itemView.textView.text.toString(), Toast.LENGTH_SHORT).show()
            val word = Word(itemView.textView.text.toString())
            wordViewModel = ViewModelProvider(viewModelStoreOwner).get(WordViewModel::class.java)
            wordViewModel.delete(word)
            notifyDataSetChanged()
            return@setOnLongClickListener true
        }

        return WordViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = current.word
    }

    internal fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size


}

