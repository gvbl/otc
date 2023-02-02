import csstype.ClassName
import csstype.Display
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

external interface PersonNameInputProps : Props {
    var onSubmit: (String) -> Unit
    var onChange: (String) -> Unit
}

val PersonNameInput = FC<PersonNameInputProps> { props ->
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
        id = "person-name-input"
        css(ClassName("ml-4")) {
            width = 100.pct
        }
        onSubmit = submitHandler
        input {
            css(ClassName("is-size-6")) {
                width = 100.pct
                display = Display.block
            }
            type = InputType.text
            placeholder = "Name"
            required = true
            maxLength = 80
            onChange = changeHandler
            value = text
        }
    }
}