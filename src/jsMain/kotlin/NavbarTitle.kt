import csstype.ClassName
import react.FC
import react.PropsWithChildren
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span

external interface NavbarTitleProps : PropsWithChildren {
    var isBurgerActive: Boolean
    var onToggleActive: () -> Unit
}

val NavbarTitle = FC<NavbarTitleProps> { props ->
    val activeClass = if (props.isBurgerActive) " is-active" else ""
    div {
        className = ClassName("navbar-brand")
        a {
            className = ClassName("navbar-item")
            href = "/"
            span {
                className = ClassName("icon")
                i {
                    className = ClassName("fas fa-lg fa-house-chimney")
                }
            }
        }
        div {
            className = ClassName("navbar-item is-flex-shrink-1")
            +props.children
        }
        a {
            className = ClassName("navbar-burger${activeClass}")
            onClick = {
                props.onToggleActive()
            }
            span { }
            span { }
            span { }
        }
    }
}