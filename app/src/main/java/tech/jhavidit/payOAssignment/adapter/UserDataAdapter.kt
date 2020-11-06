package tech.jhavidit.payOAssignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.user_item.view.*
import tech.jhavidit.payOAssignment.R
import tech.jhavidit.payOAssignment.models.Data
import tech.jhavidit.payOAssignment.util.DiffUtilCallBack

class UserDataAdapter(private val context: Context) :
    PagedListAdapter<Data, UserDataAdapter.MyViewHolder>((DiffUtilCallBack())) {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userPhoto: ImageView = itemView.user_photo
        val userFirstName: TextView = itemView.user_first_name
        val userLastName: TextView = itemView.user_last_name
        val userEmail: TextView = itemView.user_email

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return MyViewHolder(
            view
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.userEmail.text = item.email
            holder.userFirstName.text = item.firstName
            holder.userLastName.text = item.lastName
            Glide.with(context)
                .load(item.avatar)
                .into(holder.userPhoto)
        } else {
            Toast.makeText(context, "Item is null", Toast.LENGTH_LONG).show();
        }

    }
}