import React from 'react'
import ReactDOM from 'react-dom/client'
import HomePage from './homePage/HomePage'
import {createBrowserRouter, RouterProvider} from "react-router-dom"
import QuizStartPage from './quizPage/QuizStartPage'

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />
  },
  {
    path: "/quiz/start",
   element: <QuizStartPage />
  }
])


ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)


