import csstype.*
import emotion.react.css
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.figure
import react.dom.html.ReactHTML.img
import react.useContext
import react.useState

private val scope = MainScope()

val AddDeskItem = FC<Props> {
    val spaceState = useContext(SpaceContext)
    val space = spaceState.space!!
    var hasInput by useState(false)
    div {
        css(ClassName("card")) {
            margin = 1.px
        }
        div {
            className = ClassName("card-image")
            figure {
                className = ClassName("image")
                img {
                    css {
                        objectFit = ObjectFit.cover
                    }
                    src = "https://storage.googleapis.com/otc-assets/resources/images/desks/desk0.png"
                }
            }
        }
        div {
            css(ClassName("card-content")) {
                height = 105.px
            }
            DeskNameInput {
                onChange = {
                    hasInput = it.isNotEmpty()
                }
                onSubmit = { input ->
                    scope.launch {
                        val desk = Desk(input)
                        addDesk(space, desk)
                        spaceState.update()
                    }
                }
            }
            button {
                css(ClassName("button mt-2 is-small")) {
                    float = Float.right
                    visibility = if (hasInput) {
                        Visibility.visible
                    } else {
                        Visibility.hidden
                    }
                }
                form = "desk-name-input"
                type = ButtonType.submit
                +"Add"
            }
        }
    }
}
