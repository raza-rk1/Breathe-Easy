import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap CSS
import { clearTokens, getJwtToken } from '../service/apiService';

const Navigation = () => {
    const jwtToken = getJwtToken();
    var isLoggedIn = false;
    if(jwtToken === null || jwtToken.trim === ''){
         isLoggedIn = false;
    }else{
        isLoggedIn = true;
    }

    const navigate = useNavigate();
    
    const handleSignOut = () => {
        console.log('Sign out clicked');
        clearTokens();
        navigate('/login');
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container">
            <ul className="navbar-nav ms-auto">
                    {isLoggedIn ? (
                        <>
                            <li className="nav-item">
                                <Link className="nav-link" to="/dashboard">Dashboard</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/aqi-check">AQI Check</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/aqi-forecast">AQI Forecast</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/aqi-history">AQI History</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/aqi-prediction">AQI Prediction</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/change-password">Change Password</Link>
                            </li>
                            <li className="nav-item">
                            <Link className="nav-link" to="/login" onClick={handleSignOut}>Sign Out</Link>
                            </li>
                        </>
                    ) : (
                        <>
                            <li className="nav-item">
                                <Link className="nav-link" to="/">Home</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/registration">Register</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/login">Login</Link>
                            </li>
                        </>
                    )}
                </ul>
            </div>
        </nav>
    );
};

export default Navigation;
