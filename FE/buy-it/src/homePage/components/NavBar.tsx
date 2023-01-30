import "@fontsource/black-ops-one"
import "../../index.css"
import Icon from "./Icon"
import ExpandIcon from "./IconExpand"
import SearchBar from "./SearchBar"

function NavBar() {
    return (
        <nav className="flex flex-row items-center justify-between" style={{backgroundColor: "#0B0B45", }}>
            <ExpandIcon type="menu" className="flex flex-row items-end h-8 scale-150 ml-3 pb-1 pl-1 text-white" expandClassName="scale-50 -ml-2.5 -mb-1" />
            <h1 style={{ fontFamily: "Black Ops One", color: "#30D5C8" }} className="text-5xl">
                BuyIT
            </h1>
            <SearchBar inputClassName="ml-2 bg-gray-700 focus:outline-none w-11/12" className="bg-gray-700 flex flex-row justify-between p-1 rounded-xl text-white" style={{width: "700px"}}/>
            <div className="flex flex-row items-center justify-between w-28">
                <Icon type="dark" className="text-white scale-125" />
                <span className="text-white dark:text-white">
                    <ExpandIcon type="flag" iconClassName="scale-x-125" className="flex flex-row items-end h-8 scale-125 ml-3 pb-1 pl-1" expandClassName="scale-50 -ml-0.5 -mb-2"/>
                </span>
            </div>
            <div className="flex flex-row items-center justify-between mr-2 w-44" >
                <Icon type="quiz" className="scale-125 text-white" />
                <ExpandIcon type="user" className="flex flex-row items-end h-8 scale-150 ml-3 pb-1 pl-1 text-white" expandClassName="scale-50 -ml-2.5 -mb-1.5" />
                <ExpandIcon type="cart" className="flex flex-row items-end h-8 scale-150 ml-1.5 pb-1 pl-1 text-white" expandClassName="scale-50 -ml-2.5 -mb-1.5" />
            </div>
        </nav>
    )
}

/**
 * <span className="bg-gray-700 flex flex-row items-center h-8">
            <input placeholder="Search for a product" style={{width: '700px'}} className="bg-gray-700 h-2/3 focus:outline-none m-2 text-white" type="text" />
            <Icon type={"search"} className="text-white scale-75" />
        </span>
 */

export default NavBar