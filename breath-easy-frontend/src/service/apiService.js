import axios from 'axios';

const AUTH_BASE_URL = 'http://localhost:9091/api';
const AQI_BASE_URL = 'http://localhost:8095/api';


export const register = async (userData) => {
  try {
    const response = await axios.post(`${AUTH_BASE_URL}/auth/register`, userData);
    if (response.data.token) {
        storeTokens(response.data);
      }
    return response.data;
  } catch (error) {
    console.error('Registration error:', error);
    throw error; 
  }
};

export const login = async (loginData) => {
  try {
    const response = await axios.post(`${AUTH_BASE_URL}/auth/login`, loginData);
    if (response.data.token) {
        storeTokens(response.data);
      }
    return response.data;
  } catch (error) {
    console.error('Login error:', error);
    throw error;
  }
};

export const storeTokens = ({ token, tokenId, refreshToken, refreshTokenId }) => {
    localStorage.setItem('jwtToken', token);
    localStorage.setItem('tokenId', tokenId);
    localStorage.setItem('refreshToken', refreshToken);
    localStorage.setItem('refreshTokenId', refreshTokenId);
};

// Function to retrieve the JWT token
export const getJwtToken = () => {
    return localStorage.getItem('jwtToken');
};

// Function to retrieve the refresh token
export const getRefreshToken = () => {
    return localStorage.getItem('refreshToken');
};

// Function to clear the tokens from local storage (useful for logout)
export const clearTokens = () => {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('tokenId');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('refreshTokenId');
};

export const getAirQualityData = async (requestData, headers) => {
    try {
        const response = await axios.post(`${AQI_BASE_URL}/aqi/get-air-quality`, requestData, { headers });
        return response.data;
    } catch (error) {
        console.error('Error fetching air quality data:', error);
        throw error;
    }
};
