import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import "./App.css";

// Importing the MainPage component from the pages directory
import SearchFlight from "./pages/SearchFlight";
import { Typography } from "antd";
import { FlightProvider } from "./context/FlightProvider";
import { FlightRequestProvider } from "./context/FlightRequestProvider";
import ResultsPage from "./pages/ResultsPage";
import DetailsPage from "./pages/DetailsPage";

const { Title } = Typography;

const App: React.FC = () => {
  return (
    <Router>
      <FlightProvider>
        <FlightRequestProvider>
          <Routes>
            <Route path="/" element={<SearchFlight />} />
            <Route path="/results-page" element={<ResultsPage />} />
            <Route path="/details-flight/:id" element={<DetailsPage />} />
          </Routes>
        </FlightRequestProvider>
      </FlightProvider>
      <div>
        <Title level={5}>Francisco Rafael Lezama Hernandez - 2024</Title>
      </div>
    </Router>
  );
};

export default App;
