import React from 'react';
import PropTypes from 'prop-types';
import { useFieldArray } from 'react-hook-form';
import StatementTypes from './StatementTypes';
import OptionWrapper from './OptionWrapper';
import { ComponentID } from '../TabComponents/ComponentID';
import { AccordianWrapper } from './AccordianWrapper';
import { Grid, FormControl, Select, MenuItem, Typography } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { Controller } from 'react-hook-form';
import { useAnfFormStore } from '../../utils/useAnfFormStore';


const ChildWrapper = React.forwardRef(({ control, register }, ref) => {
    const { formData } = useAnfFormStore();
    const [hideOptions, setHideOptions] = React.useState(true);
    const { fields, remove } = useFieldArray({
        control,
        name: 'item'
    });
    const [rulesets] = React.useState([
        {
            ruleId: '1',
            type: 'Business Rule',
            category: 'Validation',
            description: 'Validate user responses'
        },
        {
            ruleId: '2',
            type: 'Authorization Rule',
            category: 'Access Control',
            description: 'Control access based on user responses'
        }
    ]);
    const theme = useTheme();
    const handleStatementTypeChange = (value) => setHideOptions(value !== 'main_statement_questions');
    return (
        <div className="wrapper" ref={ref}>
            {fields.map((item, index) => (
                <AccordianWrapper
                    key={item.uniqueIdentifier}  // Use a unique identifier instead of index
                    title={`${index + 1}. ${item.text} - ${item.linkId}`}
                    remove={() => remove(index)}
                >
                    <ComponentID {...{ control, register, index }} />
                    <StatementTypes {...{ control, register, index, item }} handleStatementTypeChange={handleStatementTypeChange} />
                    {hideOptions && <OptionWrapper {...{ control, register, index, item }} />}
                </AccordianWrapper>
            ))}

            <Grid
                container
                item
                xs={12}
                lg={12}
                mt={1}
                padding={3}
                alignItems="center"
                sx={{
                    border: '1px solid',
                    borderColor: theme.palette.mode === 'dark' ? theme.palette.dark.light + 15 : theme.palette.grey[200],
                    ':hover': {
                        boxShadow: theme.palette.mode === 'dark' ? '0 2px 14px 0 rgb(33 150 243 / 10%)' : '0 2px 14px 0 rgb(32 40 45 / 8%)'
                    }
                }}
            >
                <Grid item xs={12} sm={3} lg={2}>
                    <Typography variant="h5" gutterBottom>
                        Select a rule
                    </Typography>
                </Grid>
                <Grid item xs={12} sm={9} lg={10}>
                    <FormControl fullWidth>
                        <Controller
                            name={`item.ruleset`}
                            {...register(`item.ruleset`)}
                            control={control}
                            defaultValue={formData ? formData.ruleset : ''}
                            render={({ field }) => (
                                <Select {...field} id={`item.rulesets`} fullWidth variant="outlined" size="small"
                                    // onClick={(e) => setFormData({ ruleset: e.target.value })}
                                >
                                    {rulesets.map((ruleset) => (
                                        <MenuItem key={ruleset.ruleId} value={ruleset.ruleId}>
                                            {ruleset.type} - {ruleset.category} - {ruleset.description}
                                        </MenuItem>
                                    ))}
                                </Select>
                            )}
                        />
                    </FormControl>
                </Grid>
            </Grid>
        </div>
    );
});

ChildWrapper.propTypes = {
    register: PropTypes.func,
    control: PropTypes.object
};

export default ChildWrapper;
