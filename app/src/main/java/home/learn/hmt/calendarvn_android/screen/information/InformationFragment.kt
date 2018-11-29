package home.learn.hmt.calendarvn_android.screen.information

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import home.learn.hmt.calendarvn_android.R
import home.learn.hmt.calendarvn_android.base.BaseFragment
import home.learn.hmt.calendarvn_android.data.*
import home.learn.hmt.calendarvn_android.data.model.DayMonthYear
import home.learn.hmt.calendarvn_android.screen.information.adapter.FragmentDayAdapter
import home.learn.hmt.calendarvn_android.screen.information.dayfragment.FragmentDay
import kotlinx.android.synthetic.main.infor_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class InformationFragment : BaseFragment(), FragmentDay.IGetItem {

    companion object {
        const val TAG = "InformationFragment"
        fun newInstance() = InformationFragment()
    }

    private var handler: Handler? = null
    private var fragmentDayAdapter: FragmentDayAdapter? = null
    private var dmyCurrent: DayMonthYear? = null
    private var dmyChanger: DayMonthYear? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.infor_fragment, container, false)
    }

    override fun initView() {
        super.initView()
        val calendar = Calendar.getInstance()
        dmyCurrent = DayMonthYear(calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0)
        handler = Handler()
        dmyCurrent?.let {
            setAdapterDayFragment(dmyCurrent!!)
        }
        setTime()
    }

    private fun setAdapterDayFragment(dmy: DayMonthYear) {
        fragmentManager?.let {
            fragmentDayAdapter = FragmentDayAdapter(this!!.fragmentManager!!, dmy)
        }
        dmyChanger = DayMonthYear(dmy.day, dmy.month, dmy.year, 0, 0)
        fragmentDayAdapter?.let {
            view_page.apply {
                adapter = fragmentDayAdapter
                currentItem = dmyChanger!!.day
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {}

                    override fun onPageScrolled(position: Int, positionOffset: Float,
                        positionOffsetPixels: Int) {
                    }

                    override fun onPageSelected(position: Int) {
                        dmyChanger?.apply {
                            day = position
                            if (position - 1 == maxDayOfMonth(month, year)) {
                                addDay(dmyChanger!!, 1)
                                fragmentDayAdapter = FragmentDayAdapter(fragmentManager!!,
                                    dmyChanger!!)
                                fragmentDayAdapter!!.notifyDataSetChanged()
                                adapter = fragmentDayAdapter
                                currentItem = 1
                            }

                            if (position == 0) {
                                dmyChanger = addDay(dmyChanger!!, -1)
                                fragmentDayAdapter = FragmentDayAdapter(fragmentManager!!,
                                    dmyChanger!!)
                                fragmentDayAdapter!!.notifyDataSetChanged()
                                adapter = fragmentDayAdapter
                                currentItem = maxDayOfMonth(dmyChanger!!.month, dmyChanger!!.year)
                            }
                            printInfo(dmyChanger!!)
                        }

                    }

                })
            }
        }
    }

    fun printInfo(dmy: DayMonthYear) {
        val can = can(dmy)
        val chi = chi(dmy)
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

    override val maxDay: Int
        get() = TODO(
            "not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val maxDayPre: Int
        get() = TODO(
            "not implemented") //To change initializer of created properties use File | Settings | File Templates.
}