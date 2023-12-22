import axios from 'axios'
import { useState,useEffect } from 'react';
import ErrorPage from 'views/pages/ErrorPage';

// ==============================|| ELEMENT REFERENCE HOOKS ||============================== //

const Discovery = () => {
    const [isValidPage, setIsValidPage] = useState(false);

    useEffect(() => {
        axios.get('http://localhost:8761/')
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
            openInNewTab("https://localhost:8861/admin/wallboard");
        }
    }, [isValidPage]);

    return (
        <div>
            {isValidPage ? (
                <></>
            ) : (
                <ErrorPage />
            )}
        </div>
    );
};

export default Discovery;

