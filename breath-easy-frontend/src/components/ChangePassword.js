import React, { useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';
import './ChangePassword.css'; // Import the CSS file

const ChangePassword = () => {
  // State variables for form inputs and errors
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [jwtToken, setJwtToken] = useState('');

  // Validate the form inputs
  const validate = () => {
    let isValid = true;

    if (!currentPassword) {
      toast.error('Current password is required');
      isValid = false;
    }

    if (!newPassword) {
      toast.error('New password is required');
      isValid = false;
    } else if (newPassword.length < 6) {
      toast.error('New password must be at least 6 characters long');
      isValid = false;
    }

    if (!confirmPassword) {
      toast.error('Please confirm your new password');
      isValid = false;
    } else if (newPassword !== confirmPassword) {
      toast.error('New passwords do not match');
      isValid = false;
    }

    return isValid;
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validate()) {
      return;
    }

    try {
      // Replace 'http://localhost:8080/api/change-password' with your API endpoint
      const response = await axios.post(
        'http://localhost:8080/api/change-password',
        {
          currentPassword,
          newPassword,
        },
        {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );

      // Display success toast message
      toast.success('Password changed successfully');

      // Reset form inputs
      setCurrentPassword('');
      setNewPassword('');
      setConfirmPassword('');
    } catch (error) {
      // Display error toast message
      toast.error('Error changing password');
      console.error('Error:', error);
    }
  };

  return (
    <div className="container">
      <div className="row justify-content-center mt-5">
        <div className="col-md-6">
          <div className="card">
            <div className="card-header">
              <h4>Change Password</h4>
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="currentPassword" className="mb-1">
                    Current Password:
                  </label>
                  <input
                    type="password"
                    id="currentPassword"
                    className="form-control"
                    value={currentPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="newPassword" className="mb-1">
                    New Password:
                  </label>
                  <input
                    type="password"
                    id="newPassword"
                    className="form-control"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="confirmPassword" className="mb-1">
                    Confirm New Password:
                  </label>
                  <input
                    type="password"
                    id="confirmPassword"
                    className="form-control"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="jwtToken" className="mb-1">
                    JWT Token:
                  </label>
                  <input
                    type="text"
                    id="jwtToken"
                    className="form-control"
                    value={jwtToken}
                    onChange={(e) => setJwtToken(e.target.value)}
                  />
                </div>

                <div className="d-flex justify-content-center mt-3">
                  <button type="submit" className="btn btn-primary">
                    Change Password
                  </button>
                </div>
              </form>

              <ToastContainer />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChangePassword;
