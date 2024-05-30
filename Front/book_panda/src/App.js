import './App.css';
import Header  from './components/Header';
import SearchBar from './components/SearchBar';
import Nav from './components/Nav';
import Main from './pages/Main';

function App() {
  return (
    <div className="App">
        <Header></Header>
        <SearchBar></SearchBar>
        <Nav></Nav>
        
        <Main></Main>
    </div>
  );
}

export default App;
