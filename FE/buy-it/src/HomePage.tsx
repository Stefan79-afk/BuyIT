import "./index.css"
import "@fontsource/chakra-petch"
import "@fontsource/black-ops-one"
import { useNavigate } from "react-router"

function HomePage() {
  const navigate = useNavigate();
  return (
    <div style={{backgroundImage: "url(/confused.webp)", fontFamily: "Chakra Petch", backgroundSize: "100% 100%", backgroundBlendMode: "multiply", backgroundColor: "rgba(137, 137, 137, 0.6)", height: "100vh", fontSize: "30px"}} className="flex text-white" >
      <div className="flex flex-col justify-between w-10/12 m-auto text-center h-4/6">
        <h1 style={{fontFamily: "Black Ops One", color: "#30D5C8", fontSize: "96px"}}>BuildIT</h1>
        <div>
          <h2>Looking to build your next dream PC, but don't know exactly what to get?</h2>
          <h2>We've got your covered.</h2>
        </div>
        <div>
          <h2><span style={{fontFamily: "Black Ops One", color: "#30D5C8"}}>BuildIT</span> is a quiz that recommends PC builds to users based on their needs.</h2>
          <h2><span style={{fontFamily: "Black Ops One", color: "#30D5C8"}}>IT</span> gives you the best PC build based on your answers.</h2>
        </div>
        <button onClick={() => {navigate("/quiz/start")}} style={{backgroundColor: "#30D5C8", margin: "0 auto 0 auto", fontSize: "24px"}} className="rounded-md p-2 w-3/12">
          OK, let's get started!
        </button>
      </div>
    </div>
  )
}

export default HomePage
