package ebysofyan.app.made.submission.base

/**
 * Created by @ebysofyan on 7/2/19
 */

interface BasePresenter<V> {
    fun attach(v: V)
    fun detach()
}