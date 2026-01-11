import { useEffect, useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";

export default function AdminDashboard() {
  const navigate = useNavigate();

  /* ---------------- STATE ---------------- */
  const [patients, setPatients] = useState([]);
  const [latestVitals, setLatestVitals] = useState({});

  const [showRegister, setShowRegister] = useState(false);
  const [toastMessage, setToastMessage] = useState("");

  const [fullName, setFullName] = useState("");
  const [mobileNumber, setMobileNumber] = useState("");
  const [password, setPassword] = useState("");
  const [tsChannelId, setTsChannelId] = useState("");
  const [tsReadKey, setTsReadKey] = useState("");

  const ADMIN_ID = 1;

  /* ---------------- TOAST ---------------- */
  const showToast = (msg) => {
    setToastMessage(msg);
    setTimeout(() => setToastMessage(""), 3000);
  };

  /* ---------------- LOAD LATEST VITALS ---------------- */
  const loadLatestVitals = useCallback(async (userId) => {
    try {
      const res = await api.get(`/api/admin/logs/${ADMIN_ID}/${userId}`);
      const logs = res.data.data || [];

      if (logs.length > 0) {
        setLatestVitals((prev) => ({
          ...prev,
          [userId]: logs[logs.length - 1],
        }));
      }
    } catch {
      console.warn("No logs for user:", userId);
    }
  }, []);

  /* ---------------- LOAD PATIENTS ---------------- */
  const loadPatients = useCallback(async () => {
    try {
      const res = await api.get("/api/admin/patients");
      const list = res.data.data || [];
      setPatients(list);

      list.forEach((p) => loadLatestVitals(p.id));
    } catch {
      alert("Failed to load patients");
    }
  }, [loadLatestVitals]);

  /* ---------------- EFFECT ---------------- */
  useEffect(() => {
    loadPatients();
  }, [loadPatients]);

  /* ---------------- REGISTER ---------------- */
  const handleRegister = async () => {
    try {
      await api.post("/api/register", {
        fullName,
        mobileNumber,
        password,
        tsChannelId,
        tsReadKey,
      });

      setShowRegister(false);
      showToast("✅ Patient registered successfully");
      loadPatients();

      setFullName("");
      setMobileNumber("");
      setPassword("");
      setTsChannelId("");
      setTsReadKey("");
    } catch (err) {
      if (err.response?.status === 409) {
        alert("❌ Mobile number already exists");
      } else {
        alert("❌ Registration failed");
      }
    }
  };

  /* ---------------- DELETE ---------------- */
  const handleDelete = async (id) => {
    if (!window.confirm("Delete this patient?")) return;

    try {
      await api.delete(`/api/admin/patients/${id}`);
      setPatients((prev) => prev.filter((p) => p.id !== id));
    } catch {
      alert("Cannot delete patient");
    }
  };

  /* ---------------- VIEW LOGS ---------------- */
  const handleViewLogs = (patientId) => {
    navigate(`/admin/patient/${patientId}`);
  };

  /* ---------------- LOGOUT ---------------- */
  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  /* ---------------- HELPERS ---------------- */
  const bpmClass = (bpm) =>
    bpm >= 60 && bpm <= 100 ? "text-success fw-bold" : "text-danger fw-bold";

  const tempClass = (temp) =>
    temp >= 36 && temp <= 37.5 ? "text-success fw-bold" : "text-danger fw-bold";

  /* ---------------- UI ---------------- */
  return (
    <div className="container mt-4">
      {/* TOAST */}
      {toastMessage && (
        <div
          className="alert alert-success text-center"
          style={{
            position: "fixed",
            top: "20px",
            left: "50%",
            transform: "translateX(-50%)",
            zIndex: 9999,
            width: "320px",
          }}
        >
          {toastMessage}
        </div>
      )}

      {/* TOP BAR */}
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h3>Admin Dashboard</h3>

        <div>
          <button
            className="btn btn-primary me-2"
            onClick={() => setShowRegister(true)}
          >
            + Register Patient
          </button>

          <button className="btn btn-outline-danger" onClick={logout}>
            Logout
          </button>
        </div>
      </div>

      {/* PATIENT TABLE */}
      <table className="table table-bordered table-hover">
        <thead className="table-dark">
          <tr>
            <th>Name</th>
            <th>BPM</th>
            <th>Temperature</th>
            <th>Humidity</th>
            <th className="text-center">Actions</th>
          </tr>
        </thead>

        <tbody>
          {patients.map((p) => {
            const vitals = latestVitals[p.id];

            return (
              <tr key={p.id}>
                <td>{p.fullName}</td>

                <td className={vitals ? bpmClass(vitals.bpm) : ""}>
                  {vitals ? vitals.bpm : "--"}
                </td>

                <td className={vitals ? tempClass(vitals.temperature) : ""}>
                  {vitals ? vitals.temperature : "--"}
                </td>

                <td>{vitals ? vitals.humidity : "--"}</td>

                <td className="text-center">
                  <button
                    className="btn btn-sm btn-outline-primary me-2"
                    onClick={() => handleViewLogs(p.id)}
                  >
                    View Logs
                  </button>

                  <span
                    style={{ cursor: "pointer", color: "red", fontSize: "20px" }}
                    onClick={() => handleDelete(p.id)}
                    title="Delete"
                  >
                    ❌
                  </span>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>

      {/* REGISTER MODAL */}
      {showRegister && (
        <div
          className="modal show d-block"
          style={{ background: "rgba(0,0,0,0.5)" }}
        >
          <div className="modal-dialog">
            <div className="modal-content p-3">
              <h5>Register Patient</h5>

              <input
                className="form-control mb-2"
                placeholder="Full Name"
                value={fullName}
                onChange={(e) => setFullName(e.target.value)}
              />

              <input
                className="form-control mb-2"
                placeholder="Mobile Number"
                value={mobileNumber}
                onChange={(e) => setMobileNumber(e.target.value)}
              />

              <input
                type="password"
                className="form-control mb-2"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />

              <input
                className="form-control mb-2"
                placeholder="ThingSpeak Channel ID"
                value={tsChannelId}
                onChange={(e) => setTsChannelId(e.target.value)}
              />

              <input
                className="form-control mb-3"
                placeholder="ThingSpeak Read Key"
                value={tsReadKey}
                onChange={(e) => setTsReadKey(e.target.value)}
              />

              <div className="text-end">
                <button
                  className="btn btn-secondary me-2"
                  onClick={() => setShowRegister(false)}
                >
                  Cancel
                </button>

                <button className="btn btn-success" onClick={handleRegister}>
                  Register
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
