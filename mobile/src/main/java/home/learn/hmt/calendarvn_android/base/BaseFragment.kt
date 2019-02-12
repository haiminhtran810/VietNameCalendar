package home.learn.hmt.calendarvn_android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import home.learn.hmt.calendarvn_android.R

abstract class BaseFragment : Fragment(), BaseNavigator {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handlers()
        observe()
    }

    open fun initView() {}

    open fun handlers() {}

    open fun observe() {}

    fun findFragment(TAG: String): Fragment? {
        return activity?.supportFragmentManager?.findFragmentByTag(TAG)
    }

    fun findChildFragment(parentFragment: Fragment = this, TAG: String): Fragment? {
        return parentFragment.childFragmentManager.findFragmentByTag(TAG)
    }

    fun replaceFragment(fragment: Fragment, TAG: String?, addToBackStack: Boolean = false,
                        transit: Int = -1) {
        val transaction = activity!!.supportFragmentManager!!.beginTransaction()
                .replace(R.id.parent, fragment, TAG)
        commitTransaction(transaction, addToBackStack, transit)
    }

    fun replaceChildFragment(
            parentFragment: Fragment = this, containerViewId: Int, fragment: Fragment,
            TAG: String?, addToBackStack: Boolean = false, transit: Int = -1) {
        val transaction = parentFragment.childFragmentManager.beginTransaction().replace(
                containerViewId, fragment, TAG)
        commitTransaction(transaction, addToBackStack, transit)
    }

    fun addChildFragment(
            parentFragment: Fragment = this, containerViewId: Int,
            targetFragment: Fragment, TAG: String?, addToBackStack: Boolean = false,
            transit: Int = -1) {
        val transaction = parentFragment.childFragmentManager.beginTransaction().add(
                containerViewId, targetFragment, TAG)
        commitTransaction(transaction, addToBackStack, transit)
    }

    fun popChildFragment(parentFragment: Fragment = this) {
        parentFragment.childFragmentManager.popBackStack()
    }

    fun commitTransaction(transaction: FragmentTransaction?, addToBackStack: Boolean,
                          transit: Int) {
        if (addToBackStack) transaction?.addToBackStack(null)
        //Select a standard transition animation for this transaction.
        // May be one of TRANSIT_NONE, TRANSIT_FRAGMENT_OPEN, TRANSIT_FRAGMENT_CLOSE, or TRANSIT_FRAGMENT_FADE.
        if (transit != -1) transaction?.setTransition(transit)
        transaction?.commit()
    }
}