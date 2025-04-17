import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import "./App.css";

function HomePage() {
  return (
    <div className="App">
      <header className="App-header">
        <h1 className="text-4xl font-bold">Welcome to BrandMap</h1>
        <p className="mt-4">Share and Explore thoughts and opinions on your favorite brands.</p>
      </header>

      <div className="mt-8 flex justify-around">
        <Link to="/submit" className="px-6 py-3 bg-blue-500 text-white rounded-lg">
          Submit Opinion
        </Link>
        <Link to="/map" className="px-6 py-3 bg-green-500 text-white rounded-lg">
          View Brand Map
        </Link>
      </div>
    </div>
  );
}

function SubmitOpinionPage() {
  return (
    <div>
      <h1 className="text-center text-3xl mt-8">Submit Your Opinion</h1>
      {/* Form to submit opinions  */}
    </div>
  );
}

function BrandMapPage() {
  return (
    <div>
      <h1 className="text-center text-3xl mt-8">Brand Mind Map</h1>
      {/* Placeholder for Brand Map */}
    </div>
  );
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/submit" element={<SubmitOpinionPage />} />
        <Route path="/map" element={<BrandMapPage />} />
      </Routes>
    </Router>
  );
}

export default App;
