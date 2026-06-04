import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ProfilePage = () => {
  const [profile, setProfile] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  // Fetch the user's profile data from the API
  const fetchProfile = async () => {
    try {
      // Replace 'http://localhost:8080/api/profile' with your API endpoint
      const response = await axios.get('http://localhost:8080/api/profile');
      setProfile(response.data);
      setIsLoading(false);
    } catch (error) {
      toast.error('Error fetching profile data');
      console.error('Error:', error);
      setIsLoading(false);
    }
  };

  // Fetch the profile data on component mount
  useEffect(() => {
    fetchProfile();
  }, []);

  // Render the profile data
  const renderProfile = () => {
    if (isLoading) {
      return <p>Loading...</p>;
    }

    if (!profile) {
      return <p>No profile data available</p>;
    }

    const {
      firstName,
      lastName,
      email,
      countryCode,
      mobileNumber,
      username,
      gender,
      dateOfBirth,
      addresses,
    } = profile;

    return (
      <div>
        <h3>Profile Information</h3>
        <p><strong>First Name:</strong> {firstName}</p>
        <p><strong>Last Name:</strong> {lastName}</p>
        <p><strong>Email:</strong> {email}</p>
        <p><strong>Country Code:</strong> {countryCode}</p>
        <p><strong>Mobile Number:</strong> {mobileNumber}</p>
        <p><strong>Username:</strong> {username}</p>
        <p><strong>Gender:</strong> {gender}</p>
        <p><strong>Date of Birth:</strong> {dateOfBirth}</p>
        <h4>Addresses</h4>
        {addresses.map((address, index) => (
          <div key={index}>
            <p><strong>Address {index + 1}:</strong></p>
            <p>Line 1: {address.line1}</p>
            <p>Line 2: {address.line2}</p>
            <p>City: {address.city}</p>
            <p>State: {address.state}</p>
            <p>Country: {address.country}</p>
            <p>Pin Code: {address.pinCode}</p>
            <p>Type: {address.type}</p>
            <p>Primary Address: {address.primaryAddress ? 'Yes' : 'No'}</p>
          </div>
        ))}
      </div>
    );
  };

  return (
    <div className="container">
      <h2>Profile Page</h2>
      {renderProfile()}
      <ToastContainer />
    </div>
  );
};

export default ProfilePage;
