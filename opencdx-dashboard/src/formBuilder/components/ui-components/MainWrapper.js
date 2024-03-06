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
        localStorage.setItem('anf-form', JSON.stringify(anf));

        anf.updated.item.forEach((element, index) => {
            let componentType = element.componentType;
            let Type = element.anfOperatorType === 'Equal' ? 'ANF_OPERATOR_TYPE_EQUAL' :'ANF_OPERATOR_TYPE_NOT_EQUAL';
            let Value = element.operatorValue;
            console.log('element', element);

            if (Array.isArray(element.item)) {
                element.item.forEach((connector, index) => {
                
                    connector.anfStatementConnector[0].anfStatementType = componentType;
                    connector.anfStatementConnector[0].anfOperatorType = Type;
                    connector.anfStatementConnector[0].operatorValue = Value;
                    if (connector.anfStatementConnector[0].anfStatement.authors)
                        connector.anfStatementConnector[0].anfStatement.authors = [connector.anfStatementConnector[0].anfStatement.authors, connector.anfStatementConnector[0].anfStatement.authors]

                });
            }
            delete element.componentType;
            delete element.anfOperatorType;
            delete element.operatorValue; 
            delete element.markedMainANFStatement;
            delete element.selectedCategories;
            delete element.componentId;
            delete element.answerTextValue;

        });
        const updatedData = {
            questionnaire: anf.updated   // The data to be sent to the server    
        };

        const saveQuestionnare = async () => {
            const response = await axios.post(
                'https://localhost:8080/questionnaire/questionnaire',
                {
                    questionnaire: anf.updated
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('serviceToken')}`
                    }
                }
            );
            console.log(response.data);
        };
        saveQuestionnare();


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
