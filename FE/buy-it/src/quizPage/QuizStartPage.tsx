import "../index.css"
import "@fontsource/black-ops-one"
import "@fontsource/chakra-petch"
import NavBar from "../components/NavBar";

function QuizStartPage() {
    return (
            <div style={{fontFamily: "Chakra Petch", backgroundColor: "#1F1F1F", height: "100vh", fontSize: "24px"}} className="text-white" >
                <NavBar />
                <div className="flex flex-col justify-center items-center w-full m-auto text-center" style={{backgroundImage: "url(/buyit.jpg)", backgroundSize: "100% 100%", backgroundColor: "rgba(137, 137, 137, 0.4)", backgroundBlendMode: "multiply", height: "90%"}}>
                    <div style={{height: "50%", width: "90%"}} className="flex flex-col justify-between">
                        <div style={{textAlign: "justify"}}>
                            <h1>Before starting, keep in mind:</h1>
                            <ul>
                                <li className="list-item list-disc">
                                    If we cannot find a build that's recommended for your needs, we will give you the best available options we can find for you
                                </li>
                                <li className="list-item list-disc">
                                    Unfortunately, we may not always be able to find a build for you. If this happens to you, please try adjusting your answers by retaking the quiz
                                </li>
                                <li className="list-item list-disc">
                                    This website's purpose is informative. We always recommend consulting with an expert for further assistance
                                </li>
                            </ul>
                        </div>

                        <button style={{backgroundColor: "#30D5C8", margin: "0 auto 0 auto", fontSize: "24px"}} className="rounded-md p-2 w-3/12">
                        Start Quiz
                        </button>
                    </div>
                </div>
            </div>
    )
}

export default QuizStartPage;