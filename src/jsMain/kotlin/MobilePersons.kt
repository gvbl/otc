import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.createContext
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import react.useState

val HideModalContext = createContext<() -> Unit>()

val MobilePersons = FC<Props> {
    var showPersonsListModal by useState(false)
    button {
        css(ClassName("button is-info")) {
            position = Position.fixed
            bottom = 2.rem
            right = 2.rem
            boxShadow = BoxShadow(0.px, 8.px, 16.px, (-2).px, rgba(10, 10, 10, 0.1))
        }
        span {
            className = ClassName("icon is-medium")
            i {
                className = ClassName("fas fa-user")
            }
        }
        onClick = {
            showPersonsListModal = true
        }
    }
    HideModalContext.Provider {
        value = {
            showPersonsListModal = false
        }
        PersonsListModal {
            isModalActive = showPersonsListModal
            onClose = {
                showPersonsListModal = false
            }
        }
    }
}