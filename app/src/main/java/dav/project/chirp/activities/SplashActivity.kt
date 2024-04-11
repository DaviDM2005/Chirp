package dav.project.chirp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import dav.project.chirp.R

class SplashActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)


        mAuth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_splash)

        val isLogin: Boolean = mAuth.currentUser != null

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
//            if (isLogin)
//                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//            else
//                startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
//            finish()
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 1500)


    }


    // Function to open Facebook page
    fun openFacebookPage(view: View) {
        val facebookUrl = "https://www.facebook.com/profile.php?id=61557374283862"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
        startActivity(intent)
    }
}
