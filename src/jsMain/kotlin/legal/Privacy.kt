package legal

import csstype.Color
import csstype.WhiteSpace
import email
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.pre
import rootURL

val Privacy = FC<Props> {
    pre {
        css {
            background = Color("white")
            whiteSpace = WhiteSpace.preLine
        }
        +"""
        We do not collect any personal information.
        
        Cookies are used to store identifiers of the spaces created on your device and are not used for any other purposes.
        
        We may update this privacy policy from time to time in order to reflect, for example, changes to our practices or for other operational, legal or regulatory reasons.
        
        For more information about our privacy practices, if you have questions, or if you would like to make a complaint, please contact us by e-mail at $email."""
    }
}