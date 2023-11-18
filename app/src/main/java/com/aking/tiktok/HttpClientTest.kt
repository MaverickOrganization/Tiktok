package com.aking.tiktok

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aking.tiktok.databinding.ActivityHttClientTestBinding
import com.aking.tiktop.httpclient.ext.loadHttp
import com.aking.tiktop.httpclient.http.ApiClient
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnConfirmListener

class HttpClientTest : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHttClientTestBinding

    private var REQUEST_SELECT_VIDEO = 1001

    val filePermission = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_AUDIO
    )

    private val TAG = "HttpClientTest";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHttClientTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn.setOnClickListener(this)
        binding.uploadFile.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.btn -> {
                lifecycleScope.loadHttp(
                    request = { ApiClient.userApi.login("16670267862", "123456") },
                    resp = {
                        binding.btn.post {
                            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG);
                        }
                    }
                )
            }
            R.id.upload_file -> {
                val permissionName = this.getString(R.string.common_storage);
                if (!XXPermissions.isGranted(this, filePermission)) {
                    val des = this.getString(R.string.common_permission_explain_storage)
                    XPopup.Builder(this)
                        .asConfirm(this.getString(R.string.common_permission_explain_title, permissionName), des, "",
                            this.getString(R.string.common_i_know),
                            OnConfirmListener {
                                XXPermissions.with(this).permission(filePermission)
                                    .request(object : OnPermissionCallback {
                                        override fun onGranted(permissions: List<String>, allGranted: Boolean) {
                                            if (allGranted) {
                                                val intent = Intent();
                                                intent.setType("video/*");
                                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                                startActivityForResult(Intent.createChooser(intent, "选择视频"), this@HttpClientTest.REQUEST_SELECT_VIDEO);
                                            } else {

                                            }
                                        }
                                        override fun onDenied(permissions: List<String>, doNotAskAgain: Boolean) {
                                            if (doNotAskAgain) {
                                                XPopup.Builder(this@HttpClientTest)
                                                    .asConfirm(
                                                        "", this@HttpClientTest.getString(R.string.common_permission_reject_format, permissionName),
                                                        this@HttpClientTest.getString(R.string.common_reject),
                                                        this@HttpClientTest.getString(R.string.common_all_right),
                                                        { XXPermissions.startPermissionActivity(this@HttpClientTest, permissions) },
                                                        {},
                                                        false, R.layout.popup_common_confirm_sdk
                                                    ).show()
                                            } else {

                                            }
                                        }
                                    })
                            }, null , false, R.layout.popup_common_confirm_sdk
                        )
                        .show()
                }
            }
        }

    }
}