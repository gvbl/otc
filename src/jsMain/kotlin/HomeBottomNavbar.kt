import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.nav
import react.dom.html.ReactHTML.span
import react.useState

val HomeBottomNavbar = FC<Props> {
    var isMenuActive by useState(false)
    val activeClass = if (isMenuActive) " is-active" else ""
    nav {
        className = ClassName("navbar is-fixed-bottom is-light")
        div {
            className = ClassName("container")
            div {
                className = ClassName("navbar-brand")
                a {
                    className = ClassName("navbar-burger${activeClass}")
                    onClick = {
                        isMenuActive = !isMenuActive
                    }
                    span { }
                    span { }
                    span { }
                }
            }
            div {
                className = ClassName("navbar-menu${activeClass}")
                div {
                    className = ClassName("navbar-end")
                    a {
                        className = ClassName("navbar-item")
                        href = "/legal"
                        +"Legal"
                        onClick = {
                            isMenuActive = false
                        }
                    }
                }
            }
        }
    }
}