import React, { useCallback, forwardRef, useEffect } from 'react';
import PropTypes from 'prop-types';
import { FormControl, FormControlLabel, Grid, Radio, RadioGroup, Button, Typography, Checkbox } from '@mui/material';
import { Controller } from 'react-hook-form';
import { SubCard } from './SubCard';
import IconRestore from '@mui/icons-material/Restore';
import { useAnfFormStore } from '../../utils/useAnfFormStore';
import { statementType } from '../../store/constant';
const StatementTypes = forwardRef(({ register, index, control, item, handleStatementTypeChange }, ref) => {
    const { formData, setFormData } = useAnfFormStore();
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
        formData?.item?.map((element, currentIndex) => {
            if (currentIndex === index) {
                formData.item[index].componentType = value;                
            }
        });
        handleStatementTypeChange(value);
         setFormData({ item: formData.item });
        setSelectedOption(value);

        const markedMainANFStatement = formData.item
            .filter((element) => element.componentType === statementType.MAIN)
            .map((element) => element.text);
        const updatedItem = formData.item.map((element) => ({
            ...element,
            markedMainANFStatement: markedMainANFStatement
        }));
        setFormData({ item: updatedItem });
    };

    const renderList = useCallback(() => {
        switch (selectedOption) {
            case statementType.MAIN:
                return (
                    <Grid item xs={12} lg={12}>
                        <Typography variant="subtitle2"> Component marked as Main ANF type</Typography>
                    </Grid>
                );
            case statementType.ASSOCIATED:
                return (
                    <Grid item xs={12} lg={12}>
                        <Typography variant="subtitle2" sx={{ width: '300px' }}>
                            Select Main Statement for the Associated Statement
                        </Typography>
                        <Grid item xs={12} lg={12} sx={{ display: 'flex', flexDirection: 'column' }}>
                            {item?.markedMainANFStatement &&
                                item?.markedMainANFStatement.map((item, indexItem) => (
                                    <FormControlLabel
                                        key={indexItem}
                                        sx={{ width: '300px' }}
                                        control={
                                            <Checkbox
                                                {...register(`item.${index}.componentTypeAssociated.${indexItem}`)}
                                                checked={
                                                    !item.componentType && selectedAssociatedOption && selectedAssociatedOption[indexItem]
                                                }
                                                onChange={(event) => handleChangeAssociatedOption(event.target.checked, indexItem)}
                                                color="primary"
                                                size="small"
                                            />
                                        }
                                        label={item}
                                    />
                                ))}
                        </Grid>
                    </Grid>
                );
            case statementType.USER_QUESTION:
                return (
                    <Grid item xs={12} lg={12}>
                        <Typography variant="subtitle2"> User Provided Data</Typography>
                    </Grid>
                );
            case statementType.NOT_APPLICABLE:
                return (
                    <Grid item xs={12} lg={12}>
                        <Typography variant="subtitle2"> Component marked as non ANF type.</Typography>
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
                darkTitle
                secondary={
                    <Button
                        aria-label="Reset"
                        label="Reset"
                        variant="contained"
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
                            {...register(`item.${index}.anfStatementType`)}
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
                                    <Grid item xs={12} sm={2} lg={2} sx={{ pl: 3 }}>
                                        <FormControlLabel value="ANF_STATEMENT_TYPE_MAIN" control={<Radio />} label="Main ANF Statement" />

                                        {selectedOption === statementType.MAIN && renderListItem()}
                                    </Grid>
                                    <Grid item xs={12} sm={2} lg={4} sx={{ pl: 3 }}>
                                        <FormControlLabel
                                            value="ANF_STATEMENT_TYPE_ASSOCIATED"
                                            control={<Radio />}
                                            label="Associated ANF Statement"
                                        />

                                        {selectedOption === statementType.ASSOCIATED && item?.markedMainANFStatement && renderListItem()}
                                    </Grid>
                                    <Grid item xs={12} sm={2} lg={4} sx={{ pl: 3 }}>
                                        <FormControlLabel value="ANF_STATEMENT_TYPE_USER_QUESTION" control={<Radio />} label="User Question" />

                                        {selectedOption === statementType.USER_QUESTION && item?.markedMainANFStatement && renderListItem()}
                                    </Grid>
                                    <Grid item xs={12} sm={2} lg={2} sx={{ pl: 3 }}>
                                        <FormControlLabel value="ANF_STATEMENT_TYPE_NOT_APPLICABLE" control={<Radio />} label="Not Applicable" />

                                        {selectedOption === statementType.NOT_APPLICABLE && renderListItem()}
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
    item: PropTypes.object,
    handleStatementTypeChange: PropTypes.func
};

export default StatementTypes;
