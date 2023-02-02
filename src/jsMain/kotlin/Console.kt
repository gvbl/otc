import csstype.AlignItems
import csstype.Display
import csstype.JustifyContent
import csstype.pct
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.router.useLocation
import react.useState

val Console = FC<Props> {
    val location = useLocation()
    var isLoading by useState(true)
    val isMobile = useIsMobile()
    SpaceContextProvider {
        spaceId = lastPath(location.pathname)
        onLoaded = {
            isLoading = false
        }
        if (isLoading) {
            div {
                css {
                    display = Display.flex
                    height = 100.pct
                    alignItems = AlignItems.center
                    justifyContent = JustifyContent.center
                }
                Loading {
                    size = LoadingSize.Large
                }
            }
        } else {
            ConsoleNavbar { }
            ConsoleContent { }
            if (isMobile) {
                MobilePersons { }
            }
        }
    }
}