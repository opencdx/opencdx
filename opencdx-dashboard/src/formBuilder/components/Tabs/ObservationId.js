import React, { useState } from 'react';
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
    OutlinedInput,
    ListItemText
} from '@mui/material';
import { TextArea } from '../ui-components/TextArea';
import { MainCard } from '../ui-components/MainCard';
import { IconRestore } from '@tabler/icons-react';
import Typography from '@mui/material/Typography';

const observationAttributes = [
    // General Attributes
    { observationCategory: 'General', label: 'identifier', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'General', label: 'code', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'General', label: 'subject', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'General', label: 'focus', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'General', label: 'encounter', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },

    // Timing Attributes
    { observationCategory: 'Timing', label: 'effective_x_', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'Timing', label: 'issued', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },

    // Context Attributes
    { observationCategory: 'Context', label: 'performer', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'Context', label: 'encounter', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },

    // Value and Interpretation Attributes
    {
        observationCategory: 'Value and Interpretation',
        label: 'value_x_',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Value and Interpretation',
        label: 'dataAbsentReason',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Value and Interpretation',
        label: 'interpretation',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },

    // Additional Information Attributes
    {
        observationCategory: 'Additional Information',
        label: 'note',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'bodySite',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'bodyStructure',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'method',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'specimen',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'device',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },

    // Reference Range Attributes
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.low',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.high',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.normalValue',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.type',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.appliesTo',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.age',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.text',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },

    // Relationship Attributes
    { observationCategory: 'Relationship', label: 'hasMember', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'Relationship', label: 'derivedFrom', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'Relationship', label: 'component', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    {
        observationCategory: 'Relationship',
        label: 'component.code',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Relationship',
        label: 'component.value_x_',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Relationship',
        label: 'component.dataAbsentReason',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Relationship',
        label: 'component.interpretation',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Relationship',
        label: 'component.referenceRange',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    }
];

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

export const ObservationId = React.forwardRef(({ register, index, currentIndex, tab }) => {
    const [selectedCategories, setSelectedCategories] = useState([]);
    const categories = [
        'General',
        'Timing',
        'Context',
        'Value and Interpretation',
        'Additional Information',
        'Reference Range',
        'Relationship'
    ];

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
                            <InputLabel>{typeof attribute === 'object' ? attribute.label : attribute}</InputLabel>
                            {typeof attribute === 'object' && attribute.options ? (
                                <Select>
                                    {Object.entries(attribute.options).map(([key, value]) => (
                                        <MenuItem key={key} value={key}>
                                            {value}
                                        </MenuItem>
                                    ))}
                                </Select>
                            ) : (
                                <Input />
                            )}
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={2} lg={2}>
                        <FormControl fullWidth>
                            <FormControlLabel control={<Checkbox />} label="Add to Topic" />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={1} lg={1}>
                        <Button aria-label="Reset" label="Reset" onClick="">
                            <IconRestore stroke={1.5} size="20px" />
                            Reset
                        </Button>
                    </Grid>
                </Grid>
            ))}
        </>
    );
    //TODO: Remove this code if not needed
    // const handleCategoryChange = (category) => {
    //   const updatedCategories = selectedCategories.includes(category)
    //     ? selectedCategories.filter((cat) => cat !== category)
    //     : [...selectedCategories, category];

    //   setSelectedCategories(updatedCategories);
    // };

    const handleChange = (event) => {
        const {
            target: { value }
        } = event;
        setSelectedCategories(typeof value === 'string' ? value.split(',') : value);
    };
    const filteredAttributes = observationAttributes.filter((attr) => selectedCategories.includes(attr.observationCategory));

    return (
        <Grid item xs={12} lg={12}>
            <MainCard>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <FormControl sx={{ width: '100%' }}>
                            <InputLabel id="demo-multiple-checkbox-label">Select Observation Categories</InputLabel>
                            <Select
                                labelId="observation-categories-multiple-checkbox-label"
                                id="observation-categories-multiple-checkbox"
                                multiple
                                value={selectedCategories}
                                onChange={handleChange}
                                input={<OutlinedInput label="Select Observation Categories" />}
                                renderValue={(selected) => selected.join(', ')}
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

                        {/*
              //TODO: Remove this code if not needed
              <>
              <InputLabel horizontal>Select Observation Categories:</InputLabel>

                {categories.map((category, index) => (
                            <Grid container spacing={2} alignItems="center">

                  <FormControlLabel
                    key={index}
                    control={
                      <Checkbox
                        checked={selectedCategories.includes(category)}
                        onChange={() => handleCategoryChange(category)}
                      />
                    }
                    label={category}
                  />
                  </Grid>
                ))}
              </> */}
                    </Grid>
                </Grid>
            </MainCard>

            {/* Display filtered attributes based on the selected categories */}
            <ObservationAttributes filteredAttributes={filteredAttributes} />

            <MainCard>
                {/* Observation Topic */}
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={2} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Calculated Topic</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={8}>
                                <TextArea
                                    minRows={3}
                                    maxRows={10}
                                    placeholder="Calculated Topic"
                                    style={{ width: '100%' }}
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.topic`)}
                                    fullWidth
                                />
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
ObservationId.propTypes = {
    register: PropTypes.func.isRequired,
    index: PropTypes.number.isRequired,
    currentIndex: PropTypes.number.isRequired,
    tab: PropTypes.string.isRequired,
    selectedOption: PropTypes.array.isRequired,
    handleChange: PropTypes.func.isRequired,
    filteredAttributes: PropTypes.array.isRequired
};
export default ObservationId;
