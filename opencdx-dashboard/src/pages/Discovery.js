import axios from 'utils/axios';
import { useState, useEffect } from 'react';
import ErrorPage from 'pages/ErrorPage';

// ==============================|| ELEMENT REFERENCE HOOKS ||============================== //

const Discovery = () => {
    const [isValidPage, setIsValidPage] = useState(false);

    useEffect(() => {
        const url = process.env.REACT_APP_API_URL_SECURED ? 'https://localhost:8761/' : 'http://localhost:8761/';
        axios
            .get(url)
            .then(() => {
                setIsValidPage(true);
            })
            .catch(() => {
                setIsValidPage(false);
            });
    }, []);

    const openInNewTab = (url) => {
        const newWindow = window.open(url, '_blank', 'noopener,noreferrer');
        if (newWindow) newWindow.opener = null;
    };

    useEffect(() => {
        if (isValidPage) {
            openInNewTab(url);
        }
    }, [isValidPage]);

    return <div>{isValidPage ? <></> : <ErrorPage />}</div>;
};

export default Discovery;
