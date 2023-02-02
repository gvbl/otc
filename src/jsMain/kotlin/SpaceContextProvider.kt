import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.router.useNavigate

private val scope = MainScope()

class SpaceState(val space: Space?, val update: () -> Unit)

val SpaceContext = createContext<SpaceState>()

external interface SpaceContextProviderProps : PropsWithChildren {
    var spaceId: String
    var onLoaded: () -> Unit
}

val SpaceContextProvider = FC<SpaceContextProviderProps> { props ->
    var space by useState<Space?>(null)
    val navigate = useNavigate()

    useEffectOnce {
        scope.launch {
            try {
                space = getSpace(props.spaceId)
                props.onLoaded()
            } catch (e: NotFoundException) {
                navigate("/not-found")
            }
        }
    }

    SpaceContext.Provider {
        value = SpaceState(space) {
            scope.launch {
                space = getSpace(props.spaceId)
            }
        }
        +props.children
    }
}