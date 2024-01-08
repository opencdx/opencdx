import React, { useState, useEffect } from 'react';
import MainCard from 'ui-component/cards/MainCard';
import { Grid, TextField, MenuItem } from '@mui/material';
import { gridSpacing } from 'utils/store/constant';
import ReactJsonViewCompare from 'react-json-view-compare';

const FormViewer = () => {
    const [form, setForm] = useState([]);
    const [oldData, setOldData] = useState({});
    const [newData, setNewData] = useState({});

    useEffect(() => {
        const values = Object.keys(localStorage)
            .filter(key => key.includes('form-v'))
            .sort();
        setForm(values);
    }, []);

    const handleOldDataChange = (e) => {
        setOldData(JSON.parse(localStorage.getItem(e.target.value)));
    };

    const handleNewDataChange = (e) => {
        setNewData(JSON.parse(localStorage.getItem(e.target.value)));
    };

    return (
        <MainCard title="Compare Forms">
            <Grid container spacing={gridSpacing}>
                <Grid item xs={12} sm={6}>
                    <TextField
                        select
                        label="Select a form"
                        variant="outlined"
                        fullWidth
                        onChange={handleOldDataChange}
                    >
                        {form?.map((template, index) => (
                            <MenuItem key={index} value={template}>
                                {template}
                            </MenuItem>
                        ))}
                    </TextField>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        select
                        label="Select a form"
                        variant="outlined"
                        fullWidth
                        onChange={handleNewDataChange}
                    >
                        {form?.map((template, index) => (
                            <MenuItem key={index} value={template}>
                                {template}
                            </MenuItem>
                        ))}
                    </TextField>
                </Grid>
            </Grid>
            <ReactJsonViewCompare oldData={oldData} newData={newData} />
        </MainCard>
    );
};

export default FormViewer;
