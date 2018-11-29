package home.learn.hmt.calendarvn_android.screen.information

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment
import home.learn.hmt.calendarvn_android.data.FORMAT_TIME
import home.learn.hmt.calendarvn_android.screen.information.adapter.FragmentDayAdapter
import kotlinx.android.synthetic.main.infor_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class InformationFragment : BaseFragment() {
    companion object {
        const val TAG = "InformationFragment"
        fun newInstance() = InformationFragment()
    }

    private var handler: Handler? = null
    private var fragmentDayAdapter: FragmentDayAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.infor_fragment, container, false)
    }

    override fun initView() {
        super.initView()
        handler = Handler()
        setAdapterDayFragment()
        setTime()
    }

    private fun setAdapterDayFragment() {
        //fragmentDayAdapter = FragmentDayAdapter(fragmentManager,)
    }

    fun setTime() {
        val time = object : Runnable {
            override fun run() {
                val aGMTCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"))
                val time = aGMTCalendar.time
                val timeFormat = SimpleDateFormat(FORMAT_TIME, Locale.getDefault())
                val timeString = timeFormat.format(time.time)
                tv_hour.text = timeString
                handler?.postDelayed(this, 1000)
            }
        }
        handler?.postDelayed(time, 1000)
    }

    override fun observe() {
        super.observe()
    }

    override fun handlers() {
        super.handlers()
    }
}