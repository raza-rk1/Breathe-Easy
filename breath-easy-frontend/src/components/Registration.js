import React, { useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css'; // Import the Toastify CSS
import { register } from '../service/apiService';
import { useNavigate } from 'react-router-dom';

const Registration = () => {

    const navigate = useNavigate();

    const initialFormState = {
        firstName: '',
        lastName: '',
        email: '',
        countryCode: 'IN +91',
        mobileNumber: '',
        username: '',
        password: '',
        gender: '',
        dateOfBirth: '',
        addresses: [
            {
                line1: '',
                line2: '',
                city: '',
                state: '',
                country: '',
                pinCode: '',
                type: 'HOME',
                primaryAddress: true,
            },
        ],
    };

    const [formData, setFormData] = useState(initialFormState);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleAddressChange = (e, index, field) => {
        const { value } = e.target;
        const addresses = [...formData.addresses];
        addresses[index][field] = value;
        setFormData((prevState) => ({
            ...prevState,
            addresses,
        }));
    };

    const validateForm = () => {
        let isValid = true;

        // Validate first name
        if (!/^[A-Za-z]+$/.test(formData.firstName)) {
            toast.error('First name is required and should only contain letters');
            isValid = false;
        }

        // Validate last name
        if (!/^[A-Za-z]+$/.test(formData.lastName)) {
            toast.error('Last name is required and should only contain letters');
            isValid = false;
        }

        // Validate email
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(formData.email)) {
            toast.error('Email is required and must be in a valid format');
            isValid = false;
        }

        // Validate country code
        if (!/^IN \+\d{2}$/.test(formData.countryCode)) {
            toast.error('Country code must be in the format "IN +91"');
            isValid = false;
        }

        // Validate mobile number
        if (!/^\d+$/.test(formData.mobileNumber)) {
            toast.error('Mobile number is required and must contain only digits');
            isValid = false;
        }

        // Validate username
        if (formData.username.length < 5) {
            toast.error('Username is required and must be at least 5 characters long');
            isValid = false;
        }

        // Validate password
        if (formData.password.length < 6) {
            toast.error('Password is required and must be at least 6 characters long');
            isValid = false;
        }

        // Validate gender
        const allowedGenders = ['Male', 'Female', 'Other'];
        if (!allowedGenders.includes(formData.gender)) {
            toast.error('Gender is required and must be one of the allowed values ("Male", "Female", "Other")');
            isValid = false;
        }

        // Validate date of birth
        if (!formData.dateOfBirth) {
            toast.error('Date of birth is required and must be a valid date');
            isValid = false;
        }

        // Validate addresses
        const address = formData.addresses[0];
        if (!address.line1) {
            toast.error('Address Line 1 is required');
            isValid = false;
        }
        if (!address.city) {
            toast.error('City is required');
            isValid = false;
        }
        if (!address.state) {
            toast.error('State is required');
            isValid = false;
        }
        if (!address.country) {
            toast.error('Country is required');
            isValid = false;
        }
        if (!/^\d{6}$/.test(address.pinCode)) {
            toast.error('Pin code is required and must be a 6-digit number');
            isValid = false;
        }

        return isValid;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (validateForm()) {
            console.log('Form data:', formData);
            const response = await register(formData);
            console.log(response)
            toast.success('Registration successful');
            navigate('/dashboard');            
        }
    };

    const handleReset = () => {
        // Reset form data to initial state
        setFormData(initialFormState);
    };

    return (
        <div className="container">
            <div className="row justify-content-center mt-5">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-header">
                            <h4>Registration</h4>
                        </div>
                        <div className="card-body">
                            <form onSubmit={handleSubmit}>
                                <div className="form-group mb-3">
                                    <label htmlFor="firstName" className="mb-1">
                                        First Name:
                                    </label>
                                    <input
                                        type="text"
                                        id="firstName"
                                        name="firstName"
                                        className="form-control"
                                        value={formData.firstName}
                                        onChange={handleChange}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="lastName" className="mb-1">
                                        Last Name:
                                    </label>
                                    <input
                                        type="text"
                                        id="lastName"
                                        name="lastName"
                                        className="form-control"
                                        value={formData.lastName}
                                        onChange={handleChange}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="email" className="mb-1">
                                        Email:
                                    </label>
                                    <input
                                        type="email"
                                        id="email"
                                        name="email"
                                        className="form-control"
                                        value={formData.email}
                                        onChange={handleChange}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="countryCode" className="mb-1">
                                        Country Code:
                                    </label>
                                    <input
                                        type="text"
                                        id="countryCode"
                                        name="countryCode"
                                        className="form-control"
                                        value={formData.countryCode}
                                        onChange={handleChange}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="mobileNumber" className="mb-1">
                                        Mobile Number:
                                    </label>
                                    <input
                                        type="text"
                                        id="mobileNumber"
                                        name="mobileNumber"
                                        className="form-control"
                                        value={formData.mobileNumber}
                                        onChange={handleChange}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="username" className="mb-1">
                                        Username:
                                    </label>
                                    <input
                                        type="text"
                                        id="username"
                                        name="username"
                                        className="form-control"
                                        value={formData.username}
                                        onChange={handleChange}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="password" className="mb-1">
                                        Password:
                                    </label>
                                    <input
                                        type="password"
                                        id="password"
                                        name="password"
                                        className="form-control"
                                        value={formData.password}
                                        onChange={handleChange}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="gender" className="mb-1">
                                        Gender:
                                    </label>
                                    <select
                                        id="gender"
                                        name="gender"
                                        className="form-control"
                                        value={formData.gender}
                                        onChange={handleChange}
                                    >
                                        <option value="">Select gender</option>
                                        <option value="Male">Male</option>
                                        <option value="Female">Female</option>
                                        <option value="Other">Other</option>
                                    </select>
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="dateOfBirth" className="mb-1">
                                        Date of Birth:
                                    </label>
                                    <input
                                        type="date"
                                        id="dateOfBirth"
                                        name="dateOfBirth"
                                        className="form-control"
                                        value={formData.dateOfBirth}
                                        onChange={handleChange}
                                    />
                                </div>

                                <h5 className="mt-3">Address:</h5>
                                <div className="form-group mb-3">
                                    <label htmlFor="addresses0.line1" className="mb-1">
                                        Line 1:
                                    </label>
                                    <input
                                        type="text"
                                        id="addresses0.line1"
                                        name="addresses0.line1"
                                        className="form-control"
                                        value={formData.addresses[0].line1}
                                        onChange={(e) => handleAddressChange(e, 0, 'line1')}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="addresses0.line2" className="mb-1">
                                        Line 2:
                                    </label>
                                    <input
                                        type="text"
                                        id="addresses0.line2"
                                        name="addresses0.line2"
                                        className="form-control"
                                        value={formData.addresses[0].line2}
                                        onChange={(e) => handleAddressChange(e, 0, 'line2')}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="addresses0.city" className="mb-1">
                                        City:
                                    </label>
                                    <input
                                        type="text"
                                        id="addresses0.city"
                                        name="addresses0.city"
                                        className="form-control"
                                        value={formData.addresses[0].city}
                                        onChange={(e) => handleAddressChange(e, 0, 'city')}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="addresses0.state" className="mb-1">
                                        State:
                                    </label>
                                    <input
                                        type="text"
                                        id="addresses0.state"
                                        name="addresses0.state"
                                        className="form-control"
                                        value={formData.addresses[0].state}
                                        onChange={(e) => handleAddressChange(e, 0, 'state')}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="addresses0.country" className="mb-1">
                                        Country:
                                    </label>
                                    <input
                                        type="text"
                                        id="addresses0.country"
                                        name="addresses0.country"
                                        className="form-control"
                                        value={formData.addresses[0].country}
                                        onChange={(e) => handleAddressChange(e, 0, 'country')}
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="addresses0.pinCode" className="mb-1">
                                        Pin Code:
                                    </label>
                                    <input
                                        type="text"
                                        id="addresses0.pinCode"
                                        name="addresses0.pinCode"
                                        className="form-control"
                                        value={formData.addresses[0].pinCode}
                                        onChange={(e) => handleAddressChange(e, 0, 'pinCode')}
                                    />
                                </div>

                                <div className="d-flex justify-content-end mt-3">
                                    <button type="button" className="btn btn-secondary" onClick={handleReset}>
                                        Reset
                                    </button>
                                    <div className="mx-1"></div>
                                    <button type="submit" className="btn btn-primary">
                                        Register
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <ToastContainer />
        </div>
    );
};

export default Registration;
