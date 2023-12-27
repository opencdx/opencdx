import { useState } from 'react';
import { Grid, Stack, TextField, MenuItem } from '@mui/material';
import MainCard from 'ui-component/cards/MainCard';
import { useTheme } from '@mui/material/styles';

// third party
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { gridSpacing } from 'utils/store/constant';

// ==============================|| Admin PAGE ||============================== //

const Sms = () => {
    const [text, setText] = useState(
        `<div class="font-sans  w-full bg-gray-600 color: #fff; mt-[8px] pt-2 pb-2 pr-20 pl-20 flex items-center"><div></div><div display: flex-column; justify-content: center;"><h1>SAFE</h1></div></div><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">\${provider_name}:</p><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">A case has been assigned to you, encounter \${encounter_id}, \${patient_name}. Please review the findings, contact the client, document that in the medical SOAP note, then mark the case complete; but if no answer, send Sms to jennifer@safehealth.me with encounter# asserting failed to reach client with date/time for each of 3 attempts.</span></p><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Please document duration of encounter, findings, diagnosis, sites affected, allergies, agreement and issuance of the appropriate treatment prescriptions, advice and/or referrals, and advised follow up.</span></p><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">\${safely_test_result_instructions}</p><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Please contact me if you have any questions or if you need me to reassign the case.  Thank you.</span></p><p style="margin-bottom: 0;"><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Best regards,</p><div style="display: flex;"><div style="margin: auto 0"><p style="margin: 0;"><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Justin D. Pearlman MD PhD</p><p style="margin: 0;"><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Medical Director</p><p style="margin: 0;"><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Safe Health, PMC</p></div><div></div></div>`
    );

    const SmsTemplates = [
        {
            id: 1,
            name: 'Safely - Notify Provider',
            body: `<div style="font-family: Roboto, Helvetica, Arial, sans-serif; width: 100%; background-color: #474747; color: #fff; margin: 0 0 .5rem 0; padding: 2px 20px; display: flex; align-items: center;"><div><img src="https://i1.wp.com/safehealth.me/wp-content/uploads/sites/2/2020/09/safe-emblem.png?fit=200%2C200&amp;ssl=1" alt="Safe Health Logo" width="50" height="50" align="top"></div><div display: flex-column; justify-content: center;"><h1>SAFE</h1></div></div><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">\${provider_name}:</p><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">A case has been assigned to you, encounter \${encounter_id}, \${patient_name}. Please review the findings, contact the client, document that in the medical SOAP note, then mark the case complete; but if no answer, send Sms to jennifer@safehealth.me with encounter# asserting failed to reach client with date/time for each of 3 attempts.</span></p><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Please document duration of encounter, findings, diagnosis, sites affected, allergies, agreement and issuance of the appropriate treatment prescriptions, advice and/or referrals, and advised follow up.</span></p><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">\${safely_test_result_instructions}</p><p><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Please contact me if you have any questions or if you need me to reassign the case.  Thank you.</span></p><p style="margin-bottom: 0;"><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Best regards,</p><div style="display: flex;"><div style="margin: auto 0"><p style="margin: 0;"><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Justin D. Pearlman MD PhD</p><p style="margin: 0;"><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Medical Director</p><p style="margin: 0;"><span style="font-family: Avenir, -apple-system, BlinkMacSystemFont, Helvetica, Arial, sans-serif;">Safe Health, PMC</p></div><div><img src="https://i1.wp.com/safehealth.me/wp-content/uploads/sites/2/2020/09/safe-emblem.png?fit=200%2C200&amp;ssl=1" alt="Safe Health Logo" width="75" height="75" align="top"></div></div>`
        }
    ];
    const [emailParams, setEmailParams] = useState({
        encounter_id: '',
        provider_name: '',
        patient_name: '',
        provider_email: ''
    });
    const handleEmailParamsChange = (evt) => {
        const updatedFieldText = {
            ...emailParams,
            [evt.target.id]: evt.target.value
        };
        setEmailParams(updatedFieldText);
    };
    const [template, setTemplate] = useState(1);

    const handleChange = (value) => {
        setText(value);
    };
    const handleTemplateChange = (evt) => {
        setTemplate(evt.target.value);
    };

    const theme = useTheme();

    return (
        <MainCard>
            <Grid container spacing={gridSpacing}>
                <Grid item xs={12}>
                    <Stack
                        spacing={gridSpacing}
                        sx={{
                            '& .quill': {
                                bgcolor: theme.palette.mode === 'dark' ? 'dark.main' : 'grey.50',
                                borderRadius: '12px',
                                '& .ql-toolbar': {
                                    bgcolor: theme.palette.mode === 'dark' ? 'dark.light' : 'grey.100',
                                    borderColor: theme.palette.mode === 'dark' ? theme.palette.dark.light + 20 : 'primary.light',
                                    borderTopLeftRadius: '12px',
                                    borderTopRightRadius: '12px'
                                },
                                '& .ql-container': {
                                    borderColor:
                                        theme.palette.mode === 'dark' ? `${theme.palette.dark.light + 20} !important` : 'primary.light',
                                    borderBottomLeftRadius: '12px',
                                    borderBottomRightRadius: '12px',
                                    '& .ql-editor': {
                                        minHeight: 135
                                    }
                                }
                            }
                        }}
                    >
                        <Grid item xs={12} md={6}>
                            <TextField
                                id="template-selection"
                                select
                                label="Select a template..."
                                value={template}
                                onChange={handleTemplateChange}
                                variant="outlined"
                                style={{ width: '50ch' }}
                            >
                                {SmsTemplates?.map((template) => (
                                    <MenuItem key={template.id} value={template.id}>
                                        {template.name}
                                    </MenuItem>
                                ))}
                            </TextField>
                        </Grid>
                        <Grid container spacing={2}>
                            <Grid item xs={12} md={6} lg={3}>
                                <TextField
                                    id="encounter_id"
                                    label="Encounter Id"
                                    value={emailParams.encounter_id}
                                    onChange={handleEmailParamsChange}
                                    variant="outlined"
                                    style={{ width: '90%' }}
                                />
                            </Grid>
                            <Grid item xs={12} md={6} lg={3}>
                                <TextField
                                    id="provider_name"
                                    label="Provider Name"
                                    value={emailParams.provider_name}
                                    onChange={handleEmailParamsChange}
                                    variant="outlined"
                                    style={{ width: '90%' }}
                                />
                            </Grid>
                            <Grid item xs={12} md={6} lg={3}>
                                <TextField
                                    id="patient_name"
                                    label="Patient Last Name"
                                    value={emailParams.patient_name}
                                    onChange={handleEmailParamsChange}
                                    variant="outlined"
                                    style={{ width: '90%' }}
                                />
                            </Grid>
                            <Grid item xs={12} md={6} lg={3}>
                                <TextField
                                    id="provider_email"
                                    label="Provider Email Address"
                                    value={emailParams.providerEmail}
                                    onChange={handleEmailParamsChange}
                                    variant="outlined"
                                    style={{ width: '90%' }}
                                />
                            </Grid>
                        </Grid>
                        <ReactQuill value={text} onChange={handleChange} />{' '}
                    </Stack>
                </Grid>
            </Grid>
        </MainCard>
    );
};

export default Sms;
