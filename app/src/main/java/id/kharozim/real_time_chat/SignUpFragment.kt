package id.kharozim.real_time_chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.kharozim.real_time_chat.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val auth by lazy { Firebase.auth }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false).apply {

            btSignup.setOnClickListener {
                if (tieEmail.text.isNullOrEmpty() && tiePassword.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Email dan Password tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showLoading(true)
                    auth.createUserWithEmailAndPassword(
                        tieEmail.text.toString(),
                        tiePassword.text.toString()
                    )
                        .addOnSuccessListener {
                            showLoading(false)
                            it.user?.sendEmailVerification()
                            requireActivity().onBackPressed()
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                            Toast.makeText(
                                requireContext(),
                                it.message ?: "Opps something wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoading(false)
                        }
                }
            }

        }
        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.btSignup.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }
}