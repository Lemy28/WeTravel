package com.app.wetravel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.wetravel.models.House
import com.frogobox.recycler.core.FrogoRecyclerNotifyListener
import com.frogobox.recycler.core.IFrogoViewAdapter
import com.squareup.picasso.Picasso

class ContentFragment: Fragment() {

    fun listOfHouses(): List<House> {
        // 这里是一些示例数据，可以访问服务器获取数据
        return listOf(
            House("1", "Cozy Cottage", "123 Main Street", 100.0, "https://th.bing.com/th/id/R.cbe0c23764f53a7b1d897ba5bdf38b66?rik=SxvzKUHNEDJd%2fA&riu=http%3a%2f%2fimg.pconline.com.cn%2fimages%2fupload%2fupc%2ftx%2fitbbs%2f1408%2f09%2fc4%2f37236948_1407569702321.jpg&ehk=lHobZUHLVrPVxS0LAQD2RskCdDqNNddnMi1gsggjJPo%3d&risl=&pid=ImgRaw&r=0"),
            House("2", "Modern Loft", "456 High Street", 150.0, "https://example.com/modern.jpg"),
            House("3", "Rustic Cabin", "789 Forest Road", 80.0, "https://example.com/rustic.jpg"),
            House("4", "Cozy Cottage", "123 Main Street", 100.0, "https://example.com/cozy.jpg"),
        House("5", "Modern Loft", "456 High Street", 150.0, "https://example.com/modern.jpg"),
        House("6", "Rustic Cabin", "789 Forest Road", 80.0, "https://example.com/rustic.jpg")
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_grid,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 获取FrogoRecyclerView的实例
        val frogoRv = view.findViewById<com.frogobox.recycler.widget.FrogoRecyclerView>(R.id.house_rv)
        // 设置数据源、布局、回调等参数
        frogoRv.injector<House>()
            .addData(listOfHouses())
            .addCustomView(R.layout.frogo_rv_grid_type_2) // 这里是一个自定义的布局文件，用于显示每个House对象的信息，你需要自己创建或修改
            .addEmptyView(null)
            .addCallback(object : IFrogoViewAdapter<House> {
                override fun setupInitComponent(view: View, data: House, position: Int,notifyListener: FrogoRecyclerNotifyListener<House>) {
                    // 在这里绑定数据和视图
                    view.findViewById<TextView>(R.id.tv_house_name).text = data.name // 设置民宿名称
                    view.findViewById<TextView>(R.id.tv_house_address).text = data.address // 设置民宿地址
                    view.findViewById<TextView>(R.id.tv_house_price).text = "$${data.price}" // 设置民宿价格
                    Picasso.get().load(data.image).into(view.findViewById<ImageView>(R.id.iv_house_image))// 加载民宿图片
                }

                override fun onItemClicked(
                    view: View,
                    data: House,
                    position: Int,
                    notifyListener: FrogoRecyclerNotifyListener<House>
                ) {
                    // 在这里处理点击事件，你可以使用view或data或position或notifyListener参数
                    Toast.makeText(context, "You clicked on ${data.name}", Toast.LENGTH_SHORT).show() //显示一个提示信息
                }

                override fun onItemLongClicked(
                    view: View,
                    data: House,
                    position: Int,
                    notifyListener: FrogoRecyclerNotifyListener<House>
                ) {
                    // 在这里处理长按事件，你可以使用view或data或position或notifyListener参数
                    Toast.makeText(context, "You long clicked on ${data.name}", Toast.LENGTH_SHORT).show() // 显示一个提示信息
                }
            })
            .createLayoutGrid(2)
            .build()
    }
}