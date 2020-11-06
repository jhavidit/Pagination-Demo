package tech.jhavidit.payOAssignment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import tech.jhavidit.payOAssignment.R
import tech.jhavidit.payOAssignment.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseFireStore: FirebaseFirestore
    private lateinit var userID : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        firebaseFireStore = FirebaseFirestore.getInstance()
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
        binding.btnSignUp.setOnClickListener {
            if (binding.firstNameInput.text.isNullOrBlank() || binding.lastNameInput.text.isNullOrBlank() || binding.emailInput.text.isNullOrBlank() || binding.passwordInput.text.isNullOrBlank() || binding.confirmPasswordInput.text.isNullOrBlank() || binding.addressInput.text.isNullOrBlank() || binding.phoneInput.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.coordinatorLayout,
                    "Fields can not be empty",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.passwordInput.text.toString().length < 6) {
                Snackbar.make(
                    binding.coordinatorLayout,
                    "Password must be greater than or equal to 6 characters",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.passwordInput.text.toString()
                    .compareTo(binding.confirmPasswordInput.text.toString()) != 0
            ) {
                Snackbar.make(
                    binding.coordinatorLayout,
                    "Password and Confirm Password do not match",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {

                signUpUser()
            }
        }

    }

    private fun signUpUser() {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailInput.text.toString()).matches()) {
            Snackbar.make(
                binding.coordinatorLayout,
                "Please enter valid email",
                Snackbar.LENGTH_SHORT
            ).show()
            binding.emailInput.requestFocus()
            return
        }


            auth.createUserWithEmailAndPassword(
                binding.emailInput.text.toString(),
                binding.passwordInput.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        userID = auth.currentUser!!.uid
                        val documentReference= firebaseFireStore.collection("users").document(userID)
                        val userData = HashMap<String,String>()
                        userData.put("firstName",binding.firstNameInput.text.toString().trim())
                        userData.put("lastName",binding.lastNameInput.text.toString().trim())
                        userData.put("emailID",binding.emailInput.text.toString().trim())
                        userData.put("phone",binding.phoneInput.text.toString().trim())
                        userData.put("address",binding.addressInput.text.toString().trim())
                        documentReference.set(userData).addOnSuccessListener {
                            Log.d("success","Success")
                        }

                        startActivity(
                            Intent(
                                this,
                                DashboardActivity::class.java
                            )
                        )
                        finish()
                    } else {
                        Log.d("error",task.toString())

                        Snackbar.make(
                            binding.coordinatorLayout,
                            "SignUp Failed",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }


                }
        }
    }
