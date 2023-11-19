package com.aking.tiktok

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aking.tiktok.databinding.ActivityHttClientTestBinding
import com.aking.tiktop.httpclient.ext.loadHttp
import com.aking.tiktop.httpclient.http.ApiClient
import com.aking.tiktop.httpclient.upload.UploadUtil
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnConfirmListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class HttpClientTest : AppCompatActivity(), View.OnClickListener, UploadUtil.UploadListener {

    private lateinit var binding: ActivityHttClientTestBinding

    private var REQUEST_SELECT_VIDEO = 1001

    private val filePermission = arrayOf(
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
        UploadUtil.instance!!.registerUploadListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        UploadUtil.instance!!.unRegisterUploadListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
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
                        .asConfirm(
                            this.getString(
                                R.string.common_permission_explain_title,
                                permissionName
                            ), des, "",
                            this.getString(R.string.common_i_know),
                            OnConfirmListener {
                                XXPermissions.with(this).permission(filePermission)
                                    .request(object : OnPermissionCallback {
                                        override fun onGranted(
                                            permissions: List<String>,
                                            allGranted: Boolean
                                        ) {
                                            if (allGranted) {
                                                val intent = Intent();
                                                intent.type = "video/*"
                                                intent.action = Intent.ACTION_GET_CONTENT
                                                startActivityForResult(
                                                    Intent.createChooser(
                                                        intent,
                                                        "选择视频"
                                                    ), this@HttpClientTest.REQUEST_SELECT_VIDEO
                                                )
                                            } else {

                                            }
                                        }

                                        override fun onDenied(
                                            permissions: List<String>,
                                            doNotAskAgain: Boolean
                                        ) {
                                            if (doNotAskAgain) {
                                                XPopup.Builder(this@HttpClientTest)
                                                    .asConfirm(
                                                        "",
                                                        this@HttpClientTest.getString(
                                                            R.string.common_permission_reject_format,
                                                            permissionName
                                                        ),
                                                        this@HttpClientTest.getString(R.string.common_reject),
                                                        this@HttpClientTest.getString(R.string.common_all_right),
                                                        {
                                                            XXPermissions.startPermissionActivity(
                                                                this@HttpClientTest,
                                                                permissions
                                                            )
                                                        },
                                                        {},
                                                        false,
                                                        R.layout.popup_common_confirm_sdk
                                                    ).show()
                                            } else {

                                            }
                                        }
                                    })
                            }, null, false, R.layout.popup_common_confirm_sdk
                        )
                        .show()
                } else {
                    val intent = Intent();
                    intent.type = "video/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "选择视频"),
                        this@HttpClientTest.REQUEST_SELECT_VIDEO
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_VIDEO && resultCode == RESULT_OK) {
            if (data != null && data.data != null) {
                val uri = data.data;
                if (uri != null) {
                    val realFilePath = getFileAbsolutePath(this, uri)
                    Thread(Runnable {
                        UploadUtil.instance!!.uploadFile(realFilePath!!)
                    }).start()
                };
            }
        }
    }

    override fun onProgress(progress: Long, total: Long) {
        binding.uploadSeekbar.post {
            binding.uploadSeekbar.progress =
                ((progress.toDouble() / total.toDouble()) * 100).toInt()
        }
    }

    override fun onFinish(remotePath: String) {
        Log.i(TAG, "onFinish = $remotePath")
    }

    override fun onFail() {
    }


    /**
     * 根据Uri获取文件绝对路径，解决Android4.4以上版本Uri转换 兼容Android 10
     *
     * @param context
     * @param imageUri
     */
    fun getFileAbsolutePath(context: Context?, imageUri: Uri?): String? {
        if (context == null || imageUri == null) {
            return null
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getRealFilePath(context, imageUri)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && DocumentsContract.isDocumentUri(
                context,
                imageUri
            )
        ) {
            if (isExternalStorageDocument(imageUri)) {
                val docId = DocumentsContract.getDocumentId(imageUri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(imageUri)) {
                val id = DocumentsContract.getDocumentId(imageUri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(imageUri)) {
                val docId = DocumentsContract.getDocumentId(imageUri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = MediaStore.Images.Media._ID + "=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        }
        // MediaStore (and general)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return uriToFileApiQ(context, imageUri)
        } else if ("content".equals(imageUri.scheme, ignoreCase = true)) {
            // Return the remote address
            return if (isGooglePhotosUri(imageUri)) {
                imageUri.lastPathSegment
            } else getDataColumn(context, imageUri, null, null)
        } else if ("file".equals(imageUri.scheme, ignoreCase = true)) {
            return imageUri.path
        }
        return null
    }

    //此方法 只能用于4.4以下的版本
    private fun getRealFilePath(context: Context, uri: Uri?): String? {
        if (null == uri) {
            return null
        }
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null) {
            data = uri.path
        } else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)

//            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = MediaStore.Images.Media.DATA
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }


    /**
     * Android 10 以上适配 另一种写法
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("Range")
    private fun getFileFromContentUri(context: Context, uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        val filePath: String
        val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME)
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            uri, filePathColumn, null,
            null, null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            try {
                filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]))
                return filePath
            } catch (e: Exception) {
            } finally {
                cursor.close()
            }
        }
        return ""
    }

    /**
     * Android 10 以上适配
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private fun uriToFileApiQ(context: Context, uri: Uri): String? {
        var file: File? = null
        //android10以上转换
        if (uri.scheme == ContentResolver.SCHEME_FILE) {
            file = File(uri.path)
        } else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //把文件复制到沙盒目录
            val contentResolver = context.contentResolver
            val cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor!!.moveToFirst()) {
                val displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                try {
                    val `is` = contentResolver.openInputStream(uri)
                    val cache =
                        File(context.externalCacheDir!!.absolutePath, Math.round((Math.random() + 1) * 1000).toString() + displayName)
                    val fos = FileOutputStream(cache)
                    FileUtils.copy(`is`!!, fos)
                    file = cache
                    fos.close()
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return file!!.absolutePath
    }


    /**
     * 通过文件路径 uri的转字符也可以
     * @param filePath
     * @return
     */
    fun getMimeType(filePath: String?): String? {
        val ext = MimeTypeMap.getFileExtensionFromUrl(filePath)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext)
    }
}