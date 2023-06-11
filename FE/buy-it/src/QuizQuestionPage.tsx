import "./index.css";
import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "@fontsource/chakra-petch";
import NavBar from "./components/NavBar";

interface Question {
  id: number;
  question: string;
  filterProp: string;
  answers: Answer[];
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
  branch: number;
}

interface FilterObject {
  isKnowledgeable: boolean;
  [key: string]: any;
}

function isQuestionFive(
  question: Question | Question_Five
): question is Question_Five {
  return (question as Question_Five).value !== null;
}

function isValidQuestion(question: Question | Question_Five): boolean {
  try {
    if (isQuestionFive(question)) {
      return (
        question.id !== undefined &&
        question.question !== undefined &&
        question.filterProp !== undefined &&
        question.value !== undefined &&
        question.branch !== undefined
      );
    } else {
      return (
        question.id !== undefined &&
        question.question !== undefined &&
        question.filterProp !== undefined &&
        question.answers !== undefined
      );
    }
  } catch (Error) {
    return false;
  }
}

function QuizQuestion() {
  const { questionID } = useParams();
  const [filterObject, setFilterObject] = useState<FilterObject>(
    {} as FilterObject
  );
  const [question, setQuestion] = useState<Question | Question_Five>(
    {} as Question | Question_Five
  );
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);
  const [selectedAnswer, setSelectedAnswer] = useState<Answer>({} as Answer);
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    fetch(`http://localhost:8080/odata/BuyITService/Questions(${questionID})`)
      .then((response) => {
        if (response.ok) return response.json();
        throw response;
      })
      .then((data) => {
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
  };

  const handleSubmit = () => {
    if (selectedAnswer) {
      setFilterObject((prevState) => ({
        ...prevState,
        [question.filterProp]: selectedAnswer.value,
      }));

      console.log(filterObject);
      if (question.id === 6 && filterObject.isKnowledgeable === false) {
        navigate("/quiz/question/7");
      } else if (selectedAnswer.branch !== 0) {
        navigate(`/quiz/question/${selectedAnswer.branch}`);
      } else {
        navigate("/quiz/results", { state: filterObject });
      }
    }
  };

  if (loading)
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

  if (error) {
    let message: string = "Something else went wrong.";
    console.log(error.message);

    if (error instanceof Response && error.status == 404) {
      message = `Question ${questionID} not found.`;
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

  console.log(filterObject);

  if (!isValidQuestion(question))
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
            Question could not be retrieved properly
          </h1>
        </div>
      </div>
    );
  else {
    if (isQuestionFive(question)) {
      return (
        <div
          style={{
            fontFamily: "Chakra Petch",
            height: "100vh",
            fontSize: "24px",
            backgroundColor: "#1F1F1F",
          }}
        >
          <NavBar />
          <div
            style={{
              backgroundColor: "rgba(137, 137, 137, 0.4",
              height: "90%",
              backgroundImage: "url(/buyit.jpg)",
              backgroundSize: "100% 100%",
              backgroundBlendMode: "multiply",
            }}
            className="flex flex-col justify-evenly items-center text-white"
          >
            <div className="w-4/5" style={{ backgroundColor: "#2B2828" }}>
              <h1 style={{ backgroundColor: "#001540" }} className="p-4">
                {question.id}. {question.question}
              </h1>
              <div className="flex flex-col justify-center items-center">
                <div className="flex flex-row items-center p-4">
                  <input
                    id={question.id.toString()}
                    type="text"
                    name="answer"
                    value={question.value}
                    onChange={(event) => {
                      console.log(event.target.value);
                      console.log(question);
                      setQuestion((prevState) => ({
                        ...prevState,
                        value: event.target.value,
                      }));
                      console.log(question);
                    }}
                    className="bg-black p-3 text-white"
                  />
                  <label
                    className="mx-2"
                    style={{ fontSize: "40px" }}
                    htmlFor={question.value}
                  >
                    $
                  </label>
                </div>
              </div>
            </div>
            <button
              className="p-2 bg-green-700"
              onClick={() => {
                console.log(question);
                setFilterObject((prevState) => ({
                  ...prevState,
                  [question.filterProp]: question.value,
                }));

                navigate("/quiz/question/6");
              }}
            >
              Submit
            </button>
          </div>
        </div>
      );
    } else {
      return (
        <div
          style={{
            fontFamily: "Chakra Petch",
            height: "100vh",
            fontSize: "24px",
            backgroundColor: "#1F1F1F",
          }}
        >
          <NavBar />
          <div
            style={{
              fontFamily: "Chakra Petch",
              backgroundColor: "rgba(137, 137, 137, 0.4)",
              height: "90%",
              fontSize: "24px",
              backgroundImage: "url(/buyit.jpg)",
              backgroundSize: "100% 100%",
              backgroundBlendMode: "multiply",
            }}
            className="flex flex-col justify-evenly items-center text-white"
          >
            <div className="w-4/5" style={{ backgroundColor: "#2B2828" }}>
              <h1 style={{ backgroundColor: "#001540" }} className="p-4">
                {question.id}. {question.question}
              </h1>
              <div className="flex flex-col justify-between p-3">
                {question.answers.map((answer) => (
                  <div key={answer.choice} className="bg-black py-2 px-3 my-2">
                    <input
                      type="radio"
                      name="answer"
                      value={answer.value}
                      id={answer.choice}
                      onChange={() => handleAnswer(answer)}
                      className="w-4 h-4 text-blue-600 bg-gray-700 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                    />
                    <label className="mx-2" htmlFor={answer.choice}>
                      {answer.answer}
                    </label>
                  </div>
                ))}
              </div>
            </div>
            <button className="p-2 bg-green-700" onClick={handleSubmit}>
              Submit
            </button>
          </div>
        </div>
      );
    }
  }
}

export default QuizQuestion;
