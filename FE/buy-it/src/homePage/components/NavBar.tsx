import MenuIcon from "@mui/icons-material/Menu";
import { ExpandMore } from "@mui/icons-material";
import "@fontsource/black-ops-one"
import SearchIcon from '@mui/icons-material/Search';
import Brightness4Icon from "@mui/icons-material/Brightness4"
import QuizIcon from "@mui/icons-material/Quiz"
import PersonIcon from "@mui/icons-material/Person";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart"
import "../../index.css"

interface IconType {
    type: String
}

function NavBar() {
    return (
        <div className="bg-blue-900 flex flex-row items-center justify-between">
            <Icon type={"menu"}/>
            <h1 style={{ fontFamily: "Black Ops One" }} className="text-green-400 text-5xl">
                BuyIT
            </h1>
            <SearchBar />
            <Icon type="dark" />
            <div className="flex flex-row items-center">
                <Icon type={"quiz"} />
                <Icon type={"user"} />
                <Icon type={"cart"} />
            </div>
        </div>
    )
}

function Icon(props: IconType) {
    
    let icon;

    switch (props.type) {
        case "menu":
            icon = <MenuIcon fontSize="medium" className="text-white" />;
            break;
        case "dark":
            icon = <Brightness4Icon fontSize="medium" className="text-white" />
            break;
        case "quiz":
            icon = <QuizIcon fontSize="medium" className="text-white" />
            break;
        case "user":
            icon = <PersonIcon fontSize="medium" className="text-white" />
            break;
        case "cart":
            icon = <ShoppingCartIcon fontSize="medium" className="text-white" />
            break;
    }

    return (
        <span className="flex flex-row items-end">
            {icon}
            <ExpandMore fontSize="small" className="text-white" />
        </span>
    )
}

function SearchBar() {
    return (
        <span className="bg-gray-700 flex flex-row items-center h-8">
            <input placeholder="Search for a product" style={{width: '700px'}} className="bg-gray-700 h-2/3 focus:outline-none m-2 text-white" type="text" />
            <SearchIcon fontSize="large" className="text-white scale-75" />
        </span>
    )
}

export default NavBar