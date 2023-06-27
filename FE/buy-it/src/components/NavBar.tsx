import "../index.css"
import "@fontsource/black-ops-one"

function NavBar() {
    return (
        <nav className="h-1/12 flex flex-row justify-center items-center" style={{backgroundColor: "#0B0B45",width: "100%", height: "10%"}}>
            <h1 style={{fontFamily: "Black Ops One", color: "#30D5C8", fontSize: "64px"}}>BuildIT</h1>
        </nav>
    )
}

export default NavBar;