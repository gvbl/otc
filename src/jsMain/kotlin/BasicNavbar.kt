import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.nav
import react.dom.html.ReactHTML.span

val BasicNavbar = FC<Props> {
    nav {
        className = ClassName("navbar is-fixed-top")
        div {
            className = ClassName("container")
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
                    span {
                        className = ClassName("is-size-4")
                        +"Office Traffic Control"
                    }
                }
            }
        }
    }
}