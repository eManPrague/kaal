package cz.eman.kaalsample.feature.login.presentation.view.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import cz.eman.kaal.presentation.addon.manifest.shortcut.model.ShortcutVo
import cz.eman.kaal.presentation.view.hide
import cz.eman.kaal.presentation.view.show
import cz.eman.kaalsample.feature.login.presentation.R

@BindingAdapter("fillShortcuts")
fun createShortcuts(view: View, shortcutsVo: MutableLiveData<List<ShortcutVo>>) {
    val shortcuts = shortcutsVo.value?.let { shortcuts ->
        if (shortcuts.isNotEmpty()) {
            val size = shortcuts.size
            when {
                size >= 3 -> {
                    showShortcuts(
                        view = view,
                        shortcut1 = shortcuts[0],
                        shortcut2 = shortcuts[1],
                        shortcut3 = shortcuts[2]
                    )
                }
                size == 2 -> {
                    // There are only 2 shortcuts, so hide third one
                    view.findViewById<View>(R.id.layoutLoginThirdAddonShortcut).hide()
                    view.findViewById<View>(R.id.viewLoginDivider2).hide()

                    showShortcuts(view = view, shortcut1 = shortcuts[0], shortcut2 = shortcuts[1])
                }
                else -> {
                    // There are only 1 shortcut, so hide others
                    view.findViewById<View>(R.id.layoutLoginThirdAddonShortcut).hide()
                    view.findViewById<View>(R.id.layoutLoginSecondAddonShortcut).hide()
                    view.findViewById<View>(R.id.viewLoginDivider1).hide()
                    view.findViewById<View>(R.id.viewLoginDivider2).hide()

                    showShortcuts(view = view, shortcut1 = shortcuts[0])
                }
            }
        } else {
            // There is no layout..... Hide addons layout
            view.findViewById<View>(R.id.layoutLoginAddonsRoot).hide()
        }
    }
}

private fun showShortcuts(
    view: View,
    shortcut1: ShortcutVo? = null,
    shortcut2: ShortcutVo? = null,
    shortcut3: ShortcutVo? = null
) {
    with(view) {
        // Shortcut 3
        shortcut3?.let { shortcut ->
            findViewById<TextView>(R.id.txtLoginThirdAddon).text =
                resources.getText(shortcut.label)

            shortcut.icon?.let {
                findViewById<ImageView>(R.id.imgLoginThirdAddon).setImageResource(it)
            }

            findViewById<View>(R.id.layoutLoginThirdAddonShortcut).apply {
                tag = shortcut
                show()
            }
        }

        // Shortcut 2
        shortcut2?.let { shortcut ->
            findViewById<TextView>(R.id.txtLoginSecondAddon).text =
                resources.getText(shortcut.label)

            shortcut.icon?.let {
                findViewById<ImageView>(R.id.imgLoginSecondAddon).setImageResource(it)
            }

            findViewById<View>(R.id.layoutLoginSecondAddonShortcut).apply {
                tag = shortcut
                show()
            }
        }

        // Shortcut 1
        shortcut1?.let { shortcut ->
            findViewById<TextView>(R.id.txtLoginFirstAddon).text =
                resources.getText(shortcut.label)

            shortcut.icon?.let {
                findViewById<ImageView>(R.id.imgLoginFirstAddon).setImageResource(it)
            }

            findViewById<View>(R.id.layoutLoginFirstAddonShortcut).apply {
                tag = shortcut
                show()
            }
        }
    }
}
