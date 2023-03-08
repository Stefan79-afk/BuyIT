import React from 'react'
import ReactDOM from 'react-dom/client'
import HomePage from './homePage/HomePage'
import {createBrowserRouter, RouterProvider} from "react-router-dom"

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />
  },
  {
    path: "/test",
    element: <h1>Hello this is a test route, ignore pls</h1>
  }
])


ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)


