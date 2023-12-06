package com.example.androidproject

import android.content.pm.PackageManager.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.androidproject.databinding.FragmentRegistrationBinding
import com.example.androidproject.dataclass.Item
import com.example.androidproject.viewmodel.HistoryViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class registrationFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private val viewModel: HistoryViewModel by activityViewModels()
    private var binding: FragmentRegistrationBinding? = null
    private var category: String? = null

    private lateinit var firebaseRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    private var uri: Uri? = null
    private val userId = "user1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)

        firebaseRef = FirebaseDatabase.getInstance().getReference("Item")
        storageRef = FirebaseStorage.getInstance().getReference("Images")

        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding?.imgProduct?.setImageURI(it)
            if(it != null){
                uri = it
            }
        }
        binding?.btnPickImg?.setOnClickListener {
            pickImage.launch("image/*")
        }


        //카테고리 설정
        binding?.btnType?.setOnClickListener {
            showPopup(binding!!.btnType)
        }

        // 저장하면
        binding?.btnSave?.setOnClickListener {
            val date = getTime()

            val title = binding?.edtTitle?.text.toString()
            val price = binding?.edtPrice?.text.toString()
            val content = binding?.edtContent?.text.toString()

            val productId = firebaseRef.push().key!!

            uri?.let{
                storageRef.child(userId).child(productId).putFile(it).addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { url ->
                        val imgUrl = url.toString()

                        val prod = Item(
                            userId, productId, title, price, category.toString(), imgUrl, content, " ", date,
                            selled = false,
                            like = false
                        )
                        viewModel.registerProduct(prod)
                    }
                }
            }
            findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
        }
    }

    // 카테고리 버튼 팝업창 띄우기
    private fun showPopup(v: View) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(R.menu.menu_category, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        category = when (item?.itemId) {
            R.id.homeAppliances -> "가전제품"
            R.id.instrument -> "악기"
            R.id.electronics -> "전자기기"
            else -> null
        }
        return item != null
    }

    // 시간 구하기
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTime(): String {
        val format = SimpleDateFormat("yyyy년 MM월 dd일 kk시 mm분", Locale.KOREA)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(Date().time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}