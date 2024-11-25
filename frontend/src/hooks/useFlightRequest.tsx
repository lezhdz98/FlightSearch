import { useContext } from "react";
import { FlightRequestContext } from "../context/FlightRequestProvider";

export const useFlightRequest = () => {
  const context = useContext(FlightRequestContext);
  if (!context) {
    throw new Error(
      "useFlightRequest must be used within a FlightRequestProvider"
    );
  }
  return context;
};
