import kotlinx.browser.window
import react.useState

fun useIsMobile(): Boolean {
    var isMobile by useState(window.innerWidth <= 768)
    window.addEventListener("resize", {
        isMobile = window.innerWidth <= 768
    })
    return isMobile
}