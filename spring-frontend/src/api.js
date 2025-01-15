import axios from "axios";

const BASE_URL = "http://localhost:8080/api/ufo";

export const fetchSightings = async (filters = {}) => {
  const params = new URLSearchParams(filters).toString();
  const response = await axios.get(`${BASE_URL}/filter?${params}`);
  return response.data;
};

export const addSighting = async (sighting) => {
  const response = await axios.post(`${BASE_URL}/contribute`, sighting);
  return response.data;
};

export const fetchAnalytics = async () => {
  const response = await axios.get(`${BASE_URL}/analytics/yearly`);
  return response.data;
};
