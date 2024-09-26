import SimpleBody from "../components/SimpleBody.js";
import MainNav from "../components/MainNav.js";
import { Route, Routes, Outlet } from "react-router-dom";

export default function Home() {
  return (
    <div class="Home">
      <MainNav />

      <Outlet />
    </div>
  );
}
