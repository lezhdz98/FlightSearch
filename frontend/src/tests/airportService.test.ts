import axiosInstance from "../config/axiosConfig";
import MockAdapter from "axios-mock-adapter";
import { fetchAirports } from "../services/airportService";
import { Airport } from "../interfaces/Airport";

// Create an instance of MockAdapter on the axiosInstance
const mock = new MockAdapter(axiosInstance);

describe("fetchAirports", () => {
  afterEach(() => {
    mock.reset();
  });

  it("should return airport data when the request is successful", async () => {
    const keyword = "test";
    const mockData: Airport[] = [
      {
        code: "TST",
        name: "Test Airport",
        city: "Test City",
        country: "Test Country",
      },
      // other test data
    ];

    // Set up the mock response
    mock.onGet(`/airport?keyword=${keyword}`).reply(200, mockData);

    const result = await fetchAirports(keyword);
    expect(result).toEqual(mockData);
  });

  it("should throw an error when the request fails", async () => {
    const keyword = "test";

    // Set up the mock response for an error
    mock.onGet(`/airport?keyword=${keyword}`).reply(500);

    await expect(fetchAirports(keyword)).rejects.toThrow(
      "Request failed with status code 500"
    );
  });
});
