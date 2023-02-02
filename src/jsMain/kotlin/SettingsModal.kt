import csstype.ClassName
import csstype.Display
import csstype.JustifyContent
import emotion.react.css
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.events.KeyboardEvent
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import react.router.useNavigate
import react.useContext
import react.useState

private val scope = MainScope()

val SettingsModal = FC<ModalProps> { props ->
    var confirmDelete by useState(false)
    val spaceState = useContext(SpaceContext)
    val navigate = useNavigate()
    Modal {
        isModalActive = props.isModalActive
        onClose = props.onClose
        header {
            className = ClassName("modal-card-head")
            p {
                className = ClassName("modal-card-title")
                +"Settings"
            }
            button {
                className = ClassName("delete")
                onClick = {
                    props.onClose()
                }
            }
        }
        section {
            className = ClassName("modal-card-body")
            spaceState.space?.let {
                div {
                    css {
                        display = Display.flex
                        justifyContent = JustifyContent.center
                    }
                    if (confirmDelete) {
                        button {
                            className = ClassName("button is-danger")
                            +"Confirm delete"
                            onClick = {
                                scope.launch {
                                    deleteSpace(spaceState.space)
                                    navigate("/")
                                }
                            }
                        }
                    } else {
                        button {
                            className = ClassName("button is-danger is-light")
                            +"Delete this space"
                            onClick = {
                                confirmDelete = true
                            }
                        }
                    }
                }
            }
        }
        footer {
            className = ClassName("modal-card-foot")
            button {
                className = ClassName("button is-success")
                onClick = {
                    props.onClose()
                }
                +"Done"
            }
        }
    }
}