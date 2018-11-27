package home.learn.hmt.calendarvn_android.screen.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment

class InformationFragment : BaseFragment() {
    companion object {
        const val TAG = "InformationFragment"
        fun newInstance() = InformationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.infor_fragment, container, false)
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