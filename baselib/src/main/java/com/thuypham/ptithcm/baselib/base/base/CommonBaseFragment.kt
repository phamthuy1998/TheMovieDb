package com.thuypham.ptithcm.baselib.base.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.ui.dialog.ProgressDialog

abstract class CommonBaseFragment<T : ViewDataBinding>(private val layoutId: Int) : Fragment() {

    protected lateinit var binding: T
    private val dialog: Dialog by lazy { ProgressDialog.progressDialog(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFirst()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
        setupView()
        getData()
        setupDataObserver()
        setupToolbar()
    }

    protected open fun setupFirst() {}
    protected open fun getData() {}
    protected abstract fun setupView()
    protected open fun setupToolbar() {}
    protected open fun setupDataObserver() {}
    protected open fun clearData() {}

    fun showLoading() {
        runOnUiThread { dialog.show() }
    }

    fun hideLoading() {
        runOnUiThread {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
    }


    protected fun runOnUiThread(runnable: Runnable?) {
        if (activity == null || !isAdded) {
            return
        }
        activity?.runOnUiThread(runnable)
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
        if(message.isNullOrEmpty()) return
        runOnUiThread(Runnable {
            if (view != null) {
                Snackbar.make(view ?: return@Runnable, message, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    protected fun hideKeyboard() {
        if (activity == null) {
            return
        }
        val view = activity?.currentFocus
        if (view != null) {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onDestroyView() {
        clearData()
        hideKeyboard()
        clearViewBinding()
        super.onDestroyView()
    }

    /**
     * Reference:  {@link https://stackoverflow.com/questions/65295104/android-view-binding-clear-binding-in-fragment-lifecycle}
     *  once the fragment then invokes its onDestroyView() callback all references to the fragment's view should be removed,
     *  allowing the fragment's view to be garbage collected.
     *
     * The next time the fragment needs to be displayed,
     * a new view will be created. So we set _binding = null to allow for a new View to be created and referenced.
     * */
    private fun clearViewBinding() {
        binding.apply {
            logD("clearViewBinding")

            (root.parent as? ViewGroup)?.run {
                logD("clearViewBinding")
                this.removeAllViews()
                this.removeAllViewsInLayout()
            }
            this.unbind()
        }
    }
}