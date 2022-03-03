import LoginPage from "./components/LoginPage";
import Temp from "./components/Temp";

import { BrowserRouter as Router, Link, Route, Routes } from "react-router-dom";
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/after" element={<Temp />} />
      </Routes>
    </Router>
  );
}

export default App;
