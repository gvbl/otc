import react.FC
import react.Props
import react.createElement
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter

val App = FC<Props> {
    BrowserRouter {
        Routes {
            Route {
                index = true
                element = createElement(Home)
            }

            Route {
                path = "shared-space/*"
                element = createElement(Console)
            }

            Route {
                path = "legal"
                element = createElement(Legal)
            }

            Route {
                path = "*"
                element = createElement(NotFound)
            }
        }
    }
}