import axios from "axios";
import { API_BASE_URL, ACCESS_TOKEN } from '../constants';


export default axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-type": "application/json",
    "Authorization" : "Bearer " +  localStorage.getItem(ACCESS_TOKEN)
  }
});