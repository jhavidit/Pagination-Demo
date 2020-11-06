package tech.jhavidit.payOAssignment.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import tech.jhavidit.payOAssignment.R
import tech.jhavidit.payOAssignment.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            doLogin()

        }
    }

    private fun doLogin() {
        if (binding.usernameInput.text.toString().isEmpty()) {
            binding.usernameInput.error = "Please Enter Email"
            binding.usernameInput.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.usernameInput.text.toString()).matches()) {
            binding.usernameInput.error = "Please Enter Valid Email"
            binding.usernameInput.requestFocus()
            return

        }

        if (binding.usernamePassword.text.toString().isEmpty()) {
            binding.usernamePassword.error = "Please Enter password"
            binding.usernamePassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(
            binding.usernameInput.text.toString(),
            binding.usernamePassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    updateUI(user)

                } else {

                    updateUI(null)
                    Snackbar.make(binding.coordinatorLayout, "Login Failed", Snackbar.LENGTH_SHORT).show()


                }

            }


    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {



        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)

    }
}