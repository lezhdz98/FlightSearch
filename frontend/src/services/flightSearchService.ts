import axiosInstance from "../config/axiosConfig";
import { Flight } from "../interfaces/Flight";
import { FlightRequest } from "../interfaces/FlightRequest";
import { notification } from "antd";

export const fetchFlights = async (
  flightRequest: FlightRequest
): Promise<Flight[]> => {
  try {
    let stringResponse = `/flight-offers?origin=${encodeURIComponent(
      flightRequest.origin
    )}&destination=${encodeURIComponent(
      flightRequest.destination
    )}&departureDate=${encodeURIComponent(
      flightRequest.departureDate.format("YYYY-MM-DD")
    )}&adults=${encodeURIComponent(
      flightRequest.adults.toString()
    )}&nonStop=${encodeURIComponent(
      flightRequest.nonStop.toString()
    )}&currencyCode=${encodeURIComponent(flightRequest.currencyCode)}`;

    if (flightRequest.returnDate) {
      stringResponse += `&returnDate=${encodeURIComponent(
        flightRequest.returnDate.format("YYYY-MM-DD")
      )}`;
    }

    console.log("Generated URL:", stringResponse);
    const response = await axiosInstance.get(stringResponse);

    if (response.data.length === 0) {
      notification.warning({
        message: "No Flights Found",
        description:
          "No flights were found for the given criteria. Please try different search parameters.",
      });
    }

    return response.data;
  } catch (error) {
    console.error("Error fetching flights:", error);
    notification.error({
      message: "Error Fetching Flights",
      description:
        "An error occurred while fetching flights. Please try again later.",
    });
    throw error;
  }
};
