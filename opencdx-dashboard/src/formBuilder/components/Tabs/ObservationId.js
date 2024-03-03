import React, { useState, useEffect } from 'react';

import PropTypes from 'prop-types';
import { FormControl, FormControlLabel, Grid, MenuItem, Select, InputLabel, Input, Checkbox, Button, Chip, TextField } from '@mui/material';

import { Controller } from 'react-hook-form';
import { SystemVariables } from '../ui-components/SystemVariables';

import { MainCard } from '../ui-components/MainCard';
import RestoreIcon from '@mui/icons-material/Restore';
import Typography from '@mui/material/Typography';

import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { observationAttributes, categories } from '../../store/constant';
import { useAnfFormStore } from '../../utils/useAnfFormStore';

export const ObservationId = ({ currentIndex, index, control, getValues, register }) => {
    const ListItem = styled('li')(({ theme }) => ({
        margin: theme.spacing(0.5)
    }));
    const [chipData, setChipData] = React.useState([]);
    const [selectedCategories, setSelectedCategories] = useState([]);
    const { formData, setFormData } = useAnfFormStore();

    useEffect(() => {
        if (formData && formData.item) {
            const currentCategories = formData.item[index]?.selectedCategories || [];

            const newChipData = categories.map((data, index) => ({
                key: index,
                label: data.label,
                selected: currentCategories.includes(data.label)
            }));

            setChipData(newChipData);

            const newSelectedCategories = currentCategories.length > 0
                ? currentCategories
                : newChipData.filter((data) => data.selected).map((data) => data.label);

            setSelectedCategories(newSelectedCategories);
        }
    }, []); // Run once after component mounts

    // Function to handle chip click
    const handleChipClick = (data) => () => {
        setFormData((prevFormData) => {
            const currentCategories = prevFormData.item[index]?.selectedCategories || [];
            const updatedCategories = currentCategories.includes(data.label)
                ? currentCategories.filter((category) => category !== data.label)
                : [...currentCategories, data.label];

            const newFormData = { ...prevFormData };
            newFormData.item[index] = { ...newFormData.item[index], selectedCategories: updatedCategories };

            return newFormData;
        });

        setChipData((prevChipData) =>
            prevChipData.map((chip) => ({
                ...chip,
                selected: chip.key === data.key ? !chip.selected : chip.selected
            }))
        );

        setSelectedCategories((prevSelectedCategories) => {
            const updatedCategories = prevSelectedCategories.includes(data.label)
                ? prevSelectedCategories.filter((category) => category !== data.label)
                : [...prevSelectedCategories, data.label];

            return updatedCategories;
        });
    };

    // Function to handle textbox change
    const handleTextboxChange = (event) => {

        const { name, value } = event.target;
        const updatedFormData = { ...formData };
        updatedFormData.item[index].item[currentIndex][name] = value;
        setFormData(updatedFormData);
    };
 
    const ObservationAttributes = ({ filteredAttributes }) => (
        <>
            {filteredAttributes.map((attribute, i) => (
                <Grid container spacing={2} alignItems="center" key={`${index}-${i}`} sx={{ pt: 2 }}>
                    <Grid item xs={12} sm={4} lg={4}>
                        <FormControl fullWidth>
                            <Typography variant="h5">Observation.{typeof attribute === 'object' ? attribute.label : attribute}</Typography>
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={4} lg={5}>
                        <FormControl fullWidth>
                            {typeof attribute === 'object' && attribute.options ? (
                                <TextField
                                    {...register(`item.${index}.item.${currentIndex}.${attribute.label}${i}`)}
                                    fullWidth
                                    placeholder={attribute.label}
                                    value={formData.item[index]?.item[currentIndex][attribute.label + i] || ''}
                                    onChange={handleTextboxChange}
                                    name={`${attribute.label}${i}`} // Ensure name attribute is unique
                                />
                            ) : (
                                <Input />
                            )}
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={2} lg={2}>
                        <FormControl fullWidth>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        key={`${index}-${i}`} // Updated key for uniqueness
                                        name={attribute.label}
                                        color="primary"
                                        value={attribute.label}
                                    />
                                }
                                label="Add to Topic"
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={1} lg={1}>
                        <Button aria-label="Reset" label="Reset" variant="contained" size="small">
                            <RestoreIcon stroke={1.5} size="20px" />
                            Reset
                        </Button>
                    </Grid>
                </Grid>
            ))}
        </>
    );

    const filteredAttributes = observationAttributes.filter((attr) => selectedCategories.includes(attr.observationCategory));

    return (
        <Grid item xs={12} lg={12}>
            <SystemVariables index={index} currentIndex={currentIndex} tab="observation" getValues={getValues} />
            <MainCard>
                <Grid item xs={12} sm={12} lg={12} sx={{ paddingBottom: 2 }}>
                    <InputLabel style={{ fontWeight: 'bold' }} horizontal>
                        Observation Type
                    </InputLabel>
                </Grid>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12} sm={12} lg={12}>
                        <FormControl fullWidth>
                            <Controller
                                name={`item.${index}.item.${currentIndex}.observationType`}
                                control={control}
                                defaultValue={10}
                                render={({ field }) => (
                                    <Select {...field} id={`item.${index}.item.${currentIndex}.type`}>
                                        <MenuItem value={10}>Observation procedure</MenuItem>
                                    </Select>
                                )}
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        <Paper
                            sx={{
                                display: 'flex',
                                justifyContent: 'center',
                                flexWrap: 'wrap',
                                listStyle: 'none',
                                backgroundColor: '#f8fafc',
                                p: 0.5,
                                m: 0
                            }}
                            component="ul"
                        >
                            {chipData.map((data) => {
                                const icon = data.selected && <CheckCircleIcon />;
                                const color = data.selected ? 'primary' : 'default';

                                return (
                                    <ListItem key={data.key}>
                                        <Chip
                                            icon={icon}
                                            label={data.label}
                                            onClick={handleChipClick(data)}
                                            color={color}
                                            size="small"
                                            clickable
                                        />
                                    </ListItem>
                                );
                            })}
                        </Paper>
                    </Grid>
                </Grid>
            </MainCard>

            <ObservationAttributes filteredAttributes={filteredAttributes} />
        </Grid>
    );
};
ObservationId.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    tab: PropTypes.string,
    control: PropTypes.object,
    getValues: PropTypes.func,
    filteredAttributes: PropTypes.array
};
export default ObservationId;
