/**
 * axios setup to use mock service
 */

import axios from 'axios';

const url = process.env.REACT_APP_API_URL_SECURED ? 'https://localhost:8080' : 'http://localhost:8080';
const axiosServices = axios.create({ baseURL: url });
axiosServices.interceptors.response.use(
    (response) => response,
    (error) => Promise.reject((error.response && error.response.data) || 'Wrong Services')
);

export default axiosServices;
