import csstype.pct
import emotion.react.css
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.dom.html.InputType
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.useState

external interface InputProps : Props {
    var onSubmit: (String) -> Unit
    var onChange: (String) -> Unit
}

val DeskNameInput = FC<InputProps> { props ->
    var text by useState("")

    val submitHandler: FormEventHandler<HTMLFormElement> = {
        it.preventDefault()
        text = ""
        props.onChange("")
        props.onSubmit(text)
    }

    val changeHandler: ChangeEventHandler<HTMLInputElement> = {
        text = it.target.value
        props.onChange(it.target.value)
    }

    form {
        id = "desk-name-input"
        input {
            css {
                width = 100.pct
            }
            type = InputType.text
            placeholder = "Desk name"
            required = true
            maxLength = 80
            onChange = changeHandler
            value = text
        }
        onSubmit = submitHandler
    }
}