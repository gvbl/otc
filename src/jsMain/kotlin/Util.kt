import kotlinx.browser.window

fun lastPath(pathname: String): String {
    return pathname.substring(pathname.lastIndexOf('/') + 1)
}

fun isTouchDevice(): Boolean = window.navigator.maxTouchPoints > 0 && window.navigator.maxTouchPoints != 256