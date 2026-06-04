import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap CSS

const Footer = () => {
    return (
        <footer className="bg-dark text-light text-center py-3 mt-3">
            <div className="container">
                <p>Breath Easy helps us determine the air quality outside.</p>
                <p>© {new Date().getFullYear()} Breath Easy</p>
            </div>
        </footer>
    );
};

export default Footer;
