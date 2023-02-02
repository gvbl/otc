import csstype.*
import emotion.react.css
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import react.useContext

private val scope = MainScope()

external interface AssignedPersonProps : Props {
    var person: Person
    var assignment: Assignment
}

val AssignedPerson = FC<AssignedPersonProps> { props ->
    val person = props.person
    val assignment = props.assignment
    val spaceState = useContext(SpaceContext)
    val space = spaceState.space!!
    span {
        css(ClassName("tag is-medium is-info")) {
            maxWidth = 100.pct
        }
        button {
            css(ClassName("delete no-content is-small")) {
                color = Globals.inherit
                fontSize = FontSize.smaller
                marginLeft = important((-0.375).rem)
                marginRight = important(0.25.rem)
            }
            onClick = {
                scope.launch {
                    deleteAssignment(space, assignment)
                    spaceState.update()
                }
            }
            span {
                className = ClassName("icon is-small")
                i {
                    css(ClassName("fas fa-arrow-left")) {
                        position = Position.absolute
                        left = 50.pct
                        top = 50.pct
                        transform = translate((-50).pct, (-50).pct)
                    }
                }
            }
        }
        div {
            css {
                overflowX = Overflow.hidden
                textOverflow = TextOverflow.ellipsis
            }
            +person.name
        }
    }
}