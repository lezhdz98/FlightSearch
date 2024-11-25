// Importing the SearchForm component from the components
import FlightSegment from "../components/ResultsPage/FlightSegment";
import PriceInfo from "../components/ResultsPage/PriceInfo";
import GeneralInfo from "../components/GeneralInfo";
import DetailsButton from "../components/ResultsPage/DetailsButton";
import SearchButton from "../components/SearchButton";
import { useFlight } from "../hooks/useFlight";
import { Button, Typography } from "antd";
import { useFlightRequest } from "../hooks/useFlightRequest";
import { useState } from "react";
import dayjs from "dayjs";

const ResultsPage: React.FC = () => {
  const { Title } = Typography;
  const { flights } = useFlight();
  const { flightRequest } = useFlightRequest();
  const [sortCriteria, setSortCriteria] = useState<string>("");

  const parseDuration = (duration: string): number => {
    const dur = dayjs.duration(duration);
    return dur.asMinutes();
  };

  const handleSort = (criteria: string) => {
    setSortCriteria(criteria);
  };

  const sortedFlights = [...flights].sort((a, b) => {
    if (sortCriteria === "price") {
      return a.priceBreakdown.totalPrice - b.priceBreakdown.totalPrice;
    }
    if (sortCriteria === "totalTime") {
      return (
        parseDuration(a.totalFlightTime) - parseDuration(b.totalFlightTime)
      );
    }
    if (sortCriteria === "priceTime") {
      const priceDifference =
        a.priceBreakdown.totalPrice - b.priceBreakdown.totalPrice;
      if (priceDifference !== 0) {
        return priceDifference;
      }
      return (
        parseDuration(a.totalFlightTime) - parseDuration(b.totalFlightTime)
      );
    }
    return 0;
  });

  const getSortTitle = () => {
    switch (sortCriteria) {
      case "price":
        return "Sort By: Price";
      case "totalTime":
        return "Sort By: Time";
      case "priceTime":
        return "Sort By: Price and Time";
      default:
        return "Sort By:";
    }
  };

  return (
    <>
      <div>
        <div>
          <Title level={1}>Flight Search Results</Title>
        </div>
        <div>
          <div>
            <SearchButton />
          </div>
          <Title level={5}>{getSortTitle()}</Title>
          <div style={{ display: "flex" }}>
            <Button
              color="default"
              variant="outlined"
              style={{ marginLeft: "2px" }}
              onClick={() => handleSort("price")}
            >
              Price
            </Button>
            <Button
              color="default"
              variant="outlined"
              style={{ marginLeft: "5px" }}
              onClick={() => handleSort("totalTime")}
            >
              Total Time
            </Button>
            <Button
              color="default"
              variant="outlined"
              style={{ marginLeft: "5px" }}
              onClick={() => handleSort("priceTime")}
            >
              Price & Total Time
            </Button>
          </div>
        </div>
        <div style={{ width: "100%" }}>
          {sortedFlights.length > 0 ? (
            sortedFlights.map((flight) => (
              <div key={flight.id}>
                <GeneralInfo flight={flight} flightRequest={flightRequest} />
                <PriceInfo
                  price={flight.priceBreakdown}
                  traveler={flight.segments[0].travelerPricings[0]}
                />
                {flight.segments.map((segment) => (
                  <FlightSegment key={segment.id} segment={segment} />
                ))}
                <div>
                  <br />
                  <DetailsButton flight={flight} />
                </div>
              </div>
            ))
          ) : (
            <p>No flights found.</p>
          )}
        </div>
      </div>
    </>
  );
};

export default ResultsPage;
