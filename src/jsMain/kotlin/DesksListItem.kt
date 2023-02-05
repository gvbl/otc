import csstype.*
import emotion.react.css
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.figure
import react.dom.html.ReactHTML.img
import react.useContext
import react.useState

private val scope = MainScope()

external interface DesksListItemProps : Props {
    var desk: Desk
}

val DesksListItem = FC<DesksListItemProps> { props ->
    val desk = props.desk
    val spaceState = useContext(SpaceContext)
    val space = spaceState.space!!
    var isMouseOver by useState(false)
    var isDraggingOver by useState(false)
    var isLoadingAssignment by useState(false)

    div {
        css(ClassName("card")) {
            boxSizing = BoxSizing.borderBox
            border = Border(1.px, LineStyle.solid)
            borderColor = if (isDraggingOver) {
                rgb(62, 142, 208)
            } else {
                Color("transparent")
            }
        }
        onMouseEnter = {
            isMouseOver = true
        }
        onMouseLeave = {
            isMouseOver = false
        }
        onDragEnter = {
            isDraggingOver = true
            it.preventDefault()
        }
        onDragOver = {
            isDraggingOver = true
            it.preventDefault()
        }
        onDragLeave = {
            isDraggingOver = false
        }
        onDrop = {
            isDraggingOver = false
            scope.launch {
                isLoadingAssignment = true
                addAssignment(space, Assignment(it.dataTransfer.getData(PersonId).toInt(), desk.id))
                spaceState.update()
                isLoadingAssignment = false
            }
        }
        div {
            className = ClassName("card-image")
            figure {
                className = ClassName("image")
                img {
                    css {
                        objectFit = ObjectFit.cover
                    }
                    src = desk.imagePath
                }
            }
        }
        div {
            css(ClassName("card-content")) {
                height = 105.px
            }
            div {
                css(ClassName("has-text-weight-semibold")) {
                    marginBottom = 0.5.rem
                    whiteSpace = WhiteSpace.nowrap
                    overflowX = Overflow.hidden
                    textOverflow = TextOverflow.ellipsis
                }
                +desk.name
            }
            if (isLoadingAssignment) {
                div {
                    css {
                        display = Display.flex
                        height = 100.pct
                        justifyContent = JustifyContent.center
                    }
                    Loading {
                        size = LoadingSize.Small
                    }
                }
            } else {
                space.assignments.find { it.deskId == desk.id }?.let { assignment ->
                    space.persons.find { it.id == assignment.personId }?.let { person ->
                        AssignedPerson {
                            this.person = person
                            this.assignment = assignment
                        }
                    }
                }
            }
        }
        button {
            css(ClassName("delete")) {
                position = Position.absolute
                top = 0.5.rem
                right = 0.5.rem
                visibility =
                    if ((isMouseOver && !isDraggingOver) || isTouchDevice()) Visibility.visible else Visibility.hidden
            }
            onClick = {
                scope.launch {
                    deleteDesk(space, desk)
                    spaceState.update()
                }
            }
        }
    }
}