import { useContext } from "react";
import { AirportContext } from "../context/AirportProvider";

export const useAirport = () => {
  const context = useContext(AirportContext);
  if (!context) {
    throw new Error("useAirport must be used within an AirportProvider");
  }
  return context;
};
