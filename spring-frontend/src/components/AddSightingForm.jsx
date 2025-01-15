/* eslint-disable react/prop-types */
import { useState } from "react";
import { addSighting } from "../api";
import "./AddSightingForm.css";
const AddSightingForm = ({ onAdd }) => {
  const getCurrentDate = () => {
    const today = new Date();
    return today.toISOString().split('T')[0];
  };

  const [form, setForm] = useState({
    city: "",
    state: "",
    posted: getCurrentDate(),
    date: "",
    time: "",
    shape: "",
    duration: "",
    summary: "",
    images: false,
    lat: 0,
    long: 0,
    population: 0,
  });

  const [submitting, setSubmitting] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    try {
      const newSighting = await addSighting(form);
      onAdd(newSighting);
      setForm({
        city: "",
        state: "",
        posted: getCurrentDate(),
        date: "",
        time: "",
        shape: "",
        duration: "",
        summary: "",
        images: false,
        lat: 0,
        long: 0,
        population: 0,
      });
    } catch (error) {
      alert("Failed to add sighting: " + error.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <form className="formContainer" onSubmit={handleSubmit}>
      <h2 className="formTitle">Add Sighting</h2>
      <input name="city" placeholder="City" value={form.city} onChange={handleChange} required />
      <input name="state" placeholder="State" value={form.state} onChange={handleChange} required />
      <input name="posted" type="date" value={form.posted} onChange={handleChange} required />
      <input name="date" type="date" value={form.date} onChange={handleChange} required />
      <input name="time" type="time" value={form.time} onChange={handleChange} required />
      <input name="shape" placeholder="Shape" value={form.shape} onChange={handleChange} />
      <input name="duration" placeholder="Duration" value={form.duration} onChange={handleChange} />
      <textarea name="summary" placeholder="Summary" value={form.summary} onChange={handleChange}></textarea>
      <input name="lat" placeholder="Latitude" value={form.lat} onChange={handleChange} required />
      <input name="lng" placeholder="Longitude" value={form.lng} onChange={handleChange} required />
      <button className="button" type="submit" disabled={submitting}>Add</button>
    </form>
  );
};

export default AddSightingForm;