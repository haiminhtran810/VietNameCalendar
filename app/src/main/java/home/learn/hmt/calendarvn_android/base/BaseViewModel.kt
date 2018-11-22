package home.learn.hmt.calendarvn_android.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<Navigator> : ViewModel() {
    var navigator: Navigator? = null
    var compoDisposable = CompositeDisposable()
    fun addDisposable(d: Disposable) {
        compoDisposable.add(d)
    }

    fun onActivityDestroy() {
        compoDisposable.clear()
    }
}