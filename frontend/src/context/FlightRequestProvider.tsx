import React, { createContext, useState, ReactNode } from "react";
import { FlightRequest } from "../interfaces/FlightRequest";
import dayjs from "dayjs";

interface FlightReqProviderProps {
  children: ReactNode;
}

interface FlightRequestContextProps {
  flightRequest: FlightRequest;
  setFlightRequest: React.Dispatch<React.SetStateAction<FlightRequest>>;
}

const defaultFlightRequest: FlightRequest = {
  origin: "",
  destination: "",
  departureDate: dayjs(),
  returnDate: undefined,
  adults: 1,
  nonStop: false,
  currencyCode: "USD",
};

const FlightRequestContext = createContext<FlightRequestContextProps>({
  flightRequest: defaultFlightRequest,
  setFlightRequest: () => {},
});

export const FlightRequestProvider: React.FC<FlightReqProviderProps> = ({
  children,
}) => {
  const [flightRequest, setFlightRequest] =
    useState<FlightRequest>(defaultFlightRequest);

  return (
    <FlightRequestContext.Provider value={{ flightRequest, setFlightRequest }}>
      {children}
    </FlightRequestContext.Provider>
  );
};

export { FlightRequestContext };
