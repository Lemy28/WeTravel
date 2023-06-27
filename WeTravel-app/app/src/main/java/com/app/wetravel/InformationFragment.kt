import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.wetravel.R
import com.app.wetravel.databinding.LoginBinding
import com.app.wetravel.databinding.PersonalinformationBinding

class InformationFragment: Fragment() {
    private var _binding: PersonalinformationBinding?=null
    private val binding get()=_binding!!
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
            replaceFragment(InformationEditFragment())
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

}