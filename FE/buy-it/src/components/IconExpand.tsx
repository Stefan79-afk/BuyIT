import Icon from "./Icon";
import ExpandMore from "@mui/icons-material/ExpandMore";
import { FlagIconSize } from "react-flag-icon-css";

interface ExpandIconProps {
    type: string,
    className?: string,
    iconSize?: string | FlagIconSize,
    expandSize?: "small" | "inherit" | "large" | "medium" | undefined
    style?: React.CSSProperties,
    iconClassName?: string,
    iconStyle?: React.CSSProperties,
    expandClassName?: string,
    expandStyle?: React.CSSProperties
}

function ExpandIcon(props: ExpandIconProps): JSX.Element {
    return (
        <span className={props.className} style={props.style}>
            <Icon size={props.iconSize} type={props.type} className={props.iconClassName} style={props.iconStyle} />
            <ExpandMore fontSize={props.expandSize} className={props.expandClassName} style={props.expandStyle} />
        </span>
    )
}

export default ExpandIcon;