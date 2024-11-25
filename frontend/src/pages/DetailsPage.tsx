import FlightSegmentDetails from "../components/DetailsPage/FlightSegmentDetails";
import PriceDetails from "../components/DetailsPage/PriceDetails";
import ResultsButton from "../components/DetailsPage/ResultsButton";
import TravelerDetails from "../components/DetailsPage/TravelerDetails";
import GeneralInfo from "../components/GeneralInfo";
import SearchButton from "../components/SearchButton";
import { Typography } from "antd";
import { useLocation } from "react-router-dom";
import { Flight } from "../interfaces/Flight";
import { useFlightRequest } from "../hooks/useFlightRequest";

const DetailsPage: React.FC = () => {
  const { Title } = Typography;
  const location = useLocation();
  const { flight } = location.state as { flight: Flight };
  const { flightRequest } = useFlightRequest();

  return (
    <>
      <div>
        <div>
          <Title level={1}>Flight Details</Title>
        </div>
        <div style={{ display: "flex", width: "100%" }}>
          <div style={{ marginRight: "10px" }}>
            <SearchButton />
          </div>
          <ResultsButton />
        </div>

        <div style={{ marginBottom: "20px" }}>
          <div style={{ width: "100%" }}>
            <GeneralInfo flight={flight} flightRequest={flightRequest} />
            <PriceDetails price={flight.priceBreakdown} />
          </div>
          <Title level={4}>Segment Details</Title>
          <div
            style={{
              border: "1px solid black",
              padding: "20px",
              width: "75%",
            }}
          >
            {flight.segments.map((segment) => (
              <div key={segment.id}>
                <FlightSegmentDetails segment={segment} />
                <Title level={4}>Travel Details</Title>
                {segment.travelerPricings.map((traveler) => (
                  <TravelerDetails key={traveler.id} traveler={traveler} />
                ))}
              </div>
            ))}
          </div>
        </div>

        <div style={{ display: "flex", width: "100%" }}>
          <div style={{ marginRight: "10px" }}>
            <SearchButton />
          </div>
          <ResultsButton />
        </div>
      </div>
    </>
  );
};

export default DetailsPage;
