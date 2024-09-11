import SimpleBody from "../components/SimpleBody.js";
import MainNav from "../components/mainNav.js";
import { Route, Routes, Outlet } from "react-router-dom";

export default function Home() {
    return (
        <div className="Home">
            <MainNav />
            
            <Outlet />
            
        </div>
        
    
    );
}