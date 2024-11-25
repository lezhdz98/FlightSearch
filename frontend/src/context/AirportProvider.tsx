import React, { createContext, ReactNode, useState } from "react";
import { Airport } from "../interfaces/Airport";

interface AirportProviderProps {
  children: ReactNode;
}

interface AirportContextProps {
  airports: Airport[];
  setAirports: React.Dispatch<React.SetStateAction<Airport[]>>;
  loading: boolean;
  setLoading: React.Dispatch<React.SetStateAction<boolean>>;
  error: string | null;
  setError: React.Dispatch<React.SetStateAction<string | null>>;
}

export const AirportContext = createContext<AirportContextProps | undefined>(
  undefined
);

export const AirportProvider: React.FC<AirportProviderProps> = ({
  children,
}) => {
  const [airports, setAirports] = useState<Airport[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  return (
    <AirportContext.Provider
      value={{ airports, setAirports, loading, setLoading, error, setError }}
    >
      {children}
    </AirportContext.Provider>
  );
};
