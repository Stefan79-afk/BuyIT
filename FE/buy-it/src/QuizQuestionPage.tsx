import "./index.css"
import {useQuery} from "react-query"
import axios from "axios"
import { useParams } from "react-router-dom";

const getQuestion = async (questionID: string | undefined) => {
  try {
    const {data} = await axios.get(`http://localhost:8080/odata/BuyITService/Questions(${questionID})`);
    return data;
  } catch(error) {
    console.error(error);
    throw error;
  }
}

interface Question  {
  _id: string;
  question: string;
  filterProp: string;
  answers: Answer[]
}

interface Question_Seven {
  _id: string;
  question: string;
  value: any;
  branch: number;
}

interface Answer {
  choice: string;
  answer: string;
  value: any;
  branch: number
}

function QuizQuestion() {
  const {questionID} = useParams();
  const {data, isLoading, error} = useQuery<Question | Question_Seven, Error>(["question", questionID], () => getQuestion(questionID));

  if (isLoading) {
    return <div>Loading...</div>
  }
  
  if (error || data == undefined) {
    return <div>An error has occurred: {error?.message}</div>;
  }

  if(data.question == undefined) {
    return (
      <div>
        <h1>Could not get question.</h1>
      </div>
    )
  }
  return (
    <div>
        <h1>{data.question}</h1>
    </div>
  )
}

export default QuizQuestion;
