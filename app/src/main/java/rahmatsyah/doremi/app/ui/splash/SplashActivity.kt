package rahmatsyah.doremi.app.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_splash.*
import rahmatsyah.doremi.app.R
import org.koin.android.viewmodel.ext.android.viewModel
import rahmatsyah.doremi.app.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {

    private val viewModel:SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        anitameLogo()

        viewModel.isFinish().observe(this, Observer {
            if (it){
                finish()
                startActivity(Intent(this,MainActivity::class.java))
            }
        })

    }

    private fun anitameLogo(){
        val rotateAnimation = RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.repeatMode = Animation.RESTART
        rotateAnimation.duration=1800
        splashLogo.animation = rotateAnimation
        rotateAnimation.start()
        splashLogo.startAnimation(rotateAnimation)
    }

}