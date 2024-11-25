const dayjs = require("dayjs");
import axiosInstance from "../config/axiosConfig";
import MockAdapter from "axios-mock-adapter";
import { fetchFlights } from "../services/flightSearchService";
import { FlightRequest } from "../interfaces/FlightRequest";
import { notification } from "antd";

// Create an instance of MockAdapter on axiosInstance
const mock = new MockAdapter(axiosInstance);

// Mock notifications to avoid errors during tests
jest.mock("antd", () => ({
  notification: {
    warning: jest.fn(),
    error: jest.fn(),
  },
}));

describe("fetchFlights", () => {
  afterEach(() => {
    mock.reset();
  });

  it("should return flight data when the request is successful", async () => {
    const flightRequest: FlightRequest = {
      origin: "NYC",
      destination: "LAX",
      departureDate: dayjs("2024-12-01"),
      adults: 1,
      nonStop: true,
      currencyCode: "USD",
    };
    const mockData = [
      {
        id: "1",
        airline: "Test Airline",
        flightNumber: "123",
        departureTime: "2024-12-01T10:00:00Z",
        arrivalTime: "2024-12-01T13:00:00Z",
        duration: "3h",
        price: 200,
      },
      // other test data
    ];

    // Set up the mock response
    mock.onGet("/flight-offers?origin=NYC&destination=LAX&departureDate=2024-12-01&adults=1&nonStop=true&currencyCode=USD").reply(200, mockData);

    const result = await fetchFlights(flightRequest);
    expect(result).toEqual(mockData);
  });

  it("should show a warning notification when no flights are found", async () => {
    const flightRequest: FlightRequest = {
      origin: "NYC",
      destination: "LAX",
      departureDate: dayjs("2024-12-01"),
      adults: 1,
      nonStop: true,
      currencyCode: "USD",
    };

    // Set up the mock response for no flights found
    mock.onGet("/flight-offers?origin=NYC&destination=LAX&departureDate=2024-12-01&adults=1&nonStop=true&currencyCode=USD").reply(200, []);

    const result = await fetchFlights(flightRequest);
    expect(result).toEqual([]);
    expect(notification.warning).toHaveBeenCalledWith({
      message: "No Flights Found",
      description: "No flights were found for the given criteria. Please try different search parameters.",
    });
  });

  it("should throw an error and show an error notification when the request fails", async () => {
    const flightRequest: FlightRequest = {
      origin: "NYC",
      destination: "LAX",
      departureDate: dayjs("2024-12-01"),
      adults: 1,
      nonStop: true,
      currencyCode: "USD",
    };

    // Set up the mock response for an error
    mock.onGet("/flight-offers?origin=NYC&destination=LAX&departureDate=2024-12-01&adults=1&nonStop=true&currencyCode=USD").reply(500);

    await expect(fetchFlights(flightRequest)).rejects.toThrow("Request failed with status code 500");
    expect(notification.error).toHaveBeenCalledWith({
      message: "Error Fetching Flights",
      description: "An error occurred while fetching flights. Please try again later.",
    });
  });
});