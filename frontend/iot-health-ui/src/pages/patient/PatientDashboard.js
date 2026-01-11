import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";

import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend
} from "chart.js";
import { Line } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend
);

export default function PatientDashboard() {
  const navigate = useNavigate();

  // ✅ READ USER OBJECT CORRECTLY
  const user = JSON.parse(localStorage.getItem("user"));
  const userId = user?.id;
  const fullName = user?.fullName;
  const mobileNumber = user?.mobileNumber;

  const [logs, setLogs] = useState([]);

  useEffect(() => {
    if (!userId) {
      navigate("/");
      return;
    }

    api
      .get(`/api/logs/me/${userId}`)
      .then(res => setLogs(res.data.data || []))
      .catch(() => alert("Failed to load health logs"));
  }, [userId, navigate]);

  /* ---------------- CHART DATA ---------------- */

  const labels = logs.map(l => l.entryId);

  const bpmData = {
    labels,
    datasets: [
      {
        label: "BPM",
        data: logs.map(l => l.bpm),
        borderColor: "#198754",
        backgroundColor: "rgba(25,135,84,0.15)",
        borderWidth: 2,
        tension: 0.4
      }
    ]
  };

  const tempData = {
    labels,
    datasets: [
      {
        label: "Temperature (°C)",
        data: logs.map(l => l.temperature),
        borderColor: "#dc3545",
        backgroundColor: "rgba(220,53,69,0.15)",
        borderWidth: 2,
        tension: 0.4
      }
    ]
  };

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="container mt-4">

      {/* 🔹 TOP BAR */}
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h4>Patient Dashboard</h4>
        <button className="btn btn-outline-danger" onClick={logout}>
          Logout
        </button>
      </div>

      {/* 👤 PATIENT INFO */}
      <div className="card mb-4 shadow-sm">
        <div className="card-body d-flex justify-content-between">
          <div>
            <h5 className="mb-0">{fullName}</h5>
            <small className="text-muted">Patient Name</small>
          </div>
          <div>
            <h6 className="mb-0">{mobileNumber}</h6>
            <small className="text-muted">Mobile Number</small>
          </div>
        </div>
      </div>

      {/* 📊 GRAPHS */}
      {logs.length > 0 && (
        <div className="row mb-4">
          <div className="col-md-6">
            <div className="card p-3 shadow-sm">
              <h6 className="text-center">BPM Trend</h6>
              <Line data={bpmData} />
            </div>
          </div>

          <div className="col-md-6">
            <div className="card p-3 shadow-sm">
              <h6 className="text-center">Temperature Trend</h6>
              <Line data={tempData} />
            </div>
          </div>
        </div>
      )}

      {/* 📋 LOG TABLE */}
      <div className="card shadow-sm mb-4">
        <div style={{ maxHeight: "300px", overflowY: "auto" }}>
          <table className="table table-sm table-bordered mb-0">
            <thead className="table-dark">
              <tr>
                <th>Entry</th>
                <th>BPM</th>
                <th>Temperature</th>
                <th>Humidity</th>
              </tr>
            </thead>
            <tbody>
              {logs.map(log => (
                <tr key={log.id}>
                  <td>{log.entryId}</td>
                  <td
                    className={
                      log.bpm < 60 || log.bpm > 120
                        ? "text-danger fw-bold"
                        : "text-success fw-bold"
                    }
                  >
                    {log.bpm}
                  </td>
                  <td
                    className={
                      log.temperature > 38
                        ? "text-danger fw-bold"
                        : "text-success fw-bold"
                    }
                  >
                    {log.temperature}
                  </td>
                  <td>{log.humidity}</td>
                </tr>
              ))}

              {logs.length === 0 && (
                <tr>
                  <td colSpan="4" className="text-center text-muted">
                    No records available
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* ☎ CONTACT ADMIN */}
      <div className="card shadow-sm">
        <div className="card-body text-center">
          <h6>Need help?</h6>
          <p className="text-muted mb-2">
            If you notice abnormal readings, please contact the administrator.
          </p>
          <button className="btn btn-primary">
            Contact Admin
          </button>
        </div>
      </div>

    </div>
  );
}
