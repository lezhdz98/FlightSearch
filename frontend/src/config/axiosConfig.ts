import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://localhost:9090/api/v1",
  timeout: 100000,
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosInstance;
