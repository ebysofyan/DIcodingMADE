package ebysofyan.app.made.submission.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by @ebysofyan on 7/2/19
 */
abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {
    abstract fun getLayoutResourceId(): Int

    abstract fun onBindItem(view: View, data: T, position: Int)

    private val list = mutableListOf<T>()

    private fun getListData(): MutableList<T> = list
    private var isRecyclable = false

    fun addItem(newT: T) {
        getListData().add(newT)
        notifyDataSetChanged()
    }

    fun addItems(newList: MutableList<T>) {
        getListData().addAll(newList)
        notifyDataSetChanged()
    }

    fun getItems() = getListData()

    fun removeItem(exsT: T) {
        getListData().remove(exsT)
        notifyDataSetChanged()
    }

    fun setIsRecyclable(value: Boolean) {
        isRecyclable = value
    }

    fun clear() {
        getListData().clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutResourceId(), parent, false)
        return BaseViewHolder(view)
    }

    override fun getItemCount(): Int = getListData().size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItem(holder.itemView, getListData()[position], position)
        holder.setIsRecyclable(isRecyclable)
    }
}

class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)