import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

val ConsoleContent = FC<Props> {
    val isMobile = useIsMobile()
    div {
        css(ClassName("container")) {
            display = Display.flex
            height = 100.pct
        }
        if (!isMobile) {
            div {
                css {
                    padding = 0.75.rem
                    flexBasis = 25.pct
                    overflowY = Auto.auto
                }
                PersonsList { }
            }
        }
        div {
            css(ClassName("column")) {
                overflowY = Auto.auto
            }
            DesksList { }
        }
    }
}