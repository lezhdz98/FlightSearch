import { Airline } from "./Airline";
import { Airport } from "./Airport";
import { Stop } from "./Stop";
import { TravelerPricing } from "./TravelerPricing";

export interface FlightSegment {
  id: string;
  departureDateTime: string;
  arrivalDateTime: string;
  departureAirport: Airport;
  arrivalAirport: Airport;
  airline: Airline;
  operatingAirline?: Airline;
  layoverTime?: string;
  stops: Stop[];
  flightNumber: string;
  aircraftType: string;
  travelerPricings: TravelerPricing[];
}
