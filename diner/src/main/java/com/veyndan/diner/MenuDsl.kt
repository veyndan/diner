@file:Suppress("unused")

package com.veyndan.diner

import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import kotlin.DeprecationLevel.ERROR

private const val NO_GETTER: String = "Property does not have a getter"

@DslMarker
@Target(AnnotationTarget.TYPE)
private annotation class MenuDslMarker

inline fun Menu.menuItem(groupId: Int = Menu.NONE, itemId: Int = Menu.NONE, order: Int = Menu.NONE, title: String, init: (@MenuDslMarker MenuItem).() -> Unit = {}): MenuItem =
        add(groupId, itemId, order, title).apply(init)

inline fun Menu.subMenu(groupId: Int = Menu.NONE, itemId: Int = Menu.NONE, order: Int = Menu.NONE, title: String, checkable: Boolean = false, exclusive: Boolean = false, init: (@MenuDslMarker SubMenu).() -> Unit = {}): SubMenu {
    return addSubMenu(groupId, itemId, order, title).apply {
        init()
        setGroupCheckable(groupId, checkable, exclusive)
    }
}

inline fun SubMenu.item(init: (@MenuDslMarker MenuItem).() -> Unit): MenuItem = item.apply(init)

var MenuItem.iconRes: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = error(NO_GETTER)
    set(value) {
        setIcon(value)
    }

var MenuItem.showAsAction: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = error(NO_GETTER)
    set(value) = setShowAsAction(value)

inline fun MenuItem.onClick(crossinline init: (@MenuDslMarker MenuItem).() -> Boolean): MenuItem {
    return setOnMenuItemClickListener {
        init.invoke(it)
    }
}
