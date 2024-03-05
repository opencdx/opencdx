import React, { useEffect, forwardRef, useState } from 'react';
import PropTypes from 'prop-types';
import { useForm } from 'react-hook-form';
import Button from '@mui/material/Button';
import ChildWrapper from './ChildWrapper';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import { Grid } from '@mui/material';
import { useAnfFormStore } from '../../utils/useAnfFormStore';
import axios from 'axios';


const MainWrapper = forwardRef(({ uploadedFile }, ref) => {
    const { formData } = useAnfFormStore();

    const [showAlert, setShowAlert] = useState(false);

    const defaultValues = {
        item: uploadedFile.item
    };
    const { register, handleSubmit, control, getValues, errors, setValue, reset } = useForm({ defaultValues });

    const handleAlertClose = () => {
        setShowAlert(false);
    };

    useEffect(() => {
        reset({
            item: formData.item
        });
    }, [formData, reset]);

    const onSubmit = (data) => {
        setShowAlert(true);
        const anf = JSON.parse(localStorage.getItem('anf-form')); // Parse the JSON string to an object
        anf.updated.item = data.item;
        anf.updated.ruleset = data.item.ruleset;
        anf.updated.item.forEach((element, index) => {
            let componentType = element.componentType;
            let anfOperatorType = element.anfOperatorType;
            let operatorValue = element.operatorValue;
            console.log('element', element);

            if (Array.isArray(element.item)) {
                element.item.forEach((connector, index) => {
                    connector.anfStatementConnector[0].anfStatement.anfStatementType = componentType;
                    connector.anfStatementConnector[0].anfStatement.anfOperatorType = anfOperatorType;
                    connector.anfStatementConnector[0].anfStatement.operatorValue = operatorValue;
                });
            }
        });
        const updatedData = {
            questionnaire: anf.updated   // The data to be sent to the server    
        };

        const saveQuestionnare = async () => {
            const response = await axios.post(
                'questionnaire/questionnaire',
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('serviceToken')}`
                    },
                    data: updatedData
                }
            );
            console.log(response.data);
        };
        saveQuestionnare();

        localStorage.setItem('anf-form', JSON.stringify(anf));
        // setFiles({ item: data.test, ruleset: data.test.rulesets });
    };

    return (
        <div ref={ref}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <ChildWrapper {...{ control, register, getValues, setValue, errors, defaultValues }} />
                <Grid sx={{ pt: 2 }}>
                    <Button disableElevation color="primary" size="large" type="submit" variant="contained">
                        Save
                    </Button>
                </Grid>
            </form>

            <Snackbar open={showAlert} autoHideDuration={3000} onClose={handleAlertClose}>
                <MuiAlert onClose={handleAlertClose} severity="success" sx={{ width: '100%' }}>
                    Saved successfully!
                </MuiAlert>
            </Snackbar>
        </div>
    );
});

MainWrapper.propTypes = {
    uploadedFile: PropTypes.object
};

export default MainWrapper;
