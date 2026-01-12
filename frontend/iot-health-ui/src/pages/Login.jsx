import { useState } from "react";
import { useNavigate } from "react-router-dom";   // ✅ ADD THIS
import api from "../api/api";

export default function Login() {
  const [mobileNumber, setMobileNumber] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("PATIENT");
  const [message, setMessage] = useState("");

  const navigate = useNavigate(); // ✅ ADD THIS

  const handleLogin = async () => {
    setMessage("");

    try {
      const response = await api.post("/api/login", {
        mobileNumber,
        password,
        role,
      });

      // Add this to see the login data in an alert
      alert(`Role: ${role}\nMobile: ${mobileNumber}\nPassword: ${password}`);

      // ✅ SAVE FULL USER OBJECT
      localStorage.setItem("user", JSON.stringify(response.data.data));

      // ✅ REDIRECT BASED ON ROLE
      if (role === "ADMIN") {
        navigate("/admin");
      } else {
        navigate("/patient");
      }

    } catch (error) {
      setMessage("❌ Invalid credentials or role mismatch");
    }
  };

  return (
    <div className="container d-flex justify-content-center align-items-center vh-100">
      <div className="card p-4 shadow col-md-4">
        <h4 className="text-center mb-3">Login</h4>

        {/* ROLE TOGGLE */}
        <div className="btn-group mb-3 w-100">
          <button
            className={`btn ${
              role === "PATIENT" ? "btn-primary" : "btn-outline-primary"
            }`}
            onClick={() => setRole("PATIENT")}
          >
            Patient
          </button>

          <button
            className={`btn ${
              role === "ADMIN" ? "btn-primary" : "btn-outline-primary"
            }`}
            onClick={() => setRole("ADMIN")}
          >
            Admin
          </button>
        </div>

        {/* MOBILE NUMBER */}
        <input
          className="form-control mb-2"
          placeholder="Mobile Number"
          value={mobileNumber}
          onChange={(e) => setMobileNumber(e.target.value)}
        />

        {/* PASSWORD */}
        <input
          type="password"
          className="form-control mb-3"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        {/* LOGIN BUTTON */}
        <button className="btn btn-success w-100" onClick={handleLogin}>
          Login
        </button>

        {/* ERROR MESSAGE */}
        {message && (
          <div className="alert alert-danger mt-3 text-center">
            {message}
          </div>
        )}
      </div>
    </div>
  );
}
