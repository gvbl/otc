import csstype.ClassName
import legal.Privacy
import legal.Terms
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.useState

const val rootURL = "https://www.otcontrol.com"
const val organization = "OTC"
const val email = "support@otcontrol.com"

enum class LegalSection {
    Privacy,
    Terms,
}

val Legal = FC<Props> {
    var activeSection by useState(LegalSection.Privacy)
    BasicNavbar { }
    div {
        className = ClassName("container")
        div {
            className = ClassName("tabs")
            ul {
                li {
                    if (activeSection == LegalSection.Privacy) {
                        className = ClassName("is-active")
                    }
                    a {
                        onClick = {
                            activeSection = LegalSection.Privacy
                        }
                        +"Privacy"
                    }
                }
                li {
                    if (activeSection == LegalSection.Terms) {
                        className = ClassName("is-active")
                    }
                    a {
                        onClick = {
                            activeSection = LegalSection.Terms
                        }
                        +"Terms"
                    }
                }
            }
        }
        when (activeSection) {
            LegalSection.Privacy -> Privacy { }
            LegalSection.Terms -> Terms { }
        }
    }
}