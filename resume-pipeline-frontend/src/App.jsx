import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import { Link } from 'react-router-dom'

// Router
import { Routes, Route } from 'react-router-dom'
import HomePage from './pages/HomePage'
import LoginPage from './pages/LoginPage'
import UploadPage from './pages/UploadPage'

function App() {


  return (
    <>
      <h1>Testing Frontend</h1>
      <nav>
        <Link to="/">Home</Link>
        <Link to="/login">Login</Link>
        <Link to="/upload">Upload your resume</Link>
      </nav>

      <Routes>
        <Route path="/" element={<HomePage/>} />
        <Route path="/login" element={<LoginPage/>} />
        <Route path="/upload" element={<UploadPage/>} />
      </Routes>
    </>
  )
}

export default App
