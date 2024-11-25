import React, { createContext, useState, ReactNode } from "react";
import { Flight } from "../interfaces/Flight";

interface FlightProviderProps {
  children: ReactNode;
}

interface FlightContextProps {
  flights: Flight[];
  setFlights: React.Dispatch<React.SetStateAction<Flight[]>>;
}

export const FlightContext = createContext<FlightContextProps | undefined>(
  undefined
);

export const FlightProvider: React.FC<FlightProviderProps> = ({ children }) => {
  const [flights, setFlights] = useState<Flight[]>([]);

  return (
    <FlightContext.Provider value={{ flights, setFlights }}>
      {children}
    </FlightContext.Provider>
  );
};
