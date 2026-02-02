package com.example.newsaggregatorapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val articles: List<Article>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.articleTitle)
        val description: TextView = view.findViewById(R.id.articleDescription)
        val image: ImageView = view.findViewById(R.id.articleImage)
        val imgShare: ImageView = view.findViewById(R.id.imgShare)
        val btnSave: CheckBox = view.findViewById(R.id.btnSave)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.description.text = article.description
        Glide.with(holder.itemView.context).load(article.image).into(holder.image)

        val prefs = holder.itemView.context.getSharedPreferences("SavedNews", Context.MODE_PRIVATE)

        val savedSet = prefs.getStringSet("saved_titles", mutableSetOf()) ?: mutableSetOf()
        holder.btnSave.isChecked = savedSet.contains(article.title)

        holder.imgShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "${article.title}\nRead more: ${article.url}")
            }
            holder.itemView.context.startActivity(Intent.createChooser(shareIntent, "Share News"))
        }

        holder.btnSave.setOnCheckedChangeListener { _, isChecked ->
            val currentSaved = prefs.getStringSet("saved_titles", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

            if (isChecked) {
                article.title?.let { currentSaved.add(it) }
                Toast.makeText(holder.itemView.context, "Saved!", Toast.LENGTH_SHORT).show()
            } else {
                currentSaved.remove(article.title)
                Toast.makeText(holder.itemView.context, "Removed!", Toast.LENGTH_SHORT).show()
            }

            prefs.edit().putStringSet("saved_titles", currentSaved).apply()
        }
    }

    override fun getItemCount() = articles.size
}