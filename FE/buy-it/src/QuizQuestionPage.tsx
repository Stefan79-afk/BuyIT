import "./index.css"
import React, { useState, useEffect } from "react";
import {useParams, useNavigate } from "react-router-dom";

interface Question  {
  id: number;
  question: string;
  filterProp: string;
  answers: Answer[]
}

interface Question_Five {
  id: number;
  question: string;
  filterProp: string;
  value: any;
  branch: number;
}

interface Answer {
  choice: string;
  answer: string;
  value: any;
  branch: number
}

function isQuestionFive(question: Question | Question_Five): question is Question_Five {
  console.log((question as Question_Five).value !== null);
  debugger;
  return (question as Question_Five).value !== null;
}

function isValidQuestion(question: Question | Question_Five): boolean{
  try {
    console.log(question);
    if(isQuestionFive(question)) {
      debugger;
      return question.id !== undefined && question.question !== undefined && question.filterProp !== undefined && question.value !== undefined && question.branch !== undefined;
    } else {
      debugger;
      return question.id !== undefined && question.question !== undefined && question.filterProp !== undefined && question.answers !== undefined;
    }
  } catch(Error) {
    return false;
  }
}

function QuizQuestion(){
  const {questionID} = useParams();
  console.log(`QuestionID is ${questionID}`);
  const [filterObject, setFilterObject] = useState({});
  const [question, setQuestion] = useState<Question | Question_Five>({} as Question | Question_Five);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);
  const [selectedAnswer, setSelectedAnswer] = useState<Answer>({} as Answer);
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    fetch(`http://localhost:8080/odata/BuyITService/Questions(${questionID})`)
      .then((response) => {
        debugger;
        console.log("Response is: ");
        console.log(response);
        if (response.ok) return response.json();
        throw response;
      })
      .then((data) => {
        debugger;
        console.log("Data is ");
        console.log(data);
        setQuestion(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data: ");
        console.error(error);
        setError(error);
        setLoading(false);
      });
  }, [questionID]);

  const handleAnswer = (answer: Answer) => {
    setSelectedAnswer(answer);
  }

  const handleSubmit = () => {
    if(selectedAnswer) {
      setFilterObject(prevState => ({
          ...prevState,
          [question.filterProp]: selectedAnswer.value
      }));

      if(selectedAnswer.branch !== 0) {
        navigate(`/question/${selectedAnswer.branch}`);
      } else {
        navigate("question/results");
      }
    }
  }
  debugger;

  if (loading) return <p>Loading...</p>;

  if (error) return <p>Something went wrong: {error.message}</p>;

  if(!isValidQuestion(question)) return <p>Question was not received properly</p>

  else {
    console.log(question);
    if(isQuestionFive(question)) {
      return (
        <div className="question-container">
          <h1>{question.question}</h1>
          <div className="answers-container">
            <div className="answer">
              <input
                type="text"
                name="answer"
                value={question.value}
                id="inputAnswer"
              />
              <label htmlFor={question.value}>{question.value}</label>
            </div>
          </div>
          <button onClick={handleSubmit}>Submit</button>
        </div>
      );
    } else {
      console.log(question);
      return (
        <div className="question-container">
          <h1>{question.question}</h1>
          <div className="answers-container">
            {question.answers.map((answer) => (
              <div key={answer.choice} className="answer">
                <input
                  type="radio"
                  name="answer"
                  value={answer.value}
                  id={answer.choice}
                  onChange={() => handleAnswer(answer)}
                />
                <label htmlFor={answer.choice}>{answer.answer}</label>
              </div>
            ))}
          </div>
          <button onClick={handleSubmit}>Submit</button>
        </div>
      );
    }
  }
}

export default QuizQuestion;
