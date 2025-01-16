// Navbar.jsx

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">UFO Tracker</div>
      <ul className="navbar-links">
        <li><a href="#home">Home</a></li>
        <li><a href="#sightings">Sightings</a></li>
        <li><a href="#analytics">Analytics</a></li>
        <li><a href="#add-sighting">Add Sighting</a></li>
      </ul>
    </nav>
  );
};

export default Navbar;
