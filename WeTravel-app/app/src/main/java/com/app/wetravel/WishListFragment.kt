import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.app.wetravel.HouseDetailActivity
import com.app.wetravel.R
import com.app.wetravel.models.House
import com.frogobox.recycler.core.FrogoRecyclerNotifyListener
import com.frogobox.recycler.core.IFrogoViewAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException


    val userid = "22"

class WishListFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_recycler_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (userid == "unknown"){
            return
        }
        fetchData(userid)
    }

    private fun fetchData(userid: String) {
        val client = OkHttpClient()

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("39.107.60.28")
            .port(8014)
            .addPathSegment("selectAccommodationByCollection")
            .addQueryParameter("userId", userid)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 处理请求失败情况
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = response.body?.string()
                    val houses = parseJson(json)
                    updateUI(houses)
                } else {
                    // 处理请求失败情况
                    throw IOException("Unexpected code $response")
                }
            }
        })
    }

    fun parseJson(json: String?): List<House> {
        // 创建一个Gson实例
        val gson = Gson()

        // 创建一个泛型类型
        val type = object : TypeToken<List<House>>() {}.type

        // 直接将JSON字符串转换为List<House>类型
        return gson.fromJson(json, type)
    }

    private fun updateUI(houses: List<House>) {
        activity?.runOnUiThread {
            val frogoRv =
                view?.findViewById<com.frogobox.recycler.widget.FrogoRecyclerView>(R.id.wishlist_rv)
            frogoRv?.injector<House>()
                ?.addData(houses)
                ?.addCustomView(R.layout.frogo_rv_list_type_5) // 这里是一个自定义的布局文件，用于显示每个 House 对象的信息，你需要自己创建或修改
                ?.addEmptyView(null)
                ?.addCallback(object : IFrogoViewAdapter<House> {
                    override fun setupInitComponent(
                        view: View,
                        data: House,
                        position: Int,
                        notifyListener: FrogoRecyclerNotifyListener<House>
                    ) {
                        // 在这里绑定数据和视图
                        view.findViewById<TextView>(R.id.tv_wish_name).text = data.roomName // 设置民宿名称
                        view.findViewById<TextView>(R.id.tv_wish_address).text = data.location // 设置民宿地址
                        val prefix = "http://39.107.60.28:8014"

                        Picasso.get().load(  prefix + data.imageUrl)
                            .into(view.findViewById<ImageView>(R.id.iv_wish_image))// 加载民宿图片


                    }

                    override fun onItemClicked(
                        view: View,
                        data: House,
                        position: Int,
                        notifyListener: FrogoRecyclerNotifyListener<House>
                    ) {
                        val context = view.context

                        // 创建一个 Intent，并将数据作为 extra 传递给新的 Activity
                        val intent = Intent(context, HouseDetailActivity::class.java)
                        intent.putExtra("HOUSE_DATA", Gson().toJson(data))

                        // 启动新的 Activity
                        context.startActivity(intent)
                    }

                    override fun onItemLongClicked(
                        view: View,
                        data: House,
                        position: Int,
                        notifyListener: FrogoRecyclerNotifyListener<House>
                    ) {
                        // 在这里处理长按事件，你可以使用 view 或 data 或 position 或 notifyListener 参数
                        Toast.makeText(
                            context,
                            "You long clicked on ${data.roomName}",
                            Toast.LENGTH_SHORT
                        ).show() // 显示一个提示信息
                    }
                })
                ?.createLayoutGrid(2)
                ?.build()
        }

    }
}

