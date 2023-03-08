import Icon from "./Icon"

interface SearchBarProps {
    searchIconClassName?: string,
    searchIconStyle?: React.CSSProperties,
    searchIconSize?: string,
    className?: string
    style?: React.CSSProperties,
    inputClassName?: string,
    inputStyle?: React.CSSProperties

}

function SearchBar(props: SearchBarProps): JSX.Element {
    return (
        <span className={props.className} style={props.style}>
            <input placeholder="Search for a product" style={props.inputStyle} className={props.inputClassName} type="text" />
            <Icon type={"search"} size={props.searchIconSize} style={props.inputStyle} className={props.searchIconClassName} />
        </span>
    )
}

export default SearchBar;