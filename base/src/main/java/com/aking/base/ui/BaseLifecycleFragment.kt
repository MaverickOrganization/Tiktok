package com.aking.base.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aking.base.extended.TAG_C

/**
 * Created by Rick on 2023-07-08  23:10
 * Description:
 */
abstract class BaseLifecycleFragment : Fragment() {

    protected val TAG = TAG_C
    private var lifecycleLog = false

    /**
     * 生命周期日志开关
     */
    protected open fun lifecycleLogEnable(enable: Boolean) {
        lifecycleLog = enable
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (lifecycleLog) Log.v(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (lifecycleLog) Log.v(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (lifecycleLog) Log.v(TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (lifecycleLog) Log.v(TAG, "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        if (lifecycleLog) Log.v(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        if (lifecycleLog) Log.v(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        if (lifecycleLog) Log.v(TAG, "onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (lifecycleLog) Log.v(TAG, "onSaveInstanceState")
    }

    override fun onStop() {
        super.onStop()
        if (lifecycleLog) Log.v(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (lifecycleLog) Log.v(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (lifecycleLog) Log.v(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        if (lifecycleLog) Log.v(TAG, "onDetach")
    }

}