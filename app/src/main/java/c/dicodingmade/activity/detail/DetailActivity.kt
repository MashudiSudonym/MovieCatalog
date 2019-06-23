package c.dicodingmade.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import c.dicodingmade.R

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}
