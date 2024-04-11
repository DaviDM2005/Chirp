package dav.project.chirp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import dav.project.chirp.R
import dav.project.chirp.databinding.ActivitySignInBinding
import dav.project.chirp.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        enableEdgeToEdge()

        val tvAlertMessage: TextView = binding.tvAlertMessage

        binding.btnSignUp.setOnClickListener {


            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPass.text.toString()
            val confirmPassword = binding.etConfirmPass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && username.isNotEmpty()) {
                if (password.equals(confirmPassword)) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { registrationTask ->

                            val user = FirebaseAuth.getInstance().currentUser

                            if (registrationTask.isSuccessful) {

                                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                                startActivity(intent)

                                Toast.makeText(
                                    this,
                                    "Please verify your Email",
                                    Toast.LENGTH_SHORT
                                ).show()


                                firebaseAuth.currentUser?.sendEmailVerification()

                            } else {
                                tvAlertMessage.text = registrationTask.exception?.message
                                Handler().postDelayed({
                                    tvAlertMessage.text = ""
                                }, 3500)
                            }
                        }

                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvSignIn: TextView = findViewById(R.id.tvSignIn)
        tvSignIn.setOnClickListener{
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java ))
        }
    }



    fun openFacebookPage(view: View?) {
        val facebookUrl =
            "https://www.facebook.com/profile.php?id=61557374283862"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(facebookUrl))
        startActivity(intent)
    }
}