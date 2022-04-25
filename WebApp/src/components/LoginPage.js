import React from "react";
import "../modules/LoginPage-module.css";
import { useNavigate } from "react-router-dom";

function LoginPage() {
  let navigate = useNavigate();
  const routeChange = () => {
    let path = `/after`;
    navigate(path);
  };

  return (
    <div className="appp">
      <form>
        <div className="form-inner">
          <h2>Login</h2>
          <div className="form-group">
            <label htmlFor="name">Name:</label>
            <input type="text" name="name" id="name" />
          </div>
          <div className="form-group">
            <label htmlFor="email">Email:</label>
            <input type="email" name="email" id="email" />
          </div>
          <div className="form-group">
            <label className="password">Password</label>
            <input type="password" name="password" id="password" />
          </div>
          <button className="btn" onClick={routeChange}>
            Login
          </button>
        </div>
      </form>
    </div>
  );
}

export default LoginPage;
