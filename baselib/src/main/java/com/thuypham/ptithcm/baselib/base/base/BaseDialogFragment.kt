package com.thuypham.ptithcm.baselib.base.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.thuypham.ptithcm.baselib.base.extension.printErrorLog
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener

abstract class BaseDialogFragment<T : ViewDataBinding>(private val layoutId: Int) :
    DialogFragment() {

    var onFinishLoading: (() -> Unit)? = null
    open val isFullScreen: Boolean = true

    lateinit var binding: T

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFullScreen) {
            setStyle(STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar)
        }
        setupFirst()
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            setupLogic()
            getData()
            setupView()
            setupDataObserver()
            onFinishLoading?.invoke()
            binding.root.setOnSingleClickListener {
                hideKeyboard()
            }
        } catch (e: Exception) {
            e.printErrorLog("onViewCreated")
        }
    }

    protected open fun setupFirst() {}
    protected open fun setupLogic() {}
    protected open fun getData() {}
    protected open fun setupView() {}
    protected open fun setupDataObserver() {}

    fun showLoading() {
        (requireActivity() as CommonBaseActivity<*>).showLoading()
    }

    fun hideLoading() {
        (requireActivity() as CommonBaseActivity<*>).hideLoading()
    }

    protected fun showSnackBar(resMessage: Int) {
        runOnUiThread(Runnable {
            if (view != null) {
                val snackBar = Snackbar.make(
                    view ?: return@Runnable, (activity
                        ?: return@Runnable).getString(resMessage), Snackbar.LENGTH_LONG
                )
                snackBar.show()
            }
        })
    }

    fun showSnackBar(message: String?) {
        runOnUiThread(Runnable {
            if (view != null) {
                val snackBar = Snackbar.make(
                    view ?: return@Runnable, message
                        ?: return@Runnable, Snackbar.LENGTH_SHORT
                )
                snackBar.show()
            }
        })
    }

    private fun runOnUiThread(runnable: Runnable?) {
        if (activity == null || !isAdded) {
            return
        }
        activity?.runOnUiThread(runnable)
    }

    protected fun hideKeyboard() {
        val currentFocus = dialog?.currentFocus
        if (currentFocus != null) {
            dialog?.let {
                val windowToken = it.window?.decorView?.rootView
                val imm =
                    it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    windowToken?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }


}