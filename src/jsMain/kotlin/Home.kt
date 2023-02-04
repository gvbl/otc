import csstype.*
import emotion.react.css
import io.ktor.http.*
import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.router.useNavigate
import react.useEffectOnce
import react.useState

private val scope = MainScope()

val Home = FC<Props> {
    val navigate = useNavigate()
    var spaces by useState<List<Space>>(emptyList())

    useEffectOnce {
        if (document.cookie.isNotEmpty()) {
            val firstDecode = decodeCookieValue(document.cookie, CookieEncoding.URI_ENCODING)
            val secondDecode = decodeCookieValue(firstDecode, CookieEncoding.URI_ENCODING)
            val ids = secondDecode.split("#s")[1]
            scope.launch {
                spaces = getSpaces(ids)
            }
        }
    }

    BasicNavbar { }
    div {
        className = ClassName("container")
        div {
            css {
                height = 3.em
                overflowX = Overflow.scroll
                whiteSpace = WhiteSpace.nowrap
            }
            if (spaces.isNotEmpty()) {
                spaces.forEach {
                    a {
                        className = ClassName("button mx-2 my-1")
                        href = "/console/${it.id}"
                        +it.name
                    }
                }
            }
        }
    }
    div {
        css(ClassName("mx-2 is-flex is-justify-content-center is-align-items-center")) {
            height = 66.pct
        }
        div {
            className = ClassName("is-flex is-flex-direction-column is-justify-content-center is-align-items-center")
            p {
                className = ClassName("is-size-2 has-text-weight-semibold has-text-centered")
                +"Manage shared desks with OTC"
            }
            p {
                css(ClassName("is-size-5 has-text-centered")) {
                    maxWidth = 640.px
                }
                +"Control your hybrid office from a simple single-screen interface.  Assign desks and see who's in office.  Mobile friendly and collaborative."
            }
            button {
                className = ClassName("mt-6 button is-success is-size-5 has-text-weight-semibold")
                onClick = {
                    scope.launch {
                        val id = addSpace()
                        navigate("/console/$id")
                    }
                }
                +"Get started: OTC is free!"
            }
            p {
                className = ClassName("mt-2 is-size-6 has-text-centered")
                +"No account needed, and we don't collect your email."
            }
        }
    }
    HomeBottomNavbar { }
}