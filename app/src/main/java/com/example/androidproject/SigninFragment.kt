package com.example.androidproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.androidproject.databinding.FragmentSigninBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class SigninFragment : Fragment() {


    var binding: FragmentSigninBinding ?= null

    lateinit var mAuth : FirebaseAuth

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSigninBinding.inflate(inflater)
        return binding?.root

        mAuth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnPasswordedit?.setOnClickListener{
            findNavController().navigate(R.id.action_signinFragment2_to_loginFragment)

            val email = binding?.editTextText2?.text.toString().trim()
            val password = binding?.editTextText3?.text.toString().trim()

            signup(email, password)
        }

    }
    private fun signup(email: String, password: String) {
        val addOnCompleteListener = mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"회원가입성공",Toast.LENGTH_SHORT).show()


                } else {
                    // If sign in fails, display a message to the user.



                }
            }
    }

}