import { FlightSegment } from "./FlightSegment";
import { PriceBreakdown } from "./PriceBreakdown";

export interface Flight {
  id: string;
  totalFlightTime: string;
  segments: FlightSegment[];
  priceBreakdown: PriceBreakdown;
}
