import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.app.wetravel.OkHttpLogin
import com.app.wetravel.PersonalFragment
import com.app.wetravel.R
import com.app.wetravel.User
import com.app.wetravel.databinding.PersonalinformationBinding
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException

class InformationFragment: Fragment() {
    private var _binding: PersonalinformationBinding?=null
    private val binding get()=_binding!!
    private var isEditMode = false
    companion object {
        const val TAG = "Information"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= PersonalinformationBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.imageButton16.setOnClickListener(){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.textView22.setOnClickListener(){
            if(isEditMode){
                val user=UserManager.getUser()
                val phoneNumber=binding.editTextPhoneEdit.text.toString()
                val username=binding.editTextUsernameEdit.text.toString()
                val gender=binding.genderSpinnerEdit.selectedItem.toString()
                val date=binding.editTextDateEdit.text.toString()
                if(user?.userId!=null && user?.password!=null) {
                    saveEditedData(user?.userId,phoneNumber, user?.password!!,username,gender,date)
                }
            }
            else {
                isEditMode = true
                setEditMode(isEditMode)
            }
        }
        if(UserManager.isLoggedIn){
            val user=UserManager.getUser()
            binding.editTextUsername.setText(user?.username)
            binding.editTextUsernameEdit.setText(user?.username)
            binding.editTextDate.setText(user?.birthday)
            binding.editTextDateEdit.setText(user?.birthday)
            binding.editTextPhone.setText(user?.phoneNumber)
            binding.editTextPhoneEdit.setText(user?.phoneNumber)
            binding.genderSpinner.setText(user?.gender)
        }
        return view
    }
    private fun replaceFragment(fragment:Fragment){
        val fragmentManager=activity?.supportFragmentManager!!
        val transaction=fragmentManager.beginTransaction()
        transaction.replace(R.id.contentfragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun setEditMode(isEditMode: Boolean) {
        if (isEditMode) {
            val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item)
            adapter.add("男")
            adapter.add("女")
            adapter.add("其他")

            // Set the dropdown layout style
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // 设置为可编辑状态
            binding.editTextUsername.visibility = View.GONE
            binding.editTextUsernameEdit.visibility = View.VISIBLE
            binding.editTextDate.visibility=View.GONE
            binding.editTextDateEdit.visibility=View.VISIBLE
            binding.genderSpinner.visibility=View.GONE
            binding.genderSpinnerEdit.visibility=View.VISIBLE
            binding.genderSpinnerEdit.adapter = adapter
            binding.editTextPhone.visibility=View.GONE
            binding.editTextPhoneEdit.visibility=View.VISIBLE
            binding.editTextTextEmailAddress.visibility=View.GONE
            binding.editTextTextEmailAddressEdit.visibility=View.VISIBLE
            binding.editTextTextPostalAddress.visibility=View.GONE
            binding.editTextTextPostalAddressEdit.visibility=View.VISIBLE

            // 可以根据需要设置其他组件的可编辑状态
            // binding.textViewPhoneNumber.isEnabled = true
            // binding.textViewEmail.isEnabled = true
            binding.textView22.text = "保存"
        } else {
            // 设置为不可编辑状态
            binding.editTextUsername.visibility = View.VISIBLE
            binding.editTextUsernameEdit.visibility = View.GONE
            binding.editTextDate.visibility=View.VISIBLE
            binding.editTextDateEdit.visibility=View.GONE
            binding.genderSpinner.visibility=View.VISIBLE
            binding.genderSpinnerEdit.visibility=View.GONE
            binding.editTextPhone.visibility=View.VISIBLE
            binding.editTextPhoneEdit.visibility=View.GONE
            binding.editTextTextEmailAddress.visibility=View.VISIBLE
            binding.editTextTextEmailAddressEdit.visibility=View.GONE
            binding.editTextTextPostalAddress.visibility=View.VISIBLE
            binding.editTextTextPostalAddressEdit.visibility=View.GONE


            binding.textView22.text = "编辑"
        }
    }
    private fun saveEditedData(userId: String, phoneNumber: String, password:String,username:String,gender:String,date:String) {
        val client = OkHttpClient()

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("39.107.60.28")
            .port(8014)
            .addPathSegment("updateUser")
            .addQueryParameter("userId", userId)
            .build()

        val requestBody: RequestBody = FormBody.Builder()
            .add("userId", userId)
            .add("phoneNumber", phoneNumber)
            .add("password", password)
            .add("username",username)
            .add("gender",gender)
            .add("birthday",date)
            // 添加其他需要提交的编辑后的数据
            .build()

        val request = Request.Builder()
            .url(url)  // 替换为你的服务器 URL
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 请求失败的处理
                activity?.runOnUiThread {
                    // 在主线程中更新 UI
                    // 这里可以显示错误信息或执行其他操作
                    println("请求失败: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { responseBody ->
                    // 处理响应数据
                    if (response.isSuccessful) {
                        activity?.runOnUiThread {
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                    } else {
                        activity?.runOnUiThread {
                            // 在主线程中更新 UI
                            // 这里可以显示错误信息或执行其他操作
                            println("请求失败: ${response.code}")
                        }
                    }
                }
            }

        })
    }

}