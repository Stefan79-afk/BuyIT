import MenuIcon from "@mui/icons-material/Menu";
import { ExpandMore } from "@mui/icons-material";
import "@fontsource/black-ops-one"
import SearchIcon from '@mui/icons-material/Search';
import "../../index.css"

function NavBar() {
    return (
       <div className="bg-blue-900 flex flex-row items-center">
            <ProductMenu />
            <h1 style={{fontFamily: "Black Ops One"}} className="text-green-400 text-5xl">
                BuyIT
            </h1>
       </div>
    )
}

function ProductMenu() {
    return (
        <span className="flex flex-row items-end">
            <MenuIcon fontSize="large" className="text-white"/>
            <ExpandMore fontSize="small" className="text-white" />
        </span>
    )
}

export default NavBar