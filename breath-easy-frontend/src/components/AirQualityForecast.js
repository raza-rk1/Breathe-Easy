import React, { useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';
import { getJwtToken } from '../service/apiService';

const AirQualityForecast = () => {
  const [countryCode, setCountryCode] = useState('');
  const [stateName, setStateName] = useState('');
  const [cityName, setCityName] = useState('');
  const [airQualityData, setAirQualityData] = useState(null);

  const handleCountryCodeChange = (e) => {
    const { value } = e.target;
    setCountryCode(value);
    if (value.length > 2) {
      toast.error('Country code must be a two-digit code');
      setCountryCode(value.slice(0, 2));
    }
  };

  const handleStateNameChange = (e) => {
    setStateName(e.target.value);
  };

  const handleCityNameChange = (e) => {
    setCityName(e.target.value);
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    let isValid = true;

    if (countryCode.trim() === '') {
      toast.error('Country code is required');
      isValid = false;
    } else if (countryCode.length !== 2) {
      toast.error('Country code must be a two-digit code');
      isValid = false;
    }

    if (stateName.trim() === '') {
      toast.error('State name is required');
      isValid = false;
    }

    if (cityName.trim() === '') {
      toast.error('City name is required');
      isValid = false;
    }
    const jwtToken = getJwtToken();
    if (jwtToken.trim() === '') {
      toast.error('JWT token is required');
      isValid = false;
    }

    if (isValid) {
      try {
        const requestData = {
          country: countryCode,
          state: stateName,
          name: cityName,
        };

        const headers = {
          'Authorization': `Bearer ${jwtToken}`,
        };

        const response = await axios.post('http://localhost:8095/api/aqi/get-air-quality-forecast', requestData, { headers });
        setAirQualityData(response.data);
      } catch (error) {
        if (error.response && error.response.status === 401) {
          toast.error('Unauthorized: Invalid or expired JWT token');
          window.location.href = '/login';
        } else {
          toast.error('Error fetching air quality data');
        }
        console.error('Error:', error);
      }
    }
  };

  return (
    <div className="container">
      <div className="row justify-content-center mt-5">
        <div className="col-md-6">
          <div className="card">
            <div className="card-header">
              <h4>Air Quality Forecast</h4>
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="countryCode" className="mb-1">
                    Country Code:
                  </label>
                  <input
                    type="text"
                    id="countryCode"
                    className="form-control"
                    value={countryCode}
                    onChange={handleCountryCodeChange}
                    maxLength={2}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="stateName" className="mb-1">
                    State Name:
                  </label>
                  <input
                    type="text"
                    id="stateName"
                    className="form-control"
                    value={stateName}
                    onChange={handleStateNameChange}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="cityName" className="mb-1">
                    City Name:
                  </label>
                  <input
                    type="text"
                    id="cityName"
                    className="form-control"
                    value={cityName}
                    onChange={handleCityNameChange}
                  />
                </div>
                <div className="d-flex justify-content-center mt-3">
                <button type="submit" className="btn btn-primary">
                  Air Quality Forecast
                </button>
                </div>
              </form>

              {airQualityData && (
                <div className="mt-3">
                  <h5>Air Quality Data:</h5>
                  <pre>{JSON.stringify(airQualityData, null, 2)}</pre>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
      <ToastContainer />
    </div>
  );
};

export default AirQualityForecast;