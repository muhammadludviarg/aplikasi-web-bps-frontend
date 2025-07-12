import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { usePublications } from "../hooks/usePublications";
import {
  publicationService,
  uploadImageToCloudinary,
} from "../services/publicationService"; 

export default function EditPublicationPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { publications, editPublication } = usePublications();
  const initialPublication = publications.find((pub) => pub.id === Number(id));

  const [formData, setFormData] = useState({
    title: "",
    releaseDate: "",
    description: "",
    coverFile: null, 
    coverUrlToSave: null, 
    currentCoverUrl: "", 
  });
  const [isUploading, setIsUploading] = useState(false); 

  useEffect(() => {
    if (initialPublication) {
      setFormData({
        title: initialPublication.title || "",
        releaseDate: initialPublication.releaseDate || "",
        description: initialPublication.description || "",
        coverFile: null,
        coverUrlToSave: initialPublication.coverUrl || null, 
        currentCoverUrl: initialPublication.coverUrl || "", 
      });
    } else {
      const fetchPub = async () => {
        try {
          const fetchedPub = await publicationService.getPublicationById(id);
          setFormData({
            title: fetchedPub.title || "",
            releaseDate: fetchedPub.releaseDate || "",
            description: fetchedPub.description || "",
            coverFile: null,
            coverUrlToSave: fetchedPub.coverUrl || null,
            currentCoverUrl: fetchedPub.coverUrl || "",
          });
        } catch (err) {
          console.error("Gagal mengambil publikasi:", err);
          alert("Gagal memuat detail publikasi. Silakan coba lagi.");
          navigate("/publications");
        }
      };
      if (id) {
        fetchPub();
      }
    }
  }, [initialPublication, id, navigate]);

  const handleChange = async (e) => {
    
    const { name, value, files } = e.target;

    if (name === "coverFile" && files && files[0]) {
      const file = files[0];
      setFormData((prev) => ({ ...prev, coverFile: file })); 
      setFormData((prev) => ({
        ...prev,
        currentCoverUrl: URL.createObjectURL(file),
      })); 

      setIsUploading(true); 
      try {
        const uploadedUrl = await uploadImageToCloudinary(file); 
        setFormData((prev) => ({ ...prev, coverUrlToSave: uploadedUrl })); 
        console.log("Gambar berhasil diunggah ke Cloudinary:", uploadedUrl);
      } catch (uploadError) {
        console.error("Gagal mengunggah gambar ke Cloudinary:", uploadError);
        alert("Gagal mengunggah gambar. Silakan coba lagi.");
        setFormData((prev) => ({
          ...prev,
          coverFile: null,
          coverUrlToSave: null,
          currentCoverUrl: "",
        })); 
      } finally {
        setIsUploading(false); 
      }
    } else {
      setFormData((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isUploading) {
      alert("Mohon tunggu, gambar sedang diunggah.");
      return;
    }
    if (!formData.title || !formData.releaseDate || !formData.description) {
      alert("Judul, Tanggal Rilis, dan Deskripsi harus diisi!");
      return;
    }

    const dataToSend = {
      title: formData.title,
      releaseDate: formData.releaseDate,
      description: formData.description,
      coverUrl: formData.coverUrlToSave, 
    };

    try {
      const updatedFromBackend = await publicationService.updatePublication(
        id,
        dataToSend 
      );
      editPublication(updatedFromBackend);
      navigate("/publications");
    } catch (error) {
      console.error("Error updating publication:", error);
      alert(error.message || "Gagal memperbarui publikasi.");
    }
  };

  if (!initialPublication && !formData.title && !formData.description) {
    return <div className="text-center mt-10">Memuat publikasi...</div>;
  }

  return (
    <div className="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-lg">
      <h1 className="text-2xl font-bold text-gray-800 mb-6 " style={{ textAlign: "center" }} >Edit Publikasi</h1>
      <form onSubmit={handleSubmit} className="space-y-6">
        <div>
          <label
            htmlFor="title"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Judul
          </label>
          <input
            type="text"
            id="title"
            name="title"
            value={formData.title}
            onChange={handleChange}
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-sky-500 focus:border-sky-500"
            placeholder="Contoh: Indikator Ekonomi Jawa tengah 2025"
          />
        </div>
        <div>
          <label
            htmlFor="releaseDate"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Tanggal Rilis
          </label>
          <input
            type="date"
            id="releaseDate"
            name="releaseDate"
            value={formData.releaseDate}
            onChange={handleChange}
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-sky-500 focus:border-sky-500"
          />
        </div>
        <div>
          <label
            htmlFor="description"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Deskripsi
          </label>
          <textarea
            id="description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            rows="4"
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-sky-500 focus:border-sky-500"
            placeholder="Masukkan deskripsi publikasi..."
          ></textarea>
        </div>
        <div>
          <label
            htmlFor="coverFile"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Sampul (Gambar)
          </label>
          <input
            type="file"
            id="coverFile"
            name="coverFile"
            accept="image/*"
            onChange={handleChange}
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
            disabled={isUploading} 
          />
          {isUploading && (
            <p className="text-sm text-blue-500">Mengunggah gambar mohon tunggu sebentar...</p>
          )}
          {formData.currentCoverUrl && (
            <img
              src={formData.currentCoverUrl}
              alt="Sampul"
              className="h-24 mt-2 rounded shadow object-cover"
            />
          )}
        </div>
        <div className="flex justify-end space-x-2">
          <button
            type="button"
            onClick={() => navigate("/publications")}
            className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-6 rounded-lg transition-colors duration-300"
          >
            Batal
          </button>
          <button
            type="submit"
            className="bg-sky-700 hover:bg-sky-800 text-white font-bold py-2 px-6 rounded-lg transition-colors duration-300"
            disabled={isUploading} // Disable tombol submit saat mengunggah
          >
            Simpan
          </button>
        </div>
      </form>
    </div>
  );
}
