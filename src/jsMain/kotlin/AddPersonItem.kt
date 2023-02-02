import csstype.ClassName
import csstype.Visibility
import csstype.pct
import emotion.react.css
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.span
import react.useContext
import react.useState

private val scope = MainScope()

val AddPersonItem = FC<Props> {
    val spaceState = useContext(SpaceContext)
    val space = spaceState.space!!
    var hasInput by useState(false)

    span {
        css(ClassName("tag is-large is-success")) {
            width = 100.pct
        }
        PersonNameInput {
            onChange = {
                hasInput = it.isNotEmpty()
            }
            onSubmit = { input ->
                val person = Person(input)
                scope.launch {
                    addPerson(space, person)
                    spaceState.update()
                }
            }
        }
        button {
            css(ClassName("ml-2 delete no-rotate")) {
                visibility = if (hasInput) {
                    Visibility.visible
                } else {
                    Visibility.hidden
                }
            }
            form = "person-name-input"
            type = ButtonType.submit
        }
    }
}