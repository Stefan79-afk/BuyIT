import Icon from "./Icon";
import { ExpandMore } from "@mui/icons-material";

interface ExpandIconProps {
    type: string,
    className?: string,
    style?: React.CSSProperties,
    iconClassName?: string,
    iconStyle?: React.CSSProperties,
    expandClassName?: string,
    expandStyle?: React.CSSProperties
}

function ExpandIcon(props: ExpandIconProps): JSX.Element {
    return (
        <span className={props.className}>
            <Icon type={props.type} className={props.iconClassName} style={props.iconStyle} />
            <ExpandMore className={props.expandClassName} style={props.expandStyle} />
        </span>
    )
}

export default ExpandIcon;