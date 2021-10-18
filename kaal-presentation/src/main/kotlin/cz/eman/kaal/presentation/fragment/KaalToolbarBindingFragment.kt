@file:Suppress("unused")

package cz.eman.kaal.presentation.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import cz.eman.kaal.presentation.view.hideKeyboard

/**
 * Base class for managing toolbar in fragment. It registers / unregisters as Activity Action Bar and sets it up.
 *
 * The options menu can be specified both via XML and programmatically in code. For the "programmatically" menu it is
 * necessary to set [hasMenu] to `true` and override the [setupMenu] method.
 *
 * @param B Type of data binding class corresponding [layoutId]
 * @property toolbarId The Toolbar ID
 * @property hasBackNavigation Indicates that the toolbar contains back navigation. With this option
 * there is no need to add back navigation in layout and it will be added automatically with default
 * layout and content description. Default value is set to false.
 * @property menuId The menu resource ID
 * @property hasMenu Indicates that the toolbar contains Options menu
 * @param layoutId The layout resource ID of the layout to inflate
 * @author eMan a.s.
 * @see 0.9.0
 */
@Suppress("MemberVisibilityCanBePrivate")
open class KaalToolbarBindingFragment<B : ViewDataBinding>(
    @IdRes protected val toolbarId: Int? = null,
    protected val hasBackNavigation: Boolean = false,
    @MenuRes protected val menuId: Int? = null,
    protected val hasMenu: Boolean = menuId != null,
    @LayoutRes layoutId: Int
) : KaalBindingFragment<B>(layoutId) {

    private var wrappedToolbar: ActionBar? = null

    /**
     * Provides reference to Toolbar inside fragment's View. If this method is not overridden, a [toolbarId] must be
     * provided in the constructor.
     *
     * > **Note:** Overridden method should not call `super()`.
     */
    open fun findToolbar(): Toolbar = requireNotNull(requireView().findViewById(
        requireNotNull(toolbarId) { "Toolbar ID is not provided" }
    )) { "View must contain toolbar with ID $toolbarId" }

    /**
     * Provides a reference to navigation icon drawable resource. Default is set to
     * [com.google.android.material.R.drawable.abc_ic_ab_back_material].
     *
     * > **Note:** Overridden method should not call `super()`.
     */
    @DrawableRes
    open fun getNavIcon(): Int = com.google.android.material.R.drawable.abc_ic_ab_back_material

    /**
     * Provides a reference to navigation icon description string resource. Default is set to
     * [com.google.android.material.R.string.abc_action_bar_up_description].
     *
     * > **Note:** Overridden method should not call `super()`.
     */
    @StringRes
    open fun getNavIconDescription(): Int = com.google.android.material.R.string.abc_action_bar_up_description

    /**
     * Creates fragment and sets the information if it has options menu or not.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(hasMenu)
    }

    /**
     * After view is created it will find toolbar (using [findToolbar]), set it up (using [setupToolbar] and set it as
     * [wrappedToolbar].
     *
     * @see findToolbar
     * @see setupToolbar
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wrappedToolbar = (requireActivity() as AppCompatActivity).apply {
            val toolbar = findToolbar()
            // if no title is given, the screen / application label would be used
            if (toolbar.title == null) toolbar.title = ""
            setSupportActionBar(toolbar)
            setupToolbar(toolbar)
        }.supportActionBar!!
    }

    /**
     * Inflates the options menu and sets it up by [setupMenu].
     *
     * @see setupMenu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (menuId != null) {
            inflater.inflate(menuId, menu)
        }
        setupMenu(menu)
    }

    /**
     * Removes support action bar and destroys [wrappedToolbar] to prevent leaks.
     */
    @CallSuper
    override fun onDestroyView() {
        (requireActivity() as AppCompatActivity).apply {
            if (supportActionBar === wrappedToolbar) setSupportActionBar(null)
        }
        wrappedToolbar = null
        super.onDestroyView()
    }

    /**
     * Basic toolbar setup. Sets up navigation icon with navigation click and menu item clicks. Navigation click can be
     * handled using [onNavigationClick]. Menu item click by [onMenuItemClick]. Navigation icon has a drawable and
     * description which can be modified using [getNavIcon] and [getNavIconDescription].
     *
     * @param toolbar Toolbar
     * @see onNavigationClick
     * @see onMenuItemClick
     * @see getNavIcon
     * @see getNavIconDescription
     */
    protected open fun setupToolbar(toolbar: Toolbar) {
        toolbar.apply {
            setNavigationOnClickListener(::onNavigationClick)
            setOnMenuItemClickListener(::onMenuItemClick)
            if (hasBackNavigation) {
                setNavigationIcon(getNavIcon())
                setNavigationContentDescription(getNavIconDescription())
            }
        }
    }

    /**
     * Function handling click on navigation icon in toolbar. Hides keyboard by default.
     *
     * @param view of the navigation icon
     */
    protected open fun onNavigationClick(view: View?) {
        view?.hideKeyboard()
    }

    /**
     * Function handling toolbar menu item click.
     *
     * @param item {@link MenuItem} that was clicked
     * @return true if the event was handled, false otherwise.
     */
    protected open fun onMenuItemClick(item: MenuItem): Boolean {
        return false
    }

    /**
     * Basic options menu setup.
     *
     * @param menu options menu
     */
    protected open fun setupMenu(menu: Menu) {
        /* no-op */
    }
}
