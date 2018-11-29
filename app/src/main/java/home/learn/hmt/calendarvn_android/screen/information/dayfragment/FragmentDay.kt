package home.learn.hmt.calendarvn_android.screen.information.dayfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment

class FragmentDay : BaseFragment() {
    companion object {
        const val TAG = "FragmentDay"
        fun newInstance(day: Int): FragmentDay {
            val fragmentDay = FragmentDay()
            val bundle = Bundle()
            bundle.putInt(TAG, day)
            fragmentDay.arguments = bundle
            return FragmentDay()
        }
    }

    private val day: Int? = 0
    private var item: IGetItem? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        item = activity as IGetItem
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_fragment, container, false)
    }

    override fun initView() {
        super.initView()
    }

    override fun handlers() {
        super.handlers()
    }

    override fun observe() {
        super.observe()
    }

    interface IGetItem {
        val maxDay: Int
        val maxDayPre: Int
    }

}