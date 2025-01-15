/* eslint-disable react/prop-types */
import { useState } from "react";
import "./SightingsList.css"; // Assuming you have a CSS file

const SightingsList = ({ sightings }) => {
  const [verbose, setVerbose] = useState(false);

  const toggleVerbose = () => {
    setVerbose(!verbose);
  };

  return (
    <div className="sightings-container">
      <h2>Sightings</h2>
      {sightings.length === 0 ? (
        <p>No sightings found.</p>
      ) : (
        <>
        <button onClick={toggleVerbose}>
        {verbose ? "Show Simple" : "Show Verbose"}
      </button>
        <ul className="sightings-list">
          {sightings.map((sighting) => (
            <li key={sighting.id} className="sighting-item">
              {verbose ? (
                <div>
                  <strong>City:</strong> {sighting.city}<br />
                  <strong>Date:</strong> {sighting.date}<br />
                  <strong>Duration:</strong> {sighting.duration}<br />
                  <strong>ID:</strong> {sighting.id}<br />
                  <strong>Images:</strong> {sighting.images ? "Yes" : "No"}<br />
                  <strong>Image Link:</strong> {sighting.imgLink || "N/A"}<br />
                  <strong>Latitude:</strong> {sighting.lat}<br />
                  <strong>Longitude:</strong> {sighting.lng}<br />
                  <strong>Population:</strong> {sighting.population}<br />
                  <strong>Posted:</strong> {sighting.posted}<br />
                  <strong>Shape:</strong> {sighting.shape}<br />
                  <strong>State:</strong> {sighting.state}<br />
                  <strong>Summary:</strong> {sighting.summary}<br />
                  <strong>Time:</strong> {sighting.time}
                </div>
              ) : (
                <div>
                  <strong>
                    {sighting.city}, {sighting.state}
                  </strong>{" "}
                  on {sighting.date}
                  <br />
                  Shape: {sighting.shape}, Duration: {sighting.duration}
                </div>
              )}
            </li>
          ))}
        </ul>
        </>
      )}
    </div>
  );
};

export default SightingsList;