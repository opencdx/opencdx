import MainCard from 'ui-component/cards/MainCard';
import * as React from 'react';
import Box from '@mui/material/Box';
import DataGrid from 'ui-component/extended/DataGrid';

export default function AuditLog() {
    return (
        <MainCard title="Audit Log">
            <Box sx={{ height: 400, width: '100%' }}>
                <DataGrid />
            </Box>
        </MainCard>
    );
}
