import React, { useEffect, forwardRef, useState } from 'react';
import PropTypes from 'prop-types';
import { useForm } from 'react-hook-form';
import Button from '@mui/material/Button';
import ChildWrapper from './ChildWrapper';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import { Grid } from '@mui/material';
import useLocalStorage from '../../utils/useLocalStorage';

const MainWrapper = forwardRef(({ uploadedFile }, ref) => {
    const [anfFormLocal, setAnfFormLocal] = useLocalStorage('anf-form');
    const [showAlert, setShowAlert] = useState(false);

    const defaultValues = {
        test: uploadedFile.item
    };
    const { register, handleSubmit, control, getValues, errors, setValue, reset } = useForm({ defaultValues });

    const handleAlertClose = () => {
        setShowAlert(false);
    };

    useEffect(() => {
        reset({
            test: anfFormLocal.item
        });
    }, [anfFormLocal, reset]);

    const onSubmit = (data) => {
        const formData = JSON.parse(localStorage.getItem('anf-form'));
        formData?.item?.forEach((element, index) => {
            if (element.componentType === '') {
                data.test[index].componentType = element.componentType;
            }
        });

        const item = data.test;
        const markedMainANFStatement = item
            .filter((element) => element.componentType === 'main_anf_statement')
            .map((element) => element.text);

        const updatedItem = item.map((element) => ({
            ...element,
            markedMainANFStatement: markedMainANFStatement
        }));

        localStorage.setItem('anf-form', JSON.stringify({ item: updatedItem }));
        console.log('anf-form', localStorage.getItem('anf-form'));

        const values = Object.keys(localStorage)
            .filter((key) => key.includes('form-v'))
            .map((key) => localStorage.getItem(key));
        if (values.length === 0) {
            localStorage.setItem(
                'form-v1',
                JSON.stringify({
                    'anf-form': JSON.parse(localStorage.getItem('anf-form')),
                    'uploaded-form': JSON.parse(localStorage.getItem('uploaded-form'))
                })
            );
        }
        localStorage.setItem(
            'form-v' + (values.length + 1),
            JSON.stringify({
                'anf-form': JSON.parse(localStorage.getItem('anf-form')),
                'uploaded-form': JSON.parse(localStorage.getItem('uploaded-form'))
            })
        );

        setAnfFormLocal({ item: updatedItem });

        setShowAlert(true);
    };

    return (
        <div ref={ref}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <ChildWrapper {...{ control, register, getValues, setValue, errors, defaultValues }} />
                <Grid sx={{ pt: 2 }}>
                    <Button disableElevation color="primary" size="large" type="submit" variant="outlined">
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
