import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.section

val NotFound = FC<Props> {
    BasicNavbar { }
    section {
        className = ClassName("section")
        h1 {
            className = ClassName("title is-3")
            +"Page not found"
        }
    }
}