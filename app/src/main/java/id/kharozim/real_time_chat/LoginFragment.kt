package id.kharozim.real_time_chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.kharozim.real_time_chat.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val auth by lazy { Firebase.auth }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {

            btSignup.setOnClickListener { findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment()) }
            btLogin.setOnClickListener {
                if (tieEmail.text.isNullOrEmpty() && tiePassword.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(),"Email dan Password tidak boleh kosong",Toast.LENGTH_SHORT).show()
                } else {
                    showLoading(true)
                    auth.signInWithEmailAndPassword(
                        tieEmail.text.toString(),
                        tiePassword.text.toString()
                    )
                        .addOnSuccessListener {
                            if (it.user?.isEmailVerified == true) {
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Email Belum di ferivikasi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            showLoading(false)
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
        binding.btLogin.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }
}