import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.wetravel.R
import com.app.wetravel.databinding.OrderlistrecycleviewBinding

class OrderListAdapter :RecyclerView.Adapter<OrderListAdapter.ImageViewHolder>() {

    // 图像资源列表
    private val imageList: List<Int> = listOf(
        R.drawable.room,
        R.drawable.room,
        R.drawable.room
        // 添加更多图像资源
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = OrderlistrecycleviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageRes = imageList[position]
        holder.binding.imageView.setImageResource(imageRes)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageViewHolder(val binding: OrderlistrecycleviewBinding) : RecyclerView.ViewHolder(binding.root)
}
