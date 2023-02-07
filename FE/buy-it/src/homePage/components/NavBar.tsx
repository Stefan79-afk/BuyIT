import "@fontsource/black-ops-one"
import { useState } from "react"
import "../../index.css"
import Icon from "./Icon"
import ExpandIcon from "./IconExpand"
import SearchBar from "./SearchBar"


function NavBar() {

    const [size, setSize] = useState("large");
    window.addEventListener("resize", () => {
        const width = window.innerWidth;

        if(width < 500) {
            setSize("small");
        } 
        
        else if(width >= 500 && width < 1000) {
            setSize("medium");
        }

        else {
            setSize("large");
        }
    })

    // TODO: For medium and higher screen widths, the following is returned.
    // For small widths, implement custom layout (different return)
    return (
      <nav className=" text-white flex flex-row justify-between items-center h-auto md:p-0.5 md:gap-x-0 fixed -mt-12" style={{backgroundColor: "#0B0B45",width: "100%", height: "60px",}}>
        <ExpandIcon type="menu" iconSize={size} expandSize={window.innerWidth < 1000 ? "" : "small"} className=" flex flex-row items-end" expandClassName="md:-ml-1 lg:mb-0.5" iconClassName="" />
        <h1 className="md:text-3xl lg:text-5xl"  style={{fontFamily: "Black Ops One", color: "#30D5C8"}} >
            BuyIT
        </h1>
        <SearchBar searchIconSize={size} className="bg-gray-700 md:text-xs lg:text-lg flex justify-between items-center md:p-1 md:w-2/6 rounded-md" inputClassName=" w-10/12 bg-gray-700  focus:outline-none" />

        <div className="flex flex-row justify-between items-center" style={window.innerWidth < 1000 ? {width: "13%", maxWidth: "78px"}: {width: "11.5%", maxWidth: "130px"}} >
            <Icon type="dark" size={window.innerWidth < 1000 ? "small" : size} className=" md:mr-1" />
            <ExpandIcon type="flag" iconSize={window.innerWidth < 1000 ? "lg" : "2x"} expandSize={window.innerWidth < 1000 ? "" : "small"} className="flex flex-row items-end" expandClassName="md:-ml-0.5 md:-mb-1 lg:-mb-1"/> 
        </div>

        <div className="flex flex-row justify-between items-center max-w-xs" style={window.innerWidth < 1000 ? {width: "20%", maxWidth: "127px"} : {width: "15%", maxWidth: "173px"}}>
            <Icon type="quiz" size={size} />
            <ExpandIcon type="user" iconSize={size} className="flex flex-row items-end" expandSize={window.innerWidth < 1000 ? "" : "small"} expandClassName="md:-ml-1.5 md:-mb-0.5" />
            <ExpandIcon type="cart" iconSize={size} expandSize={window.innerWidth < 1000 ? "" : "small"} expandClassName="md:-ml-1.5 md:-mb-0.5 md:-mb-3" />
        </div>
      </nav>
    )
}
export default NavBar