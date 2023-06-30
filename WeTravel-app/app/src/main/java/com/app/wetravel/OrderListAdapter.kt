import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.wetravel.InOrders
import com.app.wetravel.PersonalFragment
import com.app.wetravel.R
import com.app.wetravel.databinding.OrderlistrecycleviewBinding
import com.squareup.picasso.Picasso


class OrderListAdapter :RecyclerView.Adapter<OrderListAdapter.ImageViewHolder>() {

    // 图像资源列表
    private var orderList: List<InOrders> = emptyList() // Initialize the list with an empty list

    fun setOrderList(orderList: List<InOrders>) {
        this.orderList = orderList // Update the orderList with the new list
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = OrderlistrecycleviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val order = orderList[position]
        holder.binding.textView21.text=order.roomName
        holder.binding.editTextDate2.text=order.time.toString()
        holder.binding.textView27.text=order.price.toString()
        holder.binding.textView24.text=order.location

        Picasso.get()
            .load("http://39.107.60.28:8014${order.getImageUrl()}")
            .into(holder.binding.imageView)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class ImageViewHolder(val binding: OrderlistrecycleviewBinding) : RecyclerView.ViewHolder(binding.root)

}


