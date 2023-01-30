import Menu from "@mui/icons-material/Menu";
import Brightness4 from "@mui/icons-material/Brightness4";
import Search from "@mui/icons-material/Search";
import Quiz from "@mui/icons-material/Quiz";
import Person from "@mui/icons-material/Person";
import ShoppingCart from "@mui/icons-material/ShoppingCart";
import Flag from "./FlagIcon";

interface IconProps {
    type: string,
    className?: string
    style?: React.CSSProperties
}

interface IconType {
    type: string,
    component: any
}

const icons: IconType[] = [
    {type: "menu", component: Menu},
    {type: "dark", component: Brightness4},
    {type: "search", component: Search},
    {type: "quiz", component: Quiz},
    {type: "user", component: Person},
    {type: "cart", component: ShoppingCart},
    {type: "flag", component: Flag}
]

function Icon(props: IconProps) : JSX.Element | null {
    const icon = icons.find(icon => icon.type === props.type);

    if(!icon) {
        return null;
    }

    if(icon.type === "flag") {
        return <Flag size="lg" code={"us"} className={props.className} styleName={JSON.stringify(props.style)}/>
    }

    return (
        <icon.component className={props.className} style={props.style} />
    )
}

export default Icon;
