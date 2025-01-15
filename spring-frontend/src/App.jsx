import { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import FilterForm from './components/FilterForm';
import SightingsList from './components/SightingsList';
import AddSightingForm from './components/AddSightingForm';
import SightingsGraph from './components/SightingsGraph';
import { fetchSightings, fetchAnalytics } from './api';
import './App.css';

const App = () => {
  const [filters, setFilters] = useState({});
  const [sightings, setSightings] = useState([]);
  const [analytics, setAnalytics] = useState({});

  useEffect(() => {
    const loadSightings = async () => {
      const data = await fetchSightings(filters);
      setSightings(data);
    };
    loadSightings();
  }, [filters]);

  useEffect(() => {
    const loadAnalytics = async () => {
      const data = await fetchAnalytics();
      setAnalytics(data);
    };
    loadAnalytics();
  }, []);

  return (
    <div>
      <Navbar />
      <h1 id="home">UFO Sightings</h1>
      <hr />
      <p>Welcome to the UFO sightings database. Here you can browse through the sightings, filter them, and add new sightings.</p>
      <p>Check out the analytics section for some interesting insights.</p>
      <p>Have fun! 👽🛸🌌</p>
      <hr />

      <section id="sightings">
        <FilterForm setFilters={setFilters} />
        <SightingsList sightings={sightings} />
      </section>
      <section id="add-sighting">
        <AddSightingForm onAdd={(newSighting) => setSightings([...sightings, newSighting])} />
      </section>
      <section id="analytics">
        <SightingsGraph analytics={analytics} />
      </section>
    </div>
  );
};

export default App;