import React, { useState } from 'react';
import { useForm } from 'react-hook-form';

import PropTypes from 'prop-types';
import {
    FormControl,
    Grid,
    MenuItem,
    Select,
    InputLabel,
    Input,
    Checkbox,
    OutlinedInput,
    ListItemText,
    Chip,
    Box,
    Autocomplete,
    TextField,
} from '@mui/material';

import { TextArea } from '../ui-components/TextArea';
import { MainCard } from '../ui-components/MainCard';
import Typography from '@mui/material/Typography';
import { categories, observationAttributes } from '../../store/constant';

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250
        }
    }
};

export const ObservationId = ({ index, currentIndex, tab }) => {
    const { register } = useForm();
    
  
   
    const [selectedCategories, setSelectedCategories] = useState([]);

    const handleChange = function (event) {
        const {
            target: { value }
        } = event;
        setSelectedCategories(typeof value === 'string' ? value.split(',') : value);
    };
   

    const ObservationAttributes = ({ filteredAttributes }) => (
        <>
            {filteredAttributes.map((attribute, attrIndex) => (
                <Grid container spacing={2} alignItems="center" key={attrIndex} sx={{ pt: 2 }}>
                    <Grid item xs={12} sm={4} lg={4}>
                        <FormControl fullWidth>
                            <Typography key={attrIndex} variant="h5">
                                Observation.{typeof attribute === 'object' ? attribute.label : attribute}
                            </Typography>
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={4} lg={8}>
                        <FormControl fullWidth>
                            {typeof attribute === 'object' && attribute.options ? (
                                <Autocomplete
                                    multiple
                                    freeSolo
                                    id="tags-filled"
                                    renderOption={(props, option) => (
                                        <li {...props} key={props.id}>
                                            {option}
                                        </li>
                                    )}
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.topic.${attrIndex}}`)}
                                    options={Object.values(attribute.options)}
                                    renderTags={(value, getTagProps) =>
                                        value.map((option, index) =>
                                            <Chip variant="outlined" label={option} {...getTagProps({ index })} />
                                        )
                                    }
                                    renderInput={(params) => (
                                        <TextField {...params} label={typeof attribute === 'object' ? attribute.label : attribute} />
                                    )}
                                />
                            ) : (
                                <Input />
                            )}
                        </FormControl>
                    </Grid>
                </Grid>
            ))}
        </>
    );


    const filteredAttributes = observationAttributes.filter((attr) => selectedCategories.includes(attr.observationCategory));

    return (
        <Grid item xs={12} lg={12}>
            <MainCard>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <FormControl sx={{ width: '100%' }}>
                            <InputLabel id="demo-multiple-checkbox-label">Select Observation Categories</InputLabel>
                            <Select
                                id="observation-categories-multiple-checkbox"
                                multiple
                                value={selectedCategories}
                                onChange={handleChange}
                                input={<OutlinedInput id="select-multiple-chip" label="Chip" />}
                                renderValue={(selected) => (
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                        {selected.map((value) => (
                                            <Chip key={value} label={value} />
                                        ))}
                                    </Box>
                                )}
                                MenuProps={MenuProps}
                            >
                                {categories.map((name) => (
                                    <MenuItem key={name} value={name}>
                                        <Checkbox checked={selectedCategories.indexOf(name) > -1} />
                                        <ListItemText primary={name} />
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                </Grid>
            </MainCard>

            {/* Display filtered attributes based on the selected categories */}
            <ObservationAttributes filteredAttributes={filteredAttributes} />

            <MainCard>
                {/* Observation Topic */}
            </MainCard>
        </Grid>
    );
};
ObservationId.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    tab: PropTypes.string,
    selectedOption: PropTypes.array,
    filteredAttributes: PropTypes.array,
    chips: PropTypes.array,
    handleChipAdd: PropTypes.func,
    handleChipDelete: PropTypes.func,
    handleChange: PropTypes.func,
    selectedCategories: PropTypes.array,
    setSelectedCategories: PropTypes.func,
    value: PropTypes.string,
    setValue: PropTypes.func,
    ObservationAttributes: PropTypes.func,
    filteredAttributes: PropTypes.array
    
};
export default ObservationId;
