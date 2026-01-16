// import axios from "axios";

// const api = axios.create({
//   baseURL: "https://iot-health-backend-5pjg.onrender.com",
//   headers: {
//     "Content-Type": "application/json",
//   },
// });

//export default api;
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;
