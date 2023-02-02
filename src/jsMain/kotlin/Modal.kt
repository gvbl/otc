import csstype.ClassName
import kotlinx.browser.window
import org.w3c.dom.events.KeyboardEvent
import react.FC
import react.PropsWithChildren
import react.dom.html.ReactHTML.div

external interface ModalProps : PropsWithChildren {
    var isModalActive: Boolean
    var onClose: () -> Unit
}

val Modal = FC<ModalProps> { props ->
    val activeClass = if (props.isModalActive) " is-active" else ""
    window.addEventListener("keydown", {
        if (it is KeyboardEvent && it.key == "Escape") {
            props.onClose()
        }
    })
    div {
        className = ClassName("modal$activeClass")
        div {
            className = ClassName("modal-background")
            onClick = {
                props.onClose()
            }
        }
        div {
            className = ClassName("modal-card")
            +props.children
        }
    }
}