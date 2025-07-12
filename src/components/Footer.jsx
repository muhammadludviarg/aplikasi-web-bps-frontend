// src/components/Footer.jsx

import React from "react";
import { Link } from "react-router-dom";

export default function Footer() {
  const currentYear = new Date().getFullYear();

  return (
    
    <footer className="relative bg-[#024b75] mt-20">
      <div
        className="absolute top-0 left-0 w-full overflow-hidden leading-0 h-32 md:h-40"
        style={{ transform: "translateY(-70%)" }}
      >
        {" "}
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 1440 320"
          preserveAspectRatio="none" 
          className="absolute block w-full h-full"
        >
          <path
            fill="#024b75"
            fillOpacity="1"
            d="M0,224L48,192C96,160,192,96,288,90.7C384,85,480,139,576,170.7C672,203,768,213,864,192C960,171,1056,117,1152,106.7C1248,96,1344,128,1392,144L1440,160L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"
          ></path>
        </svg>
      </div>

      <div className="container mx-auto px-4 py-8 md:py-12 text-center md:text-left relative z-10">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="md:col-span-1" style={{ textAlign: "right" }}>
            <h3 className="text-2xl font-bold text-white mb-2">
              Aplikasi Publikasi
            </h3>
            <p className="text-gray-300 text-sm">
              Sistem informasi publikasi data statistik BPS Provinsi Jawa
              Tengah.
            </p>
          </div>

          <div className="md:col-span-1" style={{ textAlign: "center" }}>
            <h4 className="text-lg font-semibold text-white mb-3">
              Navigasi Cepat
            </h4>
            <ul className="space-y-2">
              <li>
                <Link
                  to="/publications"
                  className="text-gray-300 hover:text-white transition-colors text-sm"
                >
                  Daftar Publikasi
                </Link>
              </li>
              <li>
                <Link
                  to="/publications/add"
                  className="text-gray-300 hover:text-white transition-colors text-sm"
                >
                  Tambah Publikasi
                </Link>
              </li>
            </ul>
          </div>

          <div className="md:col-span-1">
            <h4 className="text-lg font-semibold text-white mb-3">
              Hubungi Kami
            </h4>
            <ul className="space-y-2 text-gray-300 text-sm">
              <li>
                Jalan Pahlawan No.6, Pleburan, Semarang Selatan, Pleburan,
                Semarang Sel., Kota Semarang, Jawa Tengah 50241, Indonesia
              </li>
              <li>(024) 8412802</li>
              <li>
                <a href="mailto:222313248@stis.ac.id">222313248@stis.ac.id</a>
              </li>
            </ul>
          </div>
        </div>
        <div className="border-t border-white-700 mt-8 pt-4 text-center text-gray-400 text-xs">
          &copy; {currentYear} BPS Provinsi Jawa Tengah. All rights reserved.
        </div>
      </div>
    </footer>
  );
}
