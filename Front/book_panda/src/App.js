import './App.css';
import Header  from './components/Header';
import SearchBar from './components/SearchBar';
import Nav from './components/Nav';
import Main from './pages/Main';
import AdminPage from './pages/AdminPage';
import Category from './components/Category';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
     
        <Header></Header>
        <SearchBar></SearchBar>
        <Nav></Nav>
        <Routes>
          <Route path="/" element={<Main/>}></Route>
          <Route path="/admin" element={<AdminPage/>}>
                <Route path="category" element={<Category/>}/>
                <Route path="user" element={<p>member</p>}/>
          </Route>
        </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
