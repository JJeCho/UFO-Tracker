// App.jsx

import { useEffect, useState } from 'react';
import { fetchAnalytics, fetchSightings } from './api';
import './App.css'; // Make sure to import our updated CSS
import AddSightingForm from './components/AddSightingForm';
import FilterForm from './components/FilterForm';
import Navbar from './components/Navbar';
import SightingsGraph from './components/SightingsGraph';
import SightingsList from './components/SightingsList';

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
      <section id="home">
        <h1>UFO Sightings</h1>
        <hr />
        <p>
          Welcome to the UFO sightings database. Here you can browse through the
          sightings, filter them, and add new sightings.
        </p>
        <p>Check out the analytics section for some interesting insights.</p>
        <p>Have fun! 👽🛸🌌</p>
        <hr />
      </section>

      <section id="sightings">
        <div className="sightings-wrapper">
          <FilterForm setFilters={setFilters} />
          <SightingsList sightings={sightings} />
        </div>
      </section>

      <section id="add-sighting">
        <AddSightingForm
          onAdd={(newSighting) => setSightings([...sightings, newSighting])}
        />
      </section>

      <section id="analytics">
        <div className="analytics-container">
          <SightingsGraph analytics={analytics} />
        </div>
      </section>
    </div>
  );
};

export default App;
