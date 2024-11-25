// Importing the SearchForm component from the components
import { Typography } from "antd";
import SearchForm from "../components/SearchForm";
import { AirportProvider } from "../context/AirportProvider";

function SearchFlight() {
  const { Title } = Typography;
  return (
    <>
      <div>
        <Title level={1}>Flight Search App</Title>
      </div>
      <AirportProvider>
        <SearchForm />
      </AirportProvider>
    </>
  );
}

export default SearchFlight;
