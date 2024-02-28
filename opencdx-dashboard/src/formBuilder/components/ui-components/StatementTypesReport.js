import React from 'react';
import { Grid, Checkbox } from '@mui/material';
import { MainCard } from './MainCard';

import { Table, TableBody, TableCell, TableRow, FormControlLabel, TableContainer } from '@mui/material';

import { capitalizeANFTitle } from '../../utils/StringManulpations';
import { useAnfFormStore } from '../../utils/useAnfFormStore';


const StatementTypesReport = React.forwardRef((props, ref) => {
    const { formData } = useAnfFormStore();
    const [showReport, setShowReport] = React.useState(false);

    const handleCheckboxChange = (event) => {
        setShowReport(event.target.checked);
    };

    const renderList = () => {
        if (showReport && formData) {
            const groupedItems = {};

            formData?.item.forEach((item) => {
                const type = item.componentType || 'unassigned';
                if (!groupedItems[type]) {
                    groupedItems[type] = [];
                }
                groupedItems[type].push(item);
            });

            return (
                <TableContainer>
                    <Table style={{ width: '100%', border: '1px solid #e0e0e0', borderCollapse: 'collapse' }}>
                        <TableBody>
                            {Object.entries(groupedItems).map(([type, items], index) => (
                                <React.Fragment key={index}>
                                    <TableRow hover>
                                        <TableCell>
                                            List of components [<b>{capitalizeANFTitle(type.replace(/_/g, ' '))}</b>]
                                        </TableCell>
                                        <TableCell>
                                            {items.map((item) => (
                                                <li key={item.componentId}>
                                                    {item.componentId} - {item.text}
                                                </li>
                                            ))}
                                        </TableCell>
                                    </TableRow>
                                </React.Fragment>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            );
        }
        return null;
    };

    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12} sm={3} lg={3}>
                        <FormControlLabel
                            control={<Checkbox checked={showReport} onChange={handleCheckboxChange} color="primary" id='show-report'/>}
                            label={'Show Report'}
                            sx={{ color: 'primary.main', fontWeight: 600 }}
                        />
                    </Grid>
                </Grid>
                {renderList()}
            </MainCard>
        </Grid>
    );
});

export default StatementTypesReport;
