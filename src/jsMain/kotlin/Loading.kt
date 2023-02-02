import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.button

enum class LoadingSize {
    Small,
    Normal,
    Medium,
    Large
}

external interface LoadingProps : Props {
    var size: LoadingSize
}

val Loading = FC<LoadingProps> { props ->
    val loadingSizeClass = when (props.size) {
        LoadingSize.Small -> " is-small"
        LoadingSize.Medium -> " is-medium"
        LoadingSize.Large -> " is-large"
        LoadingSize.Normal -> ""
    }
    button {
        className = ClassName("button is-ghost is-loading$loadingSizeClass")
    }
}