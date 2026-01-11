import axios from "axios";

const api = axios.create({
  baseURL: "https://iot-health-backend-5pjg.onrender.com",
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;
