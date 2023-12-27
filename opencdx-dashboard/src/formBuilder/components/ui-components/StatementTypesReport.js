import React from 'react';
import { Grid, Checkbox } from '@mui/material';
import { MainCard } from './MainCard';

import { Table, TableBody, TableCell, TableRow } from '@mui/material';

import { capitalizeANFTitle } from '../../utils/StringManulpations';

const StatementTypesReport = React.forwardRef((props, ref) => {
    const [showReport, setShowReport] = React.useState(false);

    const handleCheckboxChange = (event) => {
        setShowReport(event.target.checked);
    };

    const renderList = () => {
        if (showReport && localStorage.getItem('anf-form')) {
            const storedJson = localStorage.getItem('anf-form');
            const questionnaireData = storedJson ? JSON.parse(storedJson) : null;

            const groupedItems = {};

            questionnaireData.item.forEach((item) => {
                const type = item.componentType || 'unassigned';
                if (!groupedItems[type]) {
                    groupedItems[type] = [];
                }
                groupedItems[type].push(item);
            });

            return (
                <div>
                    <Table>
                        <TableBody>
                            {Object.entries(groupedItems).map(([type, items], index) => (
                                <React.Fragment key={index}>
                                    <TableRow>
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
                </div>
            );
        }
        return null;
    };

    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12} sm={3} lg={3}>
                        <Checkbox checked={showReport} onChange={handleCheckboxChange} color="primary" 
                            label="Show Report"
                        />
                    </Grid>
                </Grid>
                {renderList()}
            </MainCard>
        </Grid>
    );
});

export default StatementTypesReport;
