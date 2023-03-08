import NavBar from '../components/NavBar'
import "../index.css"
import "@fontsource/chakra-petch"

function HomePage() {
  return (
    <body style={{height: "100vh"}}>
      <NavBar />
      <div style={{backgroundImage: "url(/homepage/buyit.jpg)", backgroundSize: "cover",  height: "95%"}} className="flex flew-row items-center justify-center mt-12" >
        <h1 style={{fontFamily: "Chakra Petch", color: "#30D5C8"}} className="lg:text-7xl md:text-4xl">
            You want IT, we got IT
        </h1>
      </div>

      <div style={{backgroundImage: "url(/homepage/confused.webp)", backgroundSize: "cover", height: "100%"}} className="flex flex-col items-center justify-center gap-y-10">
        <h1 style={{fontFamily: "Chakra Petch", color: "#30D5C8"}} className="lg:text-7xl md:text-4xl w-7/12" >
          Having trouble deciding? Take our recommendation quiz!
        </h1>
        <button style={{backgroundColor: "#30D5C8"}} className="rounded-md p-3">
          <h1 className="text-white lg:text-4xl md:text-xl" style={{fontFamily: "Chakra Petch"}} >
            Take quiz
          </h1>
        </button>
      </div>

    </body>
  )
}

export default HomePage
