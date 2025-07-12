// src/components/LoginPage.jsx

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import logoBPS from "../assets/logobps.png"; 
import { useAuth } from "../hooks/useAuth";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { loginAction, error } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Validasi sederhana
    if (!email || !password) {
      alert("Email dan password harus diisi!");
      return;
    }

    try {
      await loginAction(email, password);
      // Redirect ke publications setelah login berhasil
      navigate("/publications");
    } catch (err) {
      console.error("Login failed:", err);
    }
  };

  return (
    
    <div className="relative min-h-screen flex items-center justify-center bg-[#024b75] overflow-hidden">
      <div
        className="absolute bottom-0 left-0 w-full overflow-hidden leading-0 h-48 md:h-64"
        style={{ zIndex: 1 }}
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 1440 320"
          preserveAspectRatio="none"
          className="absolute block w-full h-full"
        >
          <path
            fill="white"
            fillOpacity="1"
            d="M0,224L48,192C96,160,192,96,288,90.7C384,85,480,139,576,170.7C672,203,768,213,864,192C960,171,1056,117,1152,106.7C1248,96,1344,128,1392,144L1440,160L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"
          ></path>{" "}
        </svg>
      </div>

      <div className="relative z-20 w-full max-w-sm p-8 space-y-6 bg-white rounded-lg shadow-xl">
        <div className="text-center">
          <img
            src={logoBPS}
            alt="Logo BPS"
            className="mx-auto h-16 w-auto mb-4"
          />

          <h1 className="text-3xl font-bold text-gray-800">Login</h1>
          <p className="text-gray-500">Silakan masuk untuk melanjutkan</p>
        </div>
        {error && (
          <div className="mb-4 p-3 bg-red-100 border border-red-400 text-red-700 rounded-md text-sm">
            <div className="flex items-center">
              <svg
                className="w-4 h-4 mr-2"
                fill="currentColor"
                viewBox="0 0 20 20"
              >
                <path
                  fillRule="evenodd"
                  d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z"
                  clipRule="evenodd"
                />
              </svg>
              {error}
            </div>
          </div>
        )}
        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="text-sm font-medium text-gray-700">Email</label>
            <input
              type="text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              placeholder="Masukkan Email"
              required
            />
          </div>
          <div>
            <label className="text-sm font-medium text-gray-700">
              Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors"
              placeholder="Masukkan password"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full py-2 px-4 bg-[#0369A1] text-white font-semibold rounded-md hover:bg-[#024b75] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-[#0369A1] transition-colors"
          >
            Login
          </button>
        </form>
      </div>
    </div>
  );
}
