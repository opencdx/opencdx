import React from 'react';
import PropTypes from 'prop-types';
import { TextField, Button, FormControl, Typography, Grid, MenuItem, Select } from '@mui/material';
import { Controller } from 'react-hook-form';
import { useFieldArray } from 'react-hook-form';
import { AccordianWrapper } from './AccordianWrapper';
import { CustomTabs } from './CustomTabs';

const OptionWrapper = React.forwardRef(({ control, register, index, item }) => {
    const [showValueField, setShowValueField] = React.useState(false);
    const { fields, append, remove, getValues } = useFieldArray({
        control,
        name: `test.${index}.items`
    });

    const getOptions = (type) => {
        const choices = [];
        switch (type) {
            case 'boolean':
                return ['Yes', 'No', 'Not Answered'];
            case 'choice':
                for (let i = 0; i < item.answerOption.length; i++) {
                    choices.push(item.answerOption[i].valueCoding.display);
                }
                choices.push('Not Answered');
                return choices;
            case 'integer':
                return ['Any value', 'Value', 'Not Answered'];
            case 'logical':
                return ['=', '!=', 'Equal', 'Not Equal'];
            default:
                return ['Not Answered'];
        }
    };
    return (
        <Grid item xs={12} lg={12} sx={{ pt: 2 }}>
            <Grid container spacing={2} alignItems="center">
                <Grid item xs={12} sm={3} lg={4} sx={{ pt: 2 }}>
                    <Typography variant="subtitle2">Operator</Typography>
                    <FormControl fullWidth sx={{ pt: 2 }}>
                        <Controller
                            fullWidth
                            {...register(`test.${index}.item.${0}.operatorValue`, { value: '' })}
                            control={control}
                            render={({ field }) => (
                                <Select {...field} id={`test.${index}.item.${0}.operatorValue`}>
                                    {getOptions('logical').map((option, index) => (
                                        <MenuItem key={index} value={option}>
                                            {option}
                                        </MenuItem>
                                    ))}
                                </Select>
                            )}
                        />
                    </FormControl>
                </Grid>
                <Grid item xs={12} sm={3} lg={4} sx={{ pt: 2 }}>
                    <Typography variant="subtitle2">Answer</Typography>

                    <FormControl fullWidth sx={{ pt: 2 }}>
                        <Controller
                            fullWidth
                            {...register(`test.${index}.item.${0}.answerValue`)}
                            control={control}
                            render={({ field }) => (
                                <Select
                                    {...field}
                                    onChange={(e) => {
                                        field.onChange(e.target.value);
                                        if (e.target.value === 'Value') {
                                            setShowValueField(true);
                                        } else {
                                            setShowValueField(false);
                                        }
                                    }}
                                    id={`test.${index}.item.${0}.answerField`}
                                >
                                    {getOptions(item.type).map((option, index) => (
                                        <MenuItem key={index} value={option}>
                                            {option}
                                        </MenuItem>
                                    ))}
                                </Select>
                            )}
                        />
                    </FormControl>
                </Grid>

                {showValueField && (
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: 2 }}>
                        <Typography variant="subtitle2">Value</Typography>

                        <FormControl fullWidth>
                            <Controller
                                fullWidth
                                {...register(`test.${index}.item.${0}.answerTextValue`)}
                                control={control}
                                defaultValue=""
                                render={({ field: { onChange, value } }) => (
                                    <TextField
                                        fullWidth
                                        id={`test.${index}.item.${0}.answerTextValue`}
                                        key={index}
                                        type="number"
                                        InputProps={{
                                            inputProps: { min: 0 }
                                        }}
                                        value={value}
                                        onChange={({ target: { value } }) => {
                                            onChange(value);
                                        }}
                                        placeholder="Enter Answer"
                                    />
                                )}
                            />
                        </FormControl>
                    </Grid>
                )}
            </Grid>
            <Grid container spacing={1} xs={12} lg={12} sx={{ pt: 2 }}>
                <Grid item xs={12} lg={12}>
                    <AccordianWrapper title="Anf Statement">
                        <CustomTabs currentIndex={0} {...{ control, register, index, item, getValues }} />
                    </AccordianWrapper>
                </Grid>
            </Grid>
            {/* * Dynamic Fields * */}

            {fields.map(({ id }, i) => (
                <div style={{ display: 'flex', flexDirection: 'column' }} key={id}>
                    <Grid container>
                        <Grid item xs={12} sm={3} lg={4} sx={{ pt: 2, pr: 2 }}>
                            <Typography variant="subtitle2">Operator</Typography>
                            <FormControl fullWidth sx={{ pt: 2 }}>
                                <Controller
                                    fullWidth
                                    {...register(`test.${index}.item.${i + 1}.operatorValue`, { value: '' })}
                                    control={control}
                                    render={({ field }) => (
                                        <Select {...field} id={`test.${index}.item.${i + 1}.operatorValue`}>
                                            {getOptions('logical').map((option, index) => (
                                                <MenuItem key={index} value={option}>
                                                    {option}
                                                </MenuItem>
                                            ))}
                                        </Select>
                                    )}
                                />
                            </FormControl>
                        </Grid>

                        <Grid item xs={12} sm={3} lg={4} sx={{ pt: 2 }}>
                            <Typography variant="subtitle2">Answer</Typography>

                            <FormControl fullWidth sx={{ pt: 2 }}>
                                <Controller
                                    fullWidth
                                    {...register(`test.${index}.item.${i + 1}.answerValue`)}
                                    control={control}
                                    render={({ field }) => (
                                        <Select
                                            {...field}
                                            onChange={(e) => {
                                                field.onChange(e.target.value);
                                            }}
                                            id={`test.${index}.item.${i}.answerField`}
                                        >
                                            {getOptions(item.type).map((option, index) => (
                                                <MenuItem key={index} value={option}>
                                                    {option}
                                                </MenuItem>
                                            ))}
                                        </Select>
                                    )}
                                />
                            </FormControl>
                        </Grid>
                    </Grid>
                    <Grid style={{ display: 'flex', alignItems: 'baseline' }}>
                        <Grid item xs={12} lg={4} sx={{ p: 2 }}>
                            <AccordianWrapper title="Anf Statement">
                                <CustomTabs currentIndex={i} {...{ control, register, index, item, getValues }} />
                            </AccordianWrapper>
                        </Grid>
                        <Button variant="outlined" type="button" onClick={() => remove(index)}>
                            Remove
                        </Button>
                    </Grid>
                </div>
            ))}
            {item.type !== 'integer' && (
                <Grid sx={{ pt: 2, textAlign: 'center' }}>
                    <Button disableElevation color="primary" size="large" type="button" onClick={() => append({})} variant="outlined">
                        + Add ANF Statement
                    </Button>
                </Grid>
            )}
        </Grid>
    );
});
OptionWrapper.propTypes = {
    control: PropTypes.any,
    register: PropTypes.any,
    index: PropTypes.number,
    item: PropTypes.any
};

export default OptionWrapper;