import { useState } from "react";
import { ToastContainer, toast } from 'react-toastify';
import { login } from "../service/apiService";
import { useNavigate } from "react-router-dom";

const Login = () => {

    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
  
    const handleUsernameChange = (e) => {
      setUsername(e.target.value);
    };
  
    const handlePasswordChange = (e) => {
      setPassword(e.target.value);
    };
  
    const handleSubmit = async (e) => {
        e.preventDefault();
        let isValid = true;
    
        if (username.trim() === '') {
          toast.error('Username is required');
          isValid = false;
        } else if (username.trim().length < 4) {
          toast.error('Username must be at least 4 characters');
          isValid = false;
        }
    
        if (password.trim() === '') {
          toast.error('Password is required');
          isValid = false;
        } else if (password.trim().length < 4) {
          toast.error('Password must be at least 4 characters');
          isValid = false;
        }
    
        if (isValid) {
          console.log('Username:', username);
          console.log('Password:', password);
          try{
            const loginData = { username, password };
            const response = await login(loginData);
            navigate('/dashboard');
            toast.success('Login successful');
            console.log('Login response:', response);
          }catch(error){
            toast.error('Login failed');
            console.error('Login error:', error);
          }

        }
    }

    const handleReset = () =>{
        setUsername('');
        setPassword('');
    };

    return (
        <div className="container">
          <div className="row justify-content-center mt-5">
            <div className="col-md-6">
              <div className="card">
                <div className="card-header">
                  <h4>Login</h4>
                </div>
                <div className="card-body">
                  <form onSubmit={handleSubmit}>
                    <div className="form-group">
                      <label htmlFor="username" className="mb-1">
                        Username:
                      </label>
                      <input
                        type="text"
                        id="username"
                        className="form-control"
                        value={username}
                        onChange={handleUsernameChange}
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="password" className="mb-1">
                        Password:
                      </label>
                      <input
                        type="password"
                        id="password"
                        className="form-control"
                        value={password}
                        onChange={handlePasswordChange}
                      />
                    </div>
                    <div className="d-flex justify-content-end mt-3">
                  
                 
                  <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={handleReset}
                  >
                    Reset
                  </button>
                  <div className="mx-1"></div>
                  <button type="submit" className="btn btn-primary mr-2">
                    Login
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

export default Login;
