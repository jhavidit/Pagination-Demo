package tech.jhavidit.payOAssignment.util

import androidx.recyclerview.widget.DiffUtil
import tech.jhavidit.payOAssignment.models.Data


class DiffUtilCallBack : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {

        return oldItem.avatar == newItem.avatar && oldItem.email == newItem.email && oldItem.firstName == newItem.firstName
                && oldItem.lastName == newItem.lastName && oldItem.id == newItem.id
    }

}
