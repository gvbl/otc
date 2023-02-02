import csstype.ClassName
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.section

val PersonsListModal = FC<ModalProps> { props ->
    Modal {
        isModalActive = props.isModalActive
        onClose = props.onClose
        header {
            className = ClassName("modal-card-head")
            button {
                className = ClassName("delete")
                onClick = {
                    props.onClose()
                }
            }
        }
        section {
            className = ClassName("modal-card-body")
            PersonsList { }
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