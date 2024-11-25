import { Dayjs } from "dayjs";

export interface FlightRequest {
  origin: string;
  destination: string;
  departureDate: Dayjs;
  returnDate?: Dayjs;
  adults: number;
  nonStop: boolean;
  currencyCode: string;
}
