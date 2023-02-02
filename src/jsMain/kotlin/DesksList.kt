import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.key
import react.useContext

val DesksList = FC<Props> {
    val spaceState = useContext(SpaceContext)
    val space = spaceState.space!!
    ul {
        className = ClassName("columns is-multiline")
        space.desks.forEach {
            li {
                key = it.id.toString()
                className = ClassName("column is-one-quarter")
                DesksListItem {
                    desk = it
                }
            }
        }
        li {
            key = "AddDesk"
            className = ClassName("column is-one-quarter")
            AddDeskItem { }
        }
        div {
            className = ClassName("column")
        }
    }
}