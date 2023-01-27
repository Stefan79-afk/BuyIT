import { Menu, Brightness4, Search, Quiz, Person, ShoppingCart} from "@mui/icons-material";
import { OverridableComponent } from "@mui/material/OverridableComponent";
import { SvgIconTypeMap } from "@mui/material";

interface IconProps {
    type: string,
    className?: string
    style?: React.CSSProperties
}

interface IconType {
    type: string,
    component: OverridableComponent<SvgIconTypeMap<{}, "svg">>
}

const icons: IconType[] = [
    {type: "menu", component: Menu},
    {type: "dark", component: Brightness4},
    {type: "search", component: Search},
    {type: "quiz", component: Quiz},
    {type: "user", component: Person},
    {type: "cart", component: ShoppingCart}
]

function Icon(props: IconProps) : JSX.Element | null {
    const icon = icons.find(icon => icon.type === props.type);

    if(!icon) {
        return null;
    }

    return (
        <icon.component className={props.className} style={props.style} />
    )
}

export default Icon;
