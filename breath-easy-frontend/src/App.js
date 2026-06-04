import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Registration from './components/Registration';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import Banner from './components/Banner';
import Navigation from './components/Navigation';
import AirQualityCheck from './components/AirQualityCheck';
import AirQualityForecast from './components/AirQualityForecast';
import AirQualityHistory from './components/AirQualityHistory';
import AirQualityPrediction from './components/AirQualityPrediction';
import ChangePassword from './components/ChangePassword';
import Footer from './components/Footer';

const App = () => {
    return (
        <Router>
            <div>
                <Banner />
                <Navigation />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/registration" element={<Registration />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/aqi-check" element={<AirQualityCheck />} />
                    <Route path="/aqi-forecast" element={<AirQualityForecast />} />
                    <Route path="/aqi-history" element={<AirQualityHistory />} />
                    <Route path="/aqi-prediction" element={<AirQualityPrediction />} />
                    <Route path="/change-password" element={<ChangePassword />} />
                    {/* Add more routes here as needed */}
                </Routes>
                <Footer/> 
            </div>
        </Router>
    );
};

export default App;
