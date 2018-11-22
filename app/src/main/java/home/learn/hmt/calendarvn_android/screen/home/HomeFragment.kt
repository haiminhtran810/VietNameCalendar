package home.learn.hmt.calendarvn_android.screen.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.BR
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment
import home.learn.hmt.calendarvn_android.databinding.HomeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {
    override val viewModelLifeCycle: HomeViewModel by viewModel<HomeViewModel>()
    override val bindingVariable: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val layoutId: Int
        get() = R.layout.home_fragment


    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
