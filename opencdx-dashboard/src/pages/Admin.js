import axios from 'axios';
import { useState, useEffect } from 'react';
import ErrorPage from 'pages/ErrorPage';

// ==============================|| ELEMENT REFERENCE HOOKS ||============================== //

const Admin = () => {
    const [isValidPage, setIsValidPage] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                await axios.get('https://localhost:8861/admin/wallboard');
                setIsValidPage(true);
            } catch (error) {
                setIsValidPage(false);
                console.log(error.message);
            }
        };

        fetchData();
    }, []);

    const openInNewTab = (url) => {
        const newWindow = window.open(url, '_blank', 'noopener,noreferrer');
        if (newWindow) newWindow.opener = null;
    };

    return <div>{isValidPage ? openInNewTab('https://localhost:8861/admin/wallboard') : <ErrorPage />}</div>;
};
export default Admin;
