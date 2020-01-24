package pt.ulp.app_try.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pt.ulp.app_try.R
import pt.ulp.app_try.ui.notifications.NotificationsViewModel

private const val ARG_PARAM1 ="param1"
private const val ARG_PARAM2 ="param2"

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }
}