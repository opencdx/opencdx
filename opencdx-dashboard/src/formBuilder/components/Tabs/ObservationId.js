import React, { useState } from 'react';
import { useForm } from 'react-hook-form';

import PropTypes from 'prop-types';
import {
    FormControl,
    FormControlLabel,
    Grid,
    MenuItem,
    Select,
    InputLabel,
    Input,
    Checkbox,
    Button,
    Chip,
    TextField
} from '@mui/material';

import { Controller } from 'react-hook-form';
import { SystemVariables } from '../ui-components/SystemVariables';

import { MainCard } from '../ui-components/MainCard';
import RestoreIcon from '@mui/icons-material/Restore';
import Typography from '@mui/material/Typography';

import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { observationAttributes, categories } from '../../store/constant';


export const ObservationId = ({ currentIndex, index, control, getValues }) => {
    const ListItem = styled('li')(({ theme }) => ({
        margin: theme.spacing(0.5),
    }));
    const [chipData, setChipData] = React.useState(categories);

    const { register } = useForm();

    const [selectedCategories, setSelectedCategories] = useState([chipData.filter((data) => data.selected).map((data) => data.label)]);

    const handleChange = function (data) {
        return function () {
            setChipData((chips) => chips.map((chip) => {
                if (chip.key === data.key) {
                    if (chip.selected) {
                        setSelectedCategories(selectedCategories.filter((category) => category !== data.label));
                    } else {
                        setSelectedCategories([...selectedCategories, data.label]);
                    }
                    return { ...chip, selected: !chip.selected };
                }
                return chip;
            }));
        };

    };

    const ObservationAttributes = ({ filteredAttributes }) => (
        <>
            {filteredAttributes.map((attribute, index) => (
                <Grid container spacing={2} alignItems="center" key={index} sx={{ pt: 2 }}>
                    <Grid item xs={12} sm={4} lg={4}>
                        <FormControl fullWidth>
                            <Typography key={index} variant="h5">
                                Observation.{typeof attribute === 'object' ? attribute.label : attribute}
                            </Typography>
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={4} lg={5}>
                        <FormControl fullWidth>
                            {typeof attribute === 'object' && attribute.options ? (
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${attribute.label}`)}
                                    fullWidth
                                    placeholder={attribute.label}
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
                                        key={index} // Add key prop to avoid rendering error
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
                    <InputLabel style={{ fontWeight: 'bold' }}
                        horizontal>Observation Type</InputLabel>
                </Grid>
                <Grid container spacing={2} alignItems="center">

                    <Grid item xs={12} sm={12} lg={12}>
                        <FormControl fullWidth>
                            <Controller
                                name={`test.${index}.item.${currentIndex}.observationType`}
                                control={control}
                                defaultValue={10}
                                render={({ field }) => (
                                    <Select {...field} id={`test.${index}.item.${currentIndex}.type`} >
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
                                m: 0,
                            }}
                            component="ul"
                        >
                            {chipData.map((data) => {
                                let icon;
                                let color;

                                if (data.selected) {
                                    icon = <CheckCircleIcon />;
                                    color = 'primary';
                                } else {
                                    color = 'default';
                                }

                                return (
                                    <ListItem key={data.key}>
                                        <Chip
                                            icon={icon}
                                            label={data.label}
                                            onClick={handleChange(data)}
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
    selectedOption: PropTypes.array,
    filteredAttributes: PropTypes.array
};
export default ObservationId;
