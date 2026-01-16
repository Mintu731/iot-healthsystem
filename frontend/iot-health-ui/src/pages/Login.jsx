import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

export default function Login() {
  const [mobileNumber, setMobileNumber] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("PATIENT");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleLogin = async () => {
    setMessage("");
    setLoading(true);

    const payload = {
      mobileNumber: mobileNumber.trim(), // ✅ ensure string + no spaces
      password: password.trim(),
      role, // ADMIN / PATIENT (exact enum)
    };

    console.log("LOGIN PAYLOAD =>", payload);

    try {
      const response = await api.post("/api/login", payload);

      console.log("LOGIN RESPONSE =>", response.data);

      // ✅ store user
      localStorage.setItem("user", JSON.stringify(response.data.data));

      // ✅ redirect
      if (role === "ADMIN") {
        navigate("/admin");
      } else {
        navigate("/patient");
      }

    } catch (error) {
      console.error("LOGIN ERROR =>", error.response?.data);

      setMessage(
        error.response?.data?.message ||
        "❌ Invalid credentials or role mismatch"
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container d-flex justify-content-center align-items-center vh-100">
      <div className="card p-4 shadow col-md-4">

        <h4 className="text-center mb-3">Login</h4>

        {/* ROLE TOGGLE */}
        <div className="btn-group mb-3 w-100">
          <button
            type="button"
            className={`btn ${
              role === "PATIENT" ? "btn-primary" : "btn-outline-primary"
            }`}
            onClick={() => setRole("PATIENT")}
          >
            Patient
          </button>

          <button
            type="button"
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
          type="text"
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
        <button
          className="btn btn-success w-100"
          onClick={handleLogin}
          disabled={loading}
        >
          {loading ? "Logging in..." : "Login"}
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
