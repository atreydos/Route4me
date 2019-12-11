package io.atreydos.route4me.presentation.common

import android.os.Build
import android.os.Bundle

import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import io.atreydos.route4me.presentation.common.annotion.ActionMenu
import io.atreydos.route4me.presentation.common.annotion.Layout

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cls = javaClass
        if (!cls.isAnnotationPresent(Layout::class.java)) return null
        val annotation = cls.getAnnotation(Layout::class.java)
        val layout = annotation as Layout
        return  inflater.inflate(layout.id, null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val cls = javaClass
        if (!cls.isAnnotationPresent(ActionMenu::class.java)) return
        val annotation = cls.getAnnotation(ActionMenu::class.java)
        val actionMenu = annotation as ActionMenu
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            menu.setGroupDividerEnabled(true)
        inflater.inflate(actionMenu.id, menu)
    }

    protected fun showAlert(message: String) {
        AlertDialog.Builder(activity!!)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
            .show()
    }
}
