package dav.project.chirp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import dav.project.chirp.R
import dav.project.chirp.databinding.ActivityResetPassBinding

class ResetPassActivity : AppCompatActivity() {




    private lateinit var binding: ActivityResetPassBinding
    private lateinit var firebaseAuth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityResetPassBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        firebaseAuth = FirebaseAuth.getInstance()


        binding.btnReset.setOnClickListener{

            val resetEmail = binding.etResetEmail.text.toString()

            if (resetEmail.isNotEmpty()){

                firebaseAuth.sendPasswordResetEmail(resetEmail).addOnCompleteListener(){
                    if (it.isSuccessful){
                        val intent = Intent(this@ResetPassActivity, SignInActivity::class.java )
                        startActivity(intent)
                        Toast.makeText(this, "Check your inbox", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }else{
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
            }

        }

        val tvResetSignIn: TextView = findViewById(R.id.tvResetSignIn)
        val tvResetSignUp: TextView = findViewById(R.id.tvResetSignUp)

        tvResetSignIn.setOnClickListener{
            startActivity(Intent(this@ResetPassActivity, SignInActivity::class.java))
        }

        tvResetSignUp.setOnClickListener{
            startActivity(Intent(this@ResetPassActivity, SignUpActivity::class.java))
        }



        val resetPassQuotes = listOf("Entering the right password is easier than remembering your anniversary. Maybe.",
            "I have a bad feeling about this... Please enter your new password.",
            "Forgot your password again? We're starting to think you do this on purpose...",
            "Live long and prosper... with a strong password.",
            "Do you know nothing, Jon Snow? You know nothing of passwords!",
            "You've been logged out. Your memory must be RAM-donly!",
            "Don't worry, be happy! Resetting your password is a breeze.",
            "404: Password Not Found. Please enter a new one.",
            "Ugh, the forgetful emoji strikes again! Reset your password here.",
            "Can't remember? No worries, we've all been there. Here's your password reset.",
            "Seeing double? Don't worry, it's not the app. Reset your password to clear things up. ")

        val randomList = resetPassQuotes.random()


        val tvBannerTxt: TextView = findViewById(R.id.tvBannerTxt)

        tvBannerTxt.text = randomList


    }

    fun openFacebookPage(view: View?) {
        val facebookUrl =
            "https://www.facebook.com/profile.php?id=61557374283862"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(facebookUrl))
        startActivity(intent)
    }
}