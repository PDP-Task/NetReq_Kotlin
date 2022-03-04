package uz.context.androidnetworking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.context.androidnetworking.R
import uz.context.androidnetworking.activity.MainActivity
import uz.context.androidnetworking.model.Poster

class PosterAdapter(
    var activity: MainActivity,
    private var items: ArrayList<Poster>
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val poster = items[position]

        if (holder is PosterViewHolder) {
            holder.apply {
                llPoster.setOnLongClickListener {
                    activity.dialogPoster(poster)
                    false
                }
                tvBody.text = poster.body.uppercase()
                tvTitle.text = poster.title
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    inner class PosterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.textTitle)
        val tvBody: TextView = view.findViewById(R.id.textBody)
        val llPoster: LinearLayout = view.findViewById(R.id.ll_poster)
    }
}