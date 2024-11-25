import { Amenity } from "./Amenity";

export interface TravelerPricing {
  id: string;
  cabin: string;
  flightClass: string;
  totalPrice: number;
  amenities: Amenity[];
}
