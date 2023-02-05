import csstype.*
import emotion.react.css
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLSpanElement
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span

private val scope = MainScope()

external interface PersonsListItemProps : Props {
    var person: Person
}

val PersonsListItem = FC<PersonsListItemProps> { props ->
    val person = props.person
    val spaceState = useContext(SpaceContext)
    val space = spaceState.space!!
    val isAssigned = space.assignments.map { it.personId }.contains(person.id)
    val itemRef = useRef<HTMLSpanElement>()
    val hideModal = useContext(HideModalContext)
    var isMouseOver by useState(false)
    var isDragging by useState(false)
    val isMobile = useIsMobile()
    if (isMobile) {
        window.requestAnimationFrame {
            if (isDragging) {
                hideModal()
            }
        }
    }
    span {
        ref = itemRef
        css(ClassName("tag is-large is-info")) {
            maxWidth = 100.pct
            cursor = Cursor.grab
            if (isDragging) {
                opacity = number(0.5)
            }
        }
        onMouseEnter = {
            isMouseOver = true
        }
        onMouseLeave = {
            isMouseOver = false
        }
        draggable = true
        onDragStart = {
            isDragging = true
            isMouseOver = false
            it.dataTransfer.setData(PersonId, person.id.toString())
        }
        onDragEnd = { event ->
            isDragging = false
            itemRef.current?.let { item ->
                val rect = item.getBoundingClientRect()
                isMouseOver = event.clientX in rect.left..rect.right
                        && event.clientY in rect.top..rect.bottom
            }
        }
        span {
            className = ClassName("icon")
            i {
                className = ClassName("fas fa-xs ${if (isAssigned) "fa-city" else "fa-home"}")
            }
        }
        span {
            css {
                overflowX = Overflow.hidden
                textOverflow = TextOverflow.ellipsis
            }
            +person.name
        }
        button {
            css(ClassName("delete")) {
                visibility =
                    if ((isMouseOver && !isDragging) || isTouchDevice()) Visibility.visible else Visibility.hidden
            }
            onClick = {
                scope.launch {
                    deletePerson(space, person)
                    spaceState.update()
                }
            }
        }
    }
}