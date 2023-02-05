import csstype.*
import emotion.react.css
import kotlinx.browser.window
import kotlinx.js.timers.setTimeout
import react.FC
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.figure
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import react.dom.html.ReactHTML.span
import react.useState
import kotlin.time.Duration

const val DragDropDemoPath = "https://storage.googleapis.com/otc-assets/resources/gifs/help/dd-demo.gif"
const val DragDropPlaceholderPath =
    "https://storage.googleapis.com/otc-assets/resources/gifs/help/dd-demo-first-frame.png"
const val DragDropDemoMobilePath = "https://storage.googleapis.com/otc-assets/resources/gifs/help/dd-demo-mobile.gif"
const val DragDropMobilePlaceholderPath =
    "https://storage.googleapis.com/otc-assets/resources/gifs/help/dd-demo-mobile-first-frame.png"

val HelpModal = FC<ModalProps> { props ->
    val isMobile = useIsMobile()
    val placeholderPath = if (isMobile) DragDropPlaceholderPath else DragDropMobilePlaceholderPath
    val demoPath = if (isMobile) DragDropDemoMobilePath else DragDropDemoPath
    var gifSrc by useState(demoPath)
    Modal {
        isModalActive = props.isModalActive
        onClose = props.onClose
        header {
            className = ClassName("modal-card-head")
            p {
                className = ClassName("modal-card-title")
                +"Help"
            }
            button {
                className = ClassName("delete")
                onClick = {
                    props.onClose()
                }
            }
        }
        section {
            className = ClassName("modal-card-body is-flex is-flex-direction-column")
            div {
                className = ClassName("notification is-warning is-light")
                span {
                    className = ClassName("has-text-weight-bold")
                    +"Bookmark this page!"
                }
                +" OTC does not use accounts.  Anyone who obtains the URL to this page may access your workspace.  Only share it with your team!"
                button {
                    css(ClassName("button mt-2")) {
                        float = Float.right
                    }
                    onClick = {
                        window.navigator.clipboard.writeText(window.location.href)
                    }
                    +"Copy URL"
                }
            }
            div {
                className = ClassName("box")
                div {
                    css {
                        position = Position.relative
                    }
                    figure {
                        className = ClassName("is-flex is-justify-content-center")
                        img {
                            css {
                                if (isMobile) {
                                    width = 187.px
                                    height = 322.px
                                }
                            }
                            src = gifSrc
                        }
                    }
                    button {
                        css(ClassName("button is-light is-small is-rounded")) {
                            position = Position.absolute
                            right = 0.px
                            bottom = 0.px
                            marginRight = (-1).rem
                            marginBottom = (-1).rem
                            padding = important(Padding(0.px, 0.9.em, 0.px, 1.1.em))
                        }
                        onClick = {
                            gifSrc = placeholderPath
                            setTimeout(Duration.ZERO) {
                                gifSrc = demoPath
                            }
                        }
                        span {
                            className = ClassName("icon")
                            i {
                                className = ClassName("fas fa-play fa-lg")
                            }
                        }
                    }
                }
            }
            div {
                className = ClassName("is-align-self-center")
                span {
                    +"Contact: "
                }
                a {
                    href = "mailto:support@otcontrol.com"
                    +"support@otcontrol.com"
                }
            }
        }

        footer {
            className = ClassName("modal-card-foot")
            button {
                className = ClassName("button is-success")
                onClick = {
                    props.onClose()
                }
                +"Done"
            }
        }
    }
}