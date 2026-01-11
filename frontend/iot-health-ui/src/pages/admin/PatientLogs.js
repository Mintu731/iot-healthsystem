import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
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

export default function PatientLogs() {
  const { id } = useParams();           // patientId
  const navigate = useNavigate();
  const ADMIN_ID = 1;

  const [logs, setLogs] = useState([]);
  const [patient, setPatient] = useState(null);

  useEffect(() => {
    api
      .get(`/api/admin/logs/${ADMIN_ID}/${id}`)
      .then(res => {
        const data = res.data.data || [];
        setLogs(data);

        // ✅ Safely extract patient info (only once)
        if (data.length > 0 && data[0].user) {
          setPatient({
            fullName: data[0].user.fullName,
            mobileNumber: data[0].user.mobileNumber
          });
        }
      })
      .catch(() => alert("Failed to load patient logs"));
  }, [id]);

  /* ---------------- CHART DATA ---------------- */

  const labels = logs.map(l => l.entryId);

  const bpmData = {
    labels,
    datasets: [
      {
        label: "BPM",
        data: logs.map(l => l.bpm),
        borderColor: "#0d6efd",
        backgroundColor: "rgba(13,110,253,0.1)",
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
        backgroundColor: "rgba(220,53,69,0.1)",
        borderWidth: 2,
        tension: 0.4
      }
    ]
  };

  return (
    <div className="container mt-4">

      {/* 🔙 BACK BUTTON */}
      <button
        className="btn btn-outline-secondary mb-3"
        onClick={() => navigate("/admin")}
      >
        ← Back to Dashboard
      </button>

      {/* 👤 PATIENT INFO */}
      {patient && (
        <div className="card mb-4 shadow-sm">
          <div className="card-body d-flex justify-content-between">
            <div>
              <h5 className="mb-0">{patient.fullName}</h5>
              <small className="text-muted">Patient Name</small>
            </div>
            <div>
              <h6 className="mb-0">{patient.mobileNumber}</h6>
              <small className="text-muted">Mobile Number</small>
            </div>
          </div>
        </div>
      )}

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

      {/* 📋 LOGS TABLE */}
      <div className="card shadow-sm">
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
                  <td>{log.bpm}</td>
                  <td>{log.temperature}</td>
                  <td>{log.humidity}</td>
                </tr>
              ))}
              {logs.length === 0 && (
                <tr>
                  <td colSpan="4" className="text-center text-muted">
                    No records found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>

    </div>
  );
}
