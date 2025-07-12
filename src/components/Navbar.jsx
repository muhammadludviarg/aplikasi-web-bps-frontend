// src/components/Navbar.jsx

import React from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";
import logoBPS from "../assets/logobps.png";

const navItems = [
  { id: "publications", label: "Daftar Publikasi", path: "/publications" },
  { id: "add", label: "Tambah Publikasi", path: "/publications/add" },
];

function BpsLogo() {
  return (
    <img
      src={logoBPS}
      alt="BPS Logo"
      className="h-12 w-12"
    />
  );
}

export default function Navbar() {
  const location = useLocation();
  const navigate = useNavigate();
  const { logoutAction, isAuthenticated } = useAuth();

  const handleLogout = async () => {
    try {
      await logoutAction(); 
      navigate("/login"); 
    } catch (error) {
      console.error("Gagal logout:", error);
      alert("Gagal logout. Silakan coba lagi.");
    }
  };

  return (
    <nav className="bg-[#0369A1] shadow-lg sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center space-x-3">
            <BpsLogo />
            <span
              className="text-white text-base md:text-lg font-bold 
tracking-wider hidden sm:block"
            >
              BPS PROVINSI JAWA TENGAH
            </span>
          </div>
          <div className="flex items-center space-x-2">
            {isAuthenticated && (
              <>
                {navItems.map((item) => {
                  const isActive =
                    location.pathname === item.path ||
                    (item.id === "add" &&
                      location.pathname.startsWith("/publications/add")) ||
                    (item.id === "publications" &&
                      location.pathname === "/publications");

                  return (
                    <Link
                      key={item.id}
                      to={item.path}
                      className={`px-3 py-2 rounded-md text-sm font-semibold
                                        transition-all duration-300 border border-transparent cursor-pointer ${
                                          isActive
                                            ? "bg-white text-sky-900 shadow-md font-bold"
                                            : "text-sky-100 hover:bg-sky-700 hover:text-white"
                                        }`}
                    >
                      {item.label}
                    </Link>
                  );
                })}
                <button
                  onClick={handleLogout}
                  className="px-3 py-2 rounded-md text-sm font-semibold bg-red-500 text-white hover:bg-red-600 transition-all duration-300 cursor-pointer shadow-sm"
                >
                  Logout
                </button>
              </>
            )}
            {!isAuthenticated && ( 
              <Link
                to="/login"
                className="px-3 py-2 rounded-md text-sm font-semibold bg-sky-700 text-white hover:bg-sky-800 transition-all duration-300 cursor-pointer shadow-sm"
              >
                Login
              </Link>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}
