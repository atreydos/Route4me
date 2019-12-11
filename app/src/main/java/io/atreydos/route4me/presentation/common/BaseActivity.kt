package io.atreydos.route4me.presentation.common

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.atreydos.route4me.presentation.common.annotion.ActionMenu
import io.atreydos.route4me.presentation.common.annotion.Layout


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cls = javaClass
        if (!cls.isAnnotationPresent(Layout::class.java)) return
        val annotation = cls.getAnnotation(Layout::class.java)
        val layout = annotation as Layout
        setContentView(layout.id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val cls = javaClass
        if (!cls.isAnnotationPresent(ActionMenu::class.java)) return super.onCreateOptionsMenu(menu)
        val annotation = cls.getAnnotation(ActionMenu::class.java)
        val actionMenu = annotation as ActionMenu
        menuInflater.inflate(actionMenu.id, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
