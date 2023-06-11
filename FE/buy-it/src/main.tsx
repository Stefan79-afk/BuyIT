import {QueryClient, QueryClientProvider} from 'react-query'
import React from 'react'
import ReactDOM from 'react-dom/client'
import HomePage from './HomePage'
import {createBrowserRouter, RouterProvider} from "react-router-dom"
import QuizStartPage from './QuizStartPage'
import QuizQuestionPage from './QuizQuestionPage'
import QuizResultPage from './QuizResultPage';

const queryClient = new QueryClient()

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />
  },
  {
    path: "/quiz/start",
   element: <QuizStartPage />
  }, {
    path: "/quiz/question/:questionID",
    element: <QuizQuestionPage />
  },
  {
    path: "/quiz/results",
    element: <QuizResultPage />
  }
])


ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  </React.StrictMode>,
)


