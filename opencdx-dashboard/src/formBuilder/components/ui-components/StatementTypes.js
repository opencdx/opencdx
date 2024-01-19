import React, { useCallback, forwardRef, useEffect } from 'react';
import PropTypes from 'prop-types';
import { FormControl, FormControlLabel, Grid, Radio, RadioGroup, Button, Typography, Checkbox } from '@mui/material';
import { Controller } from 'react-hook-form';
import { SubCard } from './SubCard';
import IconRestore from '@mui/icons-material/Restore';

const StatementTypes = forwardRef(({ register, index, control, item }, ref) => {
    const [selectedOption, setSelectedOption] = React.useState('');
    const [selectedMainOption, setSelectedMainOption] = React.useState([]);
    const [selectedAssociatedOption, setSelectedAssociatedOption] = React.useState([]);

    const handleChangeOption = useCallback(async (event, index, setSelectedOption) => {
        await setSelectedOption((prevState) => {
            const newState = Array.isArray(prevState) ? [...prevState] : [];
            newState[index] = event;
            return newState;
        });
    }, []);
    useEffect(() => {
        setSelectedMainOption(item?.componentTypeMain);
        setSelectedAssociatedOption(item?.componentTypeAssociated);
        setSelectedOption(item?.componentType);
    }, [item]);

    const handleChangeMainOption = useCallback(
        (event, index) => {
            handleChangeOption(event, index, setSelectedMainOption);
        },
        [handleChangeOption, setSelectedMainOption]
    );

    const handleChangeAssociatedOption = useCallback(
        (event, index) => {
            handleChangeOption(event, index, setSelectedAssociatedOption);
        },
        [handleChangeOption, setSelectedAssociatedOption]
    );

    const handleRadioChange = (value) => {
        const formData = JSON.parse(localStorage.getItem('anf-form'));
        formData?.item?.map((element, currentIndex) => {
            if (currentIndex === index) {
                formData.item[index].componentType = value;
            }
        });

        localStorage.setItem('anf-form', JSON.stringify({ item: formData.item }));

        setSelectedOption(value);
    };

    const renderList = useCallback(() => {
        switch (selectedOption) {
            case 'main_anf_statement':
                return (
                    <Grid item xs={12} lg={12}>
                        <Typography variant="subtitle2"> Component marked as Main ANF type</Typography>
                    </Grid>
                );
            case 'associated_anf_statement':
                return (
                    <Grid item xs={12} lg={12}>
                        <Typography variant="subtitle2">Select Main Statement for the Associated Statement</Typography>
                        <Grid item xs={12} lg={12} sx={{ display: 'flex', flexDirection: 'column' }}>
                            {item?.markedMainANFStatement &&
                                item?.markedMainANFStatement.map((item, indexItem) => (
                                    <FormControlLabel
                                        key={indexItem}
                                        control={
                                            <Checkbox
                                                {...register(`test.${index}.componentTypeAssociated.${indexItem}`)}
                                                checked={
                                                    !item.componentType && selectedAssociatedOption && selectedAssociatedOption[indexItem]
                                                }
                                                onChange={(event) => handleChangeAssociatedOption(event.target.checked, indexItem)}
                                                color="primary"
                                            />
                                        }
                                        label={item}
                                    />
                                ))}
                        </Grid>
                    </Grid>
                );
            case 'main_statement_questions':
                return (
                    <Grid item xs={12} lg={12}>
                        <Typography variant="subtitle2" sx={{ color: 'red' }}>Pending: Select Main Statement for the Associated Question</Typography>
                        <Grid item xs={12} lg={12} sx={{ display: 'flex', flexDirection: 'column' }}>
                            {item?.markedMainANFStatement &&
                                item?.markedMainANFStatement.map((item, indexItem) => (
                                    <FormControlLabel
                                        key={indexItem}
                                        control={
                                            <Checkbox
                                                {...register(`test.${index}.componentTypeMain.${indexItem}`)}
                                                checked={!item.componentType && selectedMainOption && selectedMainOption[indexItem]}
                                                onChange={(event) => handleChangeMainOption(event.target.checked, indexItem)}
                                                color="primary"
                                            />
                                        }
                                        label={item}
                                    />
                                ))}
                        </Grid>
                    </Grid>
                );
            case 'not_applicable':
                return (
                    <Grid item xs={12} lg={12}>
                        <div>Component marked as non ANF type.</div>
                    </Grid>
                );
            default:
                return null;
        }
    }, [
        selectedOption,
        item?.markedMainANFStatement,
        register,
        index,
        selectedAssociatedOption,
        handleChangeAssociatedOption,
        selectedMainOption,
        handleChangeMainOption
    ]);
    const renderListItem = useCallback(
        () => (
            <Grid container spacing={2} item xs={12} sm={12} lg={12}>
                {renderList()}
            </Grid>
        ),
        [renderList]
    );
    return (
        <Grid item xs={12} lg={12} sx={{ pt: 2 }} ref={ref}>
            <SubCard
                title="Component Type"
                secondary={
                    <Button
                        aria-label="Reset"
                        label="Reset"
                        variant="outlined"
                        size="small"
                        onClick={() => {
                            handleRadioChange('');
                            setSelectedMainOption(false);
                            setSelectedAssociatedOption(false);
                            setSelectedOption('');
                        }}
                    >
                        <IconRestore stroke={1.5} size="20px" /> &nbsp; Reset
                    </Button>
                }
            >
                <FormControl>
                    <Grid item xs={12} lg={12} sx={{ height: 'auto', overflow: 'auto' }}>
                        <Controller
                            control={control}
                            {...register(`test.${index}.componentType`)}
                            render={({ field }) => (
                                <RadioGroup
                                    row
                                    aria-label="componentType"
                                    name="componentType"
                                    {...field}
                                    onChange={(e) => {
                                        field.onChange(e.target.value);
                                        handleRadioChange(e.target.value);
                                        field.value = e.target.value;
                                    }}
                                    value={selectedOption || ''}
                                >
                                    <Grid item xs={12} sm={2} lg={2}>
                                        <FormControlLabel value="main_anf_statement" control={<Radio />} label="Main ANF Statement" />

                                        {selectedOption === 'main_anf_statement' && renderListItem()}
                                    </Grid>
                                    <Grid item xs={12} sm={2} lg={4}>
                                        <FormControlLabel
                                            value="associated_anf_statement"
                                            control={<Radio />}
                                            label="Associated ANF Statement"
                                        />

                                        {selectedOption === 'associated_anf_statement' && item?.markedMainANFStatement && renderListItem()}
                                    </Grid>
                                    <Grid item xs={12} sm={2} lg={4}>
                                        <FormControlLabel
                                            value="main_statement_questions"
                                            control={<Radio />}
                                            label="Main Statement Questions"
                                        />

                                        {selectedOption === 'main_statement_questions' && item?.markedMainANFStatement && renderListItem()}
                                    </Grid>
                                    <Grid item xs={12} sm={2} lg={2}>
                                        <FormControlLabel value="not_applicable" control={<Radio />} label="Not Applicable" />

                                        {selectedOption === 'not_applicable' && renderListItem()}
                                    </Grid>
                                </RadioGroup>
                            )}
                        />
                    </Grid>
                </FormControl>
            </SubCard>
        </Grid>
    );
});
StatementTypes.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    control: PropTypes.object,
    item: PropTypes.object
};

export default StatementTypes;
