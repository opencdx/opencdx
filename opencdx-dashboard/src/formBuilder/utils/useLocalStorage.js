import { useState, useEffect } from 'react';

export default function useLocalStorage(key, defaultValue) {
    const [value, setValue] = useState(() => {
        const storedValue = localStorage.getItem(key);
        return storedValue === null ? defaultValue : JSON.parse(storedValue);
    });

    useEffect(() => {
        const listener = (e) => {
            if (e.storageArea === localStorage && e.key === key) {
                setValue((prevValue) => {
                    const newValue = e.newValue ? JSON.parse(e.newValue) : null;
                    return typeof newValue === 'function' ? newValue(prevValue) : newValue;
                });
            }
        };
        window.addEventListener('storage', listener);

        return () => {
            window.removeEventListener('storage', listener);
        };
    }, [key]);

    const setValueInLocalStorage = (newValue) => {
        setValue((prevValue) => {
            const result = typeof newValue === 'function' ? newValue(prevValue) : newValue;
            localStorage.setItem(key, JSON.stringify(result));
            return result;
        });
    };

    return [value, setValueInLocalStorage];
}
