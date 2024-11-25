import axiosInstance from "../config/axiosConfig";
import { Airport } from "../interfaces/Airport";

export const fetchAirports = async (keyword: string): Promise<Airport[]> => {
  try {
    const response = await axiosInstance.get(`/airport?keyword=${keyword}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching airports:", error);
    throw error;
  }
};
