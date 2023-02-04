import csstype.ClassName
import csstype.pct
import csstype.px
import emotion.react.css
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.events.ChangeEventHandler
import react.dom.events.FocusEventHandler
import react.dom.events.FormEventHandler
import react.dom.html.InputType
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input

private val scope = MainScope()

val SpaceNameInput = FC<Props> {
    val spaceState = useContext(SpaceContext)
    val space = spaceState.space!!
    var text by useState(space.name)
    val inputRef = useRef<HTMLInputElement>()

    val submitHandler: FormEventHandler<HTMLFormElement> = {
        it.preventDefault()
        inputRef.current?.blur()
    }

    val changeHandler: ChangeEventHandler<HTMLInputElement> = {
        text = it.target.value
    }

    val blurHandler: FocusEventHandler<HTMLInputElement> = {
        if (text != space.name) {
            scope.launch {
                if (text.isEmpty()) {
                    text = Space.DefaultName
                    patchSpace(space.copy(name = Space.DefaultName))
                } else {
                    patchSpace(space.copy(name = text))
                }
                spaceState.update()
            }
        }
    }

    form {
        id = "space-name-input"
        className = ClassName("is-flex-shrink-1")
        onSubmit = submitHandler
        input {
            css(ClassName("is-flex-shrink-1 is-size-4 has-text-weight-medium")) {
                width = 100.pct
                maxWidth = 300.px
            }
            ref = inputRef
            placeholder = Space.DefaultName
            type = InputType.text
            maxLength = 80
            onChange = changeHandler
            onBlur = blurHandler
            value = text
        }
    }
}