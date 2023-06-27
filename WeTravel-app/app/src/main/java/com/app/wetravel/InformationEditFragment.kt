import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.app.wetravel.R
import com.app.wetravel.databinding.PersonalinformationeditBinding
import com.app.wetravel.databinding.SpinnerDropdownBinding

class InformationEditFragment: Fragment() {
    private var _binding: PersonalinformationeditBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= PersonalinformationeditBinding.inflate(inflater,container,false)
        val view = binding.root
        val genderSpinner=binding.genderSpinner
        val genders=arrayOf("男","女","其他")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderSpinner.adapter = adapter



        binding.imageButton16.setOnClickListener(){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return view
    }
}