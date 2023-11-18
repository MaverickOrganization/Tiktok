package com.aking.tiktok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.aking.tiktok.databinding.ActivityHttClientTestBinding
import com.aking.tiktop.httpclient.ext.loadHttp
import com.aking.tiktop.httpclient.http.ApiClient

class HttpClientTest : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHttClientTestBinding

    private val TAG = "HttpClientTest";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHttClientTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        lifecycleScope.loadHttp(
            request = { ApiClient.userApi.login("16670267862", "123456") },
            resp = {
                binding.btn.post {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG);
                }
            }
        )
    }
}