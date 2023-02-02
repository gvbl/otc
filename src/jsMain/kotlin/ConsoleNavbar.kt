import csstype.ClassName
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.nav
import react.dom.html.ReactHTML.span
import react.useContext
import react.useState

private val scope = MainScope()

val ConsoleNavbar = FC<Props> {
    val spaceState = useContext(SpaceContext)
    val space = spaceState.space!!
    var isHelpActive by useState(space.onboarding.showHelp)
    var isSettingsActive by useState(false)
    var isMenuActive by useState(false)
    val activeClass = if (isMenuActive) " is-active" else ""
    nav {
        className = ClassName("navbar is-fixed-top")
        div {
            className = ClassName("container")
            NavbarTitle {
                isBurgerActive = isMenuActive
                onToggleActive = {
                    isMenuActive = !isMenuActive
                }
                SpaceNameInput { }
            }
            div {
                className = ClassName("navbar-menu${activeClass}")
                div {
                    className = ClassName("navbar-end")
                    div {
                        className = ClassName("navbar-item")
                        a {
                            className = ClassName("button is-white")
                            onClick = {
                                isHelpActive = true
                            }
                            +"Help"
                        }
                    }
                    div {
                        className = ClassName("navbar-item")
                        a {
                            className = ClassName("button is-light")
                            onClick = {
                                isSettingsActive = true
                            }
                            span {
                                className = ClassName("icon")
                                i {
                                    className = ClassName("fas fa-lg fa-gear")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    HelpModal {
        isModalActive = isHelpActive
        onClose = {
            scope.launch {
                patchSpace(space.copy(onboarding = space.onboarding.copy(showHelp = false)))
            }
            isHelpActive = false
        }
    }
    SettingsModal {
        isModalActive = isSettingsActive
        onClose = {
            isSettingsActive = false
        }
    }
}