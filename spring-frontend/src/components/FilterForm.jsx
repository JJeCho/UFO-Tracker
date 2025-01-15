/* eslint-disable react/prop-types */
import { useState } from "react";

const FilterForm = ({ setFilters }) => {
  const [filter, setFilter] = useState({
    city: "",
    state: "",
    sortBy: "date",
    order: "asc",
  });

  const handleChange = (e) => {
    setFilter({ ...filter, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFilters(filter);
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Filter Sightings</h2>
      <input name="city" placeholder="City" value={filter.city} onChange={handleChange} />
      <input name="state" placeholder="State" value={filter.state} onChange={handleChange} />
      <select name="sortBy" value={filter.sortBy} onChange={handleChange}>
        <option value="date">Date</option>
        <option value="city">City</option>
        <option value="state">State</option>
      </select>
      <select name="order" value={filter.order} onChange={handleChange}>
        <option value="asc">Ascending</option>
        <option value="desc">Descending</option>
      </select>
      <button type="submit">Apply Filters</button>
    </form>
  );
};

export default FilterForm;
