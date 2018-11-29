package home.learn.hmt.calendarvn_android.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModelLifeCycle: HomeViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment,container,false)
    }

    override fun initView() {
        super.initView()
    }

    override fun observe() {
        super.observe()
    }

    override fun handlers() {
        super.handlers()
    }

}
