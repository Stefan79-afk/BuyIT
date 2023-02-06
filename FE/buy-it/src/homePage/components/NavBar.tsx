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
      <nav className=" text-white flex flex-row justify-between items-center h-auto md:p-0.5 md:gap-x-0" style={{backgroundColor: "#0B0B45", width: "100vw", height: "60px"}}>
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
/**
 * <nav className="text-white flex flex-row justify-between items-center sm:gap-x-0" style={{ backgroundColor: "#0B0B45", height: "50px", width: "100vw" }}>
            <ExpandIcon className="flex flex-row items-end lg:scale-100 md:scale-90 sm:scale-75"  type="menu" expandSize="medium" expandClassName="scale-75 -ml-1.5" iconSize="large" />
            <h1 style={{ fontFamily: "Black Ops One", color: "#30D5C8"}} className="text-4xl lg:scale-100 md:scale-90 sm:scale-75">
                BuyIT
            </h1>
            <SearchBar className="bg-gray-700 flex justify-between items-center p-1 rounded-md w-4/12 lg:scale-100 md:scale-90 sm:scale-75" style={{minWidth: "200px"}} inputClassName="bg-gray-700 focus:outline-none w-11/12"/>
            <div className="flex flex-row items-center justify-between lg:scale-100 md:scale-90 sm:scale-75" >
                <Icon className="lg:scale-100 md:scale-90 sm:scale-75 mr-1" type="dark" size="large" />
                <ExpandIcon className="flex flex-row items-end lg:scale-100 ml-1 md:scale-90 sm:scale-75" expandClassName="scale-75 -ml-0.5 -mb-1.5" iconClassName="bg-yellow-500" type="flag" iconSize="2x" />
            </div>
            <div className=" flex flex-row justify-between lg:scale-100 md:scale-90 sm:scale-75 items-center" >
                <Icon className="flex flex-row items-end" type="quiz" size="large"/>
                <ExpandIcon className="flex flex-row items-end" expandClassName="scale-75 -ml-2 -mb-1" iconSize="large" type="user" />
                <ExpandIcon className="" expandClassName="scale-75 -ml-2 -mb-5" iconClassName="" iconSize="large" type="cart" />
            </div>
        </nav>
 */

export default NavBar