import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.key
import react.useContext

const val PersonId = "personId"

val PersonsList = FC<Props> {
    val spaceState = useContext(SpaceContext)
    val space = spaceState.space!!

    ul {
        space.persons.forEach {
            li {
                key = it.id.toString()
                className = ClassName("block")
                PersonsListItem {
                    person = it
                }
            }
        }
        li {
            key = "AddPerson"
            className = ClassName("block")
            AddPersonItem { }
        }
    }
}