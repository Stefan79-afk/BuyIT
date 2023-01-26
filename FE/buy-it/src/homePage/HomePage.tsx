import { useState } from 'react'
import NavBar from './components/NavBar'


function HomePage() {
  const [count, setCount] = useState(0)

  return (
    <NavBar />
  )
}

export default HomePage
