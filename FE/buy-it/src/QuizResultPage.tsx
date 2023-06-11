import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import NavBar from "./components/NavBar";

interface Case {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  type: string;
  color: string;
  powerSupply: string;
  sidePanelWindow: string | null;
  externalFans: ExternalFan;
  internalFans: InternalFan;
}

interface ExternalFan {
  twentyFiveInchBays: string;
}

interface InternalFan {
  fiveInchBays: string;
}

interface CPU {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  coreCount: number;
  coreClock: number;
  boostClock: number | null;
  tdp: number;
  integratedGraphics: string | null;
  smt: boolean;
}

interface CPUFan {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  fan_rpm: number;
  noise_level: number | null;
  color: string | null;
  radiator_size: string | null;
}

interface Fan {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  size: string;
  color: string;
  rpm: number;
  airflow: string | null;
  noiseLevel: number | null;
  pwm: boolean;
  amount: number;
}

interface GPU {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  chipset: string;
  memory: number;
  coreClock: number;
  boostClock: number | null;
  color: string;
  length: number;
  amount: number;
}

interface InternalStorage {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  capacity: number;
  priceGB: string;
  type: string;
  cache: number | null;
  formFactor: string;
  interface: string;
}

interface Motherboard {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  socketCPU: string;
  formFactor: string;
  memoryMax: number;
  memorySlots: number;
  color: string;
}

interface NetworkCard {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  networkCardInterface: string;
  ports: string;
  color: string | null;
}

interface OpticalDrive {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  bd: string | null;
  dvd: string | null;
  cd: string | null;
  bd_write: string | null;
  dvd_write: string | null;
  cd_write: string | null;
}

interface PSU {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  formFactor: string;
  efficiencyRating: string | null;
  wattage: number;
  modular: string;
  color: string | null;
}

interface RAM {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  capacity: number;
  type: string;
  frequency: number;
  modules: string;
  priceGB: string | null;
  color: string | null;
  latency: string;
  casLatency: string;
}

interface SoundCard {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  channels: string;
  digitalAudio: string | null;
  snr: string | null;
  sampleRate: string | null;
  chipset: string | null;
  soundCardInterface: string;
}

interface WifiCard {
  id: number;
  name: string;
  rating: number;
  ratingCount: number;
  priceUSD: number;
  protocol: string;
  interface: string;
  color: string | null;
}

interface PCReccomendation {
  case: Case;
  cpu: CPU;
  cpuFan: CPUFan;
  fan: Fan;
  gpu: GPU | null;
  internalStorage: InternalStorage;
  motherboard: Motherboard;
  networkCard: NetworkCard | null;
  opticalDrive: OpticalDrive | null;
  psu: PSU;
  ram: RAM;
  soundCard: SoundCard;
  wifiCard: WifiCard;
}

function QuizResultPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);
  const [results, setResults] = useState<PCReccomendation[] | null>(null);
  const filterObject = location.state;
  useEffect(() => {
    debugger;
    setLoading(true);
    fetch("http://localhost:8080/odata/BuyITService/quiz", {
      method: "POST",
      body: JSON.stringify(filterObject),
      headers: {
        "Content-Type": "application/json",
      }
    })
      .then((response) => {
        console.log(response);
        debugger;
        if (response.ok) {
          return response.json();
        }
        throw response;
      })
      .then((data) => {
        console.log(data);
        debugger;
        setResults(data);
        setLoading(false);
      })
      .catch((error) => {
        console.log(error);
        debugger;
        setError(error);
        setLoading(false);
      });
  }, [filterObject]);

  debugger;
  if (loading) {
    return (
      <div
        style={{
          fontFamily: "Chakra Petch",
          height: "100vh",
          backgroundColor: "#1F1F1F",
        }}
      >
        <NavBar />
        <div
          className="flex flex-row justify-center items-center"
          style={{
            backgroundColor: "rgba(137, 137, 137, 0.4",
            height: "90%",
            backgroundImage: "url(/buyit.jpg)",
            backgroundSize: "100% 100%",
            backgroundBlendMode: "multiply",
          }}
        >
          <h1 className="text-white" style={{ fontSize: "48px" }}>
            Loading...
          </h1>
        </div>
      </div>
    );
  } 

  if (error) {
    let message: string = "Something else went wrong.";
    console.log(error.message);

    if (error instanceof Response && error.status == 404) {
      message = `We couldn't find any builds for your needs.`;
    }
    if (error.message == "Failed to fetch") {
      message = "Failed to connect to the server.";
    }

    return (
      <div
        style={{
          fontFamily: "Chakra Petch",
          height: "100vh",
          backgroundColor: "#1F1F1F",
        }}
      >
        <NavBar />
        <div
          className="flex flex-row justify-center items-center"
          style={{
            backgroundColor: "rgba(137, 137, 137, 0.4",
            height: "90%",
            backgroundImage: "url(/buyit.jpg)",
            backgroundSize: "100% 100%",
            backgroundBlendMode: "multiply",
          }}
        >
          <h1 className="text-white" style={{ fontSize: "48px" }}>
            {message}
          </h1>
        </div>
      </div>
    );
  }

  if(results) {
    return (
        <div
            style={{
                fontFamily: "Chakra Petch",
                height: "100vh",
                backgroundColor: "#1F1F1F",
            }} 
        >
            <NavBar />
            <div
                style={{
                    backgroundColor: "rgba(137, 137, 137, 0.4)",
                    height: "90%",
                    backgroundImage: "url(/buyit.jpg)",
                    backgroundSize: "100% 100%",
                    backgroundBlendMode: "multiply",
                }}
                className="flex flex-col text-white items-center justify-evenly"
            >
                <h1 style={{fontSize: "32px"}}>Here are your Recommendations</h1>
                <div className="flex flex-row justify-center items-center">
                    {results.map((result) => {
                        debugger;
                        if(result.gpu === null && result.networkCard == null && result.opticalDrive == null) {
                            const totalPrice = result.case.priceUSD + result.cpu.priceUSD + result.cpuFan.priceUSD + result.fan.priceUSD * result.fan.amount + result.internalStorage.priceUSD + result.motherboard.priceUSD + result.psu.priceUSD + result.ram.priceUSD + result.soundCard.priceUSD + result.wifiCard.priceUSD;
                            return (
                                <div style={{fontSize: "16px"}} className="bg-black p-2">
                                    <ul>
                                        <li>Case: {result.case.name} - {result.case.priceUSD.toString()}$</li>
                                        <li>CPU: {result.cpu.name} - {result.cpu.priceUSD.toString()}$</li>
                                        <li>CPU Fan: {result.cpuFan.name} - {result.cpuFan.priceUSD}$</li>
                                        <li>Fan: {result.fan.name} x {result.fan.amount} - {result.fan.amount} x {result.fan.priceUSD}$</li>
                                        <li>Internal Storage: {result.internalStorage.name} - {result.internalStorage.priceUSD.toString()}$</li>
                                        <li>Motherboard: {result.motherboard.name} - {result.motherboard.priceUSD.toString()}$</li>
                                        <li>PSU: {result.psu.name} - {result.psu.priceUSD.toString()}$</li>
                                        <li>RAM: {result.ram.name} - {result.ram.priceUSD.toString()}$</li>
                                        <li>Sound Card: {result.soundCard.name} - {result.soundCard.priceUSD.toString()}$</li>
                                        <li>Wifi Card: {result.wifiCard.name} - {result.wifiCard.priceUSD.toString()}$</li>
                                        <li>Total Price: {totalPrice}$</li>
                                    </ul>
                                </div>
                            )
                        } else if(result.networkCard === null && result.opticalDrive == null) {
                            let totalPrice = 0;
                            if(result.gpu?.priceUSD !== undefined) {
                                totalPrice = result.case.priceUSD + result.cpu.priceUSD + result.cpuFan.priceUSD + result.fan.priceUSD * result.fan.amount + result.gpu?.priceUSD * result.gpu?.amount + result.internalStorage.priceUSD + result.motherboard.priceUSD + result.psu.priceUSD + result.ram.priceUSD + result.soundCard.priceUSD + result.wifiCard.priceUSD;
                            }    
                            return (
                                <div style={{fontSize: "16px"}} className="bg-black p-2">
                                    <ul>
                                        <li>Case: {result.case.name} - {result.case.priceUSD.toString()}$</li>
                                        <li>CPU: {result.cpu.name} - {result.cpu.priceUSD.toString()}$</li>
                                        <li>CPU Fan: {result.cpuFan.name} - {result.cpuFan.priceUSD}$</li>
                                        <li>Fan: {result.fan.name} x {result.fan.amount} - {result.fan.amount} x {result.fan.priceUSD}$</li>
                                        <li>GPU: {result.gpu?.name} x {result.gpu?.amount} - {result.gpu?.amount} x {result.gpu?.priceUSD}$</li>
                                        <li>Internal Storage: {result.internalStorage.name} - {result.internalStorage.priceUSD.toString()}$</li>
                                        <li>Motherboard: {result.motherboard.name} - {result.motherboard.priceUSD.toString()}$</li>
                                        <li>PSU: {result.psu.name} - {result.psu.priceUSD.toString()}$</li>
                                        <li>RAM: {result.ram.name} - {result.ram.priceUSD.toString()}$</li>
                                        <li>Sound Card: {result.soundCard.name} - {result.soundCard.priceUSD.toString()}$</li>
                                        <li>Wifi Card: {result.wifiCard.name} - {result.wifiCard.priceUSD.toString()}$</li>
                                        <li>Total Price: {totalPrice}$</li>
                                    </ul>
                                </div>
                            )
                            
                        } else if(result.networkCard === null && result.gpu === null) {
                            let totalPrice = 0;
                            if(result.opticalDrive?.priceUSD !== undefined) {
                                totalPrice = result.case.priceUSD + result.cpu.priceUSD + result.cpuFan.priceUSD + result.fan.priceUSD * result.fan.amount + result.internalStorage.priceUSD + result.motherboard.priceUSD + result.opticalDrive?.priceUSD + result.psu.priceUSD + result.ram.priceUSD + result.soundCard.priceUSD + result.wifiCard.priceUSD;
                            }
                            return (
                                <div style={{fontSize: "16px"}} className="bg-black p-2">
                                    <ul>
                                        <li>Case: {result.case.name} - {result.case.priceUSD.toString()}$</li>
                                        <li>CPU: {result.cpu.name} - {result.cpu.priceUSD.toString()}$</li>
                                        <li>CPU Fan: {result.cpuFan.name} - {result.cpuFan.priceUSD}$</li>
                                        <li>Fan: {result.fan.name} x {result.fan.amount} - {result.fan.amount} x {result.fan.priceUSD}$</li>
                                        <li>Internal Storage: {result.internalStorage.name} - {result.internalStorage.priceUSD.toString()}$</li>
                                        <li>Motherboard: {result.motherboard.name} - {result.motherboard.priceUSD.toString()}$</li>
                                        <li>Optical Drive: {result.opticalDrive?.name} - {result.opticalDrive?.priceUSD.toString()}$</li>
                                        <li>PSU: {result.psu.name} - {result.psu.priceUSD.toString()}$</li>
                                        <li>RAM: {result.ram.name} - {result.ram.priceUSD.toString()}$</li>
                                        <li>Sound Card: {result.soundCard.name} - {result.soundCard.priceUSD.toString()}$</li>
                                        <li>Wifi Card: {result.wifiCard.name} - {result.wifiCard.priceUSD.toString()}$</li>
                                        <li>Total Price: {totalPrice}$</li>
                                    </ul>
                                </div>
                            )
                            
                        } else if(result.gpu === null && result.opticalDrive === null) {
                            let totalPrice = 0;
                            if(result.networkCard?.priceUSD !== undefined) {
                                totalPrice = result.case.priceUSD + result.cpu.priceUSD + result.cpuFan.priceUSD + result.fan.priceUSD * result.fan.amount + result.internalStorage.priceUSD + result.motherboard.priceUSD + result.networkCard?.priceUSD + result.psu.priceUSD + result.ram.priceUSD + result.soundCard.priceUSD + result.wifiCard.priceUSD;
                            }
                            return (
                                <div style={{fontSize: "16px"}} className="bg-black p-2">
                                    <ul>
                                        <li>Case: {result.case.name} - {result.case.priceUSD.toString()}$</li>
                                        <li>CPU: {result.cpu.name} - {result.cpu.priceUSD.toString()}$</li>
                                        <li>CPU Fan: {result.cpuFan.name} - {result.cpuFan.priceUSD}$</li>
                                        <li>Fan: {result.fan.name} x {result.fan.amount} - {result.fan.amount} x {result.fan.priceUSD}$</li>
                                        <li>Internal Storage: {result.internalStorage.name} - {result.internalStorage.priceUSD.toString()}$</li>
                                        <li>Motherboard: {result.motherboard.name} - {result.motherboard.priceUSD.toString()}$</li>
                                        <li>Network Card: {result.networkCard?.name} - {result.networkCard?.priceUSD.toString()}$</li>
                                        <li>PSU: {result.psu.name} - {result.psu.priceUSD.toString()}$</li>
                                        <li>RAM: {result.ram.name} - {result.ram.priceUSD.toString()}$</li>
                                        <li>Sound Card: {result.soundCard.name} - {result.soundCard.priceUSD.toString()}$</li>
                                        <li>Wifi Card: {result.wifiCard.name} - {result.wifiCard.priceUSD.toString()}$</li>
                                        <li>Total Price: {totalPrice}$</li>
                                    </ul>
                                </div>
                            )    
                        } else if(result.networkCard === null) {
                            let totalPrice = 0;
                            if(result.gpu?.priceUSD !== undefined && result.opticalDrive?.priceUSD !== undefined) {
                                totalPrice = result.case.priceUSD + result.cpu.priceUSD + result.cpuFan.priceUSD + result.fan.priceUSD * result.fan.amount + result.gpu?.priceUSD * result.gpu?.amount + result.internalStorage.priceUSD + result.motherboard.priceUSD + result.opticalDrive?.priceUSD + result.psu.priceUSD + result.ram.priceUSD + result.soundCard.priceUSD + result.wifiCard.priceUSD;
                            }
                            return (
                                <div style={{fontSize: "16px"}} className="bg-black p-2">
                                    <ul>
                                        <li>Case: {result.case.name} - {result.case.priceUSD.toString()}$</li>
                                        <li>CPU: {result.cpu.name} - {result.cpu.priceUSD.toString()}$</li>
                                        <li>CPU Fan: {result.cpuFan.name} - {result.cpuFan.priceUSD}$</li>
                                        <li>Fan: {result.fan.name} x {result.fan.amount} - {result.fan.amount} x {result.fan.priceUSD}$</li>
                                        <li>GPU: {result.gpu?.name} x {result.gpu?.amount} - {result.gpu?.amount} x {result.gpu?.priceUSD}$</li>
                                        <li>Internal Storage: {result.internalStorage.name} - {result.internalStorage.priceUSD.toString()}$</li>
                                        <li>Motherboard: {result.motherboard.name} - {result.motherboard.priceUSD.toString()}$</li>
                                        <li>Optical Drive: {result.opticalDrive?.name} - {result.opticalDrive?.priceUSD.toString()}$</li>
                                        <li>PSU: {result.psu.name} - {result.psu.priceUSD.toString()}$</li>
                                        <li>RAM: {result.ram.name} - {result.ram.priceUSD.toString()}$</li>
                                        <li>Sound Card: {result.soundCard.name} - {result.soundCard.priceUSD.toString()}$</li>
                                        <li>Wifi Card: {result.wifiCard.name} - {result.wifiCard.priceUSD.toString()}$</li>
                                        <li>Total Price: {totalPrice}$</li>
                                    </ul>
                                </div>
                            )
                        } else if(result.opticalDrive === null) {
                            let totalPrice = 0;
                            if(result.gpu?.priceUSD !== undefined && result.networkCard?.priceUSD !== undefined) {
                                totalPrice = result.case.priceUSD + result.cpu.priceUSD + result.cpuFan.priceUSD + result.fan.priceUSD * result.fan.amount + result.gpu?.priceUSD * result.gpu?.amount + result.internalStorage.priceUSD + result.motherboard.priceUSD + result.networkCard?.priceUSD + result.psu.priceUSD + result.ram.priceUSD + result.soundCard.priceUSD + result.wifiCard.priceUSD;
                            }
                            return (
                                <div style={{fontSize: "16px"}} className="bg-black p-2">
                                    <ul>
                                        <li>Case: {result.case.name} - {result.case.priceUSD.toString()}$</li>
                                        <li>CPU: {result.cpu.name} - {result.cpu.priceUSD.toString()}$</li>
                                        <li>CPU Fan: {result.cpuFan.name} - {result.cpuFan.priceUSD}$</li>
                                        <li>Fan: {result.fan.name} x {result.fan.amount} - {result.fan.amount} x {result.fan.priceUSD}$</li>
                                        <li>GPU: {result.gpu?.name} x {result.gpu?.amount} - {result.gpu?.amount} x {result.gpu?.priceUSD}$</li>
                                        <li>Internal Storage: {result.internalStorage.name} - {result.internalStorage.priceUSD.toString()}$</li>
                                        <li>Motherboard: {result.motherboard.name} - {result.motherboard.priceUSD.toString()}$</li>
                                        <li>Network Card: {result.networkCard?.name} - {result.networkCard?.priceUSD.toString()}$</li>
                                        <li>PSU: {result.psu.name} - {result.psu.priceUSD.toString()}$</li>
                                        <li>RAM: {result.ram.name} - {result.ram.priceUSD.toString()}$</li>
                                        <li>Sound Card: {result.soundCard.name} - {result.soundCard.priceUSD.toString()}$</li>
                                        <li>Wifi Card: {result.wifiCard.name} - {result.wifiCard.priceUSD.toString()}$</li>
                                        <li>Total Price: {totalPrice}$</li>
                                    </ul>
                                </div>
                            )
                        } else if(result.gpu === null) {
                            let totalPrice = 0;
                            if(result.networkCard?.priceUSD !== undefined && result.opticalDrive?.priceUSD !== undefined) {
                                totalPrice = result.case.priceUSD + result.cpu.priceUSD + result.cpuFan.priceUSD + result.fan.priceUSD * result.fan.amount + result.internalStorage.priceUSD + result.motherboard.priceUSD + result.networkCard?.priceUSD + result.opticalDrive?.priceUSD + result.psu.priceUSD + result.ram.priceUSD + result.soundCard.priceUSD + result.wifiCard.priceUSD;
                            }
                            return (
                                <div style={{fontSize: "16px"}} className="bg-black p-2">
                                    <ul>
                                        <li>Case: {result.case.name} - {result.case.priceUSD.toString()}$</li>
                                        <li>CPU: {result.cpu.name} - {result.cpu.priceUSD.toString()}$</li>
                                        <li>CPU Fan: {result.cpuFan.name} - {result.cpuFan.priceUSD}$</li>
                                        <li>Fan: {result.fan.name} x {result.fan.amount} - {result.fan.amount} x {result.fan.priceUSD}$</li>
                                        <li>Internal Storage: {result.internalStorage.name} - {result.internalStorage.priceUSD.toString()}$</li>
                                        <li>Motherboard: {result.motherboard.name} - {result.motherboard.priceUSD.toString()}$</li>
                                        <li>Network Card: {result.networkCard?.name} - {result.networkCard?.priceUSD.toString()}$</li>
                                        <li>Optical Drive: {result.opticalDrive?.name} - {result.opticalDrive?.priceUSD.toString()}$</li>
                                        <li>PSU: {result.psu.name} - {result.psu.priceUSD.toString()}$</li>
                                        <li>RAM: {result.ram.name} - {result.ram.priceUSD.toString()}$</li>
                                        <li>Sound Card: {result.soundCard.name} - {result.soundCard.priceUSD.toString()}$</li>
                                        <li>Wifi Card: {result.wifiCard.name} - {result.wifiCard.priceUSD.toString()}$</li>
                                        <li>Total Price: {totalPrice}$</li>
                                    </ul>
                                </div>
                            )
                        } else {
                            let totalPrice = 0;
                            if(result.networkCard?.priceUSD !== undefined && result.opticalDrive?.priceUSD !== undefined && result.gpu?.priceUSD !== undefined) {
                                totalPrice = result.case.priceUSD + result.cpu.priceUSD + result.cpuFan.priceUSD + result.fan.priceUSD * result.fan.amount + result.gpu?.priceUSD * result.gpu?.amount + result.internalStorage.priceUSD + result.motherboard.priceUSD + result.networkCard?.priceUSD + result.opticalDrive?.priceUSD + result.psu.priceUSD + result.ram.priceUSD + result.soundCard.priceUSD + result.wifiCard.priceUSD;
                            }
                            return (
                                <div style={{fontSize: "16px"}} className="bg-black p-2">
                                    <ul>
                                        <li>Case: {result.case.name} - {result.case.priceUSD.toString()}$</li>
                                        <li>CPU: {result.cpu.name} - {result.cpu.priceUSD.toString()}$</li>
                                        <li>CPU Fan: {result.cpuFan.name} - {result.cpuFan.priceUSD}$</li>
                                        <li>Fan: {result.fan.name} x {result.fan.amount} - {result.fan.amount} x {result.fan.priceUSD}$</li>
                                        <li>GPU: {result.gpu?.name} x {result.gpu?.amount} - {result.gpu?.amount} x {result.gpu?.priceUSD}$</li>
                                        <li>Internal Storage: {result.internalStorage.name} - {result.internalStorage.priceUSD.toString()}$</li>
                                        <li>Motherboard: {result.motherboard.name} - {result.motherboard.priceUSD.toString()}$</li>
                                        <li>Network Card: {result.networkCard?.name} - {result.networkCard?.priceUSD.toString()}$</li>
                                        <li>Optical Drive: {result.opticalDrive?.name} - {result.opticalDrive?.priceUSD.toString()}$</li>
                                        <li>PSU: {result.psu.name} - {result.psu.priceUSD.toString()}$</li>
                                        <li>RAM: {result.ram.name} - {result.ram.priceUSD.toString()}$</li>
                                        <li>Sound Card: {result.soundCard.name} - {result.soundCard.priceUSD.toString()}$</li>
                                        <li>Wifi Card: {result.wifiCard.name} - {result.wifiCard.priceUSD.toString()}$</li>
                                        <li>Total Price: {totalPrice}$</li>
                                    </ul>
                                </div>
                            )
                        }  
                    })}
                </div>
                
                <button style={{backgroundColor: "#30D5C8"}} className="p-2" onClick={() => {
                    navigate("/");
                }}>
                    Back to Home Page.
                </button>

            </div>
        </div>
    )
  } else {
    return (
        <div style={{fontFamily: "Chakra Petch", height: "100vh", backgroundColor: "#1F1F1F"}}>
        <NavBar />
        <div 
            style={{backgroundColor: "rgba(137, 137, 137, 0.4)", height: "90%", backgroundImage: "url(/buyit.jpg)", backgroundSize: "100% 100%", backgroundBlendMode: "multiply"}}
            className="flex flex-col">
                <h1>We couldn't find any results for you. Please retake the quiz and adjust your needs.</h1>
                <button style={{backgroundColor: "#30D5C8"}} className= "p-2" onClick={() => {
                    navigate("/quiz/question/1");
                }}>
                    Retake Quiz
                </button>
        </div>
    </div>
    )
  }
}

export default QuizResultPage;
