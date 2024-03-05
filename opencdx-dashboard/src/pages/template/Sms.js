import { useState, useEffect } from 'react';
import { Grid, Stack, TextField, MenuItem } from '@mui/material';
import MainCard from 'ui-component/cards/MainCard';
import { useTheme } from '@mui/material/styles';
import axios from 'utils/axios';

// third party
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { gridSpacing } from 'utils/store/constant';

// ==============================|| Admin PAGE ||============================== //

const Sms = () => {
    const [text, setText] = useState('');
    const [SmsTemplates, setSmsTemplates] = useState([]);
    useEffect(() => {
        const fetchSmsList = async () => {
            const response = await axios.post(
                '/communications/sms/list',
                {
                    "pagination": {
                    "pageSize": 30,
                    "sortAscending": true
                  }
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('serviceToken')}`
                    }
                }
            );
            setSmsTemplates(response.data);
        };
        fetchSmsList();
    }, []);

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
        const template = SmsTemplates.templates.find((template) => template.templateType === evt.target.value);
        setText(template.message);
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
                                {SmsTemplates?.templates?.map((template) => (
                                    <MenuItem key={template.templateType} value={template.templateType}>
                                        {template.templateType}
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
