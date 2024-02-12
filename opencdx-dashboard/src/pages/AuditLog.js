import MainCard from 'ui-component/cards/MainCard';
import Box from '@mui/material/Box';
import DataGrid from 'ui-component/extended/DataGrid';

import {  useEffect, useCallback } from 'react';
import axios from 'axios';


export default function AuditLog() {
    const fetchData = useCallback(async () => {
        try {
            const response = await axios.post('http://localhost:8632/graphql', {
                query: `{
                    getAudit {
                        purposeOfUse
                    }
                }`
            });

            response.data.data?.getAudit.forEach((item) => {
               console.log(item)
            });

        } catch (error) {
            console.error(error);
        }
    }, []);

    useEffect(() => {
        fetchData();
    }, [fetchData]);
    return (
        <MainCard title="Audit Log">
            <Box sx={{ height: 400, width: '100%' }}>
                <DataGrid />
            </Box>
        </MainCard>
    );
}
