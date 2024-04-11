package dav.project.chirp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dav.project.chirp.R
import dav.project.chirp.databinding.ActivitySignInBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseUser

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val tvAlertMessage: TextView = findViewById(R.id.tvAlertMessage)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            val user: FirebaseUser? = firebaseAuth.currentUser
                            if (user != null && user.isEmailVerified) {
                                navigateToMainActivity()
                            } else {
                                tvAlertMessage.text = "Please verify your email"
                                Handler().postDelayed({
                                    tvAlertMessage.text = ""
                                }, 3500)
                            }
                        } else {
                            tvAlertMessage.text = signInTask.exception?.message
                            Handler().postDelayed({
                                tvAlertMessage.text = ""
                            }, 3500)
                        }
                    }
            } else {
                tvAlertMessage.text = "Empty Fields are not allowed!"
                Handler().postDelayed({
                    tvAlertMessage.text = ""
                }, 3500)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvSignup: TextView = findViewById(R.id.tvSignUp)
        tvSignup.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        val tvForgotPass: TextView = findViewById(R.id.tvForgotPass)
        tvForgotPass.setOnClickListener {
            val intent = Intent(this@SignInActivity, ResetPassActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isFirstTimeSignIn(userId: String): Boolean {
        val sharedPreferences = getSharedPreferences("SignInPrefs", Context.MODE_PRIVATE)
        return !sharedPreferences.contains(userId)
    }


    private fun navigateToMainActivity() {
        val intent = Intent(this@SignInActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
